package com.team9.bookingsystem.Controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import com.team9.bookingsystem.Booking;
import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class ProfileController {
	
	private MainController mainController;
	private BookingController bookingController;
	private Stage primaryStage;
	private User loggedInUser;


	@FXML AnchorPane UserProfileAnchor;
	@FXML TableView<Booking> currentBookings;
	@FXML TableView<Booking> bookingHistory;
	@FXML Label profileTitle;
	@FXML Label userInfo;
	@FXML Label userName;
	@FXML Label passWord;
	@FXML Label firstName;
	@FXML Label lastName;
	@FXML Label adress;
	@FXML Label ssn;
	@FXML Label zipCode;
	@FXML Button ppButton;
	@FXML Button backToBookings;
	@FXML ImageView imageView;

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

			public void futureTables(ArrayList<Booking> bookings){

				ObservableList<Booking> ofBookings= FXCollections.observableArrayList(bookings);
				TableColumn roomLocation = new TableColumn("Location");
				roomLocation.setCellValueFactory(new PropertyValueFactory<Booking,String>("location"));
				TableColumn bookingDate = new TableColumn("Date");
				TableColumn startTime = new TableColumn("Start Time");
				TableColumn endTime = new TableColumn("End Time");


				currentBookings.getItems().addAll(ofBookings);
				//currentBookings.setItems(ofBookings);
				currentBookings.getColumns().addAll(roomLocation,bookingDate,startTime,endTime);
			}

			public void pastTables(ArrayList<Booking> bookings){

				ObservableList<Booking> ofBookings= FXCollections.observableArrayList(bookings);
				TableColumn roomLocation = new TableColumn("Location");
				roomLocation.setCellValueFactory(new PropertyValueFactory<Booking,String>("location"));
				TableColumn bookingDate = new TableColumn("Date");
				TableColumn startTime = new TableColumn("Start Time");
				TableColumn endTime = new TableColumn("End Time");


				bookingHistory.getItems().addAll(ofBookings);
				//currentBookings.setItems(ofBookings);
				bookingHistory.getColumns().addAll(roomLocation,bookingDate,startTime,endTime);

			}

			public void init(MainController mainController,BookingController bookingController, Stage primaryStage)
			{
				MysqlUtil util = new MysqlUtil();
				this.primaryStage = primaryStage;
				this.mainController = mainController;
				this.bookingController = bookingController;
				this.loggedInUser = bookingController.getLoggedInUser();

				userName.setText(loggedInUser.getUserName());
				firstName.setText(loggedInUser.getFirstName());
				lastName.setText(loggedInUser.getLastName());
				adress.setText(loggedInUser.getStreet());
				ssn.setText(""+loggedInUser.getpNumber());
				zipCode.setText(""+loggedInUser.getZip());

				futureTables(util.getFutureBookings(loggedInUser));
				pastTables(util.getPastBookings(loggedInUser));

				if(loggedInUser.getAvatar() != null){
					Image image = bufferedImageToWritableImage(loggedInUser.getAvatar());
					imageView.setImage(image);
				}
			}

			@FXML public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Add Profile Picture");
				File picked = fileChooser.showOpenDialog(primaryStage);
				if(picked != null){
					imageView.setImage(new Image(picked.toURI().toString()));
					try{
						loggedInUser.setAvatar(fileToBufferedImage(picked));
						Service<Boolean> uploadService = new Service<Boolean>() {
							@Override
							protected Task<Boolean> createTask() {
								Task<Boolean> task = new Task<Boolean>() {
									@Override
									protected Boolean call() throws Exception {
										MysqlUtil util = new MysqlUtil();
										util.uploadImage(picked,loggedInUser);
										return true;
									}
								};
								return task;
							}
						};
						uploadService.start();
						uploadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
							@Override
							public void handle(WorkerStateEvent event) {
								System.out.println("File uploaded");
							}
						});
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}

			private BufferedImage fileToBufferedImage(File file) throws IOException
			{
				BufferedImage image = ImageIO.read(file);
				return image;
			}
			private WritableImage bufferedImageToWritableImage(BufferedImage image){

				WritableImage writableImage = null;


				if(image != null){

//				BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
//					newImage.getGraphics().drawImage(image, 0, 0, null);
//					for(int x= 0; x < image.getWidth(); x++){
//						for(int y = 0; y < image.getWidth(); y++){
//							pixelWriter.setArgb(x,y,image.getRGB(x,y));
//						}
//					}

					writableImage = new WritableImage(image.getWidth(),image.getHeight());

					PixelWriter pixelWriter = writableImage.getPixelWriter();
						for(int x= 0; x < image.getWidth(); x++){
							for(int y = 0; y < image.getHeight(); y++){
								pixelWriter.setArgb(x,y,image.getRGB(x,y));
							}
						}

					return writableImage;
				}
				return null;
			}




     
	@FXML public void previousPage(ActionEvent event){
		
			mainController.showBookingInterface(loggedInUser);
	}
		
	

	
	public void showCurrentBookings(){
		
	}
	
}

 