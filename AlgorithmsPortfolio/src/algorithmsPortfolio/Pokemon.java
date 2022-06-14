package algorithmsPortfolio;
//-----------------------------------------------------
// Author: 		Zampa Provenzale
// Date: 		April 27, 2021
// Description:	Java code to represent a Pokemon in a directed bipartite graph representation
//-----------------------------------------------------

public class Pokemon {
    private int number;
    private int[] preferenceList;
    private int currMatch;
    
    //Constructors
    
    /**
     * Constructor - creates a Pokemon object with the given number
     * @param number of the Pokemon
     * @param numTrainers total the Pokemon can choose from
     */
    public Pokemon(int number, int numTrainers){
        this.number = number;
        preferenceList = new int[numTrainers];
        currMatch = -1;
    }

    // Getters

    /**
     * @return the number of this pokemon
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * @return the pokemon's current match
     */
    public int getCurrMatch() {
    	return this.currMatch;
    }

    //Setters
    
    /**
     * sets the trainer in this pokemon's preference list with the given ranking
     * 
     * @param trainer number
     * @param ranking of trainer
     */
    public void setPreference(int trainer, int ranking){
        preferenceList[trainer] = ranking;
    }

    //Other
    
    /**
     * given a new proposer decides whether Pokemon stay with current partner or switches
     * 
     * @param proposer number
     * @return rejected trainer number
     */
    public int choosePartner(int proposer){
        if (this.currMatch == -1) {
            this.currMatch = proposer;
            return -1;
        } else if (preferenceList[proposer] < preferenceList[this.currMatch]){
            int rejected = this.currMatch;
            this.currMatch = proposer;
            return rejected;
        } else if (preferenceList[this.currMatch] < preferenceList[proposer]){
            return proposer;
        } else {
        	System.out.println("error in choosePartner");
        	return -1;
        }
    }
}
