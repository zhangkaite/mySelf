package com.ttmv.initHbaseData;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

@SuppressWarnings({ "deprecation", "resource" })
public class HbaseUtil {

	/**
	 * 创建数据库表
	 *
	 * @param tableName
	 * @param columnFamilys
	 * @param conf
	 * @throws Exception
	 */
	public static void createTable(String tableName, String[] columnFamilys, Configuration conf) throws Exception {
		// 新建一个数据库管理员
		HBaseAdmin hAdmin = new HBaseAdmin(conf);
		if (hAdmin.tableExists(tableName)) {
			System.out.println("hbase表 " + tableName + "以创建成功");
		} else {
			// 新建一个 scores 表的描述
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			// 在描述里添加列族
			for (String columnFamily : columnFamilys) {
				tableDesc.addFamily(new HColumnDescriptor(columnFamily));
			}
			// 根据配置好的描述建表
			hAdmin.createTable(tableDesc);
			System.out.println("hbase表 " + tableName + "创建成功");
		}
	}

	/**
	 * 删除表
	 *
	 * @param tableName
	 * @param conf
	 * @throws Exception
	 */
	public static void deleteTable(String tableName, Configuration conf) throws Exception {
		// 新建一个数据库管理员
		HBaseAdmin hAdmin = new HBaseAdmin(conf);
		if (hAdmin.tableExists(tableName)) {
			// 关闭一个表
			hAdmin.disableTable(tableName);
			// 删除一个表
			hAdmin.deleteTable(tableName);
			System.out.println("habse表" + tableName + "删除表成功");

		} else {
			System.out.println("删除的habse表" + tableName + "不存在");
		}
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

	public static void addRow(String tableName, String row, String columnFamily, String column, String value,
			Configuration conf) throws Exception {
		HTable table = new HTable(conf, tableName);
		Put put = new Put(Bytes.toBytes(row));
		// 参数出分别：列族、列、值
		put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
		table.put(put);
		table.close();
	}

	/**
	 * 删除一条数据
	 *
	 * @param tableName
	 * @param row
	 * @param conf
	 * @throws Exception
	 */
	public static void delRow(String tableName, String row, Configuration conf) throws Exception {
		HTable table = new HTable(conf, tableName);
		Delete del = new Delete(Bytes.toBytes(row));
		table.delete(del);
	}

	/**
	 * 删除多条数据
	 *
	 * @param tableName
	 * @param rows
	 * @param conf
	 * @throws Exception
	 */
	public static void delMultiRows(String tableName, String[] rows, Configuration conf) throws Exception {
		HTable table = new HTable(conf, tableName);
		List<Delete> list = new ArrayList<Delete>();

		for (String row : rows) {
			Delete del = new Delete(Bytes.toBytes(row));
			list.add(del);
		}

		table.delete(list);
	}

	/**
	 * 根据key获取对应的数据
	 *
	 * @param tableName
	 * @param row
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public static Result getRow(String tableName, String row, Configuration conf) throws Exception {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(Bytes.toBytes(row));
		Result result = table.get(get);
		return result;
		/*
		 * // 输出结果 for (KeyValue rowKV : result.raw()) { System.out.print(
		 * "Row Name: " + new String(rowKV.getRow()) + " "); System.out.print(
		 * "Timestamp: " + rowKV.getTimestamp() + " "); System.out.print(
		 * "column Family: " + new String(rowKV.getFamily()) + " ");
		 * System.out.print("Row Name:  " + new String(rowKV.getQualifier()) +
		 * " "); System.out.println("Value: " + new String(rowKV.getValue()) +
		 * " "); }
		 */
	}

	/**
	 * @param tableName
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public static ResultScanner getAllRows(String tableName, Configuration conf) throws Exception {
		HTable table = new HTable(conf, tableName);
		Scan scan = new Scan();
		ResultScanner results = table.getScanner(scan);
		return results;
		// 输出结果
		/*
		 * for (Result result : results) { for (KeyValue rowKV : result.raw()) {
		 * System.out.print("Row Name: " + new String(rowKV.getRow()) + " ");
		 * System.out.print("Timestamp: " + rowKV.getTimestamp() + " ");
		 * System.out.print("column Family: " + new String(rowKV.getFamily()) +
		 * " "); System.out.print("Row Name:  " + new
		 * String(rowKV.getQualifier()) + " "); System.out.println("Value: " +
		 * new String(rowKV.getValue()) + " "); } }
		 */
	}

	/**
	 *
	 */
	public static ResultScanner getRowsByStartEndTime(String tableName, String startRow, String stopRow,
			Configuration conf) {

		HTable table = null;
		ResultScanner rs = null;
		try {
			table = new HTable(conf, tableName);
			HBaseAdmin admin = null;
			try {
				admin = new HBaseAdmin(conf);
				try {
					// 判断hbase表是否存在
					if (admin.tableExists(tableName)) {
						// table.setScannerCaching(10000);
						Scan scan = new Scan();
						// 通过配置一次拉去的较大的数据量可以减少客户端获取数据的时间，但是它会占用客户端内存
						scan.setCaching(10000);
						scan.setStartRow(Bytes.toBytes(startRow));
						scan.setStopRow(Bytes.toBytes(stopRow));
						rs = table.getScanner(scan);
						return rs;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != table) {
					table.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
