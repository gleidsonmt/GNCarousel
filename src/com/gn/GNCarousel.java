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

import javafx.beans.DefaultProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * The carousel is a control of the overlapping layer, which navigates about her, showing one for once.
 * First, he creates a skin with one clip and your indices, when navigating between the views one event is shot
 * positioning the next view to side left or right, during the event the next view pushes the actual view to the side
 * left or right.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/11/2018
 */

@DefaultProperty("children")
public class GNCarousel extends Region {

    private Container carousel;

    public GNCarousel(){
        super();
        carousel = new Container();
        this.getChildren().add(carousel);

        double prefWidth = 200;
        double prefHeight = 200;

        this.setPrefWidth(prefWidth);
        this.setPrefHeight(prefHeight);
        carousel.setPrefWidth(prefWidth);
        carousel.setPrefHeight(prefHeight);

        // add clip
        Rectangle outputClip = new Rectangle();
        outputClip.setArcWidth(0);
        outputClip.setArcHeight(0);
        carousel.setClip(outputClip);

        outputClip.widthProperty().bind(widthProperty());
        outputClip.heightProperty().bind(heightProperty());

        carousel.items.addListener((ListChangeListener<Node>) c -> {
            if(carousel.currentView.getChildren().isEmpty()){
                carousel.currentView.getChildren().add(carousel.items.get(0));
            }
        });
    }

    public void setTitle(String title){
        carousel.title.setText(title);
    }

    public void setSubtitle(String sub){
        carousel.subtitle.setText(sub);
    }

    /**
     * Configure effect velocity.
     * @param duration duration of the effect.
     */
    public void setVelocity(Duration duration) {
        carousel.duration = duration;
    }

    /**
     * Select the next item.
     */
    public void next(){
        carousel.next();
    }

    /**
     * Select the previous item.
     */
    public void previous(){
        carousel.previous();
    }

    /**
     * Select the first item.
     */
    public void first(){
        carousel.first();
    }

    /**
     * Select the last item.
     */
    public void last(){
        carousel.last();
    }

    /**
     * Select the medium item.
     * if even, select the next.
     */
    public void medium(){
        carousel.medium();
    }

    /**
     * Items to visualize.
     * @return items.
     */
    public ObservableList<Node> getItems(){
        return carousel.items;
    }

    @Override
    public String getUserAgentStylesheet() {
        return GNCarousel.class.getResource("/com/gn/carousel.css").toExternalForm();
    }

    @Override
    protected double computeMinWidth(double height) {
        return carousel.minWidth(height);
    }

    @Override
    protected double computeMinHeight(double width) {
        return carousel.minHeight(width);
    }

    @Override
    protected double computePrefWidth(double height) {
        return carousel.prefWidth(height) + snappedLeftInset() + snappedRightInset();
    }

    @Override
    protected double computePrefHeight(double width) {
        return carousel.prefHeight(width) + snappedTopInset() + snappedBottomInset();
    }

    @Override
    protected void layoutChildren() {
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = getWidth() - (snappedLeftInset() + snappedRightInset());
        final double h = getHeight() - (snappedTopInset() + snappedBottomInset());
        carousel.resizeRelocate(x, y, w, h);
    }
}
