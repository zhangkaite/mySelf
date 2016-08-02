package org.jam.dataflow;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.domain.DataMode;
import org.jam.dataflow.log.domain.FailLog;
import org.jam.dataflow.log.ldb.LocalDBAccessor;
import org.jam.dataflow.log.ldb.sqlite.SqliteAccessor;
import org.jam.dataflow.web.HttpServer;
import org.jam.dataflow.worker.WorkerManager;
import org.jam.dataflow.worker.flow.PushQueThread;

/**
 * Created by James on 16/4/19.
 */
public class Dataflow {
	private static Logger logger = LogManager.getLogger(Dataflow.class);

    private static DataMode dataMode;

    private static Properties properties;
    private static LocalDBAccessor dbAccessor;
    private static HttpServer httpServer;
    private static WorkerManager workerManager;

    private static ExecutorService pushQueThreadPool;
    private static Map<String, DistributedWriteDaoTemplate> writers = new HashMap<String, DistributedWriteDaoTemplate>();



    public Dataflow() {
        try {
            properties = new Properties();
            InputStream in = Dataflow.class.getClassLoader().getResourceAsStream("dataflow.properties");
            properties.load(in);

            Double modeValue = Double.valueOf(getProperty("mode-value-init"));
            dataMode = DataMode.toDataMode(modeValue);

            Integer pushQueThreadPoolLength = Integer.valueOf(getProperty("push-que-thread-pool-length"));
            pushQueThreadPool = Executors.newFixedThreadPool(pushQueThreadPoolLength);

            dbAccessor = new SqliteAccessor("dataflow_sqlite");
            httpServer = new HttpServer();
            workerManager = new WorkerManager();
            logger().info("Dataflow init successfully.");
        } catch (Exception e) {
            logger().error("Failed to initialize dataflow.", e);
        }
    }

    public void start() throws Exception {
        try{
            workerManager.start();
            httpServer.start();
            logger().info("Dataflow start successfully.");
        }catch (Exception e){
            logger().error("Failed to start dataflow.", e);
        }
    }

    public void close() throws Exception {
        try{
            workerManager.close();
            httpServer.close();
        }catch (Exception e){
            logger().error("Failed to start dataflow.", e);
            throw e;
        }
    }


    public static void failPushQue(String queName, String data ,String e) {
        FailLog log = new FailLog();
        log.setBiz(queName);
        log.setPhase(FailLog.PHASE_PUSHQUE);
        log.setData(data);
        log.setStatus(FailLog.FAILED);
        log.setTime(new Date().getTime());
        log.setError_msg(e);
        dbAccessor.addFailLog(log);
    }

    public static void failWritePost(String biz, String data,String e) {
        FailLog log = new FailLog();
        log.setBiz(biz);
        log.setPhase(FailLog.PHASE_WRITEPOSTDB);
        log.setData(data);
        log.setStatus(FailLog.FAILED);
        log.setTime(new Date().getTime());
        log.setError_msg(e);
        dbAccessor.addFailLog(log);
    }

    public static void pushQue(String queName, String data) {
        PushQueThread qt = new PushQueThread(queName, data);
        Dataflow.pushQueThreadPool.execute(qt);
    }

    public static DataMode getDataMode() {
        return Dataflow.dataMode;
    }

    public static void setModeRecovered() {
        Dataflow.dataMode = DataMode.RECOVERED;
    }

    public static void setModeLOW() {
        Dataflow.dataMode = DataMode.LOW;
    }

    public static void setModeTOLOW(){
        Dataflow.dataMode = DataMode.TOLOW;
    }

    public static void setModeRecovering() {
        Dataflow.dataMode = DataMode.RECOVERING;
    }

    public static LocalDBAccessor getDbAccessor(){
        return Dataflow.dbAccessor;
    }

    public static void setModeHigh() {
        Dataflow.dataMode = DataMode.HIGH;
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }


    public static Logger logger(){
        return logger;
    }

    public static Map<String, DistributedWriteDaoTemplate> getWriters() {
        return Dataflow.writers;
    }

    public static void registerWriter(String queName, DistributedWriteDaoTemplate writer) {
        Dataflow.writers.put(queName, writer);
    }

    public static void failWritePreDB(String biz, String data) {
        FailLog log = new FailLog();
        log.setBiz(biz);
        log.setData(data);
        log.setPhase(FailLog.PHASE_WRITEPREDB);
        log.setStatus(FailLog.FAILED);
        log.setTime(new Date().getTime());
        dbAccessor.addFailLog(log);
    }

    public static WorkerManager getWorkerManager(){
        return workerManager;
    }
}
