/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

/**
 *
 * @author Arjan
 */
public class Match {
    private Team HomeTeam;
    private Team AwayTeam;
    private int HomeScore;
    private int AwayScore;
    
    public Match(){
        
    }
    
    public Match(Team Home, Team Away){
        HomeTeam = Home;
        AwayTeam = Away;
        HomeScore=0;
        AwayScore=0;
    }  
    
    @Override
    public String toString(){
        return HomeTeam.getTeamName() + " " + HomeScore + "-" + AwayScore + " " + AwayTeam.getTeamName();
    }
    
    public String Fixture(){
        return HomeTeam + " - " + AwayTeam;
    }
    
    @Override
    public boolean equals(Object other){
        if(other instanceof Match){
            Match that = (Match)other;
            if(this.HomeTeam.equals(that.getHomeTeam()) && this.AwayTeam.equals(that.getAwayTeam())){
                return true;
            }
        }
        return false;
    }
    
    // getters/setters
    
    public void setHomeTeam(Team a){
        HomeTeam = a;
    }
    
    public Team getHomeTeam(){
        return HomeTeam;
    }
    
    public void setAwayTeam(Team a){
        AwayTeam = a;
    }
    
    public Team getAwayTeam(){
        return AwayTeam;
    }
    
    public void setHomeScore(int a){
        HomeScore = a;
    }
    
    public int getHomeScore(){
        return HomeScore;
    }
    
    public void setAwayScore(int a){
        AwayScore = a;
    }
    
    public int getAwayScore(){
        return AwayScore;
    }
    
}
