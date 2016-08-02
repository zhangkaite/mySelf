package org.jam.dataflow;

import org.jam.dataflow.error.FailAccessError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 16/5/5.
 */
public class StoreEmulator {

    private List<String> preDB;
    private List<String> postDB;


    private boolean preAlive = true;
    private boolean postAlive = true;


    public StoreEmulator(){
        this.preDB = new ArrayList<String>();
        this.postDB = new ArrayList<String>();
    }

    public void clean(){
        this.preDB.clear();
        this.postDB.clear();
    }

    public void write_pre() throws Exception {
        if (preAlive) {
            this.preDB.add("predata");
        }else{
            throw new FailAccessError(new Exception());
        }
    }

    public void write_post() throws Exception{
        if (postAlive) {
            this.postDB.add("postdata");
        }else{
            throw new FailAccessError(new Exception());
        }
    }

    public String read_pre() throws Exception{
        if (preAlive) {
            return this.preDB.get(this.preDB.size() -1);
        }else{
            throw new FailAccessError(new Exception());
        }
    }

    public String read_post() throws Exception{
        if (postAlive) {
            return this.postDB.get(this.postDB.size() -1);
        }else{
            throw new FailAccessError(new Exception());
        }
    }

    public List<String> getPreDB() {
        return preDB;
    }

    public void setPreDB(List<String> preDB) {
        this.preDB = preDB;
    }

    public List<String> getPostDB() {
        return postDB;
    }

    public void setPostDB(List<String> postDB) {
        this.postDB = postDB;
    }

    public boolean isPreAlive() {
        return preAlive;
    }

    public void setPreAlive(boolean preAlive) {
        this.preAlive = preAlive;
    }

    public boolean isPostAlive() {
        return postAlive;
    }

    public void setPostAlive(boolean postAlive) {
        this.postAlive = postAlive;
    }

}
