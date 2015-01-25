package rest;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.util.ArrayList;
import java.util.List;

public class MatchLogic{

    private int tCurrent;
    private int tMax;
    private int score1;
    private int score2;
    private Team team1;
    private Team team2;
    

    private double t1Offence;
    private double t1Defence;
    private double t1Endurance;

    private double t2Offence;
    private double t2Defence;
    private double t2Endurance;

    private ArrayList<Update> fullUpdateList = new ArrayList<Update>();
    
    /**
     * matchLogic: constructor
     *
     * @param t amount of time
     * @param t1 team one
     * @param t2 team two
     * @param txt html text
     * @param bar loading bar
     */
    public MatchLogic(int t, Team t1, Team t2) {
	tCurrent = 0;
	tMax = t;
	team1 = t1;
	team2 = t2;
	score1 = 0;
	score2 = 0;
    }

    /**
     * Generates an update for one tick of a match for the Home team. If the hometeam doesn't score, there is a small chance that something different happens, yellow/red card or injury.
     * @return an update object with the current time of the match, a random player, and the type of event that happened
     */
    public Update tickHome(){
            int typ =0;
            Player spelert = null;
            if(gettCurrent()==0){
                tCurrent=2;
            }
            int min=gettCurrent();
            
             double p1=0.05; //gele kaart thuisteam
             double p2=0.005; //rode kaart thuisteam
             double p3=0.04; //blessure thuisteam
                  
            if(MatchResult.scored(MatchResult.offenceSum(getTeam1()), MatchResult.defenceSum(getTeam2()), MatchResult.enduranceSum(getTeam1()), MatchResult.enduranceSum(getTeam2()), gettCurrent())==1){
                int a = (int)(Math.round(Math.random()*1.49));
                spelert = getTeam1().getLineUp().getAanvallers().get(a);
                return new Update(4, spelert, gettCurrent());
            }         
            else{
                spelert=getTeam1().getLineUp().getRandomPlayer();
                double temp = Math.random();
                
                if(temp<p1){
                    return new Update(1, spelert, gettCurrent());
                }
                if(temp<p2){
                    return new Update(2, spelert, gettCurrent());
                }
                if(temp<p3){
                    return new Update(3, spelert, gettCurrent());
                }
            }
                        
            return new Update(typ, spelert, min);
        }

    /**
     * Generates an update for one tick of a match for the awayteam. If the awayteam doesn't score, there is a small chance that something different happens, yellow/red card or injury.
     * @return an update object with the current time of the match, a random player, and the type of event that happened
     */
    public Update tickAway(){
            int typ =0;
            Player spelert = null;
               
            int min=gettCurrent();
            
             double p1=0.07; //gele kaart uitteam
             double p2=0.005; //rode kaart uitteam
             double p3=0.05; //blessure uitteam
                       
            if(MatchResult.scored(MatchResult.offenceSum(getTeam2()), MatchResult.defenceSum(getTeam1()), MatchResult.enduranceSum(getTeam2()), MatchResult.enduranceSum(getTeam1()), gettCurrent())==1){
                int a = (int)(Math.round(Math.random()*3-0.5));
                spelert = getTeam2().getDefaultLineUp().getAanvallers().get(a);
                return new Update(4, spelert, gettCurrent());
            }
            
            else{
                
                spelert=getTeam2().getDefaultLineUp().getRandomPlayer();
                double temp = Math.random();
                
                if(temp<p1){
                    return new Update(1, spelert, gettCurrent());
                }
                if(temp<p2){
                    return new Update(2, spelert, gettCurrent());
                }
                if(temp<p3){
                    return new Update(3, spelert, gettCurrent());
                }
            }
                   
            return new Update(typ, spelert, min);
        }

    /**
     * getScoreMethod: returns random comment (String) on goal
     * @return String
     */
    public static String getScoreMethod(){
           double r = Math.random();
           if(r<0.25){
            return "a beautiful pass from ";
           }
           if(r<0.5){
               return "a wonderful solo from who else than ";
           }
           if(r<0.75){
               return ("a well taken corner from the foot of ");
           }
           return "a sharp through ball by ";
        }

