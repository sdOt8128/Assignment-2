package profiles;

import java.util.*;

public class Profile {
	
	private static int numProfiles=0;
	private final int profileID;
	private int age;
	private String sex;
	private String name = "";
	private String status = "";
	private String imagePath;
	private String state;
	boolean couple = false;
	boolean parent = true;

	HashMap<Integer, Integer> connections = new HashMap<>();
	
	public Profile(String name, String imagePath, String status, String sex, int age, String state)
	{
		this.name = name;
		this.status = status;
		this.imagePath = imagePath;
		this.age=age;
		setState(state);
		setSex(sex);
		this.profileID = numProfiles;
		numProfiles++;
	}
	
	public String checkConnected(int profID) {
		try {
			return setRelation(connections.get(profID));		
		}catch(Exception e) {
			return "Not Connected";
		}
	}

	public int getNumProfiles() {
		return numProfiles;
	}
	
	public void connect(int profID,int relationIndex) {
		setRelation(relationIndex);
		connections.put(profID, relationIndex);
		if(relationIndex==5) setCouple(true);

	}
	
	public HashMap<Integer,Integer> getConnections() {
		return this.connections;
	}
	
	public Set<Integer> showConnections() {		
		return connections.keySet();
	}
	
	public void defineRelation(int profID, int relationType) {
		connections.replace(profID, relationType);
	}
	
	public String getName() {
		return name;
	}

	public void addImage(String imagePath)
	{
		this.imagePath=imagePath;
	}
	public String getImagePath() {
		return this.imagePath;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getProfileID() {
		return profileID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public boolean getCouple() {
		return couple;
	}
	public void setCouple(boolean couple) {
		this.couple = couple;
	}
	public boolean getParent() {
		return parent;
	}
	public void setParent(boolean parent) {
		this.parent = parent;
	}
	
	public void deleteConnections(int profID) {
		if(connections.containsKey(profID)) {
			connections.remove(profID);
		}
	}

	public String setRelation(int selection) {
		
		String relation="";
		switch(selection)
		{
			case 0: relation = "Friend";
					break;
			case 1: relation = "Child";
					break;
			case 2: relation = "Sibling";
					break;
			case 3: relation = "Colleague";
					break;
			case 4: relation = "Classmate";
					break;
			case 5: relation = "Couple";
					break;
			case 6: relation = "Parent";
					parent = true;
			default: break;
		}
		return relation;
	}

	

}
