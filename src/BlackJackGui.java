/*
 * the main class of the game:
 * Setup the GUI and implement event handlers
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BlackJackGui {

	//dealer will stand on DEALER_LIMIT
	static final int DEALER_LIMIT = 17;
	JLabel decksLabel;
	JComboBox<?> handsNumber;
	JComboBox<?> betsCom;
	private JFrame frame;
	//deck of cards
	private Deck deck;
	//draw panel
	private DrawFrame drawPanel;
	//player
	private Player player;
	//dealer
	private Dealer dealer;
	//message text
	private String message = "";
	//utility help class
	private Utility help;
	//game on
	private boolean gameOn;
	private boolean isFirstGame = false;
	private JLabel playerBalance ;
	private Payout payout;

	private JComboBox<?> decks;

	public static void main (String[] args) {
		BlackJackGui gui = new BlackJackGui ();
		gui.init();
	}

	/*
	 * initialize the GUI
	 */
	public void init() {
		//new frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//new draw panel
		drawPanel = new DrawFrame();
		drawPanel.setBounds(0, 0, 600, 500);
		drawPanel.setLayout(null);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(drawPanel);
		frame.setSize(600,500);

		//add new game button to panel
		JButton newGameButton = new JButton("NEW GAME");
		newGameButton.setBounds(145, 415, 100, 35);
		drawPanel.add(newGameButton);
		//add hit button to panel
		JButton hitButton = new JButton("HIT");
		hitButton.setBounds(270, 415, 60, 35);
		drawPanel.add(hitButton);
		//add stand button to panel
		JButton standButton = new JButton("STAND");
		standButton.setBounds(355, 415, 75, 35); 
		drawPanel.add(standButton);
		String multiD[]= {"one deck","five deck"};
		String bets[] =  {"5","10","20"};
		String handsN [] = {"1","2","3"};
		decks= new JComboBox<Object>(multiD);
		betsCom= new JComboBox<Object>(bets);
		JLabel betsLabel = new JLabel("bets");
		JLabel numberOfHands = new JLabel("#H");
		handsNumber= new JComboBox<Object>(handsN);
		numberOfHands.setBounds(440,385,30,30);
		handsNumber.setBounds(440,415,40,30);
		decksLabel = new JLabel("Deck Type");
		betsLabel.setFont(new Font("Arial", Font.BOLD, 20));
		decksLabel.setFont(new Font("Arial", Font.BOLD, 20));
		decksLabel.setBounds(150,100,125,35);
		betsLabel.setBounds(85,375,55,35);
		betsCom.setSelectedIndex(0);
		betsCom.setBounds(85,415,55,35);
		decks.setSelectedIndex(0);
		drawPanel.add(numberOfHands);
		decks.setBounds(150,150,200,35);
		drawPanel.add(handsNumber);
		drawPanel.add(betsLabel);
		drawPanel.add(decksLabel);
		drawPanel.add(decks);
		drawPanel.add(betsCom);
		//register hit button event listener
		hitButton.addActionListener(new HitListener());
		decks.addActionListener( new DeckListener() );
		//register new game button event listener
		newGameButton.addActionListener(new NewGameListener());
		//register stand button event listener
		standButton.addActionListener(new standListener());
		frame.setVisible(true);
	}
	public void resetGame(){
		betsCom.setEnabled(true);
		handsNumber.setEnabled(true);
		//player.setCurrentHand(0);
		//hitButton.setEnabled(false);
		//player.clearHands();
		//dealer.clearHand();
		gameOn = false;

	}

	/*
	 * set up a new game
	 */
	private void setupNewGame() {
		//create a new deck

		//new player
		player = new Player(50);
		//new dealer
		dealer = new Dealer();
		//new help class
		help = new Utility();
		//clear message
		message = "";
		//game is on
		gameOn = true;
		playerBalance= new JLabel("Player balance" + player.getBalance());
		playerBalance.setFont(new Font("Arial", Font.BOLD, 20));
		playerBalance.setBounds(400,25,220,40);
		drawPanel.add(playerBalance);
	}
	public void calculateWinnings(Player player,Dealer dealer){

	}

	/*
	 * new game button event handling
	 */
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//start new game
			if (!gameOn) {

				String command = (String) handsNumber.getSelectedItem();
				handsNumber.setEnabled(false);
				System.out.println(command);
				int betValue =  betsCom.getSelectedIndex();
				betsCom.setEnabled(false);
				if(!isFirstGame){
					setupNewGame();
					isFirstGame = true;
				}
				else {
					gameOn = true;
					player.setCurrentHand(0);
					dealer.clearHand();
					player.clearHands();
				}

				//deal two cards to dealer and player
				int numberOfHands =  Integer.parseInt(command);
				//player.setNumberOfHands(numberOfHands);
				//player number of hands
				Hand dealerHand = new Hand(deck.dealCard(),deck.dealCard(),0,false);
				for(int i=0;i<numberOfHands;i++){
					Hand playerHand = new Hand(deck.dealCard(),deck.dealCard(),betValue,true);
					player.addHand(playerHand);
				}
				player.incrementBalance(numberOfHands*betValue);
				dealer.setHand(dealerHand);

				//check if the player has a blackjack
				for(int i=0;i<player.getNumberOfHands();i++){
					if(player.getHand(i).isBlackJack2() && dealer.getHand().isBlackJack2() ){

					}
					else{

					}
				}
				/* if (help.checkBlackJack(player)) {
					//dealer has also blackjack => tie
					if (help.determineWinner(player, dealer) == Utility.Winner.TIE) {
						message = "Blackjack ! Tie !";
						gameOn = false;				
					} else {
						message = "Blackjack ! You win !";
						gameOn = false;								
					}
				}*/
				//draw hands
				gameOn = true;
				drawPanel.setDealerHand(dealer.getHand());
				drawPanel.setPlayerHand(player.getHands());
				drawPanel.setMessage(message);
				drawPanel.setGameOn(gameOn);
				frame.repaint();

			}
		}
	}
	class DeckListener implements ActionListener{

		public void actionPerformed(ActionEvent deckSelection) {

			if(!gameOn){
				JComboBox box = (JComboBox) deckSelection.getSource();
				String command = (String) box.getSelectedItem();
				System.out.println(command);
				if(command.equals("one deck")){

					deck = new Deck();
					System.out.println("one deck");
				}
				else{
					deck = new MultiDeck(5);
				}

			}
			frame.repaint();
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			decks.setVisible(false);
			decksLabel.setVisible(false);

			frame.repaint();

		}

	}

	/*
	 * hit button event handling
	 */
	class HitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {		
			//only if game is still on
			if (gameOn) {
				//deal a card and add the card to player's hand go through player hands one by one
				int currentHand = player.getCurrentHand();
				System.out.println("current hand "+ currentHand);
				if(!player.getHand(currentHand).isBusted()){
					player.addCard(currentHand,deck.dealCard());
					if(player.getHand(currentHand).canHit() && currentHand+1==player.numberOfHands() ){
						gameOn=false;
						System.out.println("game on false");
						drawPanel.setPlayerHand(player.getHands());
						//drawPanel.setMessage(message);
						drawPanel.setGameOn(gameOn);
						payout = new Payout(dealer,player);
						payout.payoutCalculation();
						drawPanel.setMessage(payout.message());
						frame.repaint();
						resetGame();
					}
					else if(player.getHand(currentHand).isBusted()){
						player.incrementCurrentHand();
						drawPanel.setPlayerHand(player.getHands());
						//drawPanel.setMessage(message);
						drawPanel.setGameOn(gameOn);
						frame.repaint();
					}
					else{
						drawPanel.setPlayerHand(player.getHands());
						//drawPanel.setMessage(message);
						drawPanel.setGameOn(gameOn);
						frame.repaint();
					}




				}


				//check if the player has busted (> 21)
				//winner = help.determineWinner(player, dealer);
				/*if (help.checkBust(player)) {
					message = "Busted ! You lose !";
					gameOn = false;
				}*/
				//draw player's hand

			}

		}
	}

	/*
	 * stand button event handling
	 */
	class standListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (gameOn) {

				int currentHandNumber = player.getCurrentHand();
				System.out.println("current hand " +currentHandNumber + " hands has "+ player.getNumberOfHands() );
				/*Hand currentHand = player.getHand(currentHandNumber);
				if(currentHand.isBusted()){
					System.out.println("current hand busted");	
				}

				else{
					System.out.println("not busted continue");

				}*/
				if(currentHandNumber+1>=player.getNumberOfHands()){

					gameOn = false;
					System.out.println("game over");
				}

				if(gameOn){
					drawPanel.setMessage(message);
					drawPanel.setGameOn(gameOn);
					System.out.println("player hand" + player.getHands()==null);
					frame.repaint();
					player.incrementCurrentHand();
				}
				else{
					payout = new Payout(dealer,player);
					payout.payoutCalculation();
					drawPanel.setPlayerHand(player.getHands());
					drawPanel.setMessage(payout.message());
					drawPanel.setGameOn(gameOn);
					System.out.println("player hand" + player.getHands()==null);
					frame.repaint();
					resetGame();
				}



			}

		}

	}

	/*
	 * class used to draw the panel
	 */
	class DrawFrame extends JPanel {

		//player's hand
		private ArrayList<Hand> playerHands;
		//dealer's hand
		private Hand dealerHand;
		//message
		String message = "";
		//game on
		boolean gameOn;

		/*
		 * set player hand to be drawn on panel
		 */
		public void setPlayerHand(ArrayList<Hand> playerHands) {
			this.playerHands = playerHands;
		}

		/*
		 * set dealer hand to be drawn on panel
		 */
		public void setDealerHand(Hand hand) {
			this.dealerHand = hand;
		}

		/*
		 * set message
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/*
		 * set gameOn signal
		 */
		public void setGameOn(boolean gameOn) {
			this.gameOn = gameOn;
		}

		/*
		 * the actual method used to draw the panel
		 */
		public void paintComponent(Graphics g) {
			//green background
			g.setColor(new Color(0.0f, 0.5f, 0.0f));
			g.fillRect(0,0,this.getWidth(), this.getHeight());
			//draw message
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(new Color(1.0f, 0.0f, 0.0f));
			g.drawString(message,100,50);
			//draw player's hand
			if (playerHands != null) {
				for (int i=0; i < playerHands.size(); i++) {
					Hand currentHand =  playerHands.get(i);
					for(int j=0;j<currentHand.getSize();j++){
						Card currentCard = currentHand.getCard(j);
						Image image = currentCard.getImage();
						g.drawImage(image,(120+i*120+ j*18),(285),this);
					}
				}	
			}
			//draw dealer's hand
			if (dealerHand != null) {
				for (int i=0; i < dealerHand.getSize(); i++) {
					Image image;
					if (gameOn) {
						//first card face down if game on
						if (i == 0) {
							image = new ImageIcon("pictures/b1fv.png").getImage();
						} else {
							image = dealerHand.getCard(i).getImage();			
						}	
						//reveal the card when game ends
					} else {
						image = dealerHand.getCard(i).getImage();
					}
					g.drawImage(image,(240+i*20),(50),this);
				}	
			}
		}

	}
}
