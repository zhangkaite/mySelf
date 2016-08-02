package org.jam.dataflow;

import org.jam.dataflow.domain.DataMode;
import org.jam.dataflow.error.FailServeError;
import org.jam.dataflow.log.domain.FailLog;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by James on 16/5/5.
 */
public class DataflowTest {
    private StoreEmulator store;

    private WriteDao writeDao;
    private ReadDao readDao;

    private Dataflow dataflow;

    private FailLog dataTest = new FailLog();

    @org.junit.Before
    public void setUp() throws Exception {
        store = new StoreEmulator();
        writeDao = new WriteDao(store);
        readDao = new ReadDao(store);
        dataflow = new Dataflow();
        dataflow.start();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        dataflow.close();
    }

    @Test
    public void use() throws Exception {
        store.clean();
        writeDao.write(dataTest);
        if (store.getPreDB().size() != 1) {
            fail("Failed to write pre db.");
        }
        Thread.sleep(10000);
        if (store.getPostDB().size() != 1) {
            fail("Failed to write post db.");
        }

        if (!"predata".equals(readDao.read(0))) {
            fail("Should read from pre db.");
        }
    }

    @Test
    public void preDBDown() throws Exception {
        store.clean();

        int count = 0;
        while (true) {
            writeDao.write(dataTest);
            count++;
            Thread.sleep(500);
            if (count < 10) {
                continue;
            }else if(10 == count){
                store.setPreAlive(false);
                if(!"postdata".equals(readDao.read(0))){
                    fail("Failed to read from post.");
                }
                if(Dataflow.getDataMode() != DataMode.TOLOW){
                    fail("Failed to switch to tolow mode.");
                }
                Thread.sleep(20000);
                if(Dataflow.getDataMode() != DataMode.LOW){
                    fail("Failed to switch to low mode.");
                }

                int before = store.getPostDB().size();
                writeDao.write(dataTest);
                int after = store.getPostDB().size();
                if(after - before != 1){
                    fail("Failed to change to low.");
                }
            }

            if (20 == count) {
                if (store.getPostDB().size() != 21) {
                    fail("Amounts of data is not right: " + store.getPostDB().size());
                }
                break;
            }
        }
    }

    @Test
    public void recover() throws Exception {
        store.clean();

        int count = 0;
        for(int i = 0; i < 10; i++){//前后都写入10个
            writeDao.write(dataTest);
            count++;
        }
        Thread.sleep(20000);

        store.setPreAlive(false);//前置库宕机
        try{
            writeDao.write(dataTest);
        }catch (FailServeError e){

        }
        Thread.sleep(20000);
        for(int i = 0; i < 10; i++){
            writeDao.write(dataTest);
            count++;
        }
        if(store.getPreDB().size() != 10){//继续保持后置库写入
            fail("Pre db should be 10.");
        }

        store.setPreAlive(true);//开启前置库
        dataflow.setModeRecovering();//开启数据恢复
        Thread.sleep(10000);
        if(store.getPreDB().size() <= 10){
            fail("Failed to recover pre data.");
        }
        if(Dataflow.getDataMode() != DataMode.RECOVERED){
            fail("Failed to recovered mode.");
        }

    }


    class ReadDao extends DistributedReadDaoTemplate<String, Integer> {

        private StoreEmulator store;

        public ReadDao(StoreEmulator store) {
            this.store = store;
        }

        public String readPreDB(Integer condition) throws Exception {
            return store.read_pre();
        }

        public String readPostDB(Integer condition) throws Exception {
            return store.read_post();
        }

        public void writePreDBBack(String data) throws Exception {

        }
    }

    class WriteDao extends DistributedWriteDaoTemplate<FailLog> {

        private StoreEmulator store;

        public WriteDao(StoreEmulator store) {
            this.store = store;
        }

        public void writePreDB(FailLog data) throws Exception {
            store.write_pre();
        }

        public void writePostDB(FailLog data) throws Exception {
            store.write_post();
        }

        public Class<FailLog> dataClass() throws Exception {
            return FailLog.class;
        }

    }

}