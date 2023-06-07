package io.github.gleidsonmt.gncarousel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/06/2023
 */
public class Sample2 extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("test.fxml")
        );

        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();

    }


    private ObservableList<Node> createItems() {

        Label lb1 = new Label("First");
        Label lb2 = new Label("Second");
        Label lb3 = new Label("Third");
        Label lb4 = new Label("Fourth");
        Label lb5 = new Label("Fifth");

        Button btn = new Button("Pedir agora");

        lb1.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb2.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb3.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb4.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb5.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");

        VBox v1 = new VBox(lb1, btn);
        VBox v2 = new VBox(lb2);
        VBox v3 = new VBox(lb3);
        VBox v4 = new VBox(lb4);
        VBox v5 = new VBox(lb5);

        v1.setAlignment(Pos.CENTER);
        v2.setAlignment(Pos.CENTER);
        v3.setAlignment(Pos.CENTER);
        v4.setAlignment(Pos.CENTER);
        v5.setAlignment(Pos.CENTER);

        v1.setStyle("-fx-background-color : #FF3547;");
        v2.setStyle("-fx-background-color : #512DA8;");
        v3.setStyle("-fx-background-color : #48CFAD;");
        v4.setStyle("-fx-background-color : #02C852;");
        v5.setStyle("-fx-background-color : #EC407A;");

        return FXCollections.observableArrayList(v1, v2, v3, v4, v5);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

