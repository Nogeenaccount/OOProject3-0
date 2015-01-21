package rest;

import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;
public class ThreadTest implements Runnable {

    
    private static JTextArea workSpace;
    private static JButton button;
    
    public ThreadTest(JButton button2) {
        button = button2;
    }

    @Override
    public void run() {
        int round = states.StateManager.getLeague().getRounds();
        Match weddie = MatchLogic.findOwnMatch(round);
        String newLine = System.getProperty("line.separator");
	String beginText = "     " + weddie.getHomeTeam().getTeamName() + " welcomes you to their \"" + weddie.getHomeTeam().getStadiumName() + "\"!" + newLine;
	workSpace.setText(beginText);

	//String advancedText = "You lost";
	//beginText = beginText + advancedText;
	//workSpace.setText(beginText);

        System.out.println("Thread has set 'ongoingMatchText'");
        
        
        //Twee teams
        
        //Echte stuff
        
        Team homeTeam = weddie.getHomeTeam();
        Team awayTeam = weddie.getAwayTeam();
        MatchLogic thisMatch = new MatchLogic(15,homeTeam,awayTeam);
        
        //Setting standard String Values
        
        String updateText = "";
        String mainText = "1st minute" + ", Score: " 
                      + thisMatch.getTeam1().getTeamName()
                      + " "+thisMatch.getScore1()
                      + "-" 
                      + thisMatch.getScore2()
                      + " "
                      +thisMatch.getTeam2().getTeamName()
                      + newLine;
        
        for(int n=0;n<90;n++){
            
            
            ArrayList<Update> tick = thisMatch.oneTick();
            
            
            if(tick.size()==2){
                if(tick.get(0).getMinuut()==tick.get(1).getMinuut()){
                tick.get(1).setMinuut(tick.get(1).getMinuut()+3);
                }
                updateText = updateText 
                       + thisMatch.LineGenerator(tick.get(0),thisMatch.getTeam1(),thisMatch.getTeam2())
                       + thisMatch.LineGenerator(tick.get(1),thisMatch.getTeam2(),thisMatch.getTeam1());
            }
            int time = thisMatch.gettCurrent();
            
            if (time==90){
                updateText = updateText + "     The referree has blown his whistle for the last time today:\n     End of the match!";
            }
            if(time>90){
                time=90;
            }
            mainText = "      "+time
                      + "th minute" + ", Score: " 
                      + thisMatch.getTeam1().getTeamName()
                      + " "+thisMatch.getScore1()
                      + "-" 
                      + thisMatch.getScore2()
                      + " "
                      +thisMatch.getTeam2().getTeamName()
                      + newLine;
            
                workSpace.setText(beginText + newLine + mainText + newLine + updateText);
                try{
                    Thread.sleep(80);
                }
                catch(InterruptedException e){
                    System.out.println(e);
                }
            }
        
        states.StateManager.getLeague().setLastResult(thisMatch.getTeam1().getTeamName()+" "+thisMatch.getScore1() + "-" + thisMatch.getScore2()+" "+thisMatch.getTeam2().getTeamName());
        
        //League.setLastResultDetailed(thisMatch.get);
        button.setEnabled(true);
    }

    public static JTextArea getWorkSpace() {
	return workSpace;
    }

    public static void setWorkSpace(JTextArea workSpace) {
	ThreadTest.workSpace = workSpace;
    }
}
