
package com.club.entity;

public class User {
    private int ID;
    private int USER_ID;
    private String OPEN_ID;
    private String USER_NAME;
    private String USER_IMG;
    private int GENDER;
    private int TOTAL_GAME_NUM;
    private int MONEY;
    private int USER_AGREE;
    private int LOGIN_STATUS;
    private int BLACK;
    private long SIGN_UP_TIME;
    private long LAST_LOGIN_TIME;
    private int CID;
    
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getOPEN_ID() {
		return OPEN_ID;
	}
	public void setOPEN_ID(String oPEN_ID) {
		OPEN_ID = oPEN_ID;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getUSER_IMG() {
		return USER_IMG;
	}
	public void setUSER_IMG(String uSER_IMG) {
		USER_IMG = uSER_IMG;
	}
	public int getGENDER() {
		return GENDER;
	}
	public void setGENDER(int gENDER) {
		GENDER = gENDER;
	}
	public int getTOTAL_GAME_NUM() {
		return TOTAL_GAME_NUM;
	}
	public void setTOTAL_GAME_NUM(int tOTAL_GAME_NUM) {
		TOTAL_GAME_NUM = tOTAL_GAME_NUM;
	}
	public int getMONEY() {
		return MONEY;
	}
	public void setMONEY(int mONEY) {
		MONEY = mONEY;
	}
	public int getUSER_AGREE() {
		return USER_AGREE;
	}
	public void setUSER_AGREE(int uSER_AGREE) {
		USER_AGREE = uSER_AGREE;
	}
	public int getLOGIN_STATUS() {
		return LOGIN_STATUS;
	}
	public void setLOGIN_STATUS(int lOGIN_STATUS) {
		LOGIN_STATUS = lOGIN_STATUS;
	}
	public int getBLACK() {
		return BLACK;
	}
	public void setBLACK(int bLACK) {
		BLACK = bLACK;
	}
	public long getSIGN_UP_TIME() {
		return SIGN_UP_TIME;
	}
	public void setSIGN_UP_TIME(long sIGN_UP_TIME) {
		SIGN_UP_TIME = sIGN_UP_TIME;
	}
	public long getLAST_LOGIN_TIME() {
		return LAST_LOGIN_TIME;
	}
	public void setLAST_LOGIN_TIME(long lAST_LOGIN_TIME) {
		LAST_LOGIN_TIME = lAST_LOGIN_TIME;
	}
	public int getCID() {
		return CID;
	}
	public void setCID(int cID) {
		CID = cID;
	}
	@Override
	public String toString() {
		return "User [ID=" + ID + ", USER_ID=" + USER_ID + ", OPEN_ID="
				+ OPEN_ID + ", USER_NAME=" + USER_NAME + ", USER_IMG="
				+ USER_IMG + ", GENDER=" + GENDER + ", TOTAL_GAME_NUM="
				+ TOTAL_GAME_NUM + ", MONEY=" + MONEY + ", USER_AGREE="
				+ USER_AGREE + ", LOGIN_STATUS=" + LOGIN_STATUS + ", BLACK="
				+ BLACK + ", SIGN_UP_TIME=" + SIGN_UP_TIME
				+ ", LAST_LOGIN_TIME=" + LAST_LOGIN_TIME + ", CID=" + CID + "]";
	}
	public User(int iD, int uSER_ID, String oPEN_ID, String uSER_NAME,
			String uSER_IMG, int gENDER, int tOTAL_GAME_NUM, int mONEY,
			int uSER_AGREE, int lOGIN_STATUS, int bLACK, long sIGN_UP_TIME,
			long lAST_LOGIN_TIME, int cID) {
		super();
		ID = iD;
		USER_ID = uSER_ID;
		OPEN_ID = oPEN_ID;
		USER_NAME = uSER_NAME;
		USER_IMG = uSER_IMG;
		GENDER = gENDER;
		TOTAL_GAME_NUM = tOTAL_GAME_NUM;
		MONEY = mONEY;
		USER_AGREE = uSER_AGREE;
		LOGIN_STATUS = lOGIN_STATUS;
		BLACK = bLACK;
		SIGN_UP_TIME = sIGN_UP_TIME;
		LAST_LOGIN_TIME = lAST_LOGIN_TIME;
		CID = cID;
	}
	public User(){}
  
}

