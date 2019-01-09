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

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  29/12/2018
 */
public class GNCarouselBehavior extends BehaviorBase<GNCarousel> {

    protected static final List<KeyBinding> CAROUSEL_BINDINGS = new ArrayList<KeyBinding>();

    static {
        CAROUSEL_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "previous"));
        CAROUSEL_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "next"));
    }

    public GNCarouselBehavior(final GNCarousel control) {
        super(control, CAROUSEL_BINDINGS);
    }

    @Override
    protected void callAction(String name) {
        GNCarousel carouselU = getControl();
        GNCarouselSkin skinU = (GNCarouselSkin) carouselU.getSkin();

        switch (name){
            case "previous" :
                skinU.previous();
                break;
            case "next" :
                skinU.next();
                break;
        }
    }
}
