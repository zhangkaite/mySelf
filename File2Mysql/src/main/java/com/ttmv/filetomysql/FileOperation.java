package com.ttmv.filetomysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.ttmv.dbcp.DbcpDao;
import com.ttmv.entity.BrokerageConsume;
import com.ttmv.entity.BrokerageRecharge;
import com.ttmv.entity.CommonEntity;
import com.ttmv.entity.TBConsume;
import com.ttmv.entity.TBRecharge;
import com.ttmv.entity.TQConsume;
import com.ttmv.entity.TQRecharge;
import com.ttmv.util.ApplicationResource;

@SuppressWarnings("unchecked")
public class FileOperation {
	private DbcpDao dbcpDao;
	public int realNum = 0;

	public FileOperation(DbcpDao dbcpDao) {
		this.dbcpDao = dbcpDao;
	}

	/**
	 * tBRecharge tQRecharge brokerageRecharge //佣金兑换 tBConsume tQConsume brokerageConsume //佣金提现
	 */
	@SuppressWarnings("rawtypes")
	public static final Map map = new HashMap();
	static {
		map.put("tBRecharge", "tBRecharge");
		map.put("tQRecharge", "tQRecharge");
		map.put("brokerageRecharge", "brokerageRecharge");
		map.put("tBConsume", "tBConsume");
		map.put("tQConsume", "tQConsume");
		map.put("brokerageConsume", "brokerageConsume");
	}

