package states;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
//FINISHED WITH FILLER
public class MenuTransfers extends State {
        //Images
        String buttonAttemptImage = "GUIFiles\\buttonAttempt.png";
	String buttonOfferAcceptImage = "GUIFiles\\buttonBuyPlayer.png";
	String buttonBackImage = "GUIFiles\\buttonDone.png";
	String panelPanelImage = "GUIFiles\\FootbalStadiumSize.png";
    
        //Components
        JTextField teamPrompt = new JTextField();
	JList playerList = new JList();
	JTextField playerPrompt = new JTextField();
	JTextField attemptPricePrompt = new JTextField();
	JList buyOrSellList = new JList(GivebuyOrSellList());
	JList teamList = new JList(GiveteamList());
	JTextField attemptPrice = new JTextField();
	JButton buttonAttempt = new JButton(new ImageIcon(buttonAttemptImage));
	JList offerList = new JList(GiveofferList());;
	JButton offerAccept = new JButton(new ImageIcon(buttonOfferAcceptImage));
	JTextField budgetDisplay = new JTextField();
        JTextField buyOrSellPrompt = new JTextField();
        JTextField lastAction = new JTextField();
        
        //Temporary storage
        ArrayList<String> alreadyTried = new ArrayList(); //Stores player names for buy/sell
        ArrayList<String> tempOffersTried = new ArrayList ();
        
    public MenuTransfers() {
    }

