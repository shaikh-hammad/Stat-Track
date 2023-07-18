import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
/*
Application class for Stat-Track
View and manage limitless NBA seasons
@author: Hammad Shaikh
build date: 2023-04-12
 */
public class App extends Application {
    //global stage to be set
    private Stage stage;
    //logo and background image initialization
    private final Image logo = new Image("file:logo.png");
    private final Image bg = new Image("file:bg.png");
    private final ImageView logoView = new ImageView(logo);
    //creation of global player list
    private static final ArrayList<Player> players = new ArrayList<>();
    //database file initialization
    private final File database = new File("database.txt");
    //record counters
    private int totalRecords;
    private int totalPlayers;
    //detect first launch
    private Boolean firstLaunch = true;
    //global BackGroundImage for application
    private final BackgroundImage backgroundImage = new BackgroundImage(
            bg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(540, BackgroundSize.AUTO, false, false, true, true)
    );
    //initial scene
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        //create Player ArrayList on launch
        if (firstLaunch){
            readDataFromFile();
            firstLaunch = false;
        }
        //set application title and logo
        stage.getIcons().add(logo);
        stage.setTitle("Stat-Track");

        //Create application banner
        HBox titleBar = new HBox();
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.BLACK);
        txtTitle.setStyle("-fx-font-weight: 900");
        //add logo
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.75)");

        //user greeting
        Text txtWelcome = new Text("Welcome to Stat-Track!");
        Text txtOption = new Text("Select an option below:");
        txtWelcome.setFont(new Font("Arial", 20));
        txtOption.setFont(new Font("Arial", 15));
        txtWelcome.setStyle("-fx-font-weight: 900");
        VBox homeText = new VBox();
        homeText.getChildren().addAll(txtWelcome, txtOption);
        homeText.setAlignment(Pos.CENTER);

        //display menu
        Button btSeason = new Button("Season Stats");
        btSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btSeason.setOnMouseEntered(e -> {
            btSeason.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btSeason.setTextFill(Color.WHITE);
            btSeason.setCursor(Cursor.HAND);
                }
        );
        btSeason.setOnMouseExited(e -> {
            btSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btSeason.setTextFill(Color.BLACK);
        });
        Button btPlayer = new Button("Player Stats");
        btPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btPlayer.setOnMouseEntered(e -> {
            btPlayer.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btPlayer.setTextFill(Color.WHITE);
            btPlayer.setCursor(Cursor.HAND);
                }
        );
        btPlayer.setOnMouseExited(e -> {
            btPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btPlayer.setTextFill(Color.BLACK);
        });
        Button btAllStats = new Button("All Seasons");
        btAllStats.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btAllStats.setOnMouseEntered(e -> {
            btAllStats.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btAllStats.setTextFill(Color.WHITE);
            btAllStats.setCursor(Cursor.HAND);
        }
        );
        btAllStats.setOnMouseExited(e -> {
            btAllStats.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btAllStats.setTextFill(Color.BLACK);
        });
        btAllStats.setOnAction(e -> viewAllStats());
        btPlayer.setOnAction(e -> choosePlayer());
        btSeason.setOnAction(e -> chooseYearStats());
        Button btAdd = new Button("Add Record");
        btAdd.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btAdd.setOnMouseEntered(e -> {
            btAdd.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btAdd.setTextFill(Color.WHITE);
            btAdd.setCursor(Cursor.HAND);
                }
        );
        btAdd.setOnMouseExited(e -> {
            btAdd.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btAdd.setTextFill(Color.BLACK);
        });
        btAdd.setOnAction(e -> addPlayer(""));

        HBox homeButtonRow = new HBox();
        homeButtonRow.getChildren().addAll(btSeason, btPlayer, btAllStats);
        homeButtonRow.setAlignment(Pos.CENTER);

        Button btResetHome = new Button("Reset Stats");
        btResetHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btResetHome.setOnMouseEntered(e -> {
            btResetHome.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btResetHome.setTextFill(Color.WHITE);
            btResetHome.setCursor(Cursor.HAND);
                }
        );
        btResetHome.setOnMouseExited(e -> {
            btResetHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btResetHome.setTextFill(Color.BLACK);
        });
        btResetHome.setOnAction(e -> confirmReset());
        Button btExitHome = new Button("Exit");
        btExitHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btExitHome.setOnMouseEntered(e -> {
            btExitHome.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btExitHome.setTextFill(Color.WHITE);
            btExitHome.setCursor(Cursor.HAND);
                }
        );
        btExitHome.setOnMouseExited(e -> {
            btExitHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btExitHome.setTextFill(Color.BLACK);
        });
        btExitHome.setOnAction(e -> cleanExit());
        HBox homeButtonRow2 = new HBox();
        homeButtonRow2.getChildren().addAll(btAdd,btResetHome);
        homeButtonRow2.setAlignment(Pos.CENTER);

        VBox homeButtonCont = new VBox(10);
        homeButtonCont.getChildren().addAll(homeText, homeButtonRow, homeButtonRow2, btExitHome);
        homeButtonCont.setAlignment(Pos.CENTER);
        homeButtonCont.setStyle("-fx-background-color: rgba(255, 255, 255, 0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        homeButtonCont.setMaxWidth(300);
        VBox space = new VBox();
        space.setPrefHeight(20);

        //create and display content container
        VBox homeContainer = new VBox(50);
        homeContainer.setBackground(new Background(backgroundImage));
        homeContainer.getChildren().addAll(titleBar, space, homeButtonCont);
        homeContainer.setAlignment(Pos.TOP_CENTER);

        //create scene and show stage
        Scene initialScene = new Scene(homeContainer, 960, 540);
        stage.setScene(initialScene);
        stage.show();
    }
    //obtain database from storage file and create Arraylists to hold data
    public void readDataFromFile() {
        //try with resources
        try(Scanner input = new Scanner(database)){
            //keep a count of total seasons and players
            totalRecords = 0;
            totalPlayers = 0;
            //scan whole file line by line
            while (input.hasNextLine()){
                String row = input.nextLine();
                //file is comma-separated in a uniform order per-line
                String[] dataArray = row.split(",");
                String name = dataArray[0];
                String year = dataArray[1];
                double points = Double.parseDouble(dataArray[2]);
                double rebounds = Double.parseDouble(dataArray[3]);
                double assists = Double.parseDouble(dataArray[4]);

                //only execute on first line of text file
                if (players.size()==0){
                    PlayerSeason season = new PlayerSeason(year, points, rebounds, assists,name);
                    Player player = new Player(name);
                    totalPlayers += 1;
                    player.seasonList.add(season);
                    players.add(player);
                    totalRecords+=1;
                }
                //if not first line, start looking for matches to avoid duplicate entries
                else {
                    boolean match = false;
                    for (int i=0;i<players.size();i++){
                        //if player exists
                        if (players.get(i).getName().equals(name)){
                            match = true;
                            boolean matchYear = false;
                            for (int j=0;j<players.get(i).seasonList.size();j++){
                                //if season for player already exists, edit values and break loop
                                if (players.get(i).seasonList.get(j).getYear().equals(year)){
                                    matchYear = true;
                                    players.get(i).seasonList.get(j).setYear(year);
                                    players.get(i).seasonList.get(j).setPoints(points);
                                    players.get(i).seasonList.get(j).setRebounds(rebounds);
                                    players.get(i).seasonList.get(j).setAssists(assists);
                                    totalRecords+=1;
                                    break;
                                }
                            }
                            //if season does not already exist, add new season to player
                            if (!matchYear){
                                PlayerSeason season = new PlayerSeason(year, points, rebounds, assists,name);
                                players.get(i).seasonList.add(season);
                                totalRecords+=1;
                            }
                            Collections.sort(players.get(i).seasonList);
                            break;
                        }
                    }
                    if (!match){
                        PlayerSeason season = new PlayerSeason(year, points, rebounds, assists,name);
                        Player player = new Player(name);
                        totalPlayers += 1;
                        player.seasonList.add(season);
                        players.add(player);
                        totalRecords+=1;
                    }
                }
            }
            Collections.sort(players);
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }
    //save data to file
    public void writeDataToFile(){
        try (PrintWriter pWriter = new PrintWriter(new FileOutputStream("database.txt", false))){
            for (int i=0;i<players.size();i++){
                for (int j=0;j<players.get(i).seasonList.size();j++){
                    //comma-separated format
                    pWriter.println(players.get(i).getName() + "," + players.get(i).seasonList.get(j).getYear() + "," + players.get(i).seasonList.get(j).getPoints() + "," + players.get(i).seasonList.get(j).getRebounds() + "," + players.get(i).seasonList.get(j).getAssists());
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Could not save data, output file not found.");
        }
    }

    //user screen to add player, takes error as parameter
    public void addPlayer(String error){
        try {
            //reconstruct user interface
            HBox titleBar = new HBox();
            // Create the User Input Screen
            Text txtTitle = new Text("Stat-Track");
            logoView.setFitWidth(100);
            logoView.setPreserveRatio(true);
            txtTitle.setFont(new Font("Arial", 40));
            txtTitle.setFill(Color.WHITE);
            txtTitle.setStyle("-fx-font-weight: 900");
            titleBar.getChildren().addAll(logoView, txtTitle);
            titleBar.setAlignment(Pos.CENTER);
            titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75)");

            Text txtAddPlayer = new Text("Add Player or Season");
            txtAddPlayer.setFont(new Font("Arial", 20));
            txtAddPlayer.setStyle("-fx-font-weight: 900");
            txtAddPlayer.setFill(Color.WHITE);

            //create user input fields for adding new player/season
            Label lblName = new Label("Player Name: ");
            TextField tfName = new TextField();
            Label lblYear = new Label("Year: ");
            TextField tfYear = new TextField();
            Label lblPoints = new Label("Points per game: ");
            TextField tfPoints = new TextField();
            Label lblAssists = new Label("Assists per game: ");
            TextField tfAssists = new TextField();
            Label lblRebounds = new Label("Rebounds per game: ");
            TextField tfRebounds = new TextField();
            GridPane inputPane = new GridPane();
            inputPane.add(lblName, 0, 0);
            inputPane.add(tfName, 1, 0);
            inputPane.add(lblYear, 0, 1);
            inputPane.add(tfYear, 1, 1);
            inputPane.add(lblPoints, 0, 2);
            inputPane.add(tfPoints, 1, 2);
            inputPane.add(lblRebounds, 0, 3);
            inputPane.add(tfRebounds, 1, 3);
            inputPane.add(lblAssists, 0, 4);
            inputPane.add(tfAssists, 1, 4);
            inputPane.setAlignment(Pos.CENTER);

            Button btAddPlayer = new Button("Add Player/Season");
            btAddPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
            btAddPlayer.setOnMouseEntered(e -> {
                btAddPlayer.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
                btAddPlayer.setTextFill(Color.WHITE);
                btAddPlayer.setCursor(Cursor.HAND);
                    }
            );
            btAddPlayer.setOnMouseExited(e -> {
                btAddPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
                btAddPlayer.setTextFill(Color.BLACK);
            });
            //upon submit, send data to handler
            btAddPlayer.setOnAction(e -> {try{
                createPlayer(tfName.getText(), tfYear.getText(), Double.parseDouble(tfPoints.getText()), Double.parseDouble(tfRebounds.getText()), Double.parseDouble(tfAssists.getText()));
            }catch(NumberFormatException ex){
                addPlayer("Invalid stat values!");
            }});
            Button btCancel = new Button("Cancel");
            btCancel.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
            btCancel.setOnMouseEntered(e -> {
                btCancel.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
                btCancel.setTextFill(Color.WHITE);
                btCancel.setCursor(Cursor.HAND);
                    }
            );
            btCancel.setOnMouseExited(e -> {
                btCancel.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
                btCancel.setTextFill(Color.BLACK);
            });
            btCancel.setOnAction(e -> start(stage));
            HBox buttonsAddPlayer = new HBox();
            buttonsAddPlayer.getChildren().addAll(btAddPlayer, btCancel);
            buttonsAddPlayer.setAlignment(Pos.CENTER);


            //create error text in case of redirect upon incorrect values
            Text txtError = new Text(error);

            VBox contentBox = new VBox(15);
            contentBox.getChildren().addAll(inputPane, buttonsAddPlayer, txtError);
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
            contentBox.setMaxWidth(600);

            //wrap in container and create, display scene
            VBox addPlayerContainer = new VBox(30);
            addPlayerContainer.getChildren().addAll(titleBar, txtAddPlayer, contentBox);
            addPlayerContainer.setAlignment(Pos.TOP_CENTER);
            addPlayerContainer.setBackground(new Background(backgroundImage));
            Scene addScene = new Scene(addPlayerContainer, 960, 540);
            stage.setScene(addScene);
            stage.show();
        }
        //upon a NFE, redirect to screen with error set
        catch (NumberFormatException e){
            addPlayer("Invalid stat value!");
        }
    }
    //handler for adding new records
    public void createPlayer(String name, String year, double points, double rebounds, double assists){
        try{
            //if fields are blank set error text and redirect
            if (name.equals("") || year.equals("") || Integer.parseInt(year) < 1949 || Integer.parseInt(year) > 2023){
                addPlayer("Please enter a valid name and year!");
                return;
            }
            //if data list is not empty
            else if (players.size()>0){
                boolean match = false;
                for (int i=0;i<players.size();i++){
                    //if player already exists
                    if (players.get(i).getName().equalsIgnoreCase(name)){
                        match = true;
                        for (int j=0;j<players.get(i).seasonList.size();j++){
                            //if season already exists, edit values and redirect
                            if (players.get(i).seasonList.get(j).getYear().equals(year)){
                                editSeason(2, i, j, year, points, rebounds, assists);
                                return;
                            }
                        }
                        //if season does not already exist, create new season
                        PlayerSeason season = new PlayerSeason(year, points, rebounds, assists,name);
                        totalRecords += 1;
                        players.get(i).seasonList.add(season);
                        Collections.sort(players.get(i).seasonList);
                    }
                }
                //if player does not exist, create new player and player season
                if (!match){
                    PlayerSeason season = new PlayerSeason(year, points, rebounds, assists,name);
                    totalRecords+= 1;
                    Player player = new Player(name);
                    totalPlayers += 1;
                    player.seasonList.add(season);
                    players.add(player);
                    start(stage);
                }
            }
            //if data list has no entries (first entry), new player and season
            else {
                PlayerSeason season = new PlayerSeason(year, points, rebounds, assists,name);
                totalRecords +=1;
                Player player = new Player(name);
                totalPlayers += 1;
                player.seasonList.add(season);
                players.add(player);
                start(stage);
            }
            Collections.sort(players);
            //save data to file upon completion
            writeDataToFile();
        }
        //redirect with error text on NFE
        catch (NumberFormatException e){
            addPlayer("Incorrect stat values!");
        }
    }
    public void deleteRecord(int pIndex, int sIndex){
        players.get(pIndex).seasonList.remove(sIndex);
        writeDataToFile();
        addPlayer("Season removed");
    }
    //user page for editing individual seasons
    public void editSeasonPage(int redirect, String name, String year, String error){
        //parameters - redirect variable, season identifiers, potential error text

        //find entry in master list based on identifiers and save indices for handler
        int pIndex = 0;
        int sIndex = 0;
        for (int i = 0; i<players.size();i++){
            if (players.get(i).getName().equals(name)){
                pIndex = i;
                for (int j=0;j<players.get(i).seasonList.size();j++){
                    if(players.get(i).seasonList.get(j).getYear().equals(year)){
                        sIndex = j;
                    }
                }
            }
        }

        //reconstruct user interface
        HBox titleBar = new HBox();
        // Create the User Input Screen
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: darkorange");

        Text txtEditSeason = new Text("Edit Season");
        txtEditSeason.setFont(new Font("Arial", 20));
        txtEditSeason.setStyle("-fx-font-weight: 900");
        txtEditSeason.setFill(Color.WHITE);

        //set data using previously saved indices
        Label lblName = new Label("Player Name: ");
        Text txtName = new Text(name);
        Label lblYear = new Label("Year: ");
        TextField tfYear = new TextField();
        tfYear.setText(year);
        Label lblPoints = new Label("Points per game: ");
        TextField tfPoints = new TextField();
        tfPoints.setText(String.valueOf(players.get(pIndex).seasonList.get(sIndex).getPoints()));
        Label lblAssists = new Label("Assists per game: ");
        TextField tfAssists = new TextField();
        tfAssists.setText(String.valueOf(players.get(pIndex).seasonList.get(sIndex).getAssists()));
        Label lblRebounds = new Label("Rebounds per game: ");
        TextField tfRebounds = new TextField();
        tfRebounds.setText(String.valueOf(players.get(pIndex).seasonList.get(sIndex).getRebounds()));
        GridPane inputPane = new GridPane();
        inputPane.add(lblName, 0, 0);
        inputPane.add(txtName, 1, 0);
        inputPane.add(lblYear, 0, 1);
        inputPane.add(tfYear, 1, 1);
        inputPane.add(lblPoints, 0, 2);
        inputPane.add(tfPoints, 1, 2);
        inputPane.add(lblRebounds, 0, 3);
        inputPane.add(tfRebounds, 1, 3);
        inputPane.add(lblAssists, 0, 4);
        inputPane.add(tfAssists, 1, 4);
        inputPane.setAlignment(Pos.CENTER);

        Button btEditSeason = new Button("Save Season");
        btEditSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btEditSeason.setOnMouseEntered(e -> {
            btEditSeason.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btEditSeason.setTextFill(Color.WHITE);
            btEditSeason.setCursor(Cursor.HAND);
                }
        );
        btEditSeason.setOnMouseExited(e -> {
            btEditSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btEditSeason.setTextFill(Color.BLACK);
        });
        int finalPIndex = pIndex;
        int finalSIndex = sIndex;

        //upon submit, send info to handler
        btEditSeason.setOnAction(e -> editSeason(redirect, finalPIndex, finalSIndex, tfYear.getText(), Double.parseDouble(tfPoints.getText()), Double.parseDouble(tfRebounds.getText()), Double.parseDouble(tfAssists.getText())));
        Button btCancel = new Button("Cancel");
        btCancel.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btCancel.setOnMouseEntered(e -> {
            btCancel.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btCancel.setTextFill(Color.WHITE);
            btCancel.setCursor(Cursor.HAND);
                }
        );
        btCancel.setOnMouseExited(e -> {
            btCancel.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btCancel.setTextFill(Color.BLACK);
        });
        btCancel.setOnAction(e -> start(stage));
        Button btDelete = new Button("Delete Season");
        btDelete.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btDelete.setOnMouseEntered(e -> {
            btDelete.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btDelete.setTextFill(Color.WHITE);
            btDelete.setCursor(Cursor.HAND);
                }
        );
        btDelete.setOnMouseExited(e -> {
            btDelete.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btDelete.setTextFill(Color.BLACK);
        });
        btDelete.setOnAction(e -> deleteRecord(finalPIndex, finalSIndex));
        HBox buttonsEditSeason= new HBox();
        buttonsEditSeason.getChildren().addAll(btEditSeason, btCancel,btDelete);
        buttonsEditSeason.setAlignment(Pos.CENTER);

        //error text in case of redirect on exception
        Text txtError = new Text(error);

        VBox contentBox = new VBox(15);
        contentBox.getChildren().addAll(inputPane, buttonsEditSeason, txtError);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);

        VBox editSeasonContainer = new VBox(30);
        editSeasonContainer.getChildren().addAll(titleBar, txtEditSeason, contentBox);
        editSeasonContainer.setAlignment(Pos.TOP_CENTER);
        editSeasonContainer.setBackground(new Background(backgroundImage));

        Scene addScene = new Scene(editSeasonContainer, 960, 540);
        stage.setScene(addScene);
        stage.show();
    }
    //handler to edit existing seasons
    public void editSeason(int redirect, int pIndex, int sIndex, String year, double points, double rebounds, double assists){
        try{
            //attempt to convert year to integer just to check format
            int testYear = Integer.parseInt(year);
            //if year is out of bounds, throw exception and redirect
            if (testYear > 2023 || testYear < 1949){
                throw new NumberFormatException();
            }
            //else, edit season and redirect based on value given
            players.get(pIndex).seasonList.get(sIndex).setYear(year);
            players.get(pIndex).seasonList.get(sIndex).setPoints(points);
            players.get(pIndex).seasonList.get(sIndex).setRebounds(rebounds);
            players.get(pIndex).seasonList.get(sIndex).setAssists(assists);
            Collections.sort(players.get(pIndex).seasonList);
            if (redirect == 0) {
                viewPlayerStats(year);
            }
            else if (redirect == 1){
                viewPlayerStats(players.get(pIndex).getName());
            } else if (redirect == 2) {
                start(stage);
            }
            Collections.sort(players);
            //save data to file
            writeDataToFile();
        }
        catch (NumberFormatException e){
            //upon NFE, redirect with error text
            editSeasonPage(2, players.get(pIndex).getName(), players.get(pIndex).seasonList.get(sIndex).getYear(), "Invalid Stat Values");
        }
    }
    //page to display all seasons to user at once
    public void viewAllStats(){
        //reconstruct user interface
        HBox titleBar = new HBox();
        // Create the User Input Screen
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView, txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75);");

        Text txtYearTitle = new Text("All Stats: " + totalRecords + " records");
        txtYearTitle.setFill(Color.WHITE);
        txtYearTitle.setFont(new Font("Arial", 20));
        txtYearTitle.setStyle("-fx-font-weight: 900");

        //use TableView  to display data in order to reduce performance overhead and wait times
        TableView<PlayerSeason> table = new TableView<>();
        table.setEditable(false);

        //create columns in table
        TableColumn<PlayerSeason, String> nameCol = new TableColumn<>("Name");
        //fetch name from current index to display in cell
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<PlayerSeason, String> yearCol = new TableColumn<>("Season");
        //fetch year from current index to display in cell
        yearCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));

        TableColumn<PlayerSeason, Double> pointsCol = new TableColumn<>("Points");
        //fetch points from current index to display in cell
        pointsCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPoints()).asObject());

        TableColumn<PlayerSeason, Double> reboundsCol = new TableColumn<>("Rebounds");
        //fetch rebounds from current index to display in cell
        reboundsCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRebounds()).asObject());

        TableColumn<PlayerSeason, Double> assistsCol = new TableColumn<>("Assists");
        //fetch assists from current index to display in cell
        assistsCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAssists()).asObject());

        //create edit button for each row
        TableColumn<PlayerSeason, Void> editCol = new TableColumn<>("Edit");
        editCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Edit");

            //required override to fetch the correct identifiers to pass parameters to editSeasonPage()
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PlayerSeason season = getTableView().getItems().get(getIndex());
                    btn.setOnAction(e -> editSeasonPage(0, season.getName(), season.getYear(),""));
                    setGraphic(btn);
                }
            }
        });

        //add columns to table
        table.getColumns().addAll(nameCol, yearCol, pointsCol, reboundsCol, assistsCol, editCol);
        //fit columns to full width of table
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //migrate data to ObservableList so the TableView is notified on any changes/updates
        ObservableList<PlayerSeason> data = FXCollections.observableArrayList();
        for (int i = 0; i<players.size();i++) {
            data.addAll(players.get(i).seasonList);
        }
        //display data on table
        table.setItems(data);
        table.setMaxHeight(250);
        table.setMaxWidth(500);

        Button btAddPlayer = new Button("Add Player/Season");
        btAddPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btAddPlayer.setOnMouseEntered(e -> {
            btAddPlayer.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btAddPlayer.setTextFill(Color.WHITE);
            btAddPlayer.setCursor(Cursor.HAND);
                }
        );
        btAddPlayer.setOnMouseExited(e -> {
            btAddPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btAddPlayer.setTextFill(Color.BLACK);
        });
        btAddPlayer.setOnAction(e -> addPlayer(""));
        Button btReset = new Button("Reset Stats");
        btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btReset.setOnMouseEntered(e -> {
            btReset.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btReset.setTextFill(Color.WHITE);
            btReset.setCursor(Cursor.HAND);
                }
        );
        btReset.setOnMouseExited(e -> {
            btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btReset.setTextFill(Color.BLACK);
        });
        btReset.setOnAction(e -> confirmReset());
        Button btHome = new Button("Home");
        btHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btHome.setOnMouseEntered(e -> {
            btHome.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btHome.setTextFill(Color.WHITE);
            btHome.setCursor(Cursor.HAND);
                }
        );
        btHome.setOnMouseExited(e -> {
            btHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btHome.setTextFill(Color.BLACK);
        });
        btHome.setOnAction(e -> start(stage));
        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(btAddPlayer, btReset, btHome);
        buttonRow.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(15);
        contentBox.getChildren().addAll(table, buttonRow);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);
        VBox statsContainer = new VBox(15);
        statsContainer.getChildren().addAll(titleBar, txtYearTitle, contentBox);
        statsContainer.setAlignment(Pos.TOP_CENTER);
        statsContainer.setBackground(new Background(backgroundImage));
        Scene yearScene = new Scene(statsContainer, 960, 540);
        stage.setScene(yearScene);
        stage.show();
    }
    //page for user to select which season to view
    public void chooseYearStats(){
        //reconstruct user interface
        HBox titleBar = new HBox();
        // Create the User Input Screen
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75)");

        Text txtYear = new Text("Select a year to see the stats:");
        txtYear.setFont(new Font("Arial", 20));
        txtYear.setStyle("-fx-font-weight: 900");
        txtYear.setFill(Color.WHITE);
        TableView<Integer> table = new TableView<>();
        table.setEditable(false);

        //Use TableView again to display data efficiently
        TableColumn<Integer, Integer> seasonCol = new TableColumn<>("Season");
        //pass int(seasonList.getYear()) as an ObservableValue for the table cell
        seasonCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue()).asObject());

        //create view season button for each row
        TableColumn<Integer, Void> viewCol = new TableColumn<>("View Season");
        viewCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("View");

            //required override to pass year to viewYearStats() method
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    String season = String.valueOf(getTableView().getItems().get(getIndex()));
                    btn.setOnAction(e -> viewYearStats(season));
                    setAlignment(Pos.CENTER);
                    setGraphic(btn);
                }
            }
        });

        //add columns to table
        table.getColumns().addAll(seasonCol,viewCol);
        //fit columns to table width
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate new ObservableList with list of all UNIQUE years that data is available
        ObservableList<Integer> data = FXCollections.observableArrayList();
        for (int i = 0;i<players.size();i++) {
            for (int j = 0;j<players.get(i).seasonList.size();j++){
                //convert year to int for sorting
                if (!data.contains(Integer.parseInt(players.get(i).seasonList.get(j).getYear()))){
                    data.add(Integer.parseInt(players.get(i).seasonList.get(j).getYear()));
                }
            }
        }
        //set data in table and sort by year descending
        table.setItems(data);
        seasonCol.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(seasonCol);
        table.sort();

        table.setMaxHeight(250);
        table.setMaxWidth(500);
        Button btCancel = new Button("Cancel");
        btCancel.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btCancel.setOnMouseEntered(e -> {
            btCancel.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btCancel.setTextFill(Color.WHITE);
            btCancel.setCursor(Cursor.HAND);
                }
        );
        btCancel.setOnMouseExited(e -> {
            btCancel.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btCancel.setTextFill(Color.BLACK);
        });
        btCancel.setOnAction(e -> start(stage));

        VBox contentBox = new VBox(15);
        contentBox.getChildren().addAll(table, btCancel);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);

        VBox yearContainer = new VBox(15);
        yearContainer.getChildren().addAll(titleBar, txtYear, contentBox);
        yearContainer.setAlignment(Pos.TOP_CENTER);
        yearContainer.setBackground(new Background(backgroundImage));
        Scene yearScene = new Scene(yearContainer, 960, 540);
        stage.setScene(yearScene);
        stage.show();
    }
    //user page to view all stats for a specific season
    public void viewYearStats(String year){
        //reconstruct user interface
        HBox titleBar = new HBox();
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75)");
        Text txtYearTitle = new Text("Stat List " + year + ": ");
        txtYearTitle.setFont(new Font("Arial", 20));
        txtYearTitle.setStyle("-fx-font-weight: 900");
        txtYearTitle.setFill(Color.WHITE);

        //TableView in use again for efficiency
        TableView<PlayerSeason> table = new TableView<>();
        table.setEditable(false);

        //create and populate columns
        TableColumn<PlayerSeason, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<PlayerSeason, Double> pointsCol = new TableColumn<>("Points");
        pointsCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPoints()).asObject());

        TableColumn<PlayerSeason, Double> reboundsCol = new TableColumn<>("Rebounds");
        reboundsCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRebounds()).asObject());

        TableColumn<PlayerSeason, Double> assistsCol = new TableColumn<>("Assists");
        assistsCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAssists()).asObject());

        //edit button for each row(season)
        TableColumn<PlayerSeason, Void> editCol = new TableColumn<>("Edit");
        editCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Edit");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PlayerSeason season = getTableView().getItems().get(getIndex());
                    btn.setOnAction(e -> editSeasonPage(0, season.getName(), season.getYear(),""));
                    setGraphic(btn);
                }
            }
        });

        //add all columns to table
        table.getColumns().addAll(nameCol, pointsCol, reboundsCol, assistsCol, editCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate ObservableList for TableView by checking for matches with year
        ObservableList<PlayerSeason> data = FXCollections.observableArrayList();
        for (int i = 0; i<players.size(); i++) {
            for (int j = 0; j < players.get(i).seasonList.size(); j++) {
                if (players.get(i).seasonList.get(j).getYear().equals(year)) {
                    data.add(players.get(i).seasonList.get(j));
                }
            }
        }
        table.setItems(data);
        table.setMaxHeight(250);
        table.setMaxWidth(500);

        Button btAddPlayer = new Button("Add Player");
        btAddPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btAddPlayer.setOnMouseEntered(e -> {
            btAddPlayer.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btAddPlayer.setTextFill(Color.WHITE);
            btAddPlayer.setCursor(Cursor.HAND);
                }
        );
        btAddPlayer.setOnMouseExited(e -> {
            btAddPlayer.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btAddPlayer.setTextFill(Color.BLACK);
        });
        btAddPlayer.setOnAction(e -> start(stage));
        btAddPlayer.setOnAction(e -> addPlayer(""));
        Button btReset = new Button("Reset Stats");
        btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btReset.setOnMouseEntered(e -> {
            btReset.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btReset.setTextFill(Color.WHITE);
            btReset.setCursor(Cursor.HAND);
                }
        );
        btReset.setOnMouseExited(e -> {
            btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btReset.setTextFill(Color.BLACK);
        });
        btReset.setOnAction(e -> confirmReset());
        Button btHome = new Button("Home");
        btHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btHome.setOnMouseEntered(e -> {
            btHome.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btHome.setTextFill(Color.WHITE);
            btHome.setCursor(Cursor.HAND);
                }
        );
        btHome.setOnMouseExited(e -> {
            btHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btHome.setTextFill(Color.BLACK);
        });
        btHome.setOnAction(e -> start(stage));
        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(btAddPlayer, btReset,btHome);
        buttonRow.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(15);
        contentBox.getChildren().addAll(table, buttonRow);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);
        VBox statsContainer = new VBox(15);
        statsContainer.getChildren().addAll(titleBar, txtYearTitle, contentBox);
        statsContainer.setAlignment(Pos.TOP_CENTER);
        statsContainer.setBackground(new Background(backgroundImage));
        Scene yearScene = new Scene(statsContainer, 960, 540);
        stage.setScene(yearScene);
        stage.show();
    }
    //user page to select which player to view career stats for
    public void choosePlayer(){
        //reconstruct user interface
        HBox titleBar = new HBox();
        // Create the User Input Screen
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75)");

        Text txtPlayerTitle = new Text("Choose from " + totalPlayers + " players");
        txtPlayerTitle.setFont(new Font("Arial", 20));
        txtPlayerTitle.setStyle("-fx-font-weight: 900");
        txtPlayerTitle.setFill(Color.WHITE);

        //TableView in use again for efficiency
        TableView<Player> table = new TableView<>();
        table.setEditable(false);

        //create and populate columns
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        //create Comparator that converts back to Double for sorting
        Comparator<String> comp = (p1, p2) -> Double.compare(Double.parseDouble(p2),Double.parseDouble(p1));

        TableColumn<Player, String> pointsCol = new TableColumn<>("Career PPG");
        //format double value to one decimal place, displayed as String
        pointsCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%2.1f",cellData.getValue().getCareerPoints())));
        pointsCol.setComparator(comp);

        TableColumn<Player, String> reboundsCol = new TableColumn<>("Career RPG");
        //format double value to one decimal place, displayed as String
        reboundsCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%2.1f",cellData.getValue().getCareerRebounds())));
        reboundsCol.setComparator(comp);

        TableColumn<Player, String> assistsCol = new TableColumn<>("Career APG");
        //format double value to one decimal place, displayed as String
        assistsCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%2.1f",cellData.getValue().getCareerAssists())));
        assistsCol.setComparator(comp);

        //button for each row to view player career
        TableColumn<Player, Void> editCol = new TableColumn<>("View Career");
        editCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("View");

            //override to fetch parameter for viewPlayerStats() page
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Player player = getTableView().getItems().get(getIndex());
                    btn.setOnAction(e -> viewPlayerStats(player.getName()));
                    setGraphic(btn);
                }
            }
        });

        //add columns to table
        table.getColumns().addAll(nameCol, pointsCol, reboundsCol, assistsCol, editCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate ObservableList for TableView by adding all players
        ObservableList<Player> data = FXCollections.observableArrayList();
        data.addAll(players);
        //add data to table
        table.setItems(data);
        table.setMaxHeight(200);
        table.setMaxWidth(500);

        //filter search functionality for players
        Label lblSearch = new Label("Search for player: ");
        TextField tfSearch = new TextField();
        //placeholder text
        tfSearch.setPromptText("Filter");
        //add listener to text field to run handler any time the value changes
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            //pass through search word, table object, and data to handler
            filterPlayer(newValue, table,data);
        });

        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER);
        searchRow.getChildren().addAll(lblSearch, tfSearch);

        Button btAddSeason = new Button("Add Season");
        btAddSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btAddSeason.setOnMouseEntered(e -> {
            btAddSeason.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btAddSeason.setTextFill(Color.WHITE);
            btAddSeason.setCursor(Cursor.HAND);
                }
        );
        btAddSeason.setOnMouseExited(e -> {
            btAddSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btAddSeason.setTextFill(Color.BLACK);
        });
        btAddSeason.setOnAction(e -> addPlayer(""));
        Button btReset = new Button("Reset Stats");
        btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btReset.setOnMouseEntered(e -> {
            btReset.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btReset.setTextFill(Color.WHITE);
            btReset.setCursor(Cursor.HAND);
                }
        );
        btReset.setOnMouseExited(e -> {
            btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btReset.setTextFill(Color.BLACK);
        });
        btReset.setOnAction(e -> confirmReset());
        Button btHome = new Button("Home");
        btHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btHome.setOnMouseEntered(e -> {
            btHome.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btHome.setTextFill(Color.WHITE);
            btHome.setCursor(Cursor.HAND);
                }
        );
        btHome.setOnMouseExited(e -> {
            btHome.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btHome.setTextFill(Color.BLACK);
        });
        btHome.setOnAction(e -> start(stage));
        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(btAddSeason, btReset, btHome);
        buttonRow.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(15);
        contentBox.getChildren().addAll(searchRow, table, buttonRow);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);

        VBox statsContainer = new VBox(10);
        statsContainer.getChildren().addAll(titleBar, txtPlayerTitle,contentBox);
        statsContainer.setAlignment(Pos.TOP_CENTER);
        statsContainer.setBackground(new Background(backgroundImage));
        Scene playerScene = new Scene(statsContainer, 960, 540);
        stage.setScene(playerScene);
        stage.show();

    }
    //filter search handler for player names
    private void filterPlayer(String filter, TableView<Player> table, ObservableList<Player> data) {
        //if search word is invalid, return
        if (filter == null || filter.length() == 0) {
            table.setItems(data);
            return;
        }
        //refill ObservableList with new filtered data based on search word
        ObservableList<Player> filteredData = FXCollections.observableArrayList();
        for (int i =0;i< players.size();i++) {
            //normalize case
            if (players.get(i).getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredData.add(players.get(i));
            }
        }
        //display filtered content
        table.setItems(filteredData);
    }
    //user page to view career for specific player
    public void viewPlayerStats(String name){
        //reconstruct user interface
        HBox titleBar = new HBox();
        // Create the User Input Screen
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75)");

        Text txtPlayerTitle = new Text("Career Stats for " + name);
        txtPlayerTitle.setFont(new Font("Arial", 20));
        txtPlayerTitle.setStyle("-fx-font-weight: 900");
        txtPlayerTitle.setFill(Color.WHITE);

        //TableView in use again for efficiency
        TableView<PlayerSeason> table = new TableView<>();
        table.setEditable(false);

        //create columns for table
        TableColumn<PlayerSeason, String> seasonCol = new TableColumn<>("Season");

        //setting career average values if row index = (first row)
        seasonCol.setCellValueFactory(cellData -> {
            //set text if
            if (cellData.getTableView().getItems().indexOf(cellData.getValue()) == 0){
                return new SimpleStringProperty("Career");
            }
                return new SimpleStringProperty(cellData.getValue().getYear());
            }
        );

        //create Comparator for sorting
        Comparator<Double> comp = (p1, p2) -> Double.compare(p2,p1);

        int index = 0;
        for(int i = 0;i<players.size();i++){
            if (players.get(i).getName().equals(name)){
                index = i;
                break;
            }
        }
        TableColumn<PlayerSeason, Double> pointsCol = new TableColumn<>("PPG");
        int finalIndex = index;
        pointsCol.setCellValueFactory(cellData -> {
                    if (cellData.getTableView().getItems().indexOf(cellData.getValue()) == 0){
                        //this first row value needs to be properly formatted to one decimal place after being sorted
                        return new SimpleDoubleProperty(Double.parseDouble(String.format("%2.1f",players.get(finalIndex).getCareerPoints()))).asObject();
                    }
                    return new SimpleDoubleProperty(cellData.getValue().getPoints()).asObject();
                }
        );
        pointsCol.setComparator(comp);

        TableColumn<PlayerSeason, Double> reboundsCol = new TableColumn<>("RPG");
        reboundsCol.setCellValueFactory(cellData -> {
                    if (cellData.getTableView().getItems().indexOf(cellData.getValue()) == 0){
                        //this first row value needs to be properly formatted to one decimal place after being sorted
                        return new SimpleDoubleProperty(Double.parseDouble(String.format("%2.1f",players.get(finalIndex).getCareerRebounds()))).asObject();
                    }
                    return new SimpleDoubleProperty(cellData.getValue().getRebounds()).asObject();
                }
        );
        reboundsCol.setComparator(comp);

        TableColumn<PlayerSeason, Double> assistsCol = new TableColumn<>("APG");
        assistsCol.setCellValueFactory(cellData -> {
                    if (cellData.getTableView().getItems().indexOf(cellData.getValue()) == 0){
                        //this first row value needs to be properly formatted to one decimal place after being sorted
                        return new SimpleDoubleProperty(Double.parseDouble(String.format("%2.1f",players.get(finalIndex).getCareerAssists()))).asObject();
                    }
                    return new SimpleDoubleProperty(cellData.getValue().getAssists()).asObject();
                }
        );
        assistsCol.setComparator(comp);

        //edit button for each season(row)
        TableColumn<PlayerSeason, Void> editCol = new TableColumn<>("Edit Season");
        editCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Edit");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PlayerSeason season = getTableView().getItems().get(getIndex());
                    btn.setOnAction(e -> editSeasonPage(0, season.getName(), season.getYear(),""));
                    setGraphic(btn);
                }
            }
        });

        //add columns to table
        table.getColumns().addAll(seasonCol, pointsCol, reboundsCol, assistsCol, editCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate ObservableList for table with players with matching name
        ObservableList<PlayerSeason> data = FXCollections.observableArrayList();
        for (int i = 0; i<players.size();i++){
            if (players.get(i).getName().equals(name))
                data.addAll(players.get(i).seasonList);
        }
        //display data to table
        table.setItems(data);
        table.setMaxHeight(250);
        table.setMaxWidth(500);

        Button btAddSeason = new Button("Add Season");
        btAddSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btAddSeason.setOnMouseEntered(e -> {
            btAddSeason.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btAddSeason.setTextFill(Color.WHITE);
            btAddSeason.setCursor(Cursor.HAND);
                }
        );
        btAddSeason.setOnMouseExited(e -> {
            btAddSeason.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btAddSeason.setTextFill(Color.BLACK);
        });
        btAddSeason.setOnAction(e -> addPlayer(""));
        Button btReset = new Button("Reset Stats");
        btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btReset.setOnMouseEntered(e -> {
            btReset.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btReset.setTextFill(Color.WHITE);
            btReset.setCursor(Cursor.HAND);
                }
        );
        btReset.setOnMouseExited(e -> {
            btReset.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btReset.setTextFill(Color.BLACK);
        });
        btReset.setOnAction(e -> confirmReset());
        Button btBack = new Button("Back");
        btBack.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btBack.setOnMouseEntered(e -> {
            btBack.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btBack.setTextFill(Color.WHITE);
            btBack.setCursor(Cursor.HAND);
                }
        );
        btBack.setOnMouseExited(e -> {
            btBack.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btBack.setTextFill(Color.BLACK);
        });
        btBack.setOnAction(e -> choosePlayer());
        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(btAddSeason, btReset, btBack);
        buttonRow.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(15);
        contentBox.getChildren().addAll(table, buttonRow);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);

        VBox statsContainer = new VBox(15);
        statsContainer.getChildren().addAll(titleBar, txtPlayerTitle,contentBox);
        statsContainer.setAlignment(Pos.TOP_CENTER);
        statsContainer.setBackground(new Background(backgroundImage));
        Scene playerScene = new Scene(statsContainer, 960, 540);
        stage.setScene(playerScene);
        stage.show();
    }
    //user page to confirm reset of data
    public void confirmReset(){
        //reconstruct user interface
        HBox titleBar = new HBox();
        Text txtTitle = new Text("Stat-Track");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        txtTitle.setFont(new Font("Arial", 40));
        txtTitle.setFill(Color.WHITE);
        txtTitle.setStyle("-fx-font-weight: 900");
        titleBar.getChildren().addAll(logoView,txtTitle);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: rgba(255,140,0,0.75)");

        Text txtPlayerTitle = new Text("Confirm Reset");
        txtPlayerTitle.setFont(new Font("Arial", 20));
        txtPlayerTitle.setStyle("-fx-font-weight: 900");
        txtPlayerTitle.setFill(Color.WHITE);

        //user message
        Text txtConfirm = new Text("Would you really like to reset the whole database?");
        txtConfirm.setFont(new Font("Arial", 20));
        txtConfirm.setFill(Color.RED);
        txtConfirm.setStyle("-fx-font-weight: 900");
        Button btYes = new Button("Yes");
        btYes.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btYes.setOnMouseEntered(e -> {
            btYes.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btYes.setTextFill(Color.WHITE);
            btYes.setCursor(Cursor.HAND);
                }
        );
        btYes.setOnMouseExited(e -> {
            btYes.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btYes.setTextFill(Color.BLACK);
        });
        Button btNo = new Button("No");
        btNo.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black;");
        btNo.setOnMouseEntered(e -> {
            btNo.setStyle("-fx-background-color: rgba(255,140,0,1);-fx-border-color:black;-fx-background-radius: 10px;-fx-border-radius: 10px;");
            btNo.setTextFill(Color.WHITE);
            btNo.setCursor(Cursor.HAND);
                }
        );
        btNo.setOnMouseExited(e -> {
            btNo.setStyle("-fx-background-color: lightgrey;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: black");
            btNo.setTextFill(Color.BLACK);
        });
        //upon confirmation, run method to wipe data
        btYes.setOnAction(e -> resetStats());
        btNo.setOnAction(e -> start(stage));
        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(btYes,btNo);
        buttonRow.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(30);
        contentBox.getChildren().addAll(txtConfirm, buttonRow);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(255,255,255,0.75);-fx-background-radius: 10px;-fx-padding: 10px;");
        contentBox.setMaxWidth(600);

        VBox statsContainer = new VBox(50);
        statsContainer.getChildren().addAll(titleBar, txtPlayerTitle,contentBox);
        statsContainer.setAlignment(Pos.TOP_CENTER);
        statsContainer.setBackground(new Background(backgroundImage));
        Scene playerScene = new Scene(statsContainer, 960, 540);
        stage.setScene(playerScene);
        stage.show();
    }
    //method to clear both master list and storage file
    public void resetStats(){
        players.clear();
        writeDataToFile();
        start(stage);
    }
    //upon exit, save data to file
    public void cleanExit(){
        writeDataToFile();
        System.exit(0);
    }
    //main method
    public static void main(String[] args) {
        launch(args);
    }}

