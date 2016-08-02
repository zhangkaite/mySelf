package org.jam.dataflow.log.ldb;

import org.jam.dataflow.log.domain.FailLog;

/**
 * Created by James on 16/4/19.
 */
public interface LocalDBAccessor {

    public void addFailLog(FailLog log);

    public FailLog takeFailLog(int phase);

    public void returnFailLog(String id);

    public void finishFailLog(String id);

    public boolean hasFailLog(int phaseWritepredb);
}
