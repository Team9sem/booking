package com.team9.bookingsystem.Controllers;

import java.io.File;

import com.team9.bookingsystem.User;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController {
	
	private MainController mainController;
	private User loggedInUser;
	
	@FXML AnchorPane UserProfileAnchor;
	@FXML TableView<?> currentBookings;
	@FXML TableView<?> bookingHistory;
	@FXML Label profileTitle;
	@FXML Label userInfo;
	@FXML Label UserName;
	@FXML Label passWord;
	@FXML Label firstName;
	@FXML Label lastName;
	@FXML Label adress;
	@FXML Label ssd;
	@FXML Label zipCode;
	@FXML Button ppButton;

//	
//	public void init(MainController mainController){
//		 this.mainController = mainController;
////		 this.loggedInUser = user;
//		
		
	/**
	 * 
	 * 
	 * Created by Alemeseged Setie
	 * 
	 * open and save png files, using JavaFX FileChooser
	 *   
	 *   

	 */

              
             final FileChooser fileChooser = new FileChooser();
//             final Button openButton = new Button("Open Image");
             ppButton.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(final ActionEvent e) {
                         setExtFilters(fileChooser);
                         File file = fileChooser.showOpenDialog(primaryStage);
                         if (file != null) {
                             openNewImageWindow(file);
                         }
                     }
                 });
      
              
             StackPane root = new StackPane();
             root.getChildren().add(ppButton);
              
             Scene scene = new Scene(root, 400, 150);
              
             primaryStage.setTitle("file picker");
             primaryStage.setScene(scene);
             primaryStage.show();
         }

			protected void setExtFilters(FileChooser fileChooser) {
				// TODO Auto-generated method stub
				
			}

			protected void openNewImageWindow(File file) {
				// TODO Auto-generated method stub
				
			}
     


	
	public void showCurrentBookings(){
		
	}
	
}

 