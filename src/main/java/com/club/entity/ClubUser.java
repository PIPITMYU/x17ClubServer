package com.club.entity;

import java.io.Serializable;

public class ClubUser implements Serializable {

	/**
	 * 俱乐部加入，离开状态
	 */
	private static final long serialVersionUID = 6978924383841632588L;
	private int ID;
	private int CLUB_ID;//俱乐部id
	private int USER_ID;//申请人id
	private int STATUS;//状态 0申请加入 1已通过 2申请退出 
	private int CREATE_TIME;//创建时间
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCLUB_ID() {
		return CLUB_ID;
	}
	public void setCLUB_ID(int cLUB_ID) {
		CLUB_ID = cLUB_ID;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public int getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(int cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ClubUser(){
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CLUB_ID;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClubUser other = (ClubUser) obj;
		if (CLUB_ID != other.CLUB_ID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ClubUser [ID=" + ID + ", CLUB_ID=" + CLUB_ID + ", USER_ID="
				+ USER_ID + ", STATUS=" + STATUS + ", CREATE_TIME="
				+ CREATE_TIME + "]";
	}
	public ClubUser(int iD, int cLUB_ID, int uSER_ID, int sTATUS,
			int cREATE_TIME) {
		super();
		ID = iD;
		CLUB_ID = cLUB_ID;
		USER_ID = uSER_ID;
		STATUS = sTATUS;
		CREATE_TIME = cREATE_TIME;
	}
	
}
