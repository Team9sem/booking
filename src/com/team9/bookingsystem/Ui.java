package com.team9.bookingsystem;/**
 * Created by pontuspohl on 13/10/15.
 */

import com.team9.bookingsystem.Controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        controller.init(thestage);

        uiScene = new Scene(ui,1100,700);




    }
}
