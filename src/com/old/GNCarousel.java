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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Skin;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  29/12/2018
 */
public class GNCarousel extends GNCarouselBase {

    private static final String DEFAULT_STYLE_CLASS = "gn-carousel";

    private ObjectProperty<ObservableList<Node>> items;

    private BooleanProperty arrows = new SimpleBooleanProperty(this, "arrows", true);
    private BooleanProperty autoRide = new SimpleBooleanProperty(this, "autoRide", false);

    public GNCarousel(){
        this(FXCollections.observableArrayList());
    }

    public GNCarousel(ObservableList<Node> items) {
        initialize();
        setAccessibleRole(AccessibleRole.NODE);
        setItems(items);
    }

    private void initialize(){
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new GNCarouselSkin(this);
    }

    public final ObservableList<Node> getItems() {
        return items == null ? null : items.get();
    }

    public void setItems(ObservableList<Node> items) {
        this.itemsProperty().set(items);
    }

    public final ObjectProperty<ObservableList<Node>> itemsProperty() {
        if (items == null) {
            items = new SimpleObjectProperty<>(this, "items");
        }
        return items;
    }

    public boolean isArrows() {
        return arrows.get();
    }

    public BooleanProperty arrowsProperty() {
        return arrows;
    }

    public void setArrows(boolean arrows) {
        this.arrows.set(arrows);
    }
}

