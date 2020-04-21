/**
 * Main.java created by aidan seeberg, ethan hood, sarat sagaram, teddy nguyen, zolo amgaabaatar on
 * MacBookPro in p3b
 *
 * Author: Sarat Sagaram (ssagaram@wisc.edu)
 * 
 * Date: April 21, 2020
 * 
 * Course: CS400
 * 
 * Lecture: 001 & 002
 * 
 * IDE: Eclipse IDE for Java Developers Version: 2019-12 (4.14.0) Build id: 20191212-1212
 * 
 * Device: SAGARAM-MacBookPro OS: macOS Mojave Version: 2019 OS Build: 18G2022
 */
package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 600;


  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();
    addLabel(root);
    addButton(root);

    root.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");

    VBox vbox = new VBox();
    HBox hbox = new HBox();
    Button b1 = new Button("Upload file");
    Button b2 = new Button("Download data as file");
    Button b3 = new Button("Add Entries");
    Button b4 = new Button("Remove Entries");

    Image img1 = new Image("cow.jpg");
    ImageView iv1 = new ImageView(img1);

    Image img2 = new Image("data.png", 500, 200, false, false);

    iv1.setPreserveRatio(true);

    b2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        if (iv1.getImage().equals(img1))
          iv1.setImage(img2);
        else
          iv1.setImage(img1);
      }
    });



    DropShadow shadow = new DropShadow();
    b1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b1.setEffect(shadow);
      }
    });

    b1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b1.setEffect(null);
      }
    });
    b2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b2.setEffect(shadow);
      }
    });

    b2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b2.setEffect(null);
      }
    });
    b3.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b3.setEffect(shadow);
      }
    });

    b3.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b3.setEffect(null);
      }
    });
    b4.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b4.setEffect(shadow);
      }
    });

    b4.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b4.setEffect(null);
      }
    });

    hbox.setSpacing(6.75);
    hbox.getChildren().add(b1);
    hbox.getChildren().add(b2);
    hbox.getChildren().add(b3);
    hbox.getChildren().add(b4);

    vbox.getChildren().add(iv1);
    vbox.getChildren().add(hbox);

    root.setCenter(vbox);


    root.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");

    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);


    // Add the stuff and set the primary stage
    primaryStage.setTitle("a2 ATEAM Project Milestone 2 GUI");
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  public void addLabel(BorderPane pane) {
    Label label = new Label("Milky Weight - Farm Data Manager");
    label.setFont(new Font("Courier", 30));

    pane.setTop(label);
  }

  public void addButton(BorderPane pane) {
    Button doneButton = new Button("Press for help");
    doneButton.setOnAction(event -> {
      if (doneButton.getText().equals("Press for help")) {
        doneButton.setText(
            "To add data: Press the ‘Add Entries’ button and enter numerical values between 0-9.\n"
                + "To remove data: Press the Remove Entries' button and enter numerical values between 0-9.\n"
                + "Pressing enter will automatically save the data results.\n"
                + "To load data, press the ‘Download data as a file button.'");
      } else {
        doneButton.setText("Press for help");
      }
    });
    pane.setRight(doneButton);
  }



  public static void main(String[] args) {
    launch(args);
  }
}
