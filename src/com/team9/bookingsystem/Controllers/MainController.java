package com.team9.bookingsystem.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by pontuspohl on 13/10/15.
 */
// Controller for Root.fxml
public class MainController {


    private GridPane welcomeAreaCopy;
    // Variables mapped to fxml elements
    @FXML private AnchorPane Ui;
    @FXML private Pane welcomeArea;
    @FXML private welcomeAreaController welcomeAreaController;
//    @FXML private Button roundButtonLeft;
//    @FXML private Button roundButtonRight;

    // is run when FXML is loaded
    public void initialize() {

        // Sets shape on Buttons to Circle
//        roundButtonLeft.setShape(new Circle(100));
//        roundButtonRight.setShape(new Circle(100));
        welcomeAreaController.init(this);
        System.out.println(welcomeAreaController.toString());

    }
    public void showLoginForm(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/login.fxml"));
            GridPane gridPane = loader.load();
            LoginController loginController = loader.getController();
            loginController.init(this);
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().addAll(gridPane.getChildren());
            System.out.println(welcomeAreaController.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void showRegisterForm(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/register.fxml"));
            GridPane gridPane = loader.load();
            RegisterController registerController = loader.getController();
            registerController.init(this);
            System.out.println(this.toString());
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().addAll(gridPane.getChildren());
        }catch(IOException e){
            e.printStackTrace();
        }



    }
    public void showWelcomeArea(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/welcomearea.fxml"));
            GridPane gridPane = loader.load();
            welcomeAreaController welcomeController = loader.getController();
            welcomeController.init(this);
            System.out.println(this.toString());
            welcomeArea.getChildren().clear();
            welcomeArea.getChildren().add(gridPane);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void showBookingInterface(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/booking.fxml"));
            BorderPane borderPane = loader.load();
            BookingController bookingController = loader.getController();
            bookingController.init(this);
            System.out.println(this.toString());
            Ui.getChildren().clear();
            Ui.getChildren().add(borderPane);
            Ui.setBottomAnchor(borderPane,0.0);
            Ui.setTopAnchor(borderPane,0.0);
            Ui.setLeftAnchor(borderPane,0.0);
            Ui.setRightAnchor(borderPane,0.0);
            System.out.println(Ui.getBottomAnchor(borderPane));
//            welcomeArea.getChildren().clear();
//            welcomeArea.getChildren().addAll(borderPane.getChildren());

        }catch(IOException e){
            e.printStackTrace();
        }


    }

//    // method mapped to onActionEvent of roundButtonLeft
//    @FXML public void showLoginForm(ActionEvent event){
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/login.fxml"));
//            GridPane gridPane = loader.load();
//            LoginController loginController = loader.getController();
//            loginController.init(this);
//            WelcomeArea.getChildren().clear();
//            WelcomeArea.getChildren().addAll(gridPane.getChildren());
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        System.out.println("Left Button Clicked");
//        //TODO: Login logic
//    }
//    // method mapped to onActionEvent of roundButtonRight
//    @FXML public void showRegisterForm(ActionEvent event){
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/register.fxml"));
//            GridPane gridPane = loader.load();
//            RegisterController registerController = loader.getController();
//            registerController.init(this);
//            System.out.println(this.toString());
//            WelcomeArea.getChildren().clear();
//            WelcomeArea.getChildren().addAll(gridPane.getChildren());
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        System.out.println("Left Button Clicked");
//
//        System.out.println("Right Button Clicked");
//        //TODO: Show Register Form
//    }
//    public void showWelcomeArea(GridPane pane){
//
//        try{
//            WelcomeArea.getChildren().clear();
//            WelcomeArea.getChildren().addAll(pane.getChildren());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//    }


}
