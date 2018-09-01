package com.club.entity;

public class DaiL {
	private int id;
	private int dail_id;//´úÀíid
	private Long inviteCode;
	private int scale;
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
	public Long getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(Long inviteCode) {
		this.inviteCode = inviteCode;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dail_id;
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
		DaiL other = (DaiL) obj;
		if (dail_id != other.dail_id)
			return false;
		return true;
	}
	public DaiL(){}
	@Override
	public String toString() {
		return "DaiL [id=" + id + ", dail_id=" + dail_id + ", inviteCode="
				+ inviteCode + ", scale=" + scale;
	}
	public DaiL(int id, int dail_id, Long inviteCode, int scale) {
		super();
		this.id = id;
		this.dail_id = dail_id;
		this.inviteCode = inviteCode;
		this.scale = scale;

	}
	
	
}
