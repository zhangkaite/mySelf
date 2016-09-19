package com.datacenter.dams.input.redis.worker.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;

/**
 * hadoop操作hdfs工具类
 * 
 * @author zkt
 *
 */
public class HadoopFSOperations {
	private static Logger logger = LogManager.getLogger(HadoopFSOperations.class);

	private HadoopFSOperations() {

	}

	static Configuration hdfsConfig = new Configuration();
	// static Configuration hbseConfig = HBaseConfiguration.create();

	/*
	 * upload the local file to the hds notice that the path is full like
	 * /tmp/test.c
	 */
	public static void uploadLocalFile2HDFS(String s, String d) throws IOException {
		FileSystem hdfs = FileSystem.get(hdfsConfig);
		Path src = new Path(s);
		Path dst = new Path(d);
		hdfs.copyFromLocalFile(src, dst);
		hdfs.close();
	}

	public static boolean isDirExit(String tarPath) throws Exception {
		FileSystem hdfs = FileSystem.newInstance(hdfsConfig);
		Path path = new Path(tarPath);
		if (hdfs.exists(path)) {
			return true;
		}
		hdfs.close();
		return false;
	}

	/*
	 * create a new file in the hdfs. notice that the toCreateFilePath is the
	 * full path and write the content to the hdfs file.
	 */
	public static void createNewHDFSFile(String toCreateFilePath, String content) throws IOException {
		// logger.info("将内容存储到指定的路径:" + toCreateFilePath+"存储的内容是："+content);
		FileSystem hdfs = FileSystem.newInstance(hdfsConfig);
		Path path = new Path(toCreateFilePath);
		// 如果文件不存在，则先创建文件
		if (!hdfs.exists(path)) {
			hdfs.create(path).close();
		}
		// if path not exit,it will be create it
		FSDataOutputStream os = hdfs.append(path);
		os.write(content.getBytes("UTF-8"));
		// start a newline
		os.write("\n".getBytes());
		// os.flush();
		os.close();
		hdfs.close();
	}

	/*
	 * delete the hdfs file notice that the dst is the full path name
	 */
	public static boolean deleteHDFSFile(String dst) throws IOException {
		FileSystem hdfs = FileSystem.get(hdfsConfig);
		Path path = new Path(dst);
		boolean isDeleted = hdfs.deleteOnExit(path);
		hdfs.close();
		return isDeleted;
	}

	/*
	 * read the hdfs file content notice that the dst is the full path name
	 */
	public static byte[] readHDFSFile(String dst) throws Exception {
		FileSystem fs = FileSystem.newInstance(hdfsConfig);
		// check if the file exists
		Path path = new Path(dst);
		if (fs.exists(path)) {
			FSDataInputStream is = fs.open(path);
			// get the file info to create the buffer
			FileStatus stat = fs.getFileStatus(path);
			// create the buffer
			byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
			is.readFully(0, buffer);
			is.close();
			fs.close();

			return buffer;
		} else {
			throw new Exception("the file is not found .");
		}
	}

	/*
	 * make a new dir in the hdfs
	 * 
	 * the dir may like '/tmp/testdir'
	 */
	public static void mkdir(String dir) throws IOException {
		FileSystem fs = FileSystem.get(hdfsConfig);
		fs.mkdirs(new Path(dir));
		fs.close();
	}

	/*
	 * delete a dir in the hdfs
	 * 
	 * dir may like '/tmp/testdir'
	 */
	public static void deleteDir(String dir) throws IOException {
		FileSystem fs = FileSystem.get(hdfsConfig);
		fs.deleteOnExit(new Path(dir));
		fs.close();
	}

	public static List<String> readHdfsDate(String dir) throws Exception {
		List<String> dataList = new ArrayList<String>();
		// 流读入和写入
		InputStream in = null;
		// 使用缓冲流，进行按行读取的功能
		BufferedReader buff = null;
		// 获取HDFS的conf
		FileSystem fs = FileSystem.newInstance(hdfsConfig);
		Path path = new Path(dir);
		if (fs.exists(path)) {
			FileStatus[] stats = fs.listStatus(path);
			for (int i = 0; i < stats.length; ++i) {
				if (stats[i].isFile()) {
					Path p = new Path(stats[i].getPath().toString());
					// 打开文件流
					in = fs.open(p);
					// BufferedReader包装一个流
					buff = new BufferedReader(new InputStreamReader(in));
					String str = null;
					while ((str = buff.readLine()) != null) {
						logger.debug("从hdfs" + dir + "路径下读取文件:" + stats[i].getPath().toString() + "内容：" + str);
						dataList.add(str);
					}
					buff.close();
				}
			}
			in.close();
			fs.close();
		}
		return dataList;
	}

