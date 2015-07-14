
public class DeckEmptyException  extends Exception{
	public DeckEmptyException(){
		super("Deck is empty");
	}
	public DeckEmptyException(String s){
		super(s);
	}
	public String message(){
		return "Deck is empty";
	}


}
