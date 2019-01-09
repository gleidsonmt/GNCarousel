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

import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.DurationConverter;
import javafx.beans.DefaultProperty;
import javafx.css.*;
import javafx.scene.control.Control;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2019
 */
@DefaultProperty("children")
public abstract class GNCarouselBase extends Control {

    private StyleableObjectProperty<Duration> transitionDuration;
    private StyleableBooleanProperty          autoRide;

    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    public GNCarouselBase() {

        this.setPrefHeight(200D);
        this.setPrefWidth(200D);

                this.transitionDuration = new SimpleStyleableObjectProperty<>(StyleableProperties.TRANSITION_DURATION, this, "transitionDuration", Duration.millis(300D));

                this.autoRide = new SimpleStyleableBooleanProperty(StyleableProperties.AUTO_RIDE, this , "autoRide", false);

    }

    @Override
    public String getUserAgentStylesheet() {
        return GNCarouselBase.class.getResource("/com/gn/carousel.css").toExternalForm();
    }

    private static class StyleableProperties {

    private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

    private static final CssMetaData<GNCarouselBase, Duration> TRANSITION_DURATION;

    private static final CssMetaData<GNCarouselBase, Boolean> AUTO_RIDE;

    private StyleableProperties(){}

        static {

            TRANSITION_DURATION = new CssMetaData<GNCarouselBase, Duration>("-gn-transition-duration", DurationConverter.getInstance(), Duration.millis(300D)) {

                @Override
                public boolean isSettable(GNCarouselBase styleable) {
                    return styleable.transitionDuration == null || !styleable.transitionDuration.isBound();
                }

                @Override
                public StyleableProperty<Duration> getStyleableProperty(GNCarouselBase styleable) {
                    return styleable.transitionDurationProperty();
                }
            };

            AUTO_RIDE = new CssMetaData<GNCarouselBase, Boolean>("-gn-auto-ride", BooleanConverter.getInstance(), false) {
                @Override
                public boolean isSettable(GNCarouselBase styleable) {
                    return styleable.autoRide == null || !styleable.autoRide.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(GNCarouselBase styleable) {
                    return styleable.autoRideProperty();
                }
            };


            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList(Control.getClassCssMetaData());
            Collections.addAll(styleables, AUTO_RIDE, TRANSITION_DURATION);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        if (this.STYLEABLES == null) {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(Control.getClassCssMetaData());
            this.STYLEABLES = Collections.unmodifiableList(styleables);
        }
        return this.STYLEABLES;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return GNCarouselBase.StyleableProperties.CHILD_STYLEABLES;
    }

    public Duration getTransitionDuration() {
        return transitionDuration.get();
    }

    public StyleableObjectProperty<Duration> transitionDurationProperty() {
        return transitionDuration;
    }

    public void setTransitionDuration(Duration transitionDuration) {
        this.transitionDuration.set(transitionDuration);
    }

    public boolean isAutoRide() {
        return autoRide.get();
    }

    public StyleableBooleanProperty autoRideProperty() {
        return autoRide;
    }

    public void setAutoRide(boolean autoRide) {
        this.autoRide.set(autoRide);
    }
}
