package algorithmsPortfolio;
//-----------------------------------------------------
// Author: 		Zampa Provenzale + Sivan Nachum
// Date: 		April 28, 2021
// Description:	Java code to represent a Matching Graph, meaning a directed bipartite graph representation
//				where half the vertices are trainers and the other half are pokemon
//				Can run the Gale-Shapley algorithm for finding a stable matching
//-----------------------------------------------------
import java.util.LinkedList;


import java.io.FileWriter;
import java.io.IOException;

public class MatchingGraph {
    // Sivan:
    private int numTrainers; // Note that this number is equivalent to the number of Pokemon as well
    private Trainer[] trainers;
    private Pokemon[] pokemon;
    
    /**
     * creates a MatchingGraph object with totalNumVertices/2 Trainers and totalNumVertices/2 Pokemon
     * @param totalNumVertices in the graph
     */ 
    public MatchingGraph(int totalNumVertices){
        numTrainers = totalNumVertices/2;
        trainers = new Trainer[numTrainers];
        pokemon = new Pokemon[numTrainers];
        for (int i = 0; i < numTrainers; i++) {
        	trainers[i] = new Trainer(i, numTrainers);
        	pokemon[i] = new Pokemon(i+numTrainers, numTrainers);
        }
    }
    
    // Zampa:

    /**
     * 
     * @param pokemonNum - Pokemon number from original graph
     * @return Index for that pokemon in the pokemon array
     */
    public int pokeToIndex(int pokemonNum) {
    	return pokemonNum - numTrainers;
    }

    // Modifiers
    // Sivan:

    /**
     * if u is a trainer, then v is a pokemon, and weight is u's ranking of v
     * if u is a pokemon, then v is a trainer, and weight is u's ranking of v
     * 
     * In the first case, adds the pokemon to the trainer's preferenceList with the given ranking
	 * In the second case, adds the trainer to the pokemon's preferenceList with the given ranking
     * 
     * @param u first vertex
     * @param v second vertex
     * @param weight of edge
     */
    public void addEdge(int u, int v, int weight) {
    	if (u < numTrainers) {
    		trainers[u].setPreference(v, weight);
    	}
    	else {
    		pokemon[pokeToIndex(u)].setPreference(v, weight);
    	}
    }
    
    // Zampa:

    /**
     * 
	 * Finds stable matching with the Gale-Shapely algorithm
     * 
     * Referenced Kleinberg & Tardos, Ch 1.1. A first problem: Stable Matching reading 
     * as well as this video: https://youtu.be/fudb8DuzQlM
     * 
     * @return Array of edges signifying matched pairs which create a stable matching
     */
    public Edge[] galeShapley() {
    	LinkedList<Trainer> freeList = new LinkedList<Trainer>();
    	for (int i = 0; i < numTrainers; i++) {
    		freeList.add(trainers[i]);
    	}
    	
    	Edge[] matches = new Edge[numTrainers];
    	
    	while (!freeList.isEmpty()) {
    		Trainer currTrainer = freeList.pollFirst();
    		int pokemonNum = currTrainer.getProposalNumber();
    		Pokemon currPokemon = pokemon[pokeToIndex(pokemonNum)];
    		int rejected = currPokemon.choosePartner(currTrainer.getNumber());
    		if (rejected != -1) {
    			freeList.add(trainers[rejected]);
    		}
    	}
    	
    	for (int i = 0; i < numTrainers; i++) {
    		Pokemon currPokemon = pokemon[i];
    		int currTrainerNum = currPokemon.getCurrMatch();
    		matches[i] = new Edge(currTrainerNum, currPokemon.getNumber());
    	}
    	
    	return matches;
    }

    // Sivan: 
    // File I/O

    /**
     * writes the edges of the stable matching produced by the Gale-Shapley algorithm
     * to the given file in the format of a Mathematica list of lists
     * ex. {{1,3},{2,4}}
     * 
     * @param filename to which to write the stable edges
     * assumes galeShapley has already been called
     */
    public void writeStableEdgesToFile(String filename) {
        String toWrite = "{";
        boolean firstElem = true;
        for (Pokemon poke : pokemon){
            if (poke.getCurrMatch() != -1){                    
                if (!firstElem) {
                    toWrite += ",";
                } else {
                    firstElem = false;
                }
                toWrite += "{" + (poke.getCurrMatch()+1) + "," + (poke.getNumber()+1) + "}";
            }
        }
        toWrite += "}";
        
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(toWrite);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
     
}
