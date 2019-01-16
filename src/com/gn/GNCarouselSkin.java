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

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2019
 */
public class GNCarouselSkin extends BehaviorSkinBase<GNCarousel, GNCarouselBehavior> {

    private AnchorPane  container = new AnchorPane();
    private StackPane   currentView;
    private StackPane   nextView;
    private Button      left_button;
    private Button      right_button;
    private SVGPath     left_arrow;
    private SVGPath     right_arrow;
    private HBox        indicators;
    private ToggleGroup group;
    private VBox        wrapper;
    private Text        title;
    private Text        subtitle;

    private Timeline    transition  = new Timeline();
    private Timer       timer = new Timer();

    private double      division    = 0;
    private int         direction   = -1;
    private int         oldId       = 0;

    private ObservableList<Node>     items;
    private ObjectProperty<Duration> duration = new SimpleObjectProperty<>(this, "transitionDuration", Duration.millis(300));

    private GNCarousel control;

    private EventHandler<MouseEvent> entered = event -> cancelTimer();
    private EventHandler<MouseEvent> exited = event -> createNewTimer();

    protected GNCarouselSkin(GNCarousel control) {

        super(control, new GNCarouselBehavior(control));

        this.control = control;

        // add clip

        Rectangle outputClip = new Rectangle();

        outputClip.setArcWidth(0);
        outputClip.setArcHeight(0);
        control.setClip(outputClip);
        outputClip.widthProperty().bind(control.widthProperty());
        outputClip.heightProperty().bind(control.heightProperty());

        items = getSkinnable().getItems();

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

        this.container.getStyleClass().add("container");
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

        if(items.isEmpty()){ // view sample
            for (int i = 0; i < 3; i++) {
                ToggleButton btn = new ToggleButton();
                btn.setId(String.valueOf(i));
                btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                btn.setToggleGroup(group);

                btn.getStyleClass().add("ind-" + i);
                indicators.getChildren().add(btn);
            }
        }

        initListener();

        items.addListener((ListChangeListener<Node>) c -> {
            initListener();
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

    private void initListener(){

//        indicators.getChildren().clear(); // replace sample indicators

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
    }

    private void composeLayout(){
        this.getChildren().add(container);

        this.wrapper.getChildren().addAll(title, subtitle, indicators);
        this.container.getChildren().addAll(currentView, nextView, wrapper, right_button, left_button);

        this.left_button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.right_button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.left_button.setGraphic(left_arrow);
        this.right_button.setGraphic(right_arrow);

        this.right_button.setPadding(new Insets(20D));
        this.left_button.setPadding(new Insets(20D));

        right_arrow.setContent("M5.88 4.12L13.76 12l-7.88 7.88L8 22l10-10L8 2z");
        left_arrow.setContent("M11.67 3.87L9.9 2.1 0 12l9.9 9.9 1.77-1.77L3.54 12z");

        duration.bind(getSkinnable().transitionDurationProperty());

        overlap(currentView);
        overlap(nextView);
        overBottom(wrapper);

        overLeft(left_button);
        overRight(right_button);


        registerChangeListener(control.arrowsProperty(), "VISIBLE_ARROWS");
        registerChangeListener(control.autoRideProperty(), "AUTO_RIDE");

        if(!getSkinnable().isArrows()){
            removeArrows(true);
        }

        if(getSkinnable().isAutoRide()){
            control.addEventHandler(MouseEvent.MOUSE_EXITED, exited);
            control.addEventHandler(MouseEvent.MOUSE_ENTERED, entered);
        }

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

    void next(){
        direction = 1;
        if(!Objects.requireNonNull(getSkinnable().getItems()).isEmpty() && getSkinnable().getItems().size() > 1){
            if(oldId == items.size() - 1){
                effect(direction, 0);
                group.selectToggle(group.getToggles().get(0));
            } else if(oldId < (items.size() - 1)){
                effect(direction, ++oldId);
                group.selectToggle(group.getToggles().get(oldId));
            }
        }
    }

    void previous(){
        direction = -1;
        if(!Objects.requireNonNull(getSkinnable().getItems()).isEmpty() && getSkinnable().getItems().size() > 1){
            if(oldId == 0){
                effect(direction, (items.size() - 1));
                group.selectToggle(group.getToggles().get(oldId));
            } else if (oldId > 0) {
                effect(direction, --oldId);
                group.selectToggle(group.getToggles().get(oldId));
            }
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

    private void effect(int direction, int view){

        nextView.getChildren().clear();
        nextView.getChildren().add(items.get(view));
        oldId = view;

        this.direction = direction;

        transition.getKeyFrames().clear();

        if(direction == 1){

            transition.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(currentView.translateXProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(nextView.translateXProperty(), getSkinnable().getWidth())),
                    new KeyFrame(duration.get(), new KeyValue(currentView.translateXProperty(), getSkinnable().getWidth() * -1)),
                    new KeyFrame(duration.get(), new KeyValue(nextView.translateXProperty(), 0))
            );

            transition.play();

            transition.setOnFinished(event -> {
                currentView.setTranslateX(0);
                nextView.setTranslateX(getSkinnable().getPrefWidth() * -1);
                currentView.getChildren().clear();
                currentView.getChildren().addAll(nextView.getChildren());
                nextView.getChildren().clear();
            });
        } else {

            nextView.setTranslateX(getSkinnable().getPrefWidth() * -1);

            transition.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(currentView.translateXProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(nextView.translateXProperty(), getSkinnable().getWidth() * - 1)),
                    new KeyFrame(duration.get(), new KeyValue(currentView.translateXProperty(), getSkinnable().getWidth())),
                    new KeyFrame(duration.get(), new KeyValue(nextView.translateXProperty(), 0))
            );

            transition.play();

            transition.setOnFinished(event -> {
                currentView.setTranslateX(0);
                nextView.setTranslateX(getSkinnable().getPrefWidth() * -1);
                currentView.getChildren().clear();
                currentView.getChildren().addAll(nextView.getChildren());
                nextView.getChildren().clear();
            });
        }
    }

    @Override
    protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);
            if ("VISIBLE_ARROWS".equals(propertyReference)) {
                if(!getSkinnable().isArrows()){
                    removeArrows(true);
                } else {
                    removeArrows(false);
                }
            } else if("AUTO_RIDE".equals(propertyReference)){
                if(!getSkinnable().isAutoRide()){
                    cancelTimer();
                    control.removeEventHandler(MouseEvent.MOUSE_ENTERED, entered);
                    control.removeEventHandler(MouseEvent.MOUSE_EXITED, exited);
                } else {
                    control.addEventHandler(MouseEvent.MOUSE_ENTERED, entered);
                    control.addEventHandler(MouseEvent.MOUSE_EXITED, exited);
                }
            }
    }

    private void removeArrows(boolean remove){
       if(remove){
           this.container.getChildren().remove(left_button);
           this.container.getChildren().remove(right_button);
       } else {
           this.container.getChildren().add(left_button);
           this.container.getChildren().add(right_button);
       }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return wrapper.minWidth(height);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.minHeight(width);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.prefWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return container.prefHeight(width) + topInset + bottomInset;
    }

    private void createNewTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (transition.getStatus() != Animation.Status.RUNNING)
                        next();
                });

            }
        },  (long) getSkinnable().getTransitionDuration().toMillis(),
            (long) getSkinnable().getTransitionDuration().toMillis()
        );
    }

    private void cancelTimer(){
        timer.cancel();
        timer.purge();
    }

//    private VBox createSample(){
//        Label lb1 = new Label("Sample");
//        lb1.setStyle("-fx-text-fill : white; -fx-font-size : 24px;");
//        VBox vSample = new VBox(lb1);
//        vSample.setAlignment(Pos.CENTER);
//        vSample.setStyle("-fx-background-color : rgba(0,0,0,.2);");
//        return vSample;
//    }
}