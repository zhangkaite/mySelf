package com.ttmv.datacenter.sentry.handle;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.sentry.http.HttpRequestPost;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by zbs on 15/10/21.
 */
public class QuickSentryTest {


    @Test
    public void sendMsg() throws Exception {
        SentryAgent  quickSentry = new QuickSentry();
        HttpRequestPost post = new HttpRequestPost("http://192.168.13.35:8080/alertingDataReport");
        quickSentry.setHttpRequestPost(post);
        quickSentry.setQueue(new LinkedList());
        int i = 2;
        while (i-- > 0){
            sendMsg(quickSentry);
            quickSentry.start();
        }
        Thread.sleep(10000);
    }
    public void sendMsg( SentryAgent  quickSentry ) {
        quickSentry.sendMsg("avServerControlServiceDataReport", "222", "333", "444");
        quickSentry.sendMsg("avServerControlServiceDataReport", "vbbb", "vbbb", "vbbb");
        quickSentry.sendMsg("avServerControlServiceDataReport", "5555", "7777", "444");
    }

    @Test
    public void expressSendHttp(){
        SentryAgent sentryAgent = new QuickSentry();
        HttpRequestPost post = new HttpRequestPost("http://192.168.13.160:8080/alertingDataReport");
        sentryAgent.setHttpRequestPost(post);
        sentryAgent.setQueue(new LinkedList());
        System.out.print(sentryAgent.expressSendHttp("aaaaa"));

    }
}