    @Override @SuppressWarnings({"rawtypes", "unchecked"})
    public void createGUI() {
	layout = new GridBagLayout();
	this.setLayout(layout);
	c = new GridBagConstraints();

        //JTextField budgetDisplay
	budgetDisplay.setOpaque(true);
	budgetDisplay.setPreferredSize(new Dimension(400, 20));
	budgetDisplay.setEditable(false);
	budgetDisplay.setText("Your budget: " + StateManager.getLeague().getTeamByName(StateManager.getLeague().getChosenTeam()).getBudget());
	budgetDisplay.setBackground(Color.decode("#525151"));
	budgetDisplay.setForeground(Color.white);
	budgetDisplay.setMinimumSize(new Dimension(400, 20));
	budgetDisplay.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 1;
	layout.setConstraints(budgetDisplay, c);
	this.add(budgetDisplay);
        
        //JTextField lastAction
        lastAction.setOpaque(true);
	lastAction.setPreferredSize(new Dimension(400, 20));
	lastAction.setEditable(false);
	lastAction.setText("Last Action: " + "---");
	lastAction.setBackground(Color.decode("#525151"));
	lastAction.setForeground(Color.white);
	lastAction.setMinimumSize(new Dimension(400, 20));
	lastAction.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 2;
	c.gridy = 1;
	layout.setConstraints(lastAction, c);
	this.add(lastAction);

	//JTextField buyOrSellPrompt
	buyOrSellPrompt.setOpaque(true);
	buyOrSellPrompt.setPreferredSize(new Dimension(400, 20));
	buyOrSellPrompt.setEditable(false);
	buyOrSellPrompt.setText("Please choose whether to buy or sell a player: ");
	buyOrSellPrompt.setBackground(Color.decode("#525151"));
	buyOrSellPrompt.setForeground(Color.white);
	buyOrSellPrompt.setMinimumSize(new Dimension(400, 20));
	buyOrSellPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 2;
	layout.setConstraints(buyOrSellPrompt, c);
	this.add(buyOrSellPrompt);

	//JList buyOrSellList
	buyOrSellList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	buyOrSellList.setVisibleRowCount(-1);
	buyOrSellList.setPreferredSize(new Dimension(400, 40));
	buyOrSellList.setBackground(Color.decode("#525151"));
	buyOrSellList.setForeground(Color.white);
	buyOrSellList.setMinimumSize(new Dimension(400, 34));
	buyOrSellList.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 3;
	layout.setConstraints(buyOrSellList, c);
	this.add(buyOrSellList);
	buyOrSellList.addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (buyOrSellList.getValueIsAdjusting() && buyOrSellList.getSelectedValue().equals("Buy") && teamList.isSelectionEmpty() == false) {
                    playerPrompt.setText("Please select a player from this team:");
                    playerList.setListData(GiveplayerList((String)buyOrSellList.getSelectedValue(), (String)teamList.getSelectedValue()));
                    NoAttemptAllowed();
		}
		if (buyOrSellList.getValueIsAdjusting() && buyOrSellList.getSelectedValue().equals("Sell") && teamList.isSelectionEmpty() == false) {
		    playerPrompt.setText("Please select a player from your team:");
                    playerList.setListData(GiveplayerList((String)buyOrSellList.getSelectedValue(), (String)teamList.getSelectedValue()));
                    NoAttemptAllowed();
		}
		if (buyOrSellList.getValueIsAdjusting() && buyOrSellList.getSelectedValue().equals("Buy")) {
		    teamPrompt.setText("Please choose a team to buy a player from: ");
		}
		if (buyOrSellList.getValueIsAdjusting() && buyOrSellList.getSelectedValue().equals("Sell")) {
		    teamPrompt.setText("Please choose a team to sell a player to: ");
		}
	    }
	});

	//JTextField teamPrompt
	teamPrompt.setOpaque(true);
	teamPrompt.setPreferredSize(new Dimension(400, 20));
	teamPrompt.setEditable(false);
	teamPrompt.setText("---");
	teamPrompt.setBackground(Color.decode("#525151"));
	teamPrompt.setForeground(Color.white);
	teamPrompt.setMinimumSize(new Dimension(400, 20));
	teamPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 4;
	layout.setConstraints(teamPrompt, c);
	this.add(teamPrompt);

	//JList teamList
	teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	teamList.setVisibleRowCount(-1);
	teamList.setBackground(Color.decode("#525151"));
	teamList.setForeground(Color.white);
	teamList.setMinimumSize(new Dimension(400, 200));
	teamList.setFont(new Font("Arial", Font.PLAIN, 12));
	JScrollPane teamScroller = new JScrollPane(teamList);
	teamScroller.setPreferredSize(new Dimension(400, 200));
	teamScroller.setMinimumSize(new Dimension(400, 200));
	c.gridx = 1;
	c.gridy = 5;
	layout.setConstraints(teamScroller, c);
	this.add(teamScroller);
	teamList.addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (teamList.getValueIsAdjusting() && buyOrSellList.isSelectionEmpty() == false &&buyOrSellList.getSelectedValue().equals("Buy")) {
                    playerPrompt.setText("Please select a player from this team:");
		    playerList.setListData(GiveplayerList((String)buyOrSellList.getSelectedValue(), (String)teamList.getSelectedValue()));
                    NoAttemptAllowed();
		}
		if (teamList.getValueIsAdjusting() && buyOrSellList.isSelectionEmpty() == false &&buyOrSellList.getSelectedValue().equals("Sell")) {
		    playerPrompt.setText("Please select a player from your team:");
                    playerList.setListData(GiveplayerList((String)buyOrSellList.getSelectedValue(), (String)teamList.getSelectedValue()));
                    NoAttemptAllowed();
		}
	    }
	});

	//Prompt choosing a player
	playerPrompt.setOpaque(true);
	playerPrompt.setPreferredSize(new Dimension(400, 20));
	playerPrompt.setEditable(false);
	playerPrompt.setText("---");
	playerPrompt.setBackground(Color.decode("#525151"));
	playerPrompt.setForeground(Color.white);
	playerPrompt.setMinimumSize(new Dimension(400, 20));
	playerPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 6;
	layout.setConstraints(playerPrompt, c);
	this.add(playerPrompt);

	//Choosing a player to buy/sell
	playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	playerList.setVisibleRowCount(-1);
	playerList.setBackground(Color.decode("#525151"));
	playerList.setForeground(Color.white);
	playerList.setMinimumSize(new Dimension(400, 200));
	playerList.setFont(new Font("Arial", Font.PLAIN, 12));
	JScrollPane playerScroller = new JScrollPane(playerList);
	playerScroller.setPreferredSize(new Dimension(400, 200));
	playerScroller.setMinimumSize(new Dimension(400, 200));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 7;
	c.gridheight = 2;
	layout.setConstraints(playerScroller, c);
	this.add(playerScroller);
	playerList.addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (teamList.isSelectionEmpty() == false && buyOrSellList.isSelectionEmpty() == false && playerList.isSelectionEmpty() == false && alreadyTried.contains((String)playerList.getSelectedValue()) == false) {
		    AttemptAllowed();
		} else {
		    NoAttemptAllowed();
		}
	    }
	});
	c.gridheight = 1;
        
	//Prompt price attempt
	attemptPricePrompt.setOpaque(true);
	attemptPricePrompt.setPreferredSize(new Dimension(400, 20));
	attemptPricePrompt.setEditable(false);
	attemptPricePrompt.setText("---");
	attemptPricePrompt.setBackground(Color.decode("#525151"));
	attemptPricePrompt.setForeground(Color.white);
	attemptPricePrompt.setMinimumSize(new Dimension(400, 20));
	attemptPricePrompt.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 9;
	layout.setConstraints(attemptPricePrompt, c);
	this.add(attemptPricePrompt);

	//Price for attempt
	attemptPrice.setOpaque(true);
	attemptPrice.setPreferredSize(new Dimension(400, 20));
	attemptPrice.setEditable(false);
	attemptPrice.setBackground(Color.decode("#525151"));
	attemptPrice.setForeground(Color.white);
	attemptPrice.setMinimumSize(new Dimension(400, 20));
	attemptPrice.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 10;
	layout.setConstraints(attemptPrice, c);
	this.add(attemptPrice);

	//Attempt action
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 11;
	createButton(buttonAttempt, "", c, layout);
	buttonAttempt.setEnabled(false);
	buttonAttempt.setPreferredSize(new Dimension(400, 50));
	buttonAttempt.setMinimumSize(new Dimension(400, 50));
	buttonAttempt.setMargin(new Insets(0, 0, 0, 0));
	buttonAttempt.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (alreadyTried.contains((String)playerList.getSelectedValue()) == false) {
                    doAttempt();
                    budgetDisplay.setText("Your budget: " + StateManager.getLeague().getTeamByName(StateManager.getLeague().getChosenTeam()).getBudget());
                    playerList.setListData(GiveplayerList((String)buyOrSellList.getSelectedValue(), (String)teamList.getSelectedValue()));
		}
	    }
	});

	//Go back
	JButton buttonBack = new JButton(new ImageIcon(buttonBackImage));
	c.weightx = 0.5;
	c.gridx = 2;
	c.gridy = 8;
	c.gridheight = 4;
	buttonBack.setMargin(new Insets(0, 0, 0, 0));
	createButton(buttonBack, "", c, layout);
        buttonBack.setPreferredSize(new Dimension(400, 240));
        buttonBack.setMinimumSize(new Dimension(400, 240));
	
	attachStateChanger(buttonBack, new MenuBetweenRounds());
	c.gridheight = 1;
        buttonBack.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < tempOffersTried.size(); i++) {
                    StateManager.getLeague().getOffersMade().remove(tempOffersTried.get(i));
                }
                alreadyTried = new ArrayList();
                tempOffersTried = new ArrayList();
	    }
	});
        
	//Prompt Offers
	JTextField lookAtOffersPrompt = new JTextField();
	lookAtOffersPrompt.setOpaque(true);
	lookAtOffersPrompt.setPreferredSize(new Dimension(400, 20));
	lookAtOffersPrompt.setEditable(false);
	lookAtOffersPrompt.setText("Here are offers from other teams: ");
	lookAtOffersPrompt.setBackground(Color.decode("#525151"));
	lookAtOffersPrompt.setForeground(Color.white);
	lookAtOffersPrompt.setMinimumSize(new Dimension(400, 20));
	lookAtOffersPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
	c.weightx = 0.5;
	c.gridx = 2;
	c.gridy = 2;
	layout.setConstraints(lookAtOffersPrompt, c);
	this.add(lookAtOffersPrompt);

	//Accept Offers
	c.weightx = 0.5;
	c.gridx = 2;
	c.gridy = 7;
	createButton(offerAccept, "", c, layout);
	offerAccept.setEnabled(false);
	offerAccept.setPreferredSize(new Dimension(400, 50));
	offerAccept.setMinimumSize(new Dimension(400, 50));
	offerAccept.setMargin(new Insets(0, 0, 0, 0));
	offerAccept.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
                if (tempOffersTried.contains((String)offerList.getSelectedValue()) == false) {
                    doOffer();
                    budgetDisplay.setText("Your budget: " + StateManager.getLeague().getTeamByName(StateManager.getLeague().getChosenTeam()).getBudget());
                    playerList.setListData(GiveplayerList((String)buyOrSellList.getSelectedValue(), (String)teamList.getSelectedValue()));
		}
	    }
	});

	//Offer List
	offerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	offerList.setVisibleRowCount(-1);
	offerList.setBackground(Color.decode("#525151"));
	offerList.setForeground(Color.white);
	offerList.setMinimumSize(new Dimension(400, 278));
	offerList.setFont(new Font("Arial", Font.PLAIN, 12));
	JScrollPane offerScroller = new JScrollPane(offerList);
	offerScroller.setPreferredSize(new Dimension(400, 278));
	offerScroller.setMinimumSize(new Dimension(400, 278));
	c.weightx = 0.5;
	c.gridx = 2;
	c.gridy = 3;
	c.gridheight = 4;
	layout.setConstraints(offerScroller, c);
	this.add(offerScroller);
	offerList.addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (offerList.isSelectionEmpty() == false && tempOffersTried.contains((String) offerList.getSelectedValue()) == false) {
		    offerAccept.setEnabled(true);
		} else {
		    offerAccept.setEnabled(false);
		}
	    }
	});

	c.gridheight = 1;

	//Padding
	JTextArea invisi2 = new JTextArea();
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 0;
	layout.setConstraints(invisi2, c);
	invisi2.setPreferredSize(new Dimension(100, 100));
	invisi2.setOpaque(false);
	invisi2.setEditable(false);
	invisi2.setMargin(new Insets(50, 0, 0, 0));
	this.add(invisi2);

	c.weightx = 0.5;
	c.gridheight = 13;
	c.gridwidth = 4;
	c.gridx = 0;
	c.gridy = 0;
	ImagePanel panel = new ImagePanel(new ImageIcon(panelPanelImage).getImage(), c, layout);
	this.add(panel);
    }
    
    public String[] GiveplayerList(String buyOrSell, String team) {
        ArrayList<String> playerList = new ArrayList();
        rest.Team team1 = StateManager.getLeague().getTeamByName(team);
        
        if (buyOrSell.equals("Buy")) {
            for (int i = 0; i < team1.getPlayers().size(); i++) {
                playerList.add(team1.getPlayers().get(i).getPlayerName());
            }
            String[] playerListArray = new String[playerList.size()];
            for (int m = 0; m < playerList.size(); m++) {
		playerListArray[m] = playerList.get(m);
            }
            return playerListArray;
        }
        
        if (buyOrSell.equals("Sell")) {
            for (int r = 0; r < StateManager.getLeague().chosenTeam().getPlayers().size(); r++) {
                playerList.add(StateManager.getLeague().chosenTeam().getPlayers().get(r).getPlayerName());
            }
            String[] playerListArray = new String[playerList.size()];
            for (int m = 0; m < playerList.size(); m++) {
                playerListArray[m] = playerList.get(m);
            }
            return playerListArray;
        }
        System.out.println("JList playerList: data error");
        return new String[1];
    }
    
    public void NoAttemptAllowed() {
        attemptPricePrompt.setText("---");
        attemptPrice.setEditable(false);
        buttonAttempt.setEnabled(false);
    }
    
    public void AttemptAllowed() {
        attemptPricePrompt.setText("For what price would you like to try this?");
	attemptPrice.setEditable(true);
	buttonAttempt.setEnabled(true);
    }
    
    public void doAttempt() {
        String soortTransactie = (String) buyOrSellList.getSelectedValue();
        String offerFormat = (String) teamList.getSelectedValue() + " " + (String) attemptPrice.getText() + " " + (String) playerList.getSelectedValue();
        boolean boolean1 = StateManager.getLeague().Transfer(soortTransactie, offerFormat);
        alreadyTried.add((String) playerList.getSelectedValue());
        
        if (boolean1) {
            lastAction.setText("You succesfully bought/sold: " + (String) playerList.getSelectedValue());
            
            //Redo default lineup
            rest.Team team1 = StateManager.getLeague().getTeamByName((String) teamList.getSelectedValue());
            StateManager.getLeague().chosenTeam().setLineUp(StateManager.getLeague().chosenTeam().getDefaultLineUp());
            team1.setLineUp(team1.getDefaultLineUp());
        }
        else {
            lastAction.setText("You failed at buying/selling: " + (String) playerList.getSelectedValue());
        }
    }
    
    public void doOffer() {
        String offer = (String) offerList.getSelectedValue();
        boolean boolean1 = StateManager.getLeague().TransferOffer("Sell", offer);
        tempOffersTried.add((String) offerList.getSelectedValue());
        if (boolean1) {
            lastAction.setText("You acepted this offer: " + offer);
            
            //Redo default lineup
            Scanner sc = new Scanner(offer);
            String team = sc.next();
            if (sc.hasNextInt() == false) {
                team = team + " " + sc.next();
            }
            if (sc.hasNextInt() == false) {
                team = team + " " + sc.next();
            }
            rest.Team team1 = StateManager.getLeague().getTeamByName(team);
            StateManager.getLeague().chosenTeam().setLineUp(StateManager.getLeague().chosenTeam().getDefaultLineUp());
            team1.setLineUp(team1.getDefaultLineUp());
        }
    }

    public String[] GivebuyOrSellList() {
        String[] buyOrSell = {"Buy", "Sell"};
        return buyOrSell;
    }
    
    public String[] GiveteamList() {
        String[] teamList = new String[20];
	rest.League league = rest.League.readResources("SaveGame.xml");
	for (int i = 0; i < league.getTeams().size(); i++) {
	    teamList[i] = league.getTeams().get(i).getTeamName();
	}
        return teamList;
    }
    
    public String[] GiveofferList() {
        String[] array2 = new String[StateManager.getLeague().getOffersMade().size()];
        for(int j = 0; j < StateManager.getLeague().getOffersMade().size(); j++) {
            array2[j] = StateManager.getLeague().getOffersMade().get(j);
        }
        return array2;
    }
}
