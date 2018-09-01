package suibianxiexie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DealCardList {
	public static Map<String,Object> carDealCard(List<Card> lastCard){
		List<Card> playCard = new ArrayList<Card>();
		//统计手牌个数
		Map<Integer,Integer> mapCard = new HashMap<Integer, Integer>();
		for(int i=0;i<playCard.size();i++){
			int sortNum = playCard.get(i).getSortNum();
			if(mapCard.get(sortNum)==null){
				mapCard.put(sortNum, 1);
			}else{
				mapCard.put(sortNum, mapCard.get(sortNum)+1);
			}
		}
		Map<String,Object>  map = new HashMap<String,Object>();
		Integer lastCardSize = lastCard.size();
		switch (lastCardSize) {
		case 1://单牌
			List<Integer> simples = new ArrayList<Integer>();
			for(Card card:playCard){
				if(card.getSortNum()>lastCard.get(1).getSortNum()){
					simples.add(card.getOrigin());
				}
			}
			map.put("simple", simples);
			//是否含有轰，返回
			map.put("hong", getHongList(mapCard,playCard));
			//是否含有炸，返回
			map.put("zha", getZhaList(mapCard,playCard));
			//是否含有独储
			map.put("doubleChu", getDoubleChu(mapCard, playCard));
			map.put("double","");
			map.put("shun","");
			map.put("doubleShun","");
			break;
		case 2://对子
			
			break;
		default:
			break;
		}
		return map;
	}
	//检测轰
	public static List<List<Integer>> getHongList(Map<Integer,Integer> map,List<Card> playCard){
		List<List<Integer>> hongList = new ArrayList<List<Integer>>();
		
		//检索出 轰或炸
		Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
			while (it.hasNext()) {
			Map.Entry<Integer, Integer> entry = it.next();
			//轰
			if(entry.getValue()==3){
				List<Integer> hong = new ArrayList<Integer>();
				for(Card card:playCard){
					if(card.getSortNum()==entry.getKey())
						hong.add(card.getOrigin());
				}
				hongList.add(hong);
			}
			//炸
			if(entry.getValue()==4){
				for(int i=0;i<playCard.size();i++){
					if(playCard.get(i).getSortNum()==entry.getKey()){
						List<Integer> hong1 = new ArrayList<Integer>();
						hong1.add(playCard.get(i).getOrigin());
						hong1.add(playCard.get(i+1).getOrigin());
						hong1.add(playCard.get(i+2).getOrigin());
						hongList.add(hong1);
						List<Integer> hong2 = new ArrayList<Integer>();
						hong2.add(playCard.get(i).getOrigin());
						hong2.add(playCard.get(i+1).getOrigin());
						hong2.add(playCard.get(i+3).getOrigin());
						hongList.add(hong2);
						List<Integer> hong3 = new ArrayList<Integer>();
						hong3.add(playCard.get(i).getOrigin());
						hong3.add(playCard.get(i+2).getOrigin());
						hong3.add(playCard.get(i+3).getOrigin());
						hongList.add(hong3);
						List<Integer> hong4 = new ArrayList<Integer>();
						hong4.add(playCard.get(i+1).getOrigin());
						hong4.add(playCard.get(i+2).getOrigin());
						hong4.add(playCard.get(i+3).getOrigin());
						hongList.add(hong4);
						break;
					}
				}
			}
		}
		return hongList;
	}
	//检测炸
	public static List<List<Integer>> getZhaList(Map<Integer,Integer> map,List<Card> playCard){
		List<List<Integer>> zhaList = new ArrayList<List<Integer>>();
		//检索出 轰或炸
		Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
			while (it.hasNext()) {
			Map.Entry<Integer, Integer> entry = it.next();
			//炸
			if(entry.getValue()==4){
				for(int i=0;i<playCard.size();i++){
					if(playCard.get(i).getSortNum()==entry.getKey()){
						List<Integer> zha = new ArrayList<Integer>();
						zha.add(playCard.get(i).getOrigin());
						zha.add(playCard.get(i+1).getOrigin());
						zha.add(playCard.get(i+2).getOrigin());
						zha.add(playCard.get(i+3).getOrigin());
						zhaList.add(zha);
						break;
					}
				}
			}
		}
		return zhaList;
	}
	//检测对储
	public static List<Integer> getDoubleChu(Map<Integer,Integer> map,List<Card> playCard){
		List<Integer> doubleChu = new ArrayList<Integer>();
		if(map.get(16)==null){
			return doubleChu;
		}
		if(map.get(16)==2){
			for(Card c:playCard){
				if(c.getSortNum()==16){
					doubleChu.add(c.getOrigin());
				}
			}
		}
		return doubleChu;
	}
	//测试
	public static void main(String[] args) {
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(103));
		cards.add(new Card(203));
		cards.add(new Card(303));
		cards.add(new Card(403));
		cards.add(new Card(104));
		cards.add(new Card(105));
		cards.add(new Card(106));
		cards.add(new Card(107));
		cards.add(new Card(108));
		cards.add(new Card(208));
		cards.add(new Card(308));
		cards.add(new Card(408));
		cards.add(new Card(113));
		cards.add(new Card(213));
		cards.add(new Card(313));
		cards.add(new Card(413));
		Map<Integer,Integer> mapCard = new HashMap<Integer, Integer>();
		for(int i=0;i<cards.size();i++){
			int sortNum = cards.get(i).getSortNum();
			if(mapCard.get(sortNum)==null){
				mapCard.put(sortNum, 1);
			}else{
				mapCard.put(sortNum, mapCard.get(sortNum)+1);
			}
		}
	}
}
