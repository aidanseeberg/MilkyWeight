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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.tools.DocumentationTool.Location;

/**
 * Main.java created by Aidan Seeberg, Ethan Hood, Sarat Sagaram, Teddy Nguyen, Zolo Amgaabaatar on
 * MacBookPro
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



public class Main extends Application {
  private JTextField fileID = new JTextField();
  private JTextField directory = new JTextField();
  private String holder; // important! holder stores the location of the file to be passed to
  FarmManager manager = new FarmManager();

  

  // FarmManager
  private JButton openFile = new JButton("Open File");
  private JButton saveFile = new JButton("Save File");
  private FileWriter fw;

  @SuppressWarnings("serial")
  final class ChooseFile extends JFrame {


    public ChooseFile() {
      JPanel panel = new JPanel();
      openFile.addActionListener(new Open());
      panel.add(openFile);
      saveFile.addActionListener(new Save());
      panel.add(saveFile);
      Container contentPlane = getContentPane();
      contentPlane.add(panel, BorderLayout.SOUTH);
      panel = new JPanel();
      panel.setLayout(new GridLayout(2, 1));
      panel.add(fileID);
      panel.add(directory);
      contentPlane.add(panel, BorderLayout.NORTH);
    }

    class Open implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        JFileChooser choose = new JFileChooser();
        int val = choose.showOpenDialog(ChooseFile.this);
        if (val == JFileChooser.APPROVE_OPTION) {
          fileID.setText(choose.getSelectedFile().getName());
          directory.setText(choose.getCurrentDirectory().toString());
          holder = directory.getText();
        }
        if (val == JFileChooser.CANCEL_OPTION) {
          fileID.setText("Action canceled.");
          directory.setText("");
        }
      }
    }

    class Save implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        if (directory.getText() == "") {
          directory.setText(""); // does nothing if there is no text
        } else {
          holder = directory.getText(); // saves file location to "holder"
          directory.setText("Successfully saved.");
          try {
            fw = new FileWriter(new File(holder)); // maybe try: + ".csv" if not working?
            fw.write(holder);
            fw.close();
          } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
        File newFile = new File(holder);
        manager.uploadData(newFile);
      }   
    }
  }


  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 600;
  int count = 0;

  static TableView<ObservableList<String>> tableView = new TableView<>();


  Scene scene1, scene2, scene3, scene4, farmScene, monthScene, yearScene, dataRangeScene, addScene,
      removeScene;

  Scanner input = new Scanner(System.in);


  public void start(Stage primaryStage) throws Exception {
    // uploading farm manager data

    // initializing border panes for each view
    BorderPane mainView = new BorderPane();
    BorderPane editView = new BorderPane();
    BorderPane dataView = new BorderPane();
    BorderPane uploadView = new BorderPane();
    BorderPane farmView = new BorderPane();
    BorderPane monthView = new BorderPane();
    BorderPane yearView = new BorderPane();
    BorderPane dataRangeView = new BorderPane();
    BorderPane addView = new BorderPane();
    BorderPane removeView = new BorderPane();

    addLabel(mainView, editView, dataView, uploadView);
    addButton(mainView);

    // setting background color of each view
    mainView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    editView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    dataView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    uploadView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    farmView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    monthView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    yearView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    dataRangeView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    addView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");
    removeView.setStyle("-fx-background-color: rgba(100, 100, 150, 0.5);");



    // setting boxes and buttons
    VBox vbox = new VBox();
    VBox vbox2 = new VBox();
    VBox vbox3 = new VBox();
    VBox vbox4 = new VBox();


    HBox hbox = new HBox();
    HBox hbox2 = new HBox();
    HBox hbox3 = new HBox();
    HBox hbox4 = new HBox();


    Button b1 = new Button("Edit Files");
    Button b2 = new Button("View Data");
    Button b3 = new Button("Add entries");
    Button b4 = new Button("Remove Entries");
    Button b5 = new Button("Upload File");
    Button farmRep = new Button("Farm Report");
    Button monthlyRep = new Button("Monthly Report");
    Button annualRep = new Button("Annual Report");
    Button dataRep = new Button("Data Range Report");

    Button back = new Button("back");
    Button back2 = new Button("back");
    Button back3 = new Button("back");

    // setting images
    Image img1 = new Image("cow.jpg");
    Image img2 = new Image("data.png", 500, 200, false, false);
    ImageView iv1 = new ImageView(img1);
    ImageView iv2 = new ImageView(img2);
    iv1.setPreserveRatio(true);
    iv2.setPreserveRatio(true);


    // ** Cool Shadowy Stuff **
    shadow(b1, b2, b3, b4, b5, back, back2, back3);

    // setting buttons to do things
    b1.setOnAction(e -> primaryStage.setScene(scene2));
    back.setOnAction(e -> primaryStage.setScene(scene1));
    b2.setOnAction(e -> primaryStage.setScene(scene3));
    back2.setOnAction(e -> primaryStage.setScene(scene1));
    b5.setOnAction(e -> primaryStage.setScene(scene4));
    back3.setOnAction(e -> primaryStage.setScene(scene1));
    farmRep.setOnAction(e -> {
      primaryStage.setScene(farmScene);
    });
    monthlyRep.setOnAction(e -> primaryStage.setScene(monthScene));
    annualRep.setOnAction(e -> primaryStage.setScene(yearScene));
    dataRep.setOnAction(e -> primaryStage.setScene(dataRangeScene));
    b3.setOnAction(e -> primaryStage.setScene(addScene));
    b4.setOnAction(e -> primaryStage.setScene(removeScene));


    // main view attributes
    hbox.setSpacing(6.75);
    hbox.getChildren().addAll(b2, b5);

    vbox.getChildren().add(iv1);
    vbox.getChildren().add(hbox);
    vbox.setSpacing(10);

    mainView.setCenter(vbox);

    // edit items view attributes
    hbox2.setSpacing(6.75);
    hbox2.getChildren().addAll(b3, b4);
    vbox2.getChildren().add(hbox2);
    vbox2.setSpacing(10);


    editView.setCenter(vbox2);
    editView.setBottom(back);

    // download view data attributes
    hbox3.setSpacing(10);
    hbox3.getChildren().addAll(back2, farmRep, monthlyRep, annualRep, dataRep);
    vbox3.getChildren().add(hbox3);
    vbox3.setSpacing(20);

    dataView.setPadding(new Insets(10, 10, 10, 10));

    dataView.setCenter(vbox3);
    dataView.setBottom(back2);

    // upload data view attributes
    hbox4.setSpacing(6.75);
    hbox4.getChildren().add(back3);
    vbox4.getChildren().addAll(hbox4);

    uploadView.setCenter(vbox4);
    uploadView.setBottom(back3);

    // add view attributes
    HBox addHbox = new HBox();



    // farmView attributes
    Button submit = new Button("Submit");
    Label farmID = new Label("Enter the farmID: ");
    Label year = new Label("Enter the year:      ");
    TextField farm = new TextField();
    TextField dataField = new TextField();
    Button farmBack = new Button("back");

    HBox one = new HBox();
    VBox vone = new VBox();
    HBox two = new HBox();
    VBox vtwo = new VBox();

    vone.setSpacing(5);
    vtwo.setSpacing(5);
    one.setSpacing(6.75);
    two.setSpacing(6.75);

    one.getChildren().addAll(farmID, farm, submit);
    two.getChildren().addAll(year, dataField);
    vone.getChildren().addAll(one, two);


    farmView.setCenter(vone);



    submit.setOnAction(e -> {
      tableView.getItems().clear();

      Label error = new Label("Error: invalid input: please try again");

      String[][] temp = null;

      if (count == 0) {
        try {
          temp = manager.getFarmReport(farm.getText(), dataField.getText());
          count = 1;

        } catch (IllegalNullKeyException e1) {
          // TODO Auto-generated catch block
          count = 0;
          farmView.setRight(error);

        } catch (KeyNotFoundException e1) {
          // TODO Auto-generated catch block
          count = 0;
          farmView.setRight(error);
        }
      }

      count = 0;

      // creating table with 2-d array attributes

      ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

      for (String[] row : temp) {
        data.add(FXCollections.observableArrayList(row));
      }
      tableView.setItems(data);

      for (int i = 0; i < temp[0].length; i++) {
        final int curCol = i;
        final TableColumn<ObservableList<String>, String> column =
            new TableColumn<>("Farm " + (curCol + 1));
        column.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
        tableView.getColumns().add(column);
      }

      // adding table to dataView screen
      tableView.setItems(data);
      farmView.setCenter(tableView);


    });

    farmView.setBottom(farmBack);

    farmBack.setOnAction(e -> {
      primaryStage.setScene(scene3);
      farmView.getChildren().remove(tableView);
      farmView.setCenter(vone);
    });


    // month view attributes
    Button submit2 = new Button("Submit");
    Label farmID2 = new Label("Enter a year: ");
    Label month2 = new Label("Enter the month:      ");
    TextField farm2 = new TextField();
    TextField dataField2 = new TextField();
    Button monthBack = new Button("back");
    TableView<ObservableList<String>> tableView2 = new TableView<>();

    HBox hone = new HBox();
    HBox htwo = new HBox();
    VBox vthree = new VBox();

    vthree.setSpacing(5);
    hone.setSpacing(6.75);
    htwo.setSpacing(6.75);

    hone.getChildren().addAll(farmID2, farm2, submit2);
    htwo.getChildren().addAll(month2, dataField2);
    vthree.getChildren().addAll(hone, htwo);

    monthView.setCenter(vthree);

    monthBack.setOnAction(e -> {
      primaryStage.setScene(scene3);
      monthView.getChildren().remove(tableView2);
      monthView.setCenter(vthree);

    });

    submit2.setOnAction(e -> {

      // dataRange report in a 2D array
      String[][] temp = manager.getMonthlyReport(farm2.getText(), dataField2.getText());

      // sample tester
      // double[][] temp = {{2.0, 3.0, 4.0}, {2.0, 3.0, 4.0}, {2.0, 3.0, 4.0}};
      // String[][] temp = {{"nice to ", "have", "titles"}, {"a", "b", "c"}, {"d", "e", "f"}};

      // creating table with 2-d array attributes

      ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

      for (String[] row : temp) {
        data.add(FXCollections.observableArrayList(row));
      }
      tableView2.setItems(data);

      for (int i = 0; i < temp[0].length; i++) {
        final int curCol = i;
        final TableColumn<ObservableList<String>, String> column =
            new TableColumn<>("Col " + (curCol + 1));
        column.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
        tableView2.getColumns().add(column);
      }

      // adding table to monthview screen
      tableView2.setItems(data);
      monthView.setCenter(tableView2);


    });

    monthView.setBottom(monthBack);

    // yearView attributes
    Button submit3 = new Button("Submit");
    Label farmID3 = new Label("Enter the year: ");
    TextField farm3 = new TextField();
    Button yearBack = new Button("back");
    TableView<ObservableList<String>> tableView3 = new TableView<>();

    HBox hthree = new HBox();
    VBox vfive = new VBox();
    HBox hfour = new HBox();

    vfive.setSpacing(5);
    hthree.setSpacing(6.75);
    hfour.setSpacing(6.75);

    hthree.getChildren().addAll(farmID3, farm3, submit3);
    vfive.getChildren().addAll(hthree, hfour);

    yearView.setCenter(vfive);

    yearBack.setOnAction(e -> {
      primaryStage.setScene(scene3);
      yearView.getChildren().remove(tableView3);
      yearView.setCenter(vfive);

    });

    submit3.setOnAction(e -> {

      // dataRange report in a 2D array
      String[][] temp = manager.getAnnualReport(farm3.getText());

      // sample tester
      // String[][] temp = {{"nice to ", "have", "titles"}, {"a", "b", "c"}, {"d", "e", "f"}};
      // double[][] temp = {{2.0, 3.0, 4.0}, {2.0, 3.0, 4.0}, {2.0, 3.0, 4.0}};

      // creating table with 2-d array attributes

      ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

      for (String[] row : temp) {
        data.add(FXCollections.observableArrayList(row));
      }
      tableView3.setItems(data);

      for (int i = 0; i < temp[0].length; i++) {
        final int curCol = i;
        final TableColumn<ObservableList<String>, String> column =
            new TableColumn<>("Col " + (curCol + 1));
        column.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
        tableView3.getColumns().add(column);
      }



      // adding table to dataView screen
      tableView3.setItems(data);
      yearView.setCenter(tableView3);


    });

    yearView.setBottom(yearBack);

    // dataRange view attributes
    Button submit4 = new Button("Submit");
    Label farmID4 = new Label("Enter the startDate (year/month/day): ");
    Label year3 = new Label("Enter the endDate (month/day):      ");
    TextField farm4 = new TextField();
    TextField dataField4 = new TextField();
    Button dataBack = new Button("back");

    TableView<ObservableList<String>> tableView4 = new TableView<>();


    HBox hfive = new HBox();
    HBox hsix = new HBox();
    VBox vsix = new VBox();

    hfive.getChildren().addAll(farmID4, farm4, submit4);
    hsix.getChildren().addAll(year3, dataField4);

    vsix.setSpacing(5);
    hfive.setSpacing(6.75);
    hsix.setSpacing(6.75);

    vsix.getChildren().addAll(hfive, hsix);

    dataRangeView.setCenter(vsix);

    dataBack.setOnAction(e -> {
      primaryStage.setScene(scene3);
      dataRangeView.getChildren().remove(tableView4);
      dataRangeView.setCenter(vsix);

    });

    submit4.setOnAction(e -> {

      // dataRange report in a 2D array
      // String[][] temp = manager.getDataRange(farm.getText(), dataField.getText());

      // sample tester
      String[][] temp = {{"nice to ", "have", "titles"}, {"a", "b", "c"}, {"d", "e", "f"}};
      // double[][] temp = {{2.0, 3.0, 4.0}, {2.0, 3.0, 4.0}, {2.0, 3.0, 4.0}};

      // creating table with 2-d array attributes
      ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

      for (String[] row : temp) {
        data.add(FXCollections.observableArrayList(row));
      }
      tableView4.setItems(data);

      for (int i = 0; i < temp[0].length; i++) {
        final int curCol = i;
        final TableColumn<ObservableList<String>, String> column =
            new TableColumn<>("Col " + (curCol + 1));
        column.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
        tableView4.getColumns().add(column);
      }

      // adding table to dataView screen
      tableView4.setItems(data);
      dataRangeView.setCenter(tableView4);


    });

    dataRangeView.setBottom(dataBack);



    // initializing scenes
    scene1 = new Scene(mainView, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene2 = new Scene(editView, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene3 = new Scene(dataView, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene4 = new Scene(uploadView, WINDOW_WIDTH, WINDOW_HEIGHT);
    farmScene = new Scene(farmView, WINDOW_WIDTH, WINDOW_HEIGHT);
    monthScene = new Scene(monthView, WINDOW_WIDTH, WINDOW_HEIGHT);
    yearScene = new Scene(yearView, WINDOW_WIDTH, WINDOW_HEIGHT);
    dataRangeScene = new Scene(dataRangeView, WINDOW_WIDTH, WINDOW_HEIGHT);


    // finalize primaryStage attributes
    primaryStage.setTitle("a2 ATEAM Project Milestone 2 GUI");
    primaryStage.setScene(scene1);
    primaryStage.show();

  }

  public void addLabel(BorderPane pane, BorderPane pane2, BorderPane pane3, BorderPane pane4) {
    Label label = new Label("Milky Wheyt - Farm Data Manager");
    label.setFont(new Font("Courier", 30));

    Label label2 = new Label("Edit Files");
    label2.setFont(new Font("Courier", 30));

    Label label3 = new Label("View Data");
    label3.setFont(new Font("Courier", 30));

    Label label4 = new Label("Upload File");
    label4.setFont(new Font("Courier", 30));

    pane.setTop(label);
    pane2.setTop(label2);
    pane3.setTop(label3);
    pane4.setTop(label4);
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

  public void shadow(Button b1, Button b2, Button b3, Button b4, Button b5, Button back,
      Button back2, Button back3) {
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

    b5.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b5.setEffect(shadow);
        run(new ChooseFile(), 250, 110);
      }
    });

    b5.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        b5.setEffect(null);
      }
    });

    back.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        back.setEffect(shadow);
      }
    });

    back.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        back.setEffect(null);
      }
    });

    back2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        back2.setEffect(shadow);
      }
    });

    back2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        back2.setEffect(null);
      }
    });

    back3.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        back3.setEffect(shadow);
      }
    });

    back3.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        back3.setEffect(null);
      }
    });
  }


  public static void run(JFrame frame, int width, int height) {
    frame.setSize(width, height);
    frame.setVisible(true);
  }



  public static void main(String[] args) {
    launch(args);
  }
}
