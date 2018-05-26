package profiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Connections extends Relation{
	
	private int size=0;
	public int adjMatrix[][];
	Profiles profiles;
	private int relationIndex;
	private String relation;
	
	public void setRelation(int selection) {
		
		relationIndex = selection;
		switch(selection)
		{
			case 1: relation = "Friend";
					break;
			case 2: relation = "Parent";
					break;
			case 3: relation = "Sibling";
					break;
			case 4: relation = "Colleague";
					break;
			case 5: relation = "Classmate";
					break;
			case 6: relation = "Couple";
					break;
			default: break;
		}
	}
	public String checkRelation(int relationIndex) {
		setRelation(relationIndex);
		return relation;
	}
	public int getRelation()
	{
		return relationIndex;
	}
	
	public ArrayList<HashMap<Integer,Integer>> getConnections() {
		size = profiles.getNumProfileIDs();
		return profiles.connectionsList();
	}
	
	public void connect() {
			
		ArrayList<HashMap<Integer,Integer>> connectionList = getConnections();
		
		adjMatrix = new int[size][size];
		Integer row,col;
		HashMap<Integer,Integer> connections = new HashMap<>();
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				adjMatrix[i][j]=0;
			}
		}
		
		for(int k=0;k<size;k++) {
			connections = connectionList.get(k);
			Iterator<Entry<Integer, Integer>> it = connections.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				row = (Integer) pair.getKey();
				col = (Integer) pair.getValue();
				int newInt1 = row;
				int newInt2 = col;
				adjMatrix[k][newInt1] = newInt2; 
			}				
		}
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				System.out.println(adjMatrix[i][j]);
			}
		}

	}
	


	
	
}
