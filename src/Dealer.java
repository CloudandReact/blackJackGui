

public class Dealer {
	private Hand hand;

	public void setHand(Hand hand){
		this.hand = hand;
	}
	public void addCard(Card c){
		//extra dealer rule
		if(hand.getScore()>=17)
			hand.addCard(c);
		System.out.println("can't add");

	}
	public Hand getHand(){
		return hand;
	}
	public void clearHand(){
		hand = null;
	}

}
