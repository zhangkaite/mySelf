package com.ttmv.datacenter.usercenter.statistics;

/**
 * Created by zkt on 2015/12/11.
 */
public class RankingEntity {

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 消费金额
     */
    private String consume_num;

    /**
     * 频道号
     */
    private String channel_id;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getConsume_num() {
        return consume_num;
    }

    public void setConsume_num(String consume_num) {
        this.consume_num = consume_num;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }
}
