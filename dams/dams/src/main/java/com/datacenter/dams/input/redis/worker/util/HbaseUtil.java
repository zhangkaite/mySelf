package com.datacenter.dams.input.redis.worker.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.snapshot.SnapshotCreationException;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;

@SuppressWarnings({ "deprecation", "resource" })
public class HbaseUtil {
	private static Logger logger = LogManager.getLogger(HbaseUtil.class);
	private HbaseUtil() {
	}

	/**
	 * hbase创建表
	 */

	public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
		if (admin.tableExists(table.getTableName())) {
			admin.disableTable(table.getTableName());
			admin.deleteTable(table.getTableName());
		}
		admin.createTable(table);
		admin.close();
		logger.info("hbase 创建表:" + table.getTableName() + "成功！");
	}

	/**
	 * 创建表
	 * 
	 * @param config
	 * @param tableName
	 * @param columnFamilys
	 * @throws Exception
	 */
	public static void createTable(Configuration config, String tableName, String columnFamily) throws Exception {
		try (Connection connection = ConnectionFactory.createConnection(config); Admin admin = connection.getAdmin()) {
			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
			// 在描述里添加列族
			table.addFamily(new HColumnDescriptor(columnFamily));
			logger.info("Creating table: " + tableName + " starting");
			createOrOverwrite(admin, table);
			logger.info(" Creating table: " + tableName + "Done.");
			connection.close();
		} catch (Exception e) {
			logger.error("hbase 创建连接失败，失败的原因是：", e);
			throw e;
		}
	}

	public static void createTableNoOverwrite(Configuration config, String tableName, String familyColumn) throws Exception {
		Connection connection = ConnectionFactory.createConnection(config);
		Admin admin = connection.getAdmin();
		HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
		if (!admin.tableExists(table.getTableName())) {
			table.addFamily(new HColumnDescriptor(familyColumn));
			admin.createTable(table);
		}
		admin.close();
		connection.close();
	}

	/**
	 * 添加一条数据
	 *
	 * @param tableName
	 * @param row
	 * @param columnFamily
	 * @param column
	 * @param value
	 * @param conf
	 * @throws Exception
	 * 
	 */
	public static void addRow(String tableName, String row, String columnFamily, String column, String value, Configuration conf)
			throws Exception {
		HTable table = new HTable(conf, tableName);
		Put put = new Put(Bytes.toBytes(row));
		// 参数出分别：列族、列、值
		put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
		table.put(put);
		table.close();
	}

	/**
	 * 批量数据添加
	 * @param table
	 * @param row
	 * @param columnFamily
	 * @param column
	 * @param value
	 * @throws Exception
	 */
	public static void addRow(Table table, String row, String columnFamily, String column, String value) throws Exception {
		Put put = new Put(Bytes.toBytes(row));
		// 参数出分别：列族、列、值
		put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
		table.put(put);
	}

	/**
	 * 添加一条数据
	 *
	 * @param tableName
	 * @param row
	 * @param columnFamily
	 * @param column
	 * @param value
	 * @param conf
	 * @throws Exception
	 */

	public static void addRowColumns(String tableName, String row, String columnFamily, Configuration conf,
			Map<String, String> keyValues) throws Exception {
		HTable table = new HTable(conf, tableName);
		Put put = new Put(Bytes.toBytes(row));
		// 参数出分别：列族、列、值
		if (keyValues != null && keyValues.size() > 0) {
			Set<String> keys = keyValues.keySet();
			for (String key : keys) {
				put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(key), Bytes.toBytes(Long.parseLong(keyValues.get(key))));
			}
		}
		table.put(put);
		table.close();
	}

	/**
	 * 查询一条数据
	 * 
	 * @param config
	 * @param table
	 * @param rowkey
	 * @throws Exception
	 */

	public static Result getOneDataByRowKey(Configuration config, Table table, String rowkey, String columnFamily, String cloumn)
			throws Exception {
		Get g = new Get(Bytes.toBytes(rowkey));
		g.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn));
		Result r = table.get(g);
		return r;

	}

	/***
	 * 删除一条数据
	 * 
	 * @param args
	 * @throws IOException
	 */

	public static void deleteByRow(Configuration config, Table table, String rowkey) throws Exception {
		long st_time = System.currentTimeMillis();
		Delete d = new Delete(Bytes.toBytes(rowkey));
		table.delete(d);// 删除一条数据
		long en_time = System.currentTimeMillis();
		logger.info("删除一条数据花费的时间:" + (en_time - st_time));
	}

	public static void rename(Configuration config, String oldTableName, String newTableName)
			throws SnapshotCreationException, IllegalArgumentException, IOException, InterruptedException {
		try (Connection connection = ConnectionFactory.createConnection(config); Admin admin = connection.getAdmin()) {
			String snapshotName = "current_" + UUID.randomUUID();
			admin.disableTable(TableName.valueOf(oldTableName));
			admin.snapshot(snapshotName, TableName.valueOf(oldTableName));
			admin.cloneSnapshot(snapshotName, TableName.valueOf(newTableName));
			admin.deleteSnapshot(snapshotName);
			admin.deleteTable(TableName.valueOf(oldTableName));
			connection.close();
		} catch (Exception e) {
			logger.error("hbase 创建连接失败，失败的原因是：", e);
		}

	}

	/**
	 * 查询多版本数据的方法
	 * 
	 * @param tableName
	 * @param rowkey
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public static ResultScanner getAllVersionsData(String tableName, String rowkey, Configuration conf) throws Exception {
		if (!Strings.isNullOrEmpty(tableName) && !Strings.isNullOrEmpty(rowkey) && conf != null) {
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				HTable table = new HTable(conf, tableName);
				Get get = new Get(rowkey.getBytes());

				Scan s = new Scan(get);
				s.setRaw(true);
				s.setMaxVersions();
				ResultScanner rs = table.getScanner(s);
				return rs;
			}
		}
		return null;
	}

	/**
	 * 非多版本 单列数据
	 * 
	 * @param conf
	 * @param tableName
	 * @param rowkey
	 * @param familiy
	 * @param familiyQualifer
	 * @return
	 * @throws Exception
	 */
	public static byte[] getStormColumnData(Configuration conf, String tableName, String rowkey, String familiy,
			String familiyQualifer) throws Exception {
		if (!Strings.isNullOrEmpty(tableName) && !Strings.isNullOrEmpty(rowkey) && conf != null) {
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				HTable table = new HTable(conf, tableName);
				Get get = new Get(rowkey.getBytes());
				get.addColumn(familiy.getBytes(), familiyQualifer.getBytes());
				Result result = table.get(get);
				if (result != null) {
					for (Cell cell : result.rawCells()) {
						byte[] data = CellUtil.cloneValue(cell);
						if (data != null) {
							return data;
						}
					}
				}
				table.close();
				hAdmin.close();
			}
		}
		return null;
	}

	/**
	 * 非多版本 全部列数据
	 * 
	 * @param tableName
	 * @param rowkey
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public static Result getStormAllColumnData(String tableName, String rowkey, Configuration conf) throws Exception {
		if (!Strings.isNullOrEmpty(tableName) && !Strings.isNullOrEmpty(rowkey) && conf != null) {
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				HTable table = new HTable(conf, tableName);
				Get get = new Get(rowkey.getBytes());
				Result result = table.get(get);
				return result;
			}
		}
		return null;
	}

	/**
	 * 查询colum是否存在
	 * 
	 * @param conf
	 * @param tableName
	 * @param rowkey
	 * @param familiy
	 * @param familiyQualifer
	 * @return
	 * @throws Exception
	 */
	public static boolean getHbaseColumnFlag(Configuration conf, String tableName, String rowkey, String familiy,
			String familiyQualifer) throws Exception {
		if (!Strings.isNullOrEmpty(tableName) && !Strings.isNullOrEmpty(rowkey) && conf != null) {
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				HTable table = new HTable(conf, tableName);
				Get get = new Get(rowkey.getBytes());
				get.addColumn(familiy.getBytes(), familiyQualifer.getBytes());
				Result result = table.get(get);
				if (result != null) {
					int length = result.rawCells().length;
					if (length == 0) {
						return false;
					} else if (length > 0) {
						return true;
					}
				}
				table.close();
				hAdmin.close();
			}
		}
		return false;
	}

	/**
	 * 获取一个hbase表的所有的rowkey
	 * 
	 * @param conf
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("unused")
	public static List<String> getTableAllRows(Configuration conf, String tableName) throws Exception {
		if (conf != null && !Strings.isNullOrEmpty(tableName)) {
			List<String> listStrings = new ArrayList<String>();
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				HTable table = new HTable(conf, tableName);
				Scan s = new Scan();
				ResultScanner rs = table.getScanner(s);
				for (Result r : rs) {
					for (Cell cell : r.rawCells()) {
						String key = Bytes.toString(r.getRow());
						listStrings.add(key);
					}
				}
				table.close();
				return listStrings;
			}
			hAdmin.close();
		}
		return null;
	}

	public static boolean isExitHbaseTable(Configuration conf, String tableName) throws Exception {
		HBaseAdmin hAdmin = new HBaseAdmin(conf);
		return hAdmin.tableExists(tableName);
	}

	public static List<String> getRowsByStartEndTime(String tableName, String startRow, String stopRow, Configuration conf)
			throws Exception {
		boolean isExit = isExitHbaseTable(conf, tableName);
		if (!isExit) {
			return null;
		}
		List<String> dataList = new ArrayList<String>();
		HTable table = new HTable(conf, tableName);
		Scan s = new Scan();
		s.setCaching(10000);
		s.setStartRow(Bytes.toBytes(startRow));
		s.setStopRow(Bytes.toBytes(stopRow));
		ResultScanner rs = table.getScanner(s);
		for (Result r : rs) {
			for (Cell cell : r.rawCells()) {
				dataList.add(Bytes.toString(CellUtil.cloneValue(cell)));
			}
		}
		rs.close();
		table.close();
		return dataList.size() > 0 ? dataList : null;
	}

	/**
	 * 随机生成5为数字
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getRandomNumber() {
		Random random = new Random();
		String numStr = String.valueOf((int) ((1 + random.nextDouble()) * 100000));
		return numStr.substring(numStr.length() - 5, numStr.length());
	}

}
