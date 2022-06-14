package algorithmsPortfolio;
//-----------------------------------------------------
// Author: 		Sivan Nachum
// Date: 		April 27, 2021
// Description:	Java code to represent a Trainer in a directed bipartite graph representation
//-----------------------------------------------------

public class Trainer {
    private int number;
    private int[] preferenceList;
    private int preferenceListIndex;
    
    /**
     * Constructor - creates a Trainer object with the given number
     * @param number of trainer
     * @param numPokemon it can choose from
     */
    public Trainer(int number, int numPokemon){
        this.number = number;
        preferenceList = new int[numPokemon];
        preferenceListIndex = -1;
    }

    // Getters

    /**
     * @return number of this trainer
     */
    public int getNumber(){
        return number;
    }

    /**
     * 
     * @return the number of the pokemon to whom the trainer will propose
     */
    public int getProposalNumber(){
        preferenceListIndex++;
        return preferenceList[preferenceListIndex];
    }
    
    //Setters
    
    /**
     * sets the pokemon in this trainer's preference list with the given ranking
     * 
     * @param pokemon number
     * @param ranking
     */
    public void setPreference(int pokemon, int ranking){
        preferenceList[ranking] = pokemon;
    }
}
