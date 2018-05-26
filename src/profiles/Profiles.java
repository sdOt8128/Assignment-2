package profiles;

import java.util.*;

public class Profiles {

	static int numProf = 0;
	public ArrayList<Profile> profiles = new ArrayList<Profile>(); 
	Connections connect;
	
	public void addProfile(String name, String imagePath, String status,String sex, int age, String state)
	{
		Profile profile = new Profile(name, imagePath, status,sex, age, state);
		profiles.add(profile);
		numProf++;
	}
	
	public String getName(int nameIndex) {
		return profiles.get(nameIndex).getName();
	}
	
	public int getIndex(String name) {	
		int i=0;
		for(Profile p:profiles) {
			if(p.getName().contains(name)) return i;
			i++;
		}return 0;
	}
	
	public int getID(int name) {
		return profiles.get(name).getProfileID();
	}
	
	public int getIndexFromID(int profID) {
		int i=0;
		for(Profile p: profiles) {
			if(p.getProfileID()==profID) return(i);
			i++;
		} return-1;
	}
	
	public int getAge(int name)
	{
		int age = profiles.get(name).getAge();
		return age;
    }
	
	public void getProfileList() {
		int[] profileList = new int[profiles.size()];
		for(int i=0;i<profiles.size();i++) {
			profileList[i] = profiles.get(i).getProfileID();
		}
	}
	
	public String[] getDetails(int nameIndex)
	{
		String[] details = new String[6];
		details[0] = profiles.get(nameIndex).getName();
		details[1] = profiles.get(nameIndex).getImagePath();
		Integer ageInt = profiles.get(nameIndex).getAge();
		String age = ageInt.toString();
		details[2] = profiles.get(nameIndex).getStatus();
		details[3] = profiles.get(nameIndex).getSex();
		details[4] = age;
		details[5] = profiles.get(nameIndex).getState();	
		return details;
	}
	
	public void readProfile(String name, String imagePath, String status, String sex, 
			int age,String state)
	{
		addProfile(name,imagePath,status,sex,age,state);
	}
	
	public String checkConnected(int name1, int name2) {
		int id2 = getID(name2);
		return profiles.get(name1).checkConnected(id2);
	}
	
	public String[] showConnections(int name) {
		Set<Integer> connections = profiles.get(name).showConnections();
		Iterator<Integer> it = connections.iterator();
		String[] connectionList = new String[connections.size()];
		int i=0;
		while(it.hasNext()) {
			Integer j=(Integer) it.next();
			connectionList[i]=profiles.get(getIndexFromID(j.intValue())).getName();
			i++;
		}	
		return connectionList;
	}
	
	public void defineRelation(int name1, int name2, int relation) {
		profiles.get(name1).defineRelation(getID(name1), relation);
		profiles.get(name2).defineRelation(getID(name2), relation);
	}
	
	public void connect(int name1, int name2, int relationIndex) {
		if(relationIndex==1) 
		{
			int age1 = profiles.get(name1).getAge();
			int age2 = profiles.get(name2).getAge();
			if(age1 > age2) {
				profiles.get(name1).connect(getID(name2),6);
				profiles.get(name2).connect(getID(name1),1);
			}
		profiles.get(name2).connect(getID(name1),6);
		profiles.get(name1).connect(getID(name2),1);
		}
		profiles.get(name2).connect(getID(name1),relationIndex);
		profiles.get(name1).connect(getID(name2),relationIndex);
	}
	
	public void initialConnect(String name1, String name2, int relation) {	
		profiles.get(getIndex(name1)).connect(getIndex(name2), relation);
		profiles.get(getIndex(name2)).connect(getIndex(name1), relation);
	}
	public void setInitialCouples() {
		profiles.get(3).setCouple(true);
		profiles.get(4).setCouple(true);
		profiles.get(7).setCouple(true);
		profiles.get(8).setCouple(true);
	}
	
	public int getNumProfileIDs() {
		return profiles.get(0).getNumProfiles();
	}
	
	public ArrayList<HashMap<Integer,Integer>> connectionsList()
	{
		ArrayList<HashMap<Integer,Integer>> connectionList = new ArrayList<>();
		for(int i=0;i<profiles.size();i++) {
			connectionList.add(profiles.get(i).getConnections());
		}		
		return connectionList;
	}
	
	public boolean removeProfile(int name) {
		if(profiles.get(name).getParent()==true) return false;
		int profID = getID(name);
		for(Profile p:profiles) {
			p.deleteConnections(profID);
		}
		profiles.remove(name);
		numProf--;
		return true;
	}

	public int getNumProfiles() {
		return numProf;
	}
	
	public boolean getCouple(int name) {
		return profiles.get(name).getCouple();
	}
	
}
