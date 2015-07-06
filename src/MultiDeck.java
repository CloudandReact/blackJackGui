import javax.swing.ImageIcon;


public class MultiDeck extends Deck {

	public MultiDeck(int numberOfDecks){

		int index = 0;	
		for(int i=0;i<numberOfDecks;i++){
			index =0;
			for(Card.FaceValue faceValue : Card.FaceValue.values())
				for(Card.Suit suit: Card.Suit.values()) {
					index++;
					deck.add(new Card(suit, faceValue, new ImageIcon("pictures/" + index + ".png").getImage()));	
				}	
		}
	}
	public boolean isSingleDeck(){
		return false;
	}

}
