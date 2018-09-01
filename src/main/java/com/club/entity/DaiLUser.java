package com.club.entity;

public class DaiLUser {
	private int id;
	private int dail_id;
	private int user_id;
	private Long time;
	private int white;//0不是白名单，1是白名单 默认为0
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
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public int getWhite() {
		return white;
	}
	public void setWhite(int white) {
		this.white = white;
	}
	public DaiLUser(int id, int dail_id, int user_id, Long time, int white) {
		super();
		this.id = id;
		this.dail_id = dail_id;
		this.user_id = user_id;
		this.time = time;
		this.white = white;
	}
	@Override
	public String toString() {
		return "DaiLUser [id=" + id + ", dail_id=" + dail_id + ", user_id="
				+ user_id + ", time=" + time + ", white=" + white + "]";
	}
	public DaiLUser(){}
}