    /**
     * Generates a string to display in the live feed during a match.
     * @param update the update object to generate a line from
     * @param t the hometeam 
     * @param s the awayteam
     * @return String the stringrepresentation of the ubdate object
     */
    public String LineGenerator(Update update, Team t, Team s){
            String newLine = System.getProperty("line.separator");
            String tab = "    ";
            if(update.getMinuut()==0){
                update.setMinuut(2);
            }
            String result = "";
            
            switch(update.getType()){
                case 0: break;
                case 1: result = result + " " + update.getMinuut() + "' " + tab+ tab+ "Booking at " + t.getTeamName() + "!" 
                            + newLine + tab + tab+ update.getSpeler().getPlayerName() + " is shown a yellow card after a "+newLine+tab+tab+ "hazardous challenge on " + s.getLineUp().getRandomPlayer().getPlayerName() + "!" + newLine + newLine; 
                        break;
                case 2: result = result + " " + update.getMinuut() + "' " + tab+tab+ "Red card at " + t.getTeamName() + "!" 
                        + newLine + tab +tab+ update.getSpeler().getPlayerName() + " sees red after a schandalous "+ newLine + tab +tab+ "charge on " + s.getLineUp().getRandomPlayer().getPlayerName() + "!" + newLine + newLine; 
                        break;
                case 3: result = result + " " + update.getMinuut() + "' " + tab+"Injury at " + t.getTeamName() + "!" 
                            + newLine + tab + tab+ update.getSpeler().getPlayerName() + " falls down like a " + newLine + tab +tab+  "dying swallow, and " + newLine + tab + tab+ "will most likely not play next match!" + newLine + newLine; 
                        break;
                case 4: 
                    Player assistMan=new Player();
                    do{
                    assistMan = t.getLineUp().getRandomPlayer();
                    }while (assistMan.equals(update.getSpeler()));
                    result = result + " " + update.getMinuut() + "' " + tab+t.getTeamName() + " SCORES!" + newLine + tab + tab+ update.getSpeler().getPlayerName() + " scores after " + newLine + tab + tab+ getScoreMethod() + assistMan.getPlayerName() + "!" + newLine + newLine;
                        break;
            }       
            
            
            return result;
        }

    /**
     * Does everything that has to happen during one tick in a match. Updates the time, and processes update objects
     * @return ArrayList<Update> An arraylist containing two update objects, one for the hometeam, one for the awayteam
     */
    public ArrayList<Update> oneTick(){
        Update updateHome = tickHome();
        Update updateAway = tickAway();
        tCurrent++;
        
        ArrayList<Update> updateList = new ArrayList<Update>();
        if(tCurrent%6==0){
        
        switch(updateHome.getType()){
            case 0: break;
            case 1: updateHome.getSpeler().setCardCount(updateHome.getSpeler().getCardCount()+1); break;
            case 2: updateHome.getSpeler().setCardCount(updateHome.getSpeler().getCardCount()+5); break;
            case 3: updateHome.getSpeler().setInjured(1); break;
            case 4: score1++;
        }
            
        switch(updateAway.getType()){
            case 0: break;
            case 1: updateAway.getSpeler().setCardCount(updateAway.getSpeler().getCardCount()+1); break;
            case 2: updateAway.getSpeler().setCardCount(updateAway.getSpeler().getCardCount()+5); break;
            case 3: updateAway.getSpeler().setInjured(1); break;
            case 4: score2++;
        }
           
          
        updateList.add(updateHome);
        updateList.add(updateAway);
        states.StateManager.getLeague().addToLastResultDetailed(updateList.get(0));
        states.StateManager.getLeague().addToLastResultDetailed(updateList.get(1));
           
        }
        return updateList;
          
    }

