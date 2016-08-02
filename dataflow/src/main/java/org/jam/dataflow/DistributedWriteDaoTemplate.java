package org.jam.dataflow;


import org.jam.dataflow.error.FailServeError;
import org.jam.dataflow.error.FailAccessError;
import org.jam.dataflow.domain.DataMode;
import com.alibaba.fastjson.JSON;


/**
 * Created by James on 16/4/18.
 */
public abstract class DistributedWriteDaoTemplate<D> {

    public abstract void writePreDB(D data) throws Exception;
    public abstract void writePostDB(D data) throws Exception;
    public abstract Class<D> dataClass() throws Exception;

    public void writePostDB(String data) throws Exception{
        D d = (D)JSON.parseObject(data, dataClass());
        writePostDB(d);
    }

    public void writePreDB(String data) throws Exception{
        D d = (D)JSON.parseObject(data, dataClass());
        writePreDB(d);
    }

    public DistributedWriteDaoTemplate(){
        Dataflow.registerWriter(this.getClass().getName(), this);
    }

    public void write(D data) throws Exception {
        DataMode mode = Dataflow.getDataMode();
        switch (mode){
            case HIGH:
                writeHigh(data);
                break;
            case LOW:
                writeLow(data);
                break;
            default:
                throw new FailServeError(new Exception());
        }
    }

    private void writeHigh(D data) throws Exception{
        try {
            writePreDB(data);
        } catch (FailAccessError e) {
            Dataflow.setModeTOLOW();
            throw new FailServeError(e);
        } catch (Exception e) {
            throw e;
        }

        Dataflow.pushQue(this.getClass().getName(), JSON.toJSONString(data));
    }


    public void writeLow(D data) throws Exception{
        try{
            writePostDB(data);
        }catch (FailAccessError e){
           throw new FailServeError(e);
        }catch (Exception e){
            throw e;
        }

        Dataflow.failWritePreDB(this.getClass().getName(), JSON.toJSONString(data));
    }


}
