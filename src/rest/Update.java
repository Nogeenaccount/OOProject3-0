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
public class Update {
    private int type; //0=niks, 1=gele kaart,2= rode kaart, 3= blessure 4=goal
    private Player speler;
    private int minuut;
    
    public Update(int typ, Player spelert, int min){
        type = typ;
        speler = spelert;
        minuut = min;
    }

    /**
     * toString: returns String of Update
     * @return String
     */
    @Override
    public String toString(){
        return "<Update("+type+", "+speler.toString()+", "+minuut+")>";
    }
    
    /**
     * checks if to Update instances are equal
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Update){
            Update u = (Update) obj;
            return (u.getType()==this.getType() && u.getSpeler().equals(this.getSpeler()) && u.getMinuut()==this.getMinuut());
        }
        return false;
    }
    
    // getters/setters
    
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the speler
     */
    public Player getSpeler() {
        return speler;
    }

    /**
     * @param speler the speler to set
     */
    public void setSpeler(Player speler) {
        this.speler = speler;
    }

    /**
     * @return the minuut
     */
    public int getMinuut() {
        return minuut;
    }

    /**
     * @param minuut the minuut to set
     */
    public void setMinuut(int minuut) {
        this.minuut = minuut;
    }
    
    
}
