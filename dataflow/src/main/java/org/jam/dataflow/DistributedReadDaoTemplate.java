package org.jam.dataflow;

import org.jam.dataflow.error.FailAccessError;
import org.jam.dataflow.error.FailServeError;
import org.jam.dataflow.error.NoDataError;
import org.jam.dataflow.domain.DataMode;

/**
 * Created by James on 16/4/18.
 */
public abstract class DistributedReadDaoTemplate<T, Q> {

    public abstract T readPreDB(Q condition) throws Exception;

    public abstract T readPostDB(Q condition) throws Exception;

    public abstract void writePreDBBack(T data) throws Exception;


    public T read(Q condition) throws Exception{
        if(Dataflow.getDataMode() == DataMode.HIGH){
            return readFromPreDB(condition);
        }else{
            return readFromPostDB(condition);
        }
    }

    private T readFromPreDB(Q condition) throws Exception {
        boolean writePreDBBack = false;
        T data = null;

        try{
            data = readPreDB(condition);
            return data;
        }catch (NoDataError e){ //只有当前置库当做非全量热点数据缓存时,未查询到数据才抛出该异常类型
            writePreDBBack = true;
        }catch (FailAccessError e){
            Dataflow.setModeTOLOW();
        }catch (Exception e){
            throw e; //业务性异常
        }

        try {
            data = readPostDB(condition);
        }catch (FailAccessError e){
            throw new FailServeError(e);
        }catch (Exception e){
            throw e;
        }

        if(writePreDBBack){
            writePreDBBack(data);
        }
        return data;
    }

    private T readFromPostDB(Q condition) throws Exception {
        try {
            return readPostDB(condition);
        }catch (FailAccessError e){
            throw new FailServeError(e);
        }catch (Exception e){
            throw e;
        }
    }

}
