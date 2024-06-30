package com.example.demo2.presentation.model.controller;

import com.example.demo2.model.Table;
import com.example.demo2.presentation.view.HashtableDrawComponent;
import com.example.demo2.service.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class Presentation {
    private int size = 0;
    public void showPresentation(Stage stage){

        Pane pane = new Pane();
int count = 0 ;

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane,700,700);



        Button button0 = new Button("Add");
        Button button1 = new Button("Remove");
        Button button2 = new Button("Size");
        Button button3 = new Button("Clear All");
        Label label = new Label("Give an Input : ");
        TextField textfield = new TextField();
        textfield.setPrefColumnCount(10);
        HBox hbox = new HBox(label,textfield, button0, button1, button2, button3);
        hbox.setMaxHeight(10);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.setPadding(new Insets(20));



        Service service = new Service(new Table(10));
        HashtableDrawComponent drawComponent = new HashtableDrawComponent();

        button0.setOnAction(event -> {

            String text = textfield.getText();
                if (size == 0 || text.equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Size");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Input !! ");
                    alert.showAndWait();
                }
                else {
                    service.add(text);
                    drawComponent.setAdded(true);
                    drawComponent.setIndex(service.hash(text));
                    pane.getChildren().clear();
                    drawComponent.setModel(drawComponent.getModel());
                    pane.getChildren().add(drawComponent.paintComponent());
                    borderPane.setCenter(pane);
                }


        });
        button1.setOnAction(event -> {
            String text = textfield.getText();

                if (size == 0 || text.equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Size");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input ");
                    alert.showAndWait();
                }
                else {
                    service.remove(text);
                    drawComponent.setAdded(false);
                    drawComponent.setIndex(service.hash(text)-1);
                    pane.getChildren().clear();
                    drawComponent.setModel(drawComponent.getModel());
                    pane.getChildren().add(drawComponent.paintComponent());
                    borderPane.setCenter(pane);
                }

            });

        button2.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Size Input");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the size:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(sizeString -> {
                try {
                    this.size = Integer.parseInt(sizeString);
                    Table table = new Table(size);
                    pane.getChildren().clear();
                    service.setTable(table);
                    drawComponent.setModel(table);
                    System.out.println("Entered size: " + size);
                    pane.getChildren().add(drawComponent.paintComponent());
                    borderPane.setCenter(pane);


                }
                catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Size Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid !! ");
                    alert.showAndWait();
                }
            });
        });

        button3.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            Table table = new Table();
            service.setTable(table);
            drawComponent.setModel(table);
            borderPane.setCenter(pane);
        });
        Button button = new Button("Delete");
        scene.setOnMouseClicked(event -> {
            if (drawComponent.getDelTest()) {

                button.setLayoutX(150);
                button.setLayoutY(300);
                pane.getChildren().add(button);
            }

        });
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Node");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                drawComponent.setConfirmed(true);
                service.remove(drawComponent.getDelName());
                drawComponent.setIndex(service.hash(drawComponent.getDelName()));
                pane.getChildren().clear();
                drawComponent.setModel(drawComponent.getModel());
                pane.getChildren().add(drawComponent.paintComponent());

            }
        });
        borderPane.setTop(hbox);

        stage.setScene(scene);
        stage.show();


    }

}
