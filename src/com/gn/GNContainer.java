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
package com.gn;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.DefaultProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/11/2018
 */

@DefaultProperty("children")
public class GNContainer extends AnchorPane {

            StackPane   currentView;
    private StackPane   nextView;
    private Button      left_button;
    private Button      right_button;
    private SVGPath     left_arrow;
    private SVGPath     right_arrow;
    private HBox        indicators;
    private ToggleGroup group;
    private VBox        wrapper;
            Text        title;
            Text        subtitle;

    private Timeline    transition  = new Timeline();
            Duration    duration    = Duration.millis(300);
    private double      division    = 0;
    private int         direction   = -1;
    private int         oldId       = 0;


    ObservableList<Node> items = FXCollections.observableArrayList();

    GNContainer() {
        super();

        this.currentView = new StackPane();
        this.nextView = new StackPane();
        this.left_button = new Button("left");
        this.right_button = new Button("right");
        this.indicators = new HBox();
        this.left_arrow = new SVGPath();
        this.right_arrow = new SVGPath();
        this.group = new ToggleGroup();
        this.wrapper = new VBox();
        this.title = new Text();
        this.subtitle = new Text();

        this.getStyleClass().add("carousel");
        this.currentView.getStyleClass().add("current-view");
        this.nextView.getStyleClass().add("next-view");
        this.indicators.getStyleClass().add("indicators");
        this.right_button.getStyleClass().add("right-button");
        this.left_button.getStyleClass().add("left-button");
        this.right_arrow.getStyleClass().add("right-arrow");
        this.left_arrow.getStyleClass().add("left-arrow");
        this.wrapper.getStyleClass().add("wrapper");
        this.title.getStyleClass().add("title");
        this.subtitle.getStyleClass().add("sub-title");

        this.indicators.setAlignment(Pos.CENTER);
        this.wrapper.setAlignment(Pos.CENTER);
        this.wrapper.setSpacing(10D);
        VBox.setMargin(this.indicators, new Insets(20, 0,0,0));
        this.indicators.setSpacing(10D);

        composeLayout();

        this.items.addListener((ListChangeListener<Node>) c -> {
            if(currentView.getChildren().isEmpty()){
               currentView.getChildren().add(items.get(0));
            }

            division = items.size() / 2D;

            for(int i = 0; i < items.size();i++){
                ToggleButton btn = new ToggleButton();
                btn.setId(String.valueOf(i));
                btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                btn.setToggleGroup(group);

                btn.getStyleClass().add("ind-" + i);
                btn.setOnMouseClicked(event -> {
                    int id = Integer.valueOf(btn.getId());

                    if(!btn.isSelected()){
                        btn.setSelected(true);
                        return;
                    }

                    if(division % 2 == 0){
                        if (Integer.valueOf(btn.getId()) < division){
                            if(oldId >= 0 && id != oldId){
                                if(id > oldId){
                                    effect(1, Integer.valueOf(btn.getId()));
                                } else {
                                    effect(-1, Integer.valueOf(btn.getId()));
                                }
                            }
                        } else if (Integer.valueOf(btn.getId()) >= division){

                            if(id < items.size() && id != oldId) { // equalise indices

                                if (id < oldId) {
                                    effect(-1, Integer.valueOf(btn.getId()));
                                } else {
                                    effect(1, Integer.valueOf(btn.getId()));
                                }
                            }
                        }
                    } else {
                        if (Integer.valueOf(btn.getId()) == (int) division) {
                            if(id != oldId) {
                                effect(direction * -1, Integer.valueOf(btn.getId()));
                            }
                        }  else if(Integer.valueOf(btn.getId()) < (int) division) {
                            if(oldId >= 0 && id != oldId){

                                if(id > oldId){
                                    effect(1, Integer.valueOf(btn.getId()));
                                } else {
                                    effect(-1, Integer.valueOf(btn.getId()));
                                }
                            }
                        } else {
                            if(id < items.size() && id != oldId) { // equalise indices

                                if (id < oldId) {
                                    effect(-1, Integer.valueOf(btn.getId()));
                                } else {
                                    effect(1, Integer.valueOf(btn.getId()));
                                }
                            }
                        }
                    }
                });
                group.selectToggle(group.getToggles().get(0));
                indicators.getChildren().add(btn);
            }
        });

        right_button.setOnMouseClicked(event -> {
            if(event.getClickCount() != 2){
                next();
            }
        });
        left_button.setOnMouseClicked(event -> {
            if(event.getClickCount() != 2) {
               previous();
            }
        });
    }

