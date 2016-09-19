package com.datacenter.dams.business.dao.redis.bean;


/**
 * TB消费数据分拣--吊牌活动数据
 * @author wll
 */
public class TquanConsumeSpendActivityRedisQueueBean extends RedisQueueBean{

	private String spendId;					//消费者ID
	private String spendToId;				//消费者消费对象ID
	private String presentId;				    //商品ID
	private String presentNum;			    //商品数量
	private Float tb;								//消费TB数量
	private Float tq;								//消费TQ数量
	private Long time;							//消费时间
	
	public String getSpendId() {
		return spendId;
	}
	public void setSpendId(String spendId) {
		this.spendId = spendId;
	}
	public String getSpendToId() {
		return spendToId;
	}
	public void setSpendToId(String spendToId) {
		this.spendToId = spendToId;
	}
	public Float getTb() {
		return tb;
	}
	public void setTb(Float tb) {
		this.tb = tb;
	}
	public Float getTq() {
		return tq;
	}
	public void setTq(Float tq) {
		this.tq = tq;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getPresentId() {
		return presentId;
	}
	public void setPresentId(String presentId) {
		this.presentId = presentId;
	}
	public String getPresentNum() {
		return presentNum;
	}
	public void setPresentNum(String presentNum) {
		this.presentNum = presentNum;
	}
}
