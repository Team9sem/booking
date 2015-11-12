package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.User;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by pontuspohl on 13/10/15.
 * Controller class for Root.fxml
 */

public class MainController {



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
     * a reference to this instance.
     */
    public void showRegisterForm(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/register.fxml"));
            GridPane gridPane = loader.load();
            RegisterController registerController = loader.getController();
            registerController.init(this);
            System.out.println(this.toString());
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().addAll(gridPane.getChildren());
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

}