	/**
	 * 
	 * @param startRow
	 *            文件从上次读取的位置读取文件，文件存储的信息应该有两个；一个是上次读取的文件名 第二个是文件读取的行数
	 *            如果这次读取的文件名称和存储的文件名称不相同，那么文件应该从第一行读取，否则继续上次的位置+1读取内容
	 * @param flagFile
	 *            记录文件存放在服务期的位置
	 * @param filePath
	 *            要读取的目标文件的存放位置
	 * @return
	 * @throws JSONException
	 */
	public void fileRead(String filePath, String flagFile) throws JSONException {
		/**
		 * 读取标志文件里面文件的起始位置和文件路径
		 */
		File fFile = new File(flagFile);
		BufferedReader flagReader = null;
		// 文件读取的位置
		String startRow = "";
		// 上次读取文件的文件路径
		String fileName = "";
		try {
			flagReader = new BufferedReader(new FileReader(fFile));
			String temp = null;
			while ((temp = flagReader.readLine()) != null) {
				String[] data = temp.split(";");
				startRow = data[0];
				fileName = data[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				flagReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 如果标志文件里面没有内容，则表示是第一次读取文件,读取的起始位置应该从0开始
		if ("".equals(startRow) || "".equals(fileName)) {
			startRow = "0";
		}
		// 如果读取的文件和标志文件里面读取的文件路径不一致，则代表读取的文件为新文件，需要判断旧的文件是否已完成所有的读写
		if (!filePath.equals(fileName) && !"".equals(fileName)) {
			// 判断旧文件是否已完成读写
			boolean flag = isFinished(fileName, startRow);
			if (flag) {
				// 旧文件已读写完成，直接读取新文件
				readCurrentFile(filePath, "0", flagFile, "1");
				return;
			} else {
				// 读取旧文件
				readCurrentFile(fileName, startRow, flagFile, "0");
				// 读取新文件
				readCurrentFile(filePath, "0", flagFile, "1");
				return;
			}
		}
		// 记录文件和读取的文件相同
		readCurrentFile(filePath, startRow, flagFile, "1");

	}

	/**
	 * 判断旧文件是否已完成文件读写
	 */

	public boolean isFinished(String oldFile, String startRow) {
		int num = 0;
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(oldFile);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				num++;
			}
			if (Integer.parseInt(startRow) == num) {
				return true;
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sc != null) {
				sc.close();
			}
		}

		return false;
	}

	/**
	 * 
	 * @param filePath
	 *            要读取的文件的路径
	 * @param startRow
	 *            读取的文件的起始行
	 * @param flagFile
	 *            读完记录写入指定的文件中
	 * @param type
	 *            0表示旧文件 1表示新文件
	 * @throws JSONException
	 */
	public void readCurrentFile(String filePath, String startRow, String flagFile, String type) throws JSONException {
		int lineNum = Integer.parseInt(startRow);
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			String tempString = null;
			int num = 0;
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, "UTF-8");
			// 从上次文件读取的位置读取下一行数据
			while (sc.hasNext()) {
				tempString = (String) sc.next();
				if (null != tempString) {
					if (num <= lineNum && lineNum != 0) {
						num++;
						continue;
					}
					// 添加判断读取的内容是否为需要的内容
					realNum = getRealData(tempString, num);
					// 需要的真正的内容
					if (num == realNum && realNum != 0) {
						// 将json串解析成json对象
						try {
							JSONObject jo = new JSONObject(tempString);
							String serviceName = jo.getString("service");
							// commonEntity = (CommonEntity) JsonUtil.getObjectFromJson(tempString, CommonEntity.class);
							dealCommonEntity(serviceName, jo);
						} catch (Exception e) {
							// json转成成对象失败
							String errorPath = ApplicationResource.getValue("excFile");
							dealError(tempString, errorPath);
							e.printStackTrace();
						}

					}
					// 处理完一行数据，文件读取的位置+1
					num++;
				}

			}
			// 修改文件读取的位置
			startRow = String.valueOf(num);
			// 读取文件类型为新文件
			if ("1".equals(type)) {
				String content = startRow + ";" + filePath;
				write2File(content, flagFile);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sc != null) {
				sc.close();
			}
		}

	}

	/**
	 * 向文件写入内容，已有的内容会清除
	 * 
	 * @throws FileNotFoundException
	 */
	public void write2File(String content, String flagFile) {
		try {
			FileOutputStream fo = new FileOutputStream(flagFile);
			fo.write(content.getBytes());
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取数据
	 */
	public int getRealData(String content, int currenRow) {
		if (content.startsWith("=") && "收到请求数据".equals(content.substring(4, 10))) {
			return currenRow + 1;
		}
		return realNum;
	}

	/**
	 * 传递获取的请求实体，对实体类进行操作
	 * 
	 * @throws Exception
	 */
	public boolean dealCommonEntity(String serviceName, JSONObject jsonData) throws Exception {

		// String serviceName = commonEntity.getService();
		if (map.containsKey(serviceName)) {
			CommonEntity commonEntity = new CommonEntity();
			commonEntity.setPlatfrom(jsonData.getString("platfrom"));
			commonEntity.setReqData(jsonData.getString("reqData"));
			commonEntity.setReqID(jsonData.getString("reqID"));
			commonEntity.setTimeStamp(jsonData.getString("timeStamp"));
			commonEntity.setTradeType(jsonData.getString("tradeType"));
			// 设定表名称和serviceName名称一致
			if ("tBRecharge".equals(serviceName)) {
				try {
					insComEnData(commonEntity, TBRecharge.class, serviceName);
					return true;

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if ("tQRecharge".equals(serviceName)) {
				try {
					insComEnData(commonEntity, TQRecharge.class, serviceName);
					return true;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("brokerageRecharge".equals(serviceName)) {
				try {
					insComEnData(commonEntity, BrokerageRecharge.class, serviceName);
					return true;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("tBConsume".equals(serviceName)) {
				try {
					insComEnData(commonEntity, TBConsume.class, serviceName);
					return true;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("tQConsume".equals(serviceName)) {
				try {
					insComEnData(commonEntity, TQConsume.class, serviceName);
					return true;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("brokerageConsume".equals(serviceName)) {
				try {
					insComEnData(commonEntity, BrokerageConsume.class, serviceName);
					return true;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	/**
	 * 将获取的数据插入对应的表
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */

	public int insComEnData(CommonEntity commonEntity, Class<?> c, String tableName) throws ClassNotFoundException {
		String reqID = commonEntity.getReqID();
		String timeStamp = commonEntity.getTimeStamp();
		String tradeType = String.valueOf(commonEntity.getTradeType());
		String platfrom = commonEntity.getPlatfrom();
		String data = commonEntity.getReqData();
		System.out.println("要处理的json数据:" + data);
		Class<?> ca = null;
		ca = Class.forName(c.getName());
		// 获取类的所有属性字段
		Field[] felds = ca.getDeclaredFields();
		String colParam = "reqID,tradeType,platfrom,";
		String valueParam = "?,?,?,";
		for (Field field : felds) {
			colParam += field.getName() + ",";
			valueParam += "?,";
		}
		// 插入的数据列名字拼接
		colParam = colParam.substring(0, colParam.length() - 1);
		// 插入的数据参数拼接
		valueParam = valueParam.substring(0, valueParam.length() - 1);
		try {
			String sql = "insert into " + tableName.toLowerCase() + "(" + colParam + ") values(" + valueParam + ")";
			// 拼接参数数据,data为json数据
			JSONObject jo = new JSONObject(data);
			Object[] obj = new Object[felds.length + 3];
			obj[0] = reqID;
			obj[1] = tradeType;
			obj[2] = platfrom;
			int num = 3;
			for (Field field : felds) {
				String value = "";
				try {
					value = jo.getString(field.getName());
				} catch (JSONException e) {
					value = "";
					e.printStackTrace();
				}

				// 将时间戳转换成正常的时间
				if (field.getName().equals("time")) {
					Long time = new Long(timeStamp + "000");
					SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					value = sdfs.format(new Date(Long.parseLong(String.valueOf(time))));
				}
				obj[num] = value;
				num++;
			}
			int flag = dbcpDao.insertData(sql, obj);
			// 如果数据插入失败
			if (flag != 1) {
				String errorPath = ApplicationResource.getValue("excFile");
				dealError(data, errorPath);
			}
			return flag;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String errorPath = ApplicationResource.getValue("excFile");
			dealError(data, errorPath);
			e.printStackTrace();
		}

		return 0;
	}

	/***
	 * 根据serviceName判断当前的请求是否为需要的请求，如果是则对传递的数据进行处理，录入数据库 tBRecharge tQRecharge brokerageRecharge //佣金兑换 tBConsume
	 * tQConsume brokerageConsume //佣金提现
	 */

	public boolean dealData(String serviceName, String reqData) {
		// 先判断serviceName是否在定义的map里面
		if (map.containsKey(serviceName)) {
			// 设定表名称和serviceName名称一致
			if ("tBRecharge".equals(serviceName)) {
				try {
					insertData(reqData, TBRecharge.class, serviceName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if ("tQRecharge".equals(serviceName)) {
				try {
					insertData(reqData, TQRecharge.class, serviceName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("brokerageRecharge".equals(serviceName)) {
				try {
					insertData(reqData, BrokerageRecharge.class, serviceName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("tBConsume".equals(serviceName)) {
				try {
					insertData(reqData, TBConsume.class, serviceName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("tQConsume".equals(serviceName)) {
				try {
					insertData(reqData, TQConsume.class, serviceName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ("brokerageConsume".equals(serviceName)) {
				try {
					insertData(reqData, BrokerageConsume.class, serviceName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 将data数据录入数据库
	 * 
	 * @param data
	 * @param c
	 *            可以接收任意的泛型对象
	 * @param tableName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public int insertData(String data, Class<?> c, String tableName) throws ClassNotFoundException {
		System.out.println("要处理的json数据:" + data);
		System.out.println("要插入的数据库表名：" + tableName);
		Class<?> ca = null;
		ca = Class.forName(c.getName());
		// 获取类的所有属性字段
		Field[] felds = ca.getDeclaredFields();
		String colParam = "";
		String valueParam = "";
		for (Field field : felds) {
			colParam += field.getName() + ",";
			valueParam += "?,";
		}
		// 插入的数据列名字拼接
		colParam = colParam.substring(0, colParam.length() - 1);
		// 插入的数据参数拼接
		valueParam = valueParam.substring(0, valueParam.length() - 1);
		try {
			String sql = "insert into " + tableName.toLowerCase() + "(" + colParam + ") values(" + valueParam + ")";
			// 拼接参数数据,data为json数据
			JSONObject jo = new JSONObject(data);
			Object[] obj = new Object[felds.length];
			int num = 0;
			for (Field field : felds) {
				String value = jo.getString(field.getName());
				// 将时间戳转换成正常的时间
				if (field.getName().equals("time")) {
					Long timeStamp = new Long(value + "000");
					SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					value = sdfs.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
				}
				obj[num] = value;
				num++;
			}
			int flag = dbcpDao.insertData(sql, obj);
			// 如果数据插入失败
			if (flag != 1) {
				String errorPath = ApplicationResource.getValue("excFile");
				dealError(data, errorPath);
			}
			return flag;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String errorPath = ApplicationResource.getValue("excFile");
			dealError(data, errorPath);
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * 数据库插入失败的操作
	 * 
	 * @param data
	 * @param errFilePath
	 */
	public void dealError(String content, String errFilePath) {
		FileWriter writer = null;
		try {

			writer = new FileWriter(errFilePath, true);
			writer.write(content);
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
