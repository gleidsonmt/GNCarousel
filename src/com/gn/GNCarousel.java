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
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/11/2018
 * Version 1.0
 */

@DefaultProperty("children")
public class GNCarousel extends Region {

    private final static String USER_AGENT_STYELESHEET
            = GNCarousel.class.getResource("/com/gn/carousel.css").toExternalForm();

    private GNContainer carousel;

    public GNCarousel(){
        super();
        carousel = new GNContainer();
        this.getChildren().add(carousel);

        this.setPrefWidth(300);
        this.setPrefHeight(300);

        // add clip
        Rectangle outputClip = new Rectangle();
        outputClip.setArcWidth(0);
        outputClip.setArcHeight(0);
        carousel.setClip(outputClip);

        carousel.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });

        this.widthProperty().addListener((observable, oldValue, newValue) -> carousel.setPrefWidth(newValue.doubleValue()));
        this.heightProperty().addListener((observable, oldValue, newValue) -> carousel.setPrefHeight(newValue.doubleValue()));

        carousel.items.addListener((ListChangeListener<Node>) c -> {
            if(carousel.currentView.getChildren().isEmpty()){
                carousel.currentView.getChildren().add(carousel.items.get(0));
            }
        });
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
        return USER_AGENT_STYELESHEET;
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
}
