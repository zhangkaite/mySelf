package com.ttmv.datacenter.sentry;

import com.ttmv.datacenter.sentry.http.HttpRequestPost;

import java.util.Queue;

/**
 * Created by zbs on 15/10/20.
 */
public abstract class SentryAgent {

    private HttpRequestPost httpRequestPost;
    private Queue queue;

    public abstract boolean sendMsg(String serverType,String serverId, String alertMsg,String type) ;

    public abstract String expressSendHttp(String json);

    public abstract void start();

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public HttpRequestPost getHttpRequestPost() {
        return httpRequestPost;
    }

    public void setHttpRequestPost(HttpRequestPost httpRequestPost) {
        this.httpRequestPost = httpRequestPost;
    }
}
