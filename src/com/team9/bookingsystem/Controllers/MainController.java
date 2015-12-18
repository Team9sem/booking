package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.Components.DialogCallback;
import com.team9.bookingsystem.Components.PopupController;
import com.team9.bookingsystem.User;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by pontuspohl on 13/10/15.
 * Controller class for Root.fxml
 */

public class MainController {


    private Stage primaryStage;
    // Variables mapped to fxml elements
    @FXML private AnchorPane Ui;
    @FXML private Pane welcomeArea;
    // variablename Ending with Controller automatically gets reference to element's Controller instance
    @FXML private welcomeAreaController welcomeAreaController;


    /**
     * Javafx Controller class constructor, Called when Controller is loaded, Sends reference
     * to this controller instance to welcomeareaController
     */
    public void initialize() {


        welcomeAreaController.init(this);
        System.out.println(welcomeAreaController.toString());

    }
    public void init(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public boolean showPopup(Stage popupStage, PopupController popupController,DialogCallback callback){
        popupStage.initOwner(primaryStage);
        popupController.setStage(popupStage);
        System.out.println(callback.toString());
        popupController.setCallBack(callback);
        popupStage.setResizable(false);
        popupStage.showAndWait();

        return popupController.isOkClicked();



    }

    public void showStartScreen(){

        try{
            System.out.println("in showAdmin");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/Root.fxml"));
            AnchorPane anchorPane = loader.load();
            loader.setController(this);
            Ui.getChildren().clear();
            Ui.getChildren().add(anchorPane);
            Ui.setBottomAnchor(anchorPane, 0.0);
            Ui.setTopAnchor(anchorPane, 0.0);
            Ui.setLeftAnchor(anchorPane, 0.0);
            Ui.setRightAnchor(anchorPane,0.0);


        }
        catch(IOException e){
            e.printStackTrace();
        }



    }


    public void changeMidContent(){


    }

    /**
     * Clears out welcomeArea Pane and loads login.fxml into it. Also gives loginController instance
     * a reference to this instance.
     */
    public void showLoginForm(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/login.fxml"));
            GridPane gridPane = loader.load();
            LoginController loginController = loader.getController();
            loginController.init(this);
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().add(gridPane);
            System.out.println(welcomeAreaController.toString());
        }// got to catch any IOExceptions when loading fxml files
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Clears out welcomeArea Pane and loads register.fxml into it. Also gives RegisterController instance
     * a reference to this instance. // Added by Olle Renard
     */
    public void showRegisterForm(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/register.fxml"));
            GridPane gridPane = loader.load();
            RegisterController registerController = loader.getController();
            registerController.init(this);
            System.out.println(this.toString());
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().add(gridPane);
        }// got to catch any IOExceptions when loading fxml files
        catch(IOException e){
            e.printStackTrace();
        }



    }
    /**
     * Clears out welcomeArea Pane and loads welcomearea.fxml into it. Also gives welcomeAreaController instance
     * a reference to this instance.
     */
    public void showWelcomeArea(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/welcomearea.fxml"));
            GridPane gridPane = loader.load();
            welcomeAreaController welcomeController = loader.getController();
            welcomeController.init(this);
            System.out.println(this.toString());
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().add(gridPane);

        }// got to catch any IOExceptions when loading fxml files
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Clears out Root anchorpane and loads booking.fxml into it. Also gives BookingController instance
     * a reference to this instance. Sets distance from anchors on root AnchorPane to 0.0 on all sides.
     */
    public void showBookingInterface(User loggedinUser){


        System.out.println(loggedinUser.getUserName());
        if(!loggedinUser.getUserName().equals("admin")){

            System.out.println("in showbooking");

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/booking.fxml"));
            BorderPane borderPane = loader.load();
            BookingController bookingController = loader.getController();
            bookingController.init(this,loggedinUser);
            System.out.println(this.toString());
            Ui.getChildren().clear();
            Ui.getChildren().add(borderPane);
            Ui.setBottomAnchor(borderPane,0.0);
            Ui.setTopAnchor(borderPane,0.0);
            Ui.setLeftAnchor(borderPane,0.0);
            Ui.setRightAnchor(borderPane,0.0);

//            welcomeArea.getChildren().clear();
//            welcomeArea.getChildren().addAll(borderPane.getChildren());

        } // got to catch any IOExceptions when loading fxml files
        catch(IOException e){
            e.printStackTrace();
            }

            }
        else{
            showAdminConsole(loggedinUser);
        }
    }
        
     public void showAdminConsole(User admin){
    	try{
            System.out.println("in showAdmin");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/administratorUI.fxml"));
    		BorderPane borderPane = loader.load();
            AdminController adminController = loader.getController();
            adminController.init(this,admin);
    		System.out.println(this.toString());
    		Ui.getChildren().clear();
    		Ui.getChildren().add(borderPane);
    		Ui.setBottomAnchor(borderPane, 0.0);
            Ui.setTopAnchor(borderPane, 0.0);
            Ui.setLeftAnchor(borderPane, 0.0);
            Ui.setRightAnchor(borderPane,0.0);

    		
    	}
    	catch(IOException e){
            e.printStackTrace();
    	}
    	 
     }

    public void showUserProfile(BookingController bookingController) {
        try {
            System.out.println("in showUserProfile");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/UserProfile.fxml"));
            BorderPane borderPane = loader.load();
            ProfileController profileController = loader.getController();
			profileController.init(this,bookingController,primaryStage);
            System.out.println(this.toString());
            Ui.getChildren().clear();
            Ui.getChildren().add(borderPane);
			Ui.setBottomAnchor(borderPane, 0.0);
			Ui.setTopAnchor(borderPane, 0.0);
			Ui.setLeftAnchor(borderPane, 0.0);
			Ui.setRightAnchor(borderPane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
