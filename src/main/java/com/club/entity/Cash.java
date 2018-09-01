package com.club.entity;

public class Cash {
	private int id;
	private int dail_id;
	private double money;
	private Long time;
	private int state;
	private String cashCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDail_id() {
		return dail_id;
	}
	public void setDail_id(int dail_id) {
		this.dail_id = dail_id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCashCode() {
		return cashCode;
	}
	public void setCashCode(String cashCode) {
		this.cashCode = cashCode;
	}
	@Override
	public String toString() {
		return "Cash [id=" + id + ", dail_id=" + dail_id + ", money=" + money
				+ ", time=" + time + ", state=" + state + ", cashCode="
				+ cashCode + "]";
	}
	public Cash(int id, int dail_id, double money, Long time, int state,
			String cashCode) {
		super();
		this.id = id;
		this.dail_id = dail_id;
		this.money = money;
		this.time = time;
		this.state = state;
		this.cashCode = cashCode;
	}
	public Cash(){}
}
