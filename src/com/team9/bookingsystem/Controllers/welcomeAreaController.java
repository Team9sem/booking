package com.team9.bookingsystem.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * Created by pontuspohl on 22/10/15.
 */
public class welcomeAreaController {

    private MainController mainController;
    private GridPane welcomeAreaCopy;
    // Variables mapped to fxml elements

    @FXML private GridPane WelcomeArea;
    @FXML private Button roundButtonLeft;
    @FXML private Button roundButtonRight;

    // is run when FXML is loaded
    public void initialize() {

        // Sets shape on Buttons to Circle
        roundButtonLeft.setShape(new Circle(100));
        roundButtonRight.setShape(new Circle(100));



    }
    public void init(MainController mainController){
        this.mainController = mainController;
    }
    // method mapped to onActionEvent of roundButtonLeft
    @FXML public void login(ActionEvent event){
        mainController.showLoginForm();

//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/login.fxml"));
//            GridPane gridPane = loader.load();
//            LoginController loginController = loader.getController();
//            loginController.init(mainController);
//            WelcomeArea.getChildren().clear();
//            WelcomeArea.getChildren().addAll(gridPane.getChildren());
//        }catch(IOException e){
//            e.printStackTrace();
//        }
        System.out.println("Left Button Clicked");
        //TODO: Login logic
    }
    // method mapped to onActionEvent of roundButtonRight
    @FXML public void register(ActionEvent event){
        mainController.showRegisterForm();
        System.out.println("Left Button Clicked");

        System.out.println("Right Button Clicked");
        //TODO: Show Register Form
    }
    public void showWelcomeArea(GridPane pane){

        try{
            WelcomeArea.getChildren().clear();
            WelcomeArea.getChildren().addAll(pane.getChildren());
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
