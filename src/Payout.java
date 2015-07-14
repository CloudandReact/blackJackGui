
public class Payout {
	private Dealer dealer;
	private Player player;
	private String message;
	private int totalWinnings=0;
	private int totalLoses=0;
	public Payout(Dealer dealer,Player player){
		this.dealer = dealer;
		this.player = player;
	}
	public void payoutCalculation(){
		//different payouts for different situations
		for(int i=0;i<player.numberOfHands();i++){
			Hand dealerHand = dealer.getHand();
			System.out.println(player.getHand(i));
			if(dealerHand==null||player.getHands()==null){
				return;
			}
			if(player.getHand(i).isBusted()){
				//skip
				totalLoses += player.getHand(i).getBet();

			}
			else if(player.getHand(i).isBlackJack2() && dealerHand.isBlackJack2()){
				totalWinnings += player.getHand(i).getBet();
				player.incrementBalance(player.getHand(i).getBet());

			}
			else if (player.getHand(i).isBlackJack2() && !dealerHand.isBlackJack2()){
				totalWinnings += player.getHand(i).getBet()*3;
				player.incrementBalance(player.getHand(i).getBet()*3);
			}


			else if(player.getHand(i).getScore()>dealer.getHand().getScore() || dealerHand.isBusted()){
				player.incrementBalance(player.getHand(i).getBet()*2);
				totalWinnings += player.getHand(i).getBet();
			}
			else{
				totalLoses += player.getHand(i).getBet();
			}
		}
	}
	public String message(){
		message ="Your winnings are " + totalWinnings + " your losses are" + totalLoses;
		return message;
	}

}
