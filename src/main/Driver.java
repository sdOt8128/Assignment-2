package main;
import java.util.*;
import exceptions.*;
import javafx.scene.image.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;

public class Driver extends Retrieve {
	
	Image image;
	ImageView imageView;
	LocalDate currentDate = LocalDate.now();
	String imagePath;
	Scanner inputStream = null;
	
	public int getAge(LocalDate birthDate) //input DOB output age in years
	{
		LocalDate currentDate = LocalDate.now();
		if ((birthDate != null) && (currentDate != null)) 
        {
            return Period.between(birthDate, currentDate).getYears();
        } 
        else return -1;
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

	public boolean profileData() //Read in pre made profiles from test file.
	  //There are 4 adults and 3 dependents of varying ages
	{
		String people = "C:\\Users\\seanr\\eclipse-workspace\\a3422814_Assignment_2\\people.txt";

		try
		{
			inputStream = new Scanner(new File(people));
			inputStream.useDelimiter(",");
		}
		catch(FileNotFoundException e)
		{
			String line = inputStream.nextLine();
			System.out.println(line);
		}	
		while (inputStream.hasNextLine()) // Use a loop to enter data
		{
			try
			{
				String name = inputStream.next();
				String imagePath = inputStream.next(); 
				String status = inputStream.next();
				String sex = inputStream.next();
				String age1 = inputStream.next();
				String state = inputStream.next();
				int age = Integer.parseInt(age1); // coerce to int
				super.readProfile(name,imagePath,status,sex,age,state);
			} 
			catch(NoSuchElementException e)
			{
				break;
			}
		}
		System.out.println("Profile Data Loaded.");
		return true;
	
	}
	
	public void relationsData() {
		super.relationsData();
	}
	
	public void connect(int name1, int name2, int relationIndex) {
		super.connect(name1, name2, relationIndex);
		 
	}
	
	public String checkConnected(int name1, int name2) {
		return super.checkConnected(name1,name2);
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
	
	public String getName(int index) {
		return super.getName(index);
	}
	
	public boolean constraints(int name1, int name2, int relationType) throws TooYoungException,NotToBeFriendsException,
			NotAvailableException,NotToBeCoupledException, NotToBeColleaguesException, NotToBeClassmatesException
	{
		int age1 = super.getAge(name1);
		int age2 = super.getAge(name2);
		boolean couple1 = super.getCouple(name1);
		boolean couple2 = super.getCouple(name2);
		if((age1 < 3||age2 < 3) && relationType != 2)
			throw new TooYoungException("Cannot connect to a Young Child");
		if((age1 < 17||age2 < 17) && Math.abs(age1-age2)>3 && relationType != 2)
			throw new NotToBeFriendsException("Cannot connect an Adult to a Child");
		if(couple1==true || couple2==true)
			throw new NotAvailableException("Cannot connect as couple with person already connected as couple");
		if((age1 < 17 || age2 < 17))
			throw new NotToBeCoupledException("Cannot connect as couple with a Child");
		if(age1<17||age2<17)
			throw new NotToBeColleaguesException("Cannot connect as colleague with a Child");
		return true;
	}
	
	
	

    
}
