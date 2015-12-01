package com.team9.bookingsystem;
/**
 * Created by pontuspohl on 13/10/15.
 */

import com.team9.bookingsystem.Controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.awt.Desktop;
import java.io.File;
//import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
//import javafx.stage.Stage;

public class Ui extends Application {

    private Stage thestage;
    private Parent ui;
    private Scene uiScene;
    private MainController controller;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        thestage = primaryStage;
        try{
            initializeScenes();
        }catch(IOException e){
            e.printStackTrace();
        }

        thestage.setScene(uiScene);
        thestage.show();

    }

    public void initializeScenes() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/view/Root.fxml"));
        ui = loader.load();

        controller= loader.getController();

        uiScene = new Scene(ui,1100,700);




    }
}



/**
 * 
 * 
 * Created by Alemeseged Setie
 * 
 * open and save png files, using JavaFX FileChooser
 *   
 *   

 */

        public class JavaFXImageFileChooser extends Application {
        	 
            @Override
            public void start(Stage primaryStage) {
                 
                final FileChooser fileChooser = new FileChooser();
                final Button openButton = new Button("Open Image");
         
                openButton.setOnAction(
                    new EventHandler<ActionEvent>() {
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
                root.getChildren().add(openButton);
                 
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
        }

