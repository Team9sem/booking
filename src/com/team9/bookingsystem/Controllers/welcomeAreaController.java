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
 * Controller for welcomearea.fxml
 *
 */
public class welcomeAreaController {

    // declaring variable to hold reference to parent's Controller
    private MainController mainController;

    // Variables mapped to fxml elements
    @FXML private GridPane WelcomeArea;
    @FXML private Button roundButtonLeft;
    @FXML private Button roundButtonRight;

    /**
     * Javafx Controller class constructor, Called when Controller is loaded.
     * Sets Shapes of buttons to Circle.
     */
    public void initialize() {

        // Sets shape on Buttons to Circle
        roundButtonLeft.setShape(new Circle(100));
        roundButtonRight.setShape(new Circle(100));
    }

    /**
     * Initializing mainController Variable
     *
     * @param mainController reference to the mainController instance controlling the root of the GUI
     */
    public void init(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Called OnClick on Login button.
     * Calls appropriate method on parent's Controller
     */
    @FXML public void login(ActionEvent event){
        mainController.showLoginForm();

//
//        }
        System.out.println("Left Button Clicked");

    }
    /**
     * Called OnClick on Register button.
     * Calls appropriate method on parent's Controller
     */
    @FXML public void register(ActionEvent event){
        mainController.showRegisterForm();

        //TODO: Show Register Form
    }


}
