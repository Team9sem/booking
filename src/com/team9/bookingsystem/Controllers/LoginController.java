package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Threading.LoginService;
import com.team9.bookingsystem.User;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;



/**
 * Created by pontuspohl on 14/10/15.
 * Controller Class for login.fxml
 */



public class LoginController
{

    // Parent Controller
    private MainController mainController;
    // Variables mapped to fxml elements
    @FXML Label loginLabel;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML ProgressIndicator progressIndicator;
    @FXML Label failedText;
    // this method runs when controller is started

    /**
     * Javafx Controller class constructor, Called when Controller is loaded, sets progressindicator and failedtext to be nonvisible.
     */
    public void initialize() {
        progressIndicator.setVisible(false);
        failedText.setVisible(false);
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
     * Called OnClick on back button
     * Calls mainController which in turn updates GUi
     */
    @FXML public void showWelcomeArea(){
        // TODO: take user back to welcome area
        mainController.showWelcomeArea();
    }

    /**
     * Performing a check of provided Login Credentials with the dataBase making use
     * running this request on a separate thread to avoid freezing up the GUI.
     * ProgressIndicators visiblity and Progress status is bound to the running
     * and progressproperties of the Thread running the database function.
     * On successful login mainController method is called that loads Bookinginterface
     * On failed login an Error message is displayed.
     */
    // Validate user input with the database.
    @FXML public void login(){
        // Make sure failedText is not visible at this stage
        failedText.setVisible(false);
        try{
            // debug purposes
            System.out.println("starting Login");
            // Instantiating a new instance of LoginService, Giving it the username and Password.
            LoginService loginService = new LoginService(username.getText(), password.getText());
            // Binding properties
            progressIndicator.visibleProperty().bind(loginService.runningProperty());
            progressIndicator.progressProperty().bind(loginService.progressProperty());
            // Starting the Service
            loginService.start();
            // when Service has succeeded Call mainController to switch to Booking interface.
            loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    User loggedInUser = (User)loginService.getValue();
                    mainController.showBookingInterface(loggedInUser);
                }
            });
            // if Service Fails: ie login fails, display failedText.
            loginService.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    failedText.setVisible(true);
                }
            });

            // TODO Display the booking interface



        }catch(Exception e){
            System.out.println(e.getMessage());
            // Todo: show error message in GUI
        }

    }



}