	public static void readHDFSListAll(String dir, String tableName, Configuration conf) throws IOException {
		// 流读入和写入
		InputStream in = null;
		// 使用缓冲流，进行按行读取的功能
		BufferedReader buff = null;
		// 获取HDFS的conf
		FileSystem fs = FileSystem.newInstance(hdfsConfig);
		Path path = new Path(dir);
		if (fs.exists(path)) {
			FileStatus[] stats = fs.listStatus(path);
			for (int i = 0; i < stats.length; ++i) {
				if (stats[i].isFile()) {
					Path p = new Path(stats[i].getPath().toString());
					// 打开文件流
					in = fs.open(p);
					// BufferedReader包装一个流
					buff = new BufferedReader(new InputStreamReader(in));
					String str = null;
					while ((str = buff.readLine()) != null) {
						logger.debug("从hdfs" + dir + "路径下读取文件:" + stats[i].getPath().toString() + "内容：" + str);
						try {
							String[] strs = str.split("\\s+");
							HbaseUtil.addRow(tableName, strs[0], ConsumeSpendConstant.HBASECOLUMNFAMILY,
									ConsumeSpendConstant.HBASECLOUMN, strs[1], conf);
						} catch (Exception e) {
							logger.error("hdfs 读取的数据插入失败");
						}

					}
					buff.close();
				}
			}
			in.close();
			fs.close();
		}
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 递归遍历hdfs上的文件
	 * 
	 * @param hdfsMainPath
	 * @param recursionMaxNum
	 * @param currenDate
	 * @return
	 * @throws Exception
	 */
	public static String recursionHdfsPath(String hdfsMainPath, int recursionMaxNum, Date currenDate, Date targetDate)
			throws Exception {
		FileSystem hdfs = FileSystem.newInstance(hdfsConfig);

		String hdfsPath = hdfsMainPath + sdf.format(currenDate);
		String targetPath = hdfsMainPath + sdf.format(targetDate);
		Path path = new Path(hdfsPath);
		if (!hdfs.exists(path)) {
			if (recursionMaxNum > 0) {
				--recursionMaxNum;
				Date date = DateUtil.getQueryFixedTime(currenDate, 1, -1);
				return recursionHdfsPath(hdfsMainPath, recursionMaxNum, date, targetDate);
			}
		} else {
			// 需要对寻址到的hdfs内容拷贝
			if (recursionMaxNum < ConsumeSpendConstant.HADOOPMAXNUMBER) {
				copyHdfsFileToTargetPath(hdfsPath, targetPath);
			}
			return hdfsPath;
		}
		hdfs.close();
		return targetPath;

	}

	/***
	 * 拷贝hdfs上的文件到指定的位置
	 * 
	 * @param
	 * @throws Exception
	 */

	public static boolean copyHdfsFileToTargetPath(String origPath, String targetPath) throws Exception {
		// 流读入和写入
		InputStream in = null;
		// 使用缓冲流，进行按行读取的功能
		BufferedReader buff = null;
		// 获取HDFS的conf
		FileSystem fs = FileSystem.newInstance(hdfsConfig);
		Path path = new Path(origPath);
		if (fs.exists(path)) {
			FileStatus[] stats = fs.listStatus(path);
			for (int i = 0; i < stats.length; ++i) {
				if (stats[i].isFile()) {
					Path p = new Path(stats[i].getPath().toString());
					// 打开文件流
					in = fs.open(p);
					// BufferedReader包装一个流
					buff = new BufferedReader(new InputStreamReader(in));
					String str = null;
					String fileName = stats[i].getPath().getName();
					while ((str = buff.readLine()) != null) {
						createNewHDFSFile(targetPath + File.separator + fileName, str);
					}
					buff.close();
					in.close();
				}

			}

		}
		fs.close();
		return false;
	}

}
