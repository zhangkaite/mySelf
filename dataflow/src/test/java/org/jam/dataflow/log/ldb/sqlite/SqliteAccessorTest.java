package org.jam.dataflow.log.ldb.sqlite;

import org.jam.dataflow.log.domain.FailLog;
import org.jam.dataflow.log.ldb.LocalDBAccessor;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by James on 16/5/17.
 */
public class SqliteAccessorTest {
    private LocalDBAccessor localDBAccessor;
    private FailLog log;

    @Before
    public void setUp() throws Exception {
        localDBAccessor = new SqliteAccessor("dataflow_sqlite_test" + new Date().toString() + ".db");
        log = new FailLog();
        log.setBiz("com.write");
        log.setPhase(FailLog.PHASE_WRITEPREDB);
        log.setData("datas");
        log.setStatus(FailLog.FAILED);
        log.setTime(new Date().getTime());
    }

    @Test
    public void failLog() throws Exception {
        localDBAccessor.addFailLog(log);
        if(!localDBAccessor.hasFailLog(FailLog.PHASE_WRITEPREDB)){
            fail("Should have writepredb phase fail log.");
        }
        if(localDBAccessor.hasFailLog(FailLog.PHASE_PUSHQUE)){
            fail("Shouldn`t have pushque phase fail log.");
        }
        FailLog takeLog = localDBAccessor.takeFailLog(FailLog.PHASE_WRITEPREDB);
        if(!takeLog.getId().equals(log.getId())){
            fail("Failed to take log.");
        }
    }


}