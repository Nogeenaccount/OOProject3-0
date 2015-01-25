/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Stefan
 */
public class ThreadTestTest {
    JButton preButton = new JButton();
    Thread preThread = new Thread(new ThreadTest(preButton));
    
    @Before
    public void runBeforeEveryTest() throws FileNotFoundException {
        //threadTest is the resource file + altered teamname/gamename
	states.StateManager.setLeague(rest.League.readResources("threadTest.xml"));
        rest.ThreadTest.setWorkSpace(new JTextArea());
    }
    
    @After
    public void runAfterEveryTest() throws FileNotFoundException {
        states.StateManager.setLeague(rest.League.readResources("threadTest.xml"));
        rest.ThreadTest.setWorkSpace(new JTextArea());
    }
    
    @Test
    public void runTest() {
        preThread.start();
        do {
            int i = 0;
        } while(preThread.isAlive());
        String workspace = rest.ThreadTest.getWorkSpace().getText();
        assertTrue(workspace.contains("welcomes you to their"));
        assertTrue(workspace.contains(System.getProperty("line.separator")));
        assertTrue(workspace.contains("     The referree has blown his whistle for the last time today:\n     End of the match!"));
    }
}
