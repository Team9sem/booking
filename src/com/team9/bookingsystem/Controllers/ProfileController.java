package com.team9.bookingsystem.Controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
	// instance vars
	private MainController mainController;
	private BookingController bookingController;
	private Stage primaryStage;
	private User loggedInUser;

	// fxml mapped elements
	@FXML AnchorPane UserProfileAnchor;
	@FXML TableView<?> currentBookings;
	@FXML TableView<?> bookingHistory;
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
	 * by Pontus and Nima
	 * @param mainController reference to mainController instance
	 * @param bookingController reference to bookingController Instance
	 * @param primaryStage reference to primaryStage instance
	 */
			public void init(MainController mainController,BookingController bookingController, Stage primaryStage)
			{
				// assigning instance variables
				this.primaryStage = primaryStage;
				this.mainController = mainController;
				this.bookingController = bookingController;
				this.loggedInUser = bookingController.getLoggedInUser();

				// setting user fields to values from the current loggedin user.
				userName.setText(loggedInUser.getUserName());
				firstName.setText(loggedInUser.getFirstName());
				lastName.setText(loggedInUser.getLastName());
				adress.setText(loggedInUser.getStreet());
				ssn.setText(""+loggedInUser.getpNumber());
				zipCode.setText(""+loggedInUser.getZip());

				// if the user profilePic is downloaded, set it to imageView
				if(loggedInUser.getAvatar() != null){
					Image image = bufferedImageToWritableImage(loggedInUser.getAvatar());
					imageView.setImage(image);
				}
				// if its still being downloaded, start a thread that waits for it to finish
				if(loggedInUser.getAvatar() == null && loggedInUser.isDownloading()){
					Service<Boolean> finishedDownload = new Service<Boolean>() {
						@Override
						protected Task<Boolean> createTask() {
							Task<Boolean> task = new Task<Boolean>() {
								@Override
								protected Boolean call() throws Exception {
									if(!loggedInUser.isDownloading()){
										return true;
									}
									while(loggedInUser.isDownloading()){
										Thread.sleep(100);
									}
									return true;

								}
							};
						return task;
						}
					};
					finishedDownload.start();

					// when thread finishes set the image to the imageview.
					finishedDownload.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						@Override
						public void handle(WorkerStateEvent event) {
							if(loggedInUser.getAvatar() !=null){
								Image image = bufferedImageToWritableImage(loggedInUser.getAvatar());
								imageView.setImage(image);
							}
						}
					});

				}
			}

	/**
	 * by Pontus and Alemeseged
	 *
	 * OnAction event on the file upload button in the ProfilePageGUI.
	 * Creates a file chooser and then puts the choosen file into the fileChooser.
	 * It then starts a new Thread that uploads the selected image to the database.
	 *
	 * @param event
	 */
			@FXML public void handle(ActionEvent event){
				// Instantiate FileChooser
				FileChooser fileChooser = new FileChooser();
				// set the title
				fileChooser.setTitle("Add Profile Picture");
				// Get File Object from dialog
				File picked = fileChooser.showOpenDialog(primaryStage);
				if(picked != null){
					// set the image to the imageview
					imageView.setImage(new Image(picked.toURI().toString()));
					try{
						// set the avatar(image) on the loggedInUser instance
						loggedInUser.setAvatar(fileToBufferedImage(picked));
						// create a new Service that uploads the image as BLOB to DB.
						Service<Boolean> uploadService = new Service<Boolean>() {
							@Override
							protected Task<Boolean> createTask() {
								Task<Boolean> task = new Task<Boolean>() {
									@Override
									protected Boolean call() throws Exception {
										MysqlUtil util = new MysqlUtil();
										// send FileObject and loggedInUser instances to DB method.
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

	/**
	 * by Pontus
	 * @param file The File object to be converted
	 * @return The converted result.
	 * @throws IOException If method fails to read from file path.
	 */
			private BufferedImage fileToBufferedImage(File file) throws IOException
			{
				// Create BufferedImage form File param
				BufferedImage image = ImageIO.read(file);
				return image;
			}

	/**
	 *
	 * @param image BufferedImage to be converted
	 * @return converted Image as WriteableImage( subclass of Image)
	 */
			private WritableImage bufferedImageToWritableImage(BufferedImage image){

				// declare object to be returned
				WritableImage writableImage = null;

				// check if image param != null
				if(image != null){

//				BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
//					newImage.getGraphics().drawImage(image, 0, 0, null);
//					for(int x= 0; x < image.getWidth(); x++){
//						for(int y = 0; y < image.getWidth(); y++){
//							pixelWriter.setArgb(x,y,image.getRGB(x,y));
//						}
//					}
					// set the height and width to WritableImage
					writableImage = new WritableImage(image.getWidth(),image.getHeight());

					// User PixelWriter to loop through all pixels of bufferedImage and write them to WritableImage
					PixelWriter pixelWriter = writableImage.getPixelWriter();
						for(int x= 0; x < image.getWidth(); x++)
						{
							for(int y = 0; y < image.getHeight(); y++)
							{
								pixelWriter.setArgb(x,y,image.getRGB(x,y));
							}
						}
					// finally return WritableImage
					return writableImage;
				}
				return null;
			}


	/**
	 * by Nima
	 * Switches window back to bookinginterface
	 * @param event
	 */
	@FXML public void previousPage(ActionEvent event)
	{
			// call method on mainController that switches from User Profile to Booking Interface
			mainController.showBookingInterface(loggedInUser);
	}
		
	

	
	public void showCurrentBookings(){
		
	}
	
}

 