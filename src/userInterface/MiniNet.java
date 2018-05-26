package userInterface;
import profiles.*;
import main.*;
import exceptions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class MiniNet extends Application
{

	Stage stage;
	Scene scene, scene1,scene2;
	String workspace = "";
	static String style = "";
	Button button;
	String title = "";
	String name1;
	String name2;
	String status = "";
	String imagePath = "";
	String sex = "";
	String age = "";
	String state = "";
	String relation = "";
	int ageYears;
	int profileIndex1;
	int profileIndex2;
	int relationIndex;
	boolean cancelFlag = false;
	Driver driver = new Driver();

	public static void main(String[] args) {

        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
    	loadData();
    	styleSheet();
    	pathChooser();
    	stage = primaryStage;
    	
    	HBox topMenu = new HBox();
    	topMenu.setPadding(new Insets(0,20,10,20));
    	
    	Pane pane = new Pane();
    	pane.setPadding(new Insets(20,20,20,20));
		
    	Menu profileMenu = new Menu("_Profile");
    	MenuItem addProfile = new MenuItem("Add Profile...");
    	MenuItem viewProfile = new MenuItem("View Profile...");
    	MenuItem removeProfile = new MenuItem("Remove Profile...");
    	
		profileMenu.getItems().add(addProfile);		
		profileMenu.getItems().add(viewProfile);	
		profileMenu.getItems().add(removeProfile);
		
		addProfile.setOnAction(e -> {
			try {
				addProfile();
			} catch (NoSuchAgeException e1) {
				e1.printStackTrace();
			} 
		});
	
		viewProfile.setOnAction(e -> {
			selectName("Enter Name");
			viewProfile(name1,imagePath,status,sex,age,state);
		});
		removeProfile.setOnAction(e -> {
			try {
				removeProfile();
			} catch (NoParentException e1) {
				System.out.println("Exception: "+e1);
			}
		});
			
		Menu connectionsMenu = new Menu("_Connections");
		MenuItem connect = new MenuItem("Connect two People");
		MenuItem checkConnect = new MenuItem("Check if two People Are Connected");
		
		MenuItem showConnections = new MenuItem("Show all Connections");
		
		connectionsMenu.getItems().add(connect);
		connectionsMenu.getItems().add(checkConnect);
		connectionsMenu.getItems().add(showConnections);
		
		connect.setOnAction(e -> {
			selectTwoNames("Connect two People","Enter two profile names:");
			connect(profileIndex1, profileIndex2,"Connect two People", "How are they connected?");
			try {
				cancelFlag=constraints();
			} catch (TooYoungException e1) {
				System.out.println("Exception: "+e1);
			} catch (NotToBeFriendsException e2) {
				System.out.println("Exception: "+e2);
			}  catch (NotAvailableException e3) {
				System.out.println("Exception: "+e3);
			} catch (NotToBeCoupledException e4) {
				System.out.println("Exception: "+e4);
			} catch (NotToBeColleaguesException e5) {
				System.out.println("Exception: "+e5);
			} catch (NotToBeClassmatesException e6) {
				System.out.println("Exception: "+e6);
			}
			if(cancelFlag==true) {
				driver.connect(profileIndex1, profileIndex2, relationIndex);
				display("Connection Made",getName(profileIndex1)+" is now connected to "+
						getName(profileIndex2)+" as "+relation);
				e.consume();
			}
			if(cancelFlag=false) display("Error","Cannot Connect");
		});
		checkConnect.setOnAction(e ->{
			selectTwoNames("Check if Connected","Enter two profile names:");
			checkConnected(profileIndex1,profileIndex2);			
		});
		showConnections.setOnAction(e -> showConnections());
		
		Menu relationshipsMenu = new Menu("_Relationships");
		MenuItem defineRelation = new MenuItem("Define a Relation");
		MenuItem getDependency = new MenuItem("Show Dependency");

		relationshipsMenu.getItems().add(defineRelation);
		relationshipsMenu.getItems().add(getDependency);
		defineRelation.setOnAction(e -> {
			selectTwoNames("How are these two people related?","Enter two profile names:");
			connect(profileIndex1, profileIndex2,"Connect two People", "How are they connected?");	
			driver.defineRelation(profileIndex1, profileIndex2, relationIndex);
		});
		getDependency.setOnAction(e -> selectName(""));
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(profileMenu,connectionsMenu,relationshipsMenu);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
	
		Button exit = new Button("Exit");
		exit.setPrefWidth(50);
		exit.setPrefHeight(20);
		exit.setLayoutX(440);
		exit.setLayoutY(300);
		exit.setOnAction(e -> stage.close());

		Text title = new Text ("MiniNet");
		title.setId("titletext");
		title.setLayoutX(150);
		title.setLayoutY(200);
    	
    	pane.getChildren().addAll(menuBar,title,exit);
		
    	
    	Scene scene = new Scene(pane, 500, 340);
    	scene.getStylesheets().add(style);
    	
    	stage.setTitle("MiniNet");
    	stage.setResizable(false);
    	stage.setScene(scene);
    	stage.show();
	
    }
    
    public String getName(int nameIndex) {
    	return driver.getName(nameIndex);
    }
    
    public void selectName(String title) {
    	Stage stage = new Stage();
    	stage.setTitle("View Profile");
    	stage.setOnCloseRequest(e -> e.consume());
    	
    	GridPane grid = new GridPane();
    	grid.setPadding(new Insets(25,25,25,25)); 
    	grid.setVgap(10);
    	grid.setHgap(10);
    	
    	ChoiceBox<String> chooseName= new ChoiceBox<>();
    	int num = driver.getNumProfiles();
    	for(int i=0;i<num;i++) {
    		String names = driver.getName(i);
    		chooseName.getItems().add(names);
    	}
    	chooseName.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {
    		setProfileIndex1(newValue.intValue());
    	});
		chooseName.setStyle("-fx-text-align: center");
		chooseName.setTooltip(new Tooltip("Choose Profile name"));
    	Label label = new Label("Choose profile name:"); 	
    	 
    	Button cancel = new Button("Cancel");
    	cancel.setOnAction(e -> stage.close());
    	Button submit = new Button("Select");
    	submit.setOnAction(e-> {
    		getInfo(profileIndex1);
    		stage.close();
    	});
    	GridPane.setConstraints(label, 0, 0);
    	GridPane.setConstraints(chooseName, 1, 0);
    	GridPane.setConstraints(submit, 1, 1);
    	GridPane.setConstraints(cancel,1, 2);
    	
    	grid.getChildren().addAll(label, submit, chooseName, cancel);
    	
    	scene = new Scene(grid,400,170);
    	scene.getStylesheets().add(style);
    	stage.setScene(scene);
    	stage.showAndWait(); 	
    }

    public void getInfo(int nameIndex) {
    	String[] details = driver.getDetails(nameIndex);
    	name1 = details[0];
    	imagePath = details[1];
    	status = details[2];
    	sex = details[3];
    	age = details[4];
    	state = details[5];
    }
    
    public static void display(String title, String message)
	{
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> stage.close());
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout,300,250);
		layout.setId("display");
		scene.getStylesheets().add(style);
		stage.sizeToScene();
		stage.setScene(scene);
		stage.showAndWait();
	}
    
    public void viewProfile(String name, String imagePath, String status, String sex,
			String age, String state)
	{
		ImageView imageView = new ImageView();
		File file = new File(imagePath);
		Image image;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
		imageView.setFitHeight(300.0);
		imageView.setFitWidth(300.0);
		imageView.setPreserveRatio(true);
        HBox imageBox = new HBox();
		imageBox.setAlignment(Pos.CENTER);
		imageBox.getChildren().add(imageView);
				
		Stage stage = new Stage();
		Scene scene;
		stage.setTitle("Profile of "+name);
		GridPane grid = new GridPane();		
		grid.setPadding(new Insets(20,20,20,20));
		
		VBox info = new VBox();
		info.setSpacing(20);
		info.setPadding(new Insets(10,10,10,10));
		Label label1 = new Label("Name:  "+name);
		Label label2 = new Label("Status:  "+status);
		Label label3 = new Label("Sex:  "+sex);
		Label label4 = new Label("Age:  " +age);
		Label label5 = new Label("State:  "+ state);
		info.getChildren().addAll(label1,label3,label4,label5);
		
		GridPane.setConstraints(imageBox, 0, 0);
		GridPane.setConstraints(info, 1, 0);
		GridPane.setConstraints(label2, 0, 2);
		grid.getChildren().addAll(imageBox,info,label2);
		
		
		scene = new Scene(grid,500,400);
		scene.getStylesheets().add(style);
		stage.setScene(scene);
		stage.show();
	}
    
	public void addProfile() throws NoSuchAgeException
	{
		Stage stage = new Stage();
		
		ChoiceBox<String> stateChoice = new ChoiceBox<>();
		ChoiceBox<String> sexChoice = new ChoiceBox<>();
		
		Label label1 = new Label();
		Label label2 = new Label();
		Label label3 = new Label();
		Label label4 = new Label();
		Label label5 = new Label();
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> stage.close());
		
		GridPane addProfile = new GridPane();
		addProfile.setPadding(new Insets(10, 10, 20, 20));
		addProfile.setVgap(8);
		addProfile.setHgap(10);
		
		Label name = new Label("Enter full name of profile:");
		GridPane.setConstraints(name, 0, 0);
		TextField enterName = new TextField();
		enterName.setPromptText("enter name");
		GridPane.setConstraints(enterName, 1, 0);
		GridPane.setConstraints(label1, 2, 0);
		
		Label status = new Label("Enter status: (optional) ");
		GridPane.setConstraints(status, 0, 1);
		TextField enterStatus = new TextField();
		enterStatus.setPromptText("status");
		GridPane.setConstraints(enterStatus, 1, 1);
		GridPane.setConstraints(label2, 2, 1);
		
		Label age = new Label("Enter date of birth:");
		GridPane.setConstraints(age,0,2);
		
		DatePicker datePicker = new DatePicker();
		datePicker.setOnAction(e -> {
			LocalDate date = datePicker.getValue();
			System.out.println(date);
		});
		Button upload = new Button("Upload Image");
		Label choosePic = new Label("Choose Profile Picture");
		GridPane.setConstraints(upload, 1, 3);
		GridPane.setConstraints(choosePic, 0, 3);
		upload.setOnAction(e ->{
			FileChooser fileChooser = new FileChooser();
	         
	        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	        
	        File imageFile = fileChooser.showOpenDialog(null);
	        imagePath = imageFile.getAbsolutePath();
		});
		
		GridPane.setConstraints(datePicker, 1, 2);
		GridPane.setConstraints(label3, 2, 2);
		
		Label state = new Label("Select State:");
		stateChoice.getItems().addAll("ACT","NSW","NT","QLD","SA","TAS","VIC","WA");
		stateChoice.setValue("VIC");
		GridPane.setConstraints(stateChoice, 0, 6);
		GridPane.setConstraints(state, 0, 5);
		
		Label enterSex = new Label("Male/Female");
		sexChoice.getItems().addAll("Male","Female");
		sexChoice.setValue("Male");
		GridPane.setConstraints(sexChoice, 1, 6);
		GridPane.setConstraints(enterSex, 1, 5);
		
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 2, 5);
		
		submit.setOnAction(e -> {
			int valid=0;
			if ((enterName.getText().isEmpty())) {
				label1.setStyle("-fx-text-fill: red");
				label1.setText("Please enter name");
				valid++;
			} else label1.setText(null);
			if (datePicker.getValue()==null) {
				label3.setStyle("-fx-text-fill: red");
				label3.setText("Please enter date of birth");
				valid++;
			}else label3.setText(null);

			if(sexChoice.getValue().equals("Male")) {
				sex="M";
			}	else sex="F";

			if(valid==0) 
			{
				LocalDate birthDate = datePicker.getValue();
				try {
						int setAge = getAge(birthDate);
						driver.addProfile(enterName.getText(),imagePath,enterStatus.getText(),
								sex,setAge,stateChoice.getValue());
						if(setAge<17) {
							int temp = profileIndex1;
							selectTwoNames("Dependent: Connect to both Parents to Continue", "Select both parents.");
							if(cancelFlag==true) {
								driver.connect(temp,profileIndex1,2);
								driver.connect(temp,profileIndex2,2);
							} 
							e.consume();
							display("Connected to Parents","You are now connected to your parents "+
									getName(profileIndex1)+" and "+getName(profileIndex2));
						}
				} catch(NoSuchAgeException e1) {
					System.out.println("Exception: "+e1);
				}
				
			}
			display("Profile Added",enterName.getText()+" has been added");
			stage.close();
					

		});
		
		
		Button cancel = new Button("Cancel");
		GridPane.setConstraints(cancel, 2, 6);
		cancel.setOnAction(e -> stage.close());
				
		addProfile.getChildren().addAll(name,enterName,status,enterStatus,age,
				submit,cancel,label1,label2,label3,label4,label5,
				datePicker,stateChoice,sexChoice,state,enterSex,choosePic,upload);
		
		Scene scene = new Scene(addProfile,600,300);
		scene.getStylesheets().add(style);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void removeProfile() throws NoParentException {
		selectName("Choose Profile to Remove");
		String temp = getName(profileIndex1);
		cancelFlag = driver.removeProfile(profileIndex1);
		if(cancelFlag==true) display("Profile Removed",temp + " has been removed.");
		if(cancelFlag==false) throw new NoParentException("Cannot remove, profile is parent of a dependent");
	}

	public void selectTwoNames(String title, String task)
	{
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setOnCloseRequest(e -> {
			display("Warning","Both names not entered");
			e.consume();
		});
		
		Scene scene;
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25,25,25,25)); 
    	grid.setVgap(10);
    	grid.setHgap(10);
    	
		ChoiceBox<String> prof1 = new ChoiceBox<>();
		ChoiceBox<String> prof2 = new ChoiceBox<>();
		
    	int num = driver.getNumProfiles();
    	for(int i=0;i<num;i++) {
    		String names = driver.getName(i);
    		prof1.getItems().add(names);
    		prof2.getItems().add(names);
    	}
    	prof1.getSelectionModel().selectedIndexProperty().addListener((v, oldValue1, newValue1) -> {
    		setProfileIndex1(newValue1.intValue());
    	});
		prof2.getSelectionModel().selectedIndexProperty().addListener((v, oldValue1, newValue2) -> {
    		setProfileIndex2(newValue2.intValue());
    	});
		prof1.setStyle("-fx-text-align: center");
		prof1.setTooltip(new Tooltip("Choose Profile name"));
		prof2.setStyle("-fx-text-align: center");
		prof2.setTooltip(new Tooltip("Choose Profile name"));
		
    	Label label1 = new Label("Choose first profile name:"); 	
    	Label label2 = new Label("Choose second profile name:"); 
    	
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e->{
			cancelFlag = false;
			stage.close();
			});
		
		Button submit = new Button("Submit");
		submit.getStyleClass().add("submit-button");
		submit.setOnAction(e2 -> {
			if(prof1.getValue()==null||prof2.getValue()==null) {
				e2.consume();
			}
			stage.close();
		});
		
		GridPane.setConstraints(label1, 0, 0);
		GridPane.setConstraints(label2, 1, 0);
		GridPane.setConstraints(prof1, 0, 1);
		GridPane.setConstraints(prof2, 1, 1);
		GridPane.setConstraints(submit, 2, 1);
		GridPane.setConstraints(cancel, 1, 3);
		GridPane.setHalignment(cancel, HPos.CENTER);
		grid.getChildren().addAll(label1,label2,prof1,prof2,submit,cancel);
		
		scene = new Scene(grid,700,200);
		label1.setPrefWidth(300);
		label2.setPrefWidth(300);
		scene.getStylesheets().add(style);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public void checkConnected(int name1, int name2) {
		String relationship = driver.checkConnected(name1, name2);
		display("Check Connection",getName(name1)+" is connected to "+
				getName(name2)+" as "+relationship);
	}
	
	public void connect(int name1, int name2, String title, String labelText) {
		Stage stage = new Stage();
		VBox box = new VBox();
		box.setPadding(new Insets(15,15,15,15));
		box.setSpacing(30);
		Label label = new Label(labelText);
		ChoiceBox<String> choose = new ChoiceBox<>();
		choose.getItems().addAll("Friend","Parent","Sibling","Colleague","Classmate","Couple");
		choose.setValue("Friend");
		choose.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {		
			setRelation(newValue.intValue());
		});
		Button submit = new Button("Submit");
		submit.setOnAction(e-> stage.close());
		
		box.getChildren().addAll(label,choose,submit);
		
		Scene scene = new Scene(box,300,200);
		stage.sizeToScene();
		scene.getStylesheets().add(style);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public void showConnections() {
		selectName("Enter Name of Profile");	
		String[] connections = driver.showConnections(profileIndex1);
		stage = new Stage();
		stage.setTitle("Connections List");
		
		ListView<String> listView = new ListView<>();		
		for(int i=0;i<connections.length;i++) {
			listView.getItems().add(connections[i]);
		}			
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		layout.getChildren().add(listView);
		
		scene = new Scene(layout, 300, 250);
		scene.getStylesheets().add(style);
		stage.setScene(scene);
		stage.show();
			
	}
	
	public void defineRelation() {
		selectTwoNames("Define a Relation","Enter two names:");
		connect(profileIndex1,profileIndex2,"Update relation","How are they connected?");
	}
	

	public void loadData() {
    	if(driver.profileData()==true);
    		driver.relationsData();
	}

	
	public int getAge(LocalDate birthDate) throws NoSuchAgeException {
		LocalDate currentDate = LocalDate.now();
		int age=0;
		int days=0;
		if ((birthDate != null) && (currentDate != null)) 
        {
            age = Period.between(birthDate, currentDate).getYears();
            days =Period.between(birthDate, currentDate).getDays();
        }
		if(days==0||age>150) {
			throw new NoSuchAgeException("Not a realistic age",age);} 
		return age;
	}

	public void setRelation(int selection) {
		
		String relation ="";
		switch(selection)
		{
			case 0: relation = "Friend";
					break;
			case 1: relation = "Parent-Child";
					break;
			case 2: relation = "Sibling";
					break;
			case 3: relation = "Colleague";
					break;
			case 4: relation = "Classmate";
					break;
			case 5: relation = "Couple";
					break;
			default: break;
		}
		this.relation = relation;
	}
    
	public void styleSheet() {
		String addstyle = getClass().getResource("style.css").toString();
		style=addstyle;
	}
	
	private void setProfileIndex1(int index) {
		profileIndex1=index;
	}
	
	private void setProfileIndex2(int index) {
		profileIndex2=index;
	}

	public boolean constraints() throws TooYoungException,NotToBeFriendsException,NotAvailableException,
			NotToBeCoupledException, NotToBeColleaguesException, NotToBeClassmatesException {
		return driver.constraints(profileIndex1,profileIndex2,relationIndex);
	}

	public void pathChooser() {
		Stage stage = new Stage();
		stage.setTitle("Enter workspace directory");
		stage.setOnCloseRequest(e -> e.consume());
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20,20,20,20));
		vbox.setSpacing(10);
		TextField path = new TextField();
		workspace = path.getText();	
		Button submit = new Button("Submit");
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e-> stage.close());
		vbox.getChildren().addAll(path,submit,cancel);
		
		Scene scene = new Scene(vbox,320,150);
		scene.getStylesheets().add(style);
		stage.setScene(scene);
		stage.showAndWait();
	}
}
