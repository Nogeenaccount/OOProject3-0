package rest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoundTest {
    
    League testLeague = League.readResources("resourceV6.xml");
    
    @Test
    public void getMatchTest(){
        assertTrue(true);
    }
    
    @Test
    public void containsTest(){
        Match test = new Match(testLeague.getByName("Manchester United"),testLeague.getByName("Swansea City"));
        assertTrue(testLeague.nextRound("Speelschema.xml",0).contains(test));
        assertFalse(testLeague.nextRound("Speelschema.xml", 1).contains(test));
    }
    
    @Test
    public void getOpponentTest(){
        Team manU = testLeague.getByName("Manchester United");
        Team swa = testLeague.getByName("Swansea City");
        Round testRound = testLeague.nextRound("Speelschema.xml", 0);
        assertEquals(testRound.getOpponent(swa),manU);
        assertEquals(testRound.getOpponent(manU),swa);
    }
}