    void next(){
        if(oldId == items.size() - 1){
            direction = 1;
            effect(direction, 0);
            group.selectToggle(group.getToggles().get(0));
        } else if(oldId < (items.size() - 1)){
            direction = 1;
            effect(direction, ++oldId);
            group.selectToggle(group.getToggles().get(oldId));
        }
    }

    void previous(){
        if(oldId == 0){
            direction = -1;
            effect(direction, (items.size() - 1));
            group.selectToggle(group.getToggles().get(oldId));
        } else if (oldId > 0) {
            direction = -1;
            effect(direction, --oldId);
            group.selectToggle(group.getToggles().get(oldId));
        }
    }

    void first(){
        effect(direction, 0);
        group.selectToggle(group.getToggles().get(0));
    }

    void last(){
        effect(direction, items.size() -1);
        group.selectToggle(group.getToggles().get(items.size()-1));
    }

    void medium(){
        effect(direction, (int) division); // get Absolute number
        group.selectToggle(group.getToggles().get((int) division));
    }

    private void composeLayout(){

        this.wrapper.getChildren().addAll(title, subtitle, indicators);
        this.getChildren().addAll(currentView, nextView, wrapper, left_button, right_button);

        this.left_button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.right_button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.left_button.setGraphic(left_arrow);
        this.right_button.setGraphic(right_arrow);

        right_arrow.setContent("M5.88 4.12L13.76 12l-7.88 7.88L8 22l10-10L8 2z");
        left_arrow.setContent("M11.67 3.87L9.9 2.1 0 12l9.9 9.9 1.77-1.77L3.54 12z");

        overlap(currentView);
        overlap(nextView);
        overBottom(wrapper);
        overLeft(left_button);
        overRight(right_button);
    }

    private void overlap(Node node){
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
    }

    private void overLeft(Node node) {
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
    }

    private void overRight(Node node) {
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    private void overBottom(Node node) {
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
    }

    private void effect(int direction, int view){

        nextView.getChildren().clear();
        nextView.getChildren().add(items.get(view));
        oldId = view;

        this.direction = direction;

        transition.getKeyFrames().clear();

        if(direction == 1){

            transition.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(currentView.translateXProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(nextView.translateXProperty(), this.getPrefWidth())),
                    new KeyFrame(duration, new KeyValue(currentView.translateXProperty(), this.getPrefWidth() * -1)),
                    new KeyFrame(duration, new KeyValue(nextView.translateXProperty(), 0))
            );

            transition.play();

            transition.setOnFinished(event -> {
                currentView.setTranslateX(0);
                nextView.setTranslateX(getPrefWidth() * -1);
                currentView.getChildren().clear();
                currentView.getChildren().addAll(nextView.getChildren());
                nextView.getChildren().clear();
            });
        } else {

            nextView.setTranslateX(this.getPrefWidth() * -1);

            transition.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(currentView.translateXProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(nextView.translateXProperty(), this.getPrefWidth() * - 1)),
                    new KeyFrame(duration, new KeyValue(currentView.translateXProperty(), this.getPrefWidth())),
                    new KeyFrame(duration, new KeyValue(nextView.translateXProperty(), 0))
            );

            transition.play();

            transition.setOnFinished(event -> {
                currentView.setTranslateX(0);
                nextView.setTranslateX(getPrefWidth() * -1);
                currentView.getChildren().clear();
                currentView.getChildren().addAll(nextView.getChildren());
                nextView.getChildren().clear();
            });
        }
    }
}
