package com.club.util;

public class dai {
	private int id;
	private int binding_playerId;
	private int scale;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBinding_playerId() {
		return binding_playerId;
	}
	public void setBinding_playerId(int binding_playerId) {
		this.binding_playerId = binding_playerId;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	@Override
	public String toString() {
		return "dai [id=" + id + ", binding_playerId=" + binding_playerId
				+ ", scale=" + scale + "]";
	}
	
}
