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
 * @author Matthias Tavasszy
 */
public class MatchLogicTest {

    Team team1;
    Team team2;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    LineUp lineup;
    
    @Before
    public void before(){
	team1 = new Team("Chelsea", "Arena", 10000);
	team2 = new Team("Manchester", "idk", 11000);
        
	p1 = new Player("p1", 1, 500, 10, 20, 30, "F", 0, 0);
	p2 = new Player("p2", 2, 500, 20, 30, 40, "M", 0, 0);
	p3 = new Player("p3", 3, 500, 30, 40, 50, "D", 0, 0);
	p4 = new Player("p4", 3, 500, 40, 50, 60, "G", 0, 0);
        
        team1.add(p1);
        team1.add(p2);
        team1.add(p3);
        team1.add(p4);
        team2.add(p1);
        team2.add(p2);
        team2.add(p3);
        team2.add(p4);
        
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
	team1.setLineUp(lineup);
        team2.setLineUp(lineup);
    }
    
    @Test
    public void LineGeneratorTest(){
        Update u = new Update(0, p1, 35);
        String newLine = System.getProperty("line.separator");
        String tab = "    ";
        MatchLogic temp = new MatchLogic(15,team1,team2);
        // CASE U.TYPE==0
        assertEquals(temp.LineGenerator(u, team1, team2),"");
        
        u.setType(1);
        String str = temp.LineGenerator(u, team1, team2);
        
        // CASE U.TYPE==1
        assertTrue(str.equals(" 35'         Booking at Chelsea!"+newLine+"        p1 is shown a yellow card after a "+newLine+"        hazardous challenge on p1!"+newLine+newLine) ||
                    str.equals(" 35'         Booking at Chelsea!"+newLine+"        p1 is shown a yellow card after a "+newLine+"        hazardous challenge on p2!"+newLine+newLine) ||
                     str.equals(" 35'         Booking at Chelsea!"+newLine+"        p1 is shown a yellow card after a "+newLine+"        hazardous challenge on p3!"+newLine+newLine) ||
                      str.equals(" 35'         Booking at Chelsea!"+newLine+"        p1 is shown a yellow card after a "+newLine+"        hazardous challenge on p4!"+newLine+newLine) );
        
        u.setType(2);
        str = temp.LineGenerator(u, team1, team2);
        
        // CASE U.TYPE==2
        assertTrue(str.equals(" 35'         Red card at Chelsea!"+newLine+"        p1 sees red after a schandalous "+newLine+"        charge on p1!"+newLine+newLine) ||
                    str.equals(" 35'         Red card at Chelsea!"+newLine+"        p1 sees red after a schandalous "+newLine+"        charge on p2!"+newLine+newLine) ||
                     str.equals(" 35'         Red card at Chelsea!"+newLine+"        p1 sees red after a schandalous "+newLine+"        charge on p3!"+newLine+newLine) ||
                      str.equals(" 35'         Red card at Chelsea!"+newLine+"        p1 sees red after a schandalous "+newLine+"        charge on p4!"+newLine+newLine) );
        
        u.setType(3);
        str = temp.LineGenerator(u, team1, team2);
        
        // CASE U.TYPE==3
        assertTrue(str.equals(" 35'     Injury at Chelsea!"+newLine+"        p1 falls down like a "+newLine+"        dying swallow, and "+newLine+"        will most likely not play next match!"+newLine+newLine) ||
                    str.equals(" 35'     Injury at Chelsea!"+newLine+"        p1 falls down like a "+newLine+"        dying swallow, and "+newLine+"        will most likely not play next match!"+newLine+newLine) ||
                     str.equals(" 35'     Injury at Chelsea!"+newLine+"        p1 falls down like a "+newLine+"        dying swallow, and "+newLine+"        will most likely not play next match!"+newLine+newLine) ||
                      str.equals(" 35'     Injury at Chelsea!"+newLine+"        p1 falls down like a "+newLine+"        dying swallow, and "+newLine+"        will most likely not play next match!"+newLine+newLine) );

        u.setType(4);
        str = temp.LineGenerator(u, team1, team2);
        //System.out.println(str);
       
        // CASE U.TYPE==4 
        // generate all possibilities with the for-loops, then check if equal to outcome
        boolean pass = false;
        String[] playerNames = {"p1","p2","p3","p4"};
        String poss;
        
        for(int i = 0; i<3; i++){
            for(int p = 0; p<3; p++){
                poss = " 35'     Chelsea SCORES!"+newLine+"        "+playerNames[i]+" scores after "+newLine+"        a well taken corner from the foot of "+playerNames[p]+"!"+newLine+newLine;
                //System.out.println(poss);
                if (str.equals(poss));
                    pass = true;
                
            }
        }
        assertTrue(pass);
    }
    
    @Test
    public void findOwnMatchTest() throws FileNotFoundException{
        League testLeague = League.readResources("ResourceV6.xml");
        states.StateManager.setLeague(testLeague);
        states.StateManager.getLeague().setChosenTeam("Chelsea");

        Match testMatch = new Match(states.StateManager.getLeague().getTeamByString("Burnley"), states.StateManager.getLeague().getTeamByString("Chelsea"));
        
        //System.out.println(testMatch.toString());
        //System.out.println(MatchLogic.findOwnMatch(0).toString());
        
        assertEquals(MatchLogic.findOwnMatch(0), testMatch);
    }
    
    @Test
    public void tickHomeTest(){
        MatchLogic m = new MatchLogic(35, team1, team2);
        Update uT = m.tickHome();
        System.out.println(uT.toString());
        Update uM = new Update(0, p1, 2);
        
        boolean pass = false;
        
        for(int i = 0; i<3; i++){
            for(int p = 0; p<team1.getPlayers().size(); p++){
                uM.setType(i);
                uM.setSpeler(team1.getPlayers().get(p));
                
                System.out.println(uM.toString());

                if(uM.equals(uT))
                    pass = true;
            }
        }
        
        assertTrue(pass);
    }
    
    @Test
    public void getScoreMethodTest(){
        boolean pass = false;
        String str = MatchLogic.getScoreMethod();
        if(str.equals("a beautiful pass from "               ) ||
            str.equals("a wonderful solo from who else than " ) ||
             str.equals("a well taken corner from the foot of ") ||
              str.equals("a sharp through ball by "             )  )
            pass=true;
        
        assertTrue(pass);  
    }
    
    @Test
    public void randomInjuryTest(){
        boolean pass = false;
        String str = MatchLogic.randomInjury();
        if(str.equals("Broken ankle"        ) ||
            str.equals("Dislocated shoulder" ) ||
             str.equals("Strained ligaments"  ) ||
              str.equals("Torn hamstring"      )  )
            pass=true;
        
        assertTrue(pass);  
    }
    
}
