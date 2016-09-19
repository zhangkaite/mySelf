package com.ttmv.filetomysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import com.ttmv.dbcp.DbcpBean;
import com.ttmv.dbcp.DbcpDao;
import com.ttmv.entity.BrokerageConsume;
import com.ttmv.entity.BrokerageRecharge;
import com.ttmv.entity.TBConsume;
import com.ttmv.entity.TBRecharge;
import com.ttmv.entity.TQConsume;
import com.ttmv.entity.TQRecharge;
import com.ttmv.util.ApplicationResource;
import com.ttmv.util.TextUtils;

public class TestFx {
	public static void main(String[] args) throws Exception {
		readFileByLines("F:\\20150906.txt", CommonEntity.class);
	}
	/**
	 * 读取文件
	 */
	public static void readFileByLines(String fileName, Class<CommonEntity> o) throws Exception {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				CommonEntity commonEntity = TextUtils.parseJson(tempString, o);
				DbcpBean dbcpBean = new DbcpBean();
				DbcpDao dao = dbcpBean.getDbcpDao();
				String serviceName=commonEntity.getService();
				if ("tBRecharge".equals(serviceName)) {
					try {
						insComEnData(commonEntity, TBRecharge.class, commonEntity.getService(), dao);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("tQRecharge".equals(serviceName)) {
					try {
						insComEnData(commonEntity, TQRecharge.class, commonEntity.getService(), dao);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("brokerageRecharge".equals(serviceName)) {
					try {
						insComEnData(commonEntity, BrokerageRecharge.class, commonEntity.getService(), dao);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("tBConsume".equals(serviceName)) {
					try {
						insComEnData(commonEntity, TBConsume.class, commonEntity.getService(), dao);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("tQConsume".equals(serviceName)) {
					try {
						insComEnData(commonEntity, TQConsume.class, commonEntity.getService(), dao);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("brokerageConsume".equals(serviceName)) {
					try {
						insComEnData(commonEntity, BrokerageConsume.class, commonEntity.getService(), dao);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static int insComEnData(CommonEntity commonEntity, Class<?> c, String tableName, DbcpDao dbcpDao)
			throws ClassNotFoundException {
		String reqID = commonEntity.getReqID();
		String timeStamp = commonEntity.getTimeStamp();
		String tradeType = String.valueOf(commonEntity.getTradeType());
		String platfrom = commonEntity.getPlatfrom();
		LinkedHashMap data = (LinkedHashMap) commonEntity.getReqData();
		System.out.println("数据要插入的库:" + commonEntity.getService());
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
			// JSONObject jo = new JSONObject(data);
			Object[] obj = new Object[felds.length + 3];
			obj[0] = reqID;
			obj[1] = tradeType;
			obj[2] = platfrom;
			int num = 3;
			for (Field field : felds) {
				String value = "";
				try {
					value = String.valueOf(data.get(field.getName()));
				} catch (Exception e) {
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
				System.out.println("数据插入失败："+data);
				// dealError(data, errorPath);
			}
			return flag;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String errorPath = ApplicationResource.getValue("excFile");
			System.out.println("数据插入失败："+data);
			// dealError(data, errorPath);
			e.printStackTrace();
		}

		return 0;
	}

}
