package main;
import profiles.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import exceptions.NoParentException;


public class Retrieve extends Profiles {
	
	public Retrieve(){
		
	}
	
	public void addProfile(String name, String imagePath, String status, String sex,
			int age, String state)
	{		
		super.addProfile(name,imagePath, status, sex, age, state);
	}
	
	public boolean removeProfile(int name) {
		return super.removeProfile(name);
	}
	
	public String[] getDetails(int nameIndex)
	{
		String[] details = super.getDetails(nameIndex);
		return details;
	}
	
	public void readProfile(String name, String imagePath, String status, String sex,
			int age, String state) {
		super.readProfile(name, imagePath, status, sex, age, state);
	}		
	
	public String checkConnected(int name1, int name2) {
		return super.checkConnected(name1, name2);
	}
	
	public void connect(int name1, int name2, int relationIndex) {
		super.connect(name1, name2, relationIndex);
	}
	
	public String[] showConnections(int name) {
		return super.showConnections(name);	
	}
	
	public void defineRelation(int name1, int name2, int relation) {
		super.defineRelation(name1, name2, relation);		
	}
	
	public int getNumProfiles() {
		return super.getNumProfiles();
	}

	public void relationsData() //Read in relationships from test file.
	{
		String relations = "C:\\Users\\seanr\\eclipse-workspace\\a3422814_Assignment_2\\relations.txt";
		Scanner inputStream = null;

		try
		{
			inputStream = new Scanner(new File(relations));
			inputStream.useDelimiter(",");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}	
		while (inputStream.hasNextLine()) // Use a loop to enter data
		{
			try
			{
				String name1 = inputStream.next();
				String name2 = inputStream.next();
				String relationship = inputStream.next();
				int relationIndex = Integer.parseInt(relationship);
				super.initialConnect(name1,name2,relationIndex);
			} 
			catch(NoSuchElementException e)
			{
				break;
			}
		}
		System.out.println("Relations Data Loaded.");
	}
	
	public String getName(int index) {
		return super.getName(index);
	}
	
	public boolean getCouple(int name) {
		return super.getCouple(name);
	}
}
