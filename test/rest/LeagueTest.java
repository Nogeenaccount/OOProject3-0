/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author user
 */
public class LeagueTest {
    League testleague;
    
    @Before
    public void before(){
	testleague = League.readResources("resourceV6.xml");
//	assertTrue(League.readResources("resourceV6.xml").equals(testleague));
    }
    
    @Test
    public void readTest() {
	assertEquals(testleague.getLeagueName(), "Barclays Premier League");
	assertEquals(testleague.getRounds(), 38);
	assertEquals(testleague.getTeams().size(), 20);
	assertEquals(testleague.getTeams().get(5).getPlayers().size(), 22);
	assertEquals(testleague.getTeams().get(10).getPlayers().get(16).getPlayerName(), "Robin van Persie");
	assertEquals(testleague.getTeams().get(8).getPlayers().get(8).getEndurance(), 91);
	assertEquals(testleague.getTeams().get(8).getBudget(), 37166000);
	assertEquals(testleague.getTeams().get(11).getStadiumName(), "St. James' Park");
	assertEquals(testleague.getTeams().get(17).getPlayers().get(21).getPlayerName(), "Christian Eriksen");
   	assertEquals(testleague.getTeams().get(17).getPlayers().get(21).getShirtNumber(), 22);
	assertEquals(testleague.getTeams().get(14).getPlayers().get(5).getPrice(),10400000 );
    
        League testleague2 = League.readResources("notexisting.xml");
        League emptyleague = new League("", 0, "", "");
        assertEquals(testleague2,emptyleague);
    
    }
    
    @Test
    public void writeTest() {
	testleague.writeToXML("resourceV6.xml");
	readTest();
    }
    
    @Test
    public void toStringTest() {
	Team teama = testleague.getTeams().get(0);
	Team teamb = testleague.getTeams().get(1);
	testleague.setTeams(new ArrayList<Team>());
	testleague.add(teama);
	testleague.add(teamb);
	
	String str = "<League(" + testleague.getLeagueName() + ", " + testleague.getRounds() + ", " + teama + ", " + teamb + ")>";
	assertEquals(str, testleague.toString());
    }
    
    @Test
    public void addTeamTest() {
	assertEquals(testleague.getTeams().size(), 20);
	testleague.add(testleague.getTeams().get(3));
	assertEquals(testleague.getTeams().size(), 20);
    }
    
    @Test
    public void equalsTest(){
        assertFalse(testleague.equals(new Team("test","test",0)));
        League testleague2 = testleague;
        testleague2.setTeam(0, testleague.getTeams().get(7));
        assertTrue(testleague.equals(testleague2));
    }
    
    @Test
    public void chosenTeamTest(){
        assertEquals(testleague.chosenTeam(),null);
        testleague.setChosenTeam("Arsenal");
        assertEquals(testleague.chosenTeam(),testleague.getTeams().get(0));
    }
    
    @Test
    public void setterTest() {
	testleague.setGameName("test");
	assertEquals(testleague.getGameName(),"test");
	
	testleague.setChosenTeam("Arsenal");
	assertEquals(testleague.getChosenTeam(),"Arsenal");
	
	testleague.setLeagueName("Eredivisie");
	assertEquals(testleague.getLeagueName(),"Eredivisie");
	
	testleague.setRounds(34);
	assertEquals(testleague.getRounds(),34);
    }
    
    
    @Test
    public void getByNameTest(){
        Team test = testleague.getByName("Arsenal");
        assertEquals(test,testleague.getTeams().get(0));
        assertFalse(test.equals(testleague.getTeams().get(1)));
    }
    
    @Test
    public void roundsResetTest(){
        testleague.getTeams().get(5).setDraws(500);
        testleague.setRounds(0);
        testleague.roundsReset();
        assertEquals(testleague.getTeams().get(5).getDraws(),0);
        assertEquals(testleague.getRounds(),38);
    }
    
    @Test
    public void setCustomLineUpTest(){
        String[] testArray = new String[20];
        for(int n=0;n<15;n++){
            testArray[n] = testleague.getTeams().get(0).getPlayers().get(n).getPlayerName();
        }
        testleague.setCustomLineUp(testleague.getTeams().get(0), testArray);
        assertEquals(testleague.getTeams().get(0).getLineUp().getAanvallers().get(0),testleague.getTeams().get(0).getPlayers().get(0));
    }
    
    
    @Test
    public void nextRoundTest(){
        Round test = testleague.nextRound("Speelschema.xml", 5);
        assertEquals(test.getMatch(2).getHomeTeam(),testleague.getByName("Crystal Palace"));
    
        Round testings = testleague.nextRound("Speelschema.xml", 0);
        assertEquals(testings.getMatch(0).getHomeTeam(),testleague.getByName("Manchester United"));
    }
    
    @Test 
    public void processResultTest(){
        int drawsbefore = testleague.getTeams().get(0).getDraws();
        int winsbefore = testleague.getTeams().get(0).getWins();
        int lossesbefore = testleague.getTeams().get(0).getWins();
        //Draw
        testleague.processResult(testleague.getTeams().get(0), testleague.getTeams().get(1), 0, 0);
        assertEquals(testleague.getTeams().get(0).getDraws(),drawsbefore+1);
        //Home won
        testleague.processResult(testleague.getTeams().get(0), testleague.getTeams().get(1), 1, 0);
        assertEquals(testleague.getTeams().get(0).getWins(),winsbefore+1);
        //Away won
        testleague.processResult(testleague.getTeams().get(0), testleague.getTeams().get(1), 0, 1);
        assertEquals(testleague.getTeams().get(0).getLosses(),lossesbefore+1);
    }
    
    
}
