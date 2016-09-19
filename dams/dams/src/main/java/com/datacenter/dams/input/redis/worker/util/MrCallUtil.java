package com.datacenter.dams.input.redis.worker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.datacenter.dams.util.ConsumeSpendConstant;

public class MrCallUtil {
	private static Logger logger = LogManager.getLogger(MrCallUtil.class);
	static String hostname = ConsumeSpendConstant.HADOOPMASTERHOST;
	static String username = ConsumeSpendConstant.HADOOPMASTERUSERNAME;
	static String password = ConsumeSpendConstant.HADOOPMASTERPASSWORD;

	public static void execMr(final String command) throws Exception {
		Thread t = new Thread(new Runnable() {
			@SuppressWarnings("resource")
			public void run() {
				try {
					logger.info("远程调用连接开始。。。。。。。");
					Connection conn = new Connection(hostname);
					conn.connect();
					boolean isAuthenticated = conn.authenticateWithPassword(username, password);
					if (isAuthenticated == false)
						throw new IOException("Authentication failed.");
					Session sess = conn.openSession();
					sess.execCommand(command);
					logger.info("远程调用命令" + command + "开始执行。。。。。。");
					InputStream stdout = new StreamGobbler(sess.getStdout());
					BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
					}
					sess.close();
					conn.close();
					logger.info("远程调用命令" + command + "结束执行。。。。。。");
				} catch (IOException e) {
					logger.error("远程调用jar命令读取返回信息失败，失败的原因是：", e);
				}

			}
		});
		t.start();
	}

	/**
	 * 首充、首消、用户登陆记录、消费等调用
	 * @param command
	 * @throws Exception
	 */
	public static void execNewMr(final String command) throws Exception {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					logger.info("远程调用连接开始。。。。。。。");
					Connection conn = new Connection(hostname);
					conn.connect();
					boolean isAuthenticated = conn.authenticateWithPassword(username, password);
					if (isAuthenticated == false)
						throw new IOException("Authentication failed.");
					Session sess = conn.openSession();
					logger.info("远程调用命令" + command + "开始执行。。。。。。");
					sess.execCommand(command);
					sess.close();
					conn.close();
					logger.info("远程调用命令" + command + "结束执行。。。。。。");
				} catch (IOException e) {
					logger.error("远程调用jar命令读取返回信息失败，失败的原因是：", e);
				}

			}
		});
		t.start();
	}
	
	public static void main(String[] args) {
		/*try {
			execMr("/opt/hadoop-2.7.1/bin/hadoop jar /opt/countSpending.jar countSpending /datacenter/payment/score/end_day_score_20160109 /datacenter/payment/spending/spend_20160109");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
