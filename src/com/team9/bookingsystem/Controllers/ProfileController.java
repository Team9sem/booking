package com.team9.bookingsystem.Controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import com.team9.bookingsystem.Booking;
import com.team9.bookingsystem.Components.getBookingsCallback;
import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Threading.User.getFutureBookingsTask;
import com.team9.bookingsystem.Threading.User.getPastBookingsTask;
import com.team9.bookingsystem.User;
import com.team9.bookingsystem.Threading.User.getBookingsService;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
	@FXML private HBox progressLayer;

//	
//	public void init(MainController mainController){
//		 this.mainController = mainController;
////		 this.loggedInUser = user;
//		


	/**
	 * by Pontus and Nima
	 *
	 * Logic regarding avatar by Pontus
	 *
	 * @param mainController reference to mainController instance
	 * @param bookingController reference to bookingController Instance
	 * @param primaryStage reference to primaryStage instance
	 */

	public void init(MainController mainController,BookingController bookingController, Stage primaryStage)
	{
		MysqlUtil util = new MysqlUtil();

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

//		futureTables(util.getFutureBookings(loggedInUser));
//		pastTables(util.getPastBookings(loggedInUser));
//		getBookings();

		// if the user profilePic is downloaded, set it to imageView
		if(loggedInUser.getAvatar() != null){
			System.out.println("allready have an avatar");
			Image image = bufferedImageToWritableImage(loggedInUser.getAvatar());
			imageView.setImage(image);
			getBookings(new getBookingsCallback() {
				@Override
				public void onSuccess() {
					progressLayer.setVisible(false);
				}

				@Override
				public void onFailure() {
					progressLayer.setVisible(false);
				}
			});
		}
		// if its still being downloaded, start a thread that waits for it to finish
		if(loggedInUser.getAvatar() == null && loggedInUser.isDownloading()){
			System.out.println("still downloading avatar");
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
						getBookings(new getBookingsCallback() {
							@Override
							public void onSuccess() {
								progressLayer.setVisible(false);
							}

							@Override
							public void onFailure() {
								progressLayer.setVisible(false);
							}
						});
					}
				}
			});
			finishedDownload.setOnFailed(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					getBookings(new getBookingsCallback() {
						@Override
						public void onSuccess() {
							progressLayer.setVisible(false);
						}

						@Override
						public void onFailure() {
							progressLayer.setVisible(false);
						}
					});
				}
			});
		}


	}

		public void futureTables(ArrayList<Booking> bookings){
				System.out.println("in future tables");

				ObservableList<Booking> ofBookings= FXCollections.observableArrayList(bookings);

				TableColumn roomLocation = new TableColumn("Location");
				roomLocation.setCellValueFactory(new PropertyValueFactory<Booking,String>("roomLocation"));
				TableColumn bookingDate = new TableColumn("Date");
				bookingDate.setCellValueFactory(new PropertyValueFactory<Booking,String>("bdate"));
				TableColumn startTime = new TableColumn("Start Time");
				startTime.setCellValueFactory(new PropertyValueFactory<Booking,String>("bStart"));
				TableColumn endTime = new TableColumn("End Time");
				endTime.setCellValueFactory(new PropertyValueFactory<Booking,String>("bEnd"));
				System.out.println("future bookings");

//				currentBookings.getItems().addAll(ofBookings);
//				currentBookings.setItems(ofBookings);
				currentBookings.getItems().setAll(ofBookings);
				currentBookings.getColumns().addAll(roomLocation,bookingDate,startTime,endTime);

				currentBookings.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				currentBookings.getColumns().get(0).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.40));
				currentBookings.getColumns().get(1).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.20));
				currentBookings.getColumns().get(2).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.20));
				currentBookings.getColumns().get(3).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.192));

				System.out.println("end of future tables");
			}

			public void pastTables(ArrayList<Booking> bookings){
				System.out.println("in past tables");
				ObservableList<Booking> ofBookings= FXCollections.observableArrayList(bookings);

				TableColumn location = new TableColumn("Location");
				location.setCellValueFactory(new PropertyValueFactory<>("roomLocation"));
				TableColumn bookingDate = new TableColumn("Date");
				bookingDate.setCellValueFactory(new PropertyValueFactory<Booking,String>("bdate"));
				TableColumn startTime = new TableColumn("Start Time");
				startTime.setCellValueFactory(new PropertyValueFactory<Booking,String>("bStart"));
				TableColumn endTime = new TableColumn("End Time");
				endTime.setCellValueFactory(new PropertyValueFactory<Booking,String>("bEnd"));
//				System.out.println("future bookings");


				//bookingHistory.getItems().addAll(ofBookings);
				bookingHistory.setItems(ofBookings);
				//bookingHistory.getItems().setAll(ofBookings);
				bookingHistory.getColumns().addAll(location,bookingDate,startTime,endTime);

				bookingHistory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				bookingHistory.getColumns().get(0).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.40));
				bookingHistory.getColumns().get(1).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.20));
				bookingHistory.getColumns().get(2).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.20));
				bookingHistory.getColumns().get(3).prefWidthProperty().bind(currentBookings.widthProperty().multiply(0.192));
				System.out.println("end of past tables");
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
	 * By Pontus
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
	/**
	 * By Pontus
	 * Instanties Service Classes that retrieves arraylist of bookings for the tableviews in the userProfile.
	 *
	 *
	 */
	private void getBookings(getBookingsCallback callback){

		getBookingsService getPastBookingsService = new getBookingsService(new getPastBookingsTask(loggedInUser));
		getBookingsService getFutureBookingsService = new getBookingsService(new getFutureBookingsTask(loggedInUser));
		getPastBookingsService.start();

		getPastBookingsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {

				pastTables(getPastBookingsService.getValue());
				callback.setGotPastBookings(true);
				getFutureBookingsService.start();
			}
		});
		getPastBookingsService.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("failed");
				getFutureBookingsService.start();
			}
		});


		getFutureBookingsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("got future Bookings");
				futureTables(getFutureBookingsService.getValue());
				callback.setGotFutureBookings(true);
				if(callback.getGotFutureBookings() && callback.getGotPastBookings()){
					callback.onSuccess();
				}
				else{
					callback.onFailure();
				}
			}
		});
		getFutureBookingsService.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("failed");
				callback.onFailure();
			}
		});
	}


}