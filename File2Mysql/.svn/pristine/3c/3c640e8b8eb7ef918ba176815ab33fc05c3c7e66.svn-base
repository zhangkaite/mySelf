package com.ttmv.filetomysql;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;

import com.ttmv.dbcp.DbcpBean;
import com.ttmv.dbcp.DbcpDao;
import com.ttmv.util.ApplicationResource;

public class DataStore {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws JSONException {
		DbcpBean dbcpBean = new DbcpBean();
		DbcpDao dao = dbcpBean.getDbcpDao();
		FileOperation fileOperation = new FileOperation(dao);
		String targetFilePath = ApplicationResource.getValue("pchome");
		File file = new File(targetFilePath);
		List<String> ls = new ArrayList<String>();
		File[] files = file.listFiles();
		for (File file2 : files) {
			System.out.println("获取的日志文件有:" + file2.getName());
			ls.add(file2.getName());
		}
		Collections.sort(ls, new Comparator() {
			public int compare(Object o1, Object o2) {
				Integer a = Integer.valueOf(o1.toString().split("-")[1]);
				Integer b = Integer.valueOf(o2.toString().split("-")[1]);
				return a.compareTo(b);
			}
		});

		// 获取当前采集日志目录下最后一个文件
		String currenFile = targetFilePath + File.separator + ls.get(ls.size() - 1);
		System.out.println("要读取的当前文件路径:" + currenFile);
		String flagFile = ApplicationResource.getValue("flagFile");
		fileOperation.fileRead(currenFile, flagFile);
		try {
			dbcpBean.shutdownDataSource();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
