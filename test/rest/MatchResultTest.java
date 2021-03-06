/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class MatchResultTest {
    League testLeague;
    Team team;
    LineUp lineup;

    public MatchResultTest() throws FileNotFoundException {
        this.testLeague = League.readResources("resourceV6.xml");
    }
    
    @Before
    public void before(){
	team = new Team("Chelsea", "Arena", 10000);
	Player p1 = new Player("p1", 1, 500, 10, 20, 30, "gk", 0, 0);
	Player p2 = new Player("p2", 2, 500, 20, 30, 40, "gk", 0, 0);
	Player p3 = new Player("p3", 3, 500, 30, 40, 50, "gk", 0, 0);
	Player p4 = new Player("p3", 3, 500, 40, 50, 60, "gk", 0, 0);
	lineup = new LineUp();
	lineup.addAanvaller(p1);
	lineup.addAanvaller(p2);
	lineup.addAanvaller(p3);
	lineup.addMiddenvelder(p2);
	lineup.addMiddenvelder(p3);
	lineup.addMiddenvelder(p4);
	lineup.addVerdediger(p1);
	lineup.addVerdediger(p2);
	lineup.addVerdediger(p3);
	lineup.addVerdediger(p4);
	lineup.setKeeper(p1);
	team.setLineUp(lineup);
    }
    
    @Test
    public void testOffenceSum(){
	assertEquals(150,MatchResult.offenceSum(team),0.1);
    }
    
    @Test
    public void testDefenceSum(){
	assertEquals(285,MatchResult.defenceSum(team),0.1);
    }
    
    @Test
    public void testEnduranceSum(){
	assertEquals(260,MatchResult.enduranceSum(team),0.1);
    }
    
    @Test
    public void testScored(){
        assertTrue(MatchResult.scored(1000,0,100,100,0)==1);
        assertTrue(MatchResult.scored(1000, 2000, 100, 100, 0)==0);
    }
    
    @Test
    public void testGetResult(){
        Match result = MatchResult.getResult(testLeague.getByName("Manchester United"),testLeague.getByName("Burnley"),15);
        assertTrue(result.getHomeScore()>=0);
        assertTrue(result.getAwayScore()>=0);
        assertEquals(result.getHomeTeam(),testLeague.getByName("Manchester United"));
        assertEquals(result.getAwayTeam(),testLeague.getByName("Burnley"));
    }
}
