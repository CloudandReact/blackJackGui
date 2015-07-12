import java.util.ArrayList;

public class Hand {
	private int maxCards =5;
	private int bet;
	private boolean isPlayer;
	private boolean canHit;
	ArrayList<Card> cards = new ArrayList<Card>();
	public Hand(Card firstCard, Card secondCard,int bet, boolean isPlayer){
		this.bet = bet;
		this.isPlayer = isPlayer;
		cards.add(firstCard);
		cards.add(secondCard);
		canHit =true;
	}
	//set Rank
	public int countAces(){
		int ace = 0;
		for(int i=0;i<cards.size();i++){
			if(cards.get(i).getFaceValue()==Card.FaceValue.ACE){
				ace++;

			}
		}
		return ace;
	}
	public int getScore(){
		int handSum =0;
		int aceCount = countAces();
		for(int i=0;i<cards.size();i++){
			handSum += cards.get(i).getFaceValue().getIntValue();

		}
		while(handSum>21 && aceCount>0){
			handSum -=10;
			aceCount--;
		}
		return handSum;

	}
	public boolean isBlackJack2(){
		return getScore()==21 && cards.size()==2;
	}
	public boolean isBusted(){
		return getScore()>21;
	}
	public void setBet(int bet){
		this.bet = bet;
	}
	public boolean canHit(){
		return canHit && getScore()<21 && cards.size()<5;
	}
	public void addCard(Card card){
		if(canHit()){
			cards.add(card);
		}
		else{
			System.out.println("too many cards cant hit");
		}

	}
	public int getBet(){
		return bet;
	}
	public int getSize(){
		return cards.size();
	}
	public Card getCard(int i){
		return cards.get(i);
	}


}
