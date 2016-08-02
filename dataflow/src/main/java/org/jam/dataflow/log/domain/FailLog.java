package org.jam.dataflow.log.domain;

import com.alibaba.fastjson.JSON;

import java.util.UUID;

/**
 * Created by James on 16/4/21.
 */
public class FailLog{

    public static final int FAILED = -1;
    public static final int TAKED = 0;
    public static final int FINISHED = 1;
    public static final int PHASE_WRITEPREDB = 1;
    public static final int PHASE_PUSHQUE = 3;
    public static final int PHASE_WRITEPOSTDB = 2;

    private String id;

    private Long time;

    private String biz;

    private Integer phase;

    private Integer retry;

    private Integer status;

    private Long ok_time;

    private String error_msg;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FailLog(){
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }
    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOk_time() {
        return ok_time;
    }

    public void setOk_time(Long ok_time) {
        this.ok_time = ok_time;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

}
