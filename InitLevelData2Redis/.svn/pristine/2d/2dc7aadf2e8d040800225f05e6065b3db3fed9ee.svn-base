package com.ttmv.datacenter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * 
 * @author kate
 *
 */
public class FileInputReadUtil {

	private static Logger logger = LogManager.getLogger(FileInputReadUtil.class);

	public static void fileRead(String filePath, String fileType) {
		File fFile = new File(filePath);
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(fFile));
			String data = null;
			while ((data = fileReader.readLine()) != null) {
				if (fileType.equals(Constant.TDOU)) {
					String jsonData = ReadFileAssemb.tdouTypeAssemb(data);
					if (jsonData != null) {
						RedisUtil.pushData(Constant.TBREDISQUEUENAME, jsonData);
					}
				}
				if (fileType.equals(Constant.HEART)) {
					String jsonData = ReadFileAssemb.heartTypeAssemb(data);
					if (jsonData != null) {
						RedisUtil.pushData(Constant.HEARTREDISQUEUENAME, jsonData);
					}
				}
				if (fileType.equals(Constant.FLOWER)) {
					String jsonData = ReadFileAssemb.flowerTypeAssemb(data);
					if (jsonData != null) {
						RedisUtil.pushData(Constant.FLOWERREDISQUEUENAME, jsonData);
					}
				}
			}
		} catch (Exception e) {
			logger.error("文件读取根据类型处理将数据写入redis失败，失败的原因是:" + e);
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				logger.error("文件关闭失败，失败的原因是:" + e);
				e.printStackTrace();
			}
		}

	}

}