    /**
     * Finds the match of the chosen team in a certain round
     * @return Match the match of the chosen team during the chosen round
     */       
    public static Match findOwnMatch(int round){
       Round temp = states.StateManager.getLeague().nextRound("Speelschema.xml",round);
       Team myTeam = states.StateManager.getLeague().getTeamByString(states.StateManager.getLeague().getChosenTeam());
       for(int n=0;n<10;n++){
           if(temp.getMatch(n).getHomeTeam().equals(myTeam)||temp.getMatch(n).getAwayTeam().equals(myTeam)){
               return temp.getMatch(n);
           }
       }
       return new Match();
    }

    /**
     * randomInjury: returns random comment (String) on injury
     * @return String
     */
    public static String randomInjury(){
        int aantal = 4;
        double a = Math.random();
        if(a<1/aantal){
            return "Broken ankle";
        }
        if(a<2/aantal){
            return "Dislocated shoulder";
        }
        if(a<3/aantal){
            return "Strained ligaments";
        }
        
        return "Torn hamstring";
        
    }
    
    
    // getters/setters
    
    /**
     * @return the tCurrent
     */
    public int gettCurrent() {
        return tCurrent;
    }

    /**
     * @param tCurrent the tCurrent to set
     */
    public void settCurrent(int tCurrent) {
        this.tCurrent = tCurrent;
    }

    /**
     * @return the tMax
     */
    public int gettMax() {
        return tMax;
    }

    /**
     * @param tMax the tMax to set
     */
    public void settMax(int tMax) {
        this.tMax = tMax;
    }

    /**
     * @return the score1
     */
    public int getScore1() {
        return score1;
    }

    /**
     * @param score1 the score1 to set
     */
    public void setScore1(int score1) {
        this.score1 = score1;
    }

    /**
     * @return the score2
     */
    public int getScore2() {
        return score2;
    }

    /**
     * @param score2 the score2 to set
     */
    public void setScore2(int score2) {
        this.score2 = score2;
    }

    /**
     * @return the team1
     */
    public Team getTeam1() {
        return team1;
    }

    /**
     * @param team1 the team1 to set
     */
    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    /**
     * @return the team2
     */
    public Team getTeam2() {
        return team2;
    }

    /**
     * @param team2 the team2 to set
     */
    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    /**
     * @return the t1Offence
     */
    public double getT1Offence() {
        return t1Offence;
    }

    /**
     * @param t1Offence the t1Offence to set
     */
    public void setT1Offence(double t1Offence) {
        this.t1Offence = t1Offence;
    }

    /**
     * @return the t1Defence
     */
    public double getT1Defence() {
        return t1Defence;
    }

    /**
     * @param t1Defence the t1Defence to set
     */
    public void setT1Defence(double t1Defence) {
        this.t1Defence = t1Defence;
    }

    /**
     * @return the t1Endurance
     */
    public double getT1Endurance() {
        return t1Endurance;
    }

    /**
     * @param t1Endurance the t1Endurance to set
     */
    public void setT1Endurance(double t1Endurance) {
        this.t1Endurance = t1Endurance;
    }

    /**
     * @return the t2Offence
     */
    public double getT2Offence() {
        return t2Offence;
    }

    /**
     * @param t2Offence the t2Offence to set
     */
    public void setT2Offence(double t2Offence) {
        this.t2Offence = t2Offence;
    }

    /**
     * @return the t2Defence
     */
    public double getT2Defence() {
        return t2Defence;
    }

    /**
     * @param t2Defence the t2Defence to set
     */
    public void setT2Defence(double t2Defence) {
        this.t2Defence = t2Defence;
    }

    /**
     * @return the t2Endurance
     */
    public double getT2Endurance() {
        return t2Endurance;
    }

    /**
     * @param t2Endurance the t2Endurance to set
     */
    public void setT2Endurance(double t2Endurance) {
        this.t2Endurance = t2Endurance;
    }

    

   
        
}
