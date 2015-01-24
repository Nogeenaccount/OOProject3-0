/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

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
public class MatchLogicTest {

    Team team;
    LineUp lineup;
    
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
    public void getScoreMethodTest(){
        boolean pass = false;
        String str = MatchLogic.getScoreMethod();
        if(str.equals("a beautiful pass from "               )    ||
            str.equals("a wonderful solo from who else than " )   ||
             str.equals("a well taken corner from the foot of ")  ||
              str.equals("a sharp through ball by "             )  )
            pass=true;
        
        assertTrue(pass);  
    }
    
    @Test
    public void randomInjuryTest(){
        boolean pass = false;
        String str = MatchLogic.randomInjury();
        if(str.equals("Broken ankle"        )    ||
            str.equals("Dislocated shoulder" )   ||
             str.equals("Strained ligaments"  )  ||
              str.equals("Torn hamstring"      )  )
            pass=true;
        
        assertTrue(pass);  
    }
    
}
