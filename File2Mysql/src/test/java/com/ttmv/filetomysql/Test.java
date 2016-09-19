package com.ttmv.filetomysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ttmv.entity.UserEntity;

@SuppressWarnings("unchecked")
public class Test {

	public static final Map map = new HashMap();
	static {
		map.put("tBRecharge", "tBRecharge");
		map.put("tQRecharge", "tQRecharge");
		map.put("brokerageRecharge", "brokerageRecharge");
		map.put("tBConsume", "tBConsume");
		map.put("tQConsume", "tQConsume");
		map.put("brokerageConsume", "brokerageConsume");
	}

	public static void main(String[] args) throws ClassNotFoundException {

		//String filePath = "F:\\a.txt";
		// int num=isFinished(filePath);
		// System.out.println(num);
		// write2File("张凯特测试",filePath);
		Set keys = map.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			System.out.println("获取map所有的key："+key);
			
		}

	}

	public static String getRealData(String content, int currenRow) {
		String data = content.substring(4, 10);
		System.out.println(data);
		if (content.startsWith("=") && content.substring(4, 6).equals("收到请求数据")) {
			System.out.println(content);
		} else {
			System.out.println(data);
		}
		return "";
	}

	public static void demo() throws ClassNotFoundException {
		Class<?> class2 = UserEntity.class;
		Class<?> c = null;
		c = Class.forName(class2.getName());
		Field[] felds = c.getDeclaredFields();
		for (int i = 0; i < felds.length; i++) {
			Field field = felds[i];
			System.out.println("获取的类的属性名称:" + field.getName());

		}
		System.out.println("Demo: 包名: " + class2.getPackage().getName() + "，" + "完整类名: " + class2.getName());
	}

	public static void write2File(String content, String flagFile) {
		FileWriter writer = null;
		File file = new File(flagFile);
		if (file.exists()) {
			file.deleteOnExit();
		}
		try {

			writer = new FileWriter(flagFile, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static int isFinished(String oldFile) {
		File fFile = new File(oldFile);
		BufferedReader flagReader = null;
		int num = 0;
		// 文件读取的位置
		try {
			flagReader = new BufferedReader(new FileReader(fFile));
			String temp = null;
			while ((temp = flagReader.readLine()) != null) {
				if (temp.startsWith("=") && "收到请求数据".equals(temp.substring(4, 10))) {

					System.out.println(temp);
				}

				num++;
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
		return num;
	}

}
