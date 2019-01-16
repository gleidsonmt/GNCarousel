/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sample;

import com.gn.GNCarousel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  19/11/2018
 * Version 1.0
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        GNCarousel carousel = new GNCarousel();
//        carousel.getItems().setAll(createItems());
        carousel.setArrows(true);
//        carousel.setAutoRide(true);
//
//        Label odd = new Label("Six");
//        odd.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
//        VBox v1 = new VBox(odd);
//        v1.setStyle("-fx-background-color : yellow;");
//        v1.setAlignment(Pos.CENTER);
//        carousel.getItems().add(new Label("dfasdf"));
//        carousel.getItems().add(v1);

        Scene scene = new Scene(new StackPane(carousel), 600, 400);
        stage.setScene(scene);
        stage.show();



//        ScenicView.show(scene);

    }

    private ObservableList<Node> createItems(){

        Label lb1 = new Label("First");
        Label lb2 = new Label("Second");
        Label lb3 = new Label("Third");
        Label lb4 = new Label("Fourth");
        Label lb5 = new Label("Fifth");

        lb1.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb2.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb3.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb4.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
        lb5.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");

        VBox v1 = new VBox(lb1);
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
