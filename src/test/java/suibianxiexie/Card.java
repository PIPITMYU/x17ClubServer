package suibianxiexie;

public class Card implements Comparable<Card>{
	
	private int origin;//三位数牌数
	
	private int type;//黑红梅方
	
	private int symble;//AKQJ987654321 牌点
	
	private boolean isChu;
	
	private int sortNum;//排序用数 chu---16 2---15 1---14
	
	public int getSymble() {
		return symble;
	}

	public void setSymble(int symble) {
		this.symble = symble;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}
	

	public boolean isChu() {
		return isChu;
	}
	
	
	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public void setChu(boolean isChu) {
		//只有A 可为储
		if(this.symble!=1)
			return;
		this.isChu = isChu;
		//设置储牌
		this.sortNum = 16;
	}
	
	public Card(int src){
		this.origin = src;
		this.type = src/100;
		this.symble = src%100;
		this.isChu = false;
		if(this.symble==1)
			this.sortNum = 14;
		else if(this.symble==2)
			this.sortNum = 15;
		else{
			this.sortNum = this.symble;
		}
	}

	public boolean isEqual(int src){
		return this.origin == src;
	}
	@Override
	public int compareTo(Card o) {
		int i = this.getSortNum() - o.getSortNum();//先按照大小排序
		
		if(i == 0){ 
            return this.getType() - o.getType();//如果大小相等了再花色进行排序  
        }  
		return -i;
	}

	@Override
	public String toString() {
		return "Card [origin=" + origin + ", type=" + type + ", symble="
				+ symble + ", isChu=" + isChu + ", sortNum=" + sortNum + "]";
	}

}
