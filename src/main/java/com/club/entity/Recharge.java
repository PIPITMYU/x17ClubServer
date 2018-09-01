package com.club.entity;

public class Recharge {
	private int id;
	private int user_id;
	private int dail_id;
	private int money;
	private int card;
	private Long time;
	private int ifkou;
	private String orderNum;
	private String cashCode;
	private int ifcash;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getDail_id() {
		return dail_id;
	}
	public void setDail_id(int dail_id) {
		this.dail_id = dail_id;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getCard() {
		return card;
	}
	public void setCard(int card) {
		this.card = card;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public int getIfkou() {
		return ifkou;
	}
	public void setIfkou(int ifkou) {
		this.ifkou = ifkou;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getCashCode() {
		return cashCode;
	}
	public void setCashCode(String cashCode) {
		this.cashCode = cashCode;
	}
	public int getIfcash() {
		return ifcash;
	}
	public void setIfcash(int ifcash) {
		this.ifcash = ifcash;
	}
	@Override
	public String toString() {
		return "Recharge [id=" + id + ", user_id=" + user_id + ", dail_id="
				+ dail_id + ", money=" + money + ", card=" + card + ", time="
				+ time + ", ifkou=" + ifkou + ", orderNum=" + orderNum
				+ ", cashCode=" + cashCode + ", ifcash=" + ifcash + "]";
	}
	public Recharge(int id, int user_id, int dail_id, int money, int card,
			Long time, int ifkou, String orderNum, String cashCode, int ifcash) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.dail_id = dail_id;
		this.money = money;
		this.card = card;
		this.time = time;
		this.ifkou = ifkou;
		this.orderNum = orderNum;
		this.cashCode = cashCode;
		this.ifcash = ifcash;
	}
	public Recharge(){}
	
}
