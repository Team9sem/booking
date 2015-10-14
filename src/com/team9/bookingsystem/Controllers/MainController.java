package com.team9.bookingsystem.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by pontuspohl on 13/10/15.
 */
public class MainController {

    //

    @FXML private AnchorPane Ui;
    @FXML private GridPane WelcomeArea;
    @FXML private Button roundButtonLeft;
    @FXML private Button roundButtonRight;

    // is run when FXML is loaded
    public void initialize() {
        // Sets shape on Buttons to Circle
        roundButtonLeft.setShape(new Circle(100));
        roundButtonRight.setShape(new Circle(100));
//        int[] array;
//        array = new int[10];
    }
    // method mapped to onActionEvent of roundButtonLeft
    @FXML public void showLoginForm(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/login.fxml"));
            GridPane gridPane = loader.load();
            LoginController loginController = loader.getController();
            loginController.init(this);
            WelcomeArea.getChildren().clear();
            WelcomeArea.getChildren().addAll(gridPane.getChildren());
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Left Button Clicked");
        //TODO: Login logic
    }
    // method mapped to onActionEvent of roundButtonRight
    @FXML public void showRegisterForm(ActionEvent event){
        System.out.println("Right Button Clicked");
        //TODO: Show Register Form
    }

}
