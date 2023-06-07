package io.github.gleidsonmt.gncarousel;

import javafx.css.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/06/2023
 */
public class GNCarouselBase extends Control {

    private static final StyleablePropertyFactory<GNCarouselBase> FACTORY =
            new StyleablePropertyFactory<>(Control.getClassCssMetaData());

    private static final CssMetaData<GNCarouselBase, Duration> TRANSITION_DURATION =
            FACTORY.createDurationCssMetaData("-gn-transition-duration",
                    GNCarouselBase::transitionDurationProperty, Duration.millis(300));

    private static final CssMetaData<GNCarouselBase, Boolean> AUTO_RIDE =
            FACTORY.createBooleanCssMetaData("-gn-auto-ride",
                    GNCarouselBase::autoRideProperty, false);


    private StyleableObjectProperty<Duration> transitionDuration;
    private StyleableBooleanProperty autoRide;

    public GNCarouselBase() {
        this.setPrefHeight(200D);
        this.setPrefWidth(200D);

        this.transitionDuration = new SimpleStyleableObjectProperty<>(TRANSITION_DURATION, this, "transitionDuration", Duration.millis(300D));

        this.autoRide = new SimpleStyleableBooleanProperty(AUTO_RIDE, this , "autoRide", false);

    }

    @Override
    public String getUserAgentStylesheet() {
        return GNCarouselBase.class.getResource("carousel.css").toExternalForm();
    }


    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return FACTORY.getCssMetaData();
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
