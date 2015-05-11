/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.data.manipulators.entities;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.manipulators.entities.HorseData;
import org.spongepowered.api.data.types.HorseColor;
import org.spongepowered.api.data.types.HorseColors;
import org.spongepowered.api.data.types.HorseStyle;
import org.spongepowered.api.data.types.HorseStyles;
import org.spongepowered.api.data.types.HorseVariant;
import org.spongepowered.api.data.types.HorseVariants;
import org.spongepowered.common.data.manipulators.SpongeAbstractData;

public class SpongeHorseData extends SpongeAbstractData<HorseData> implements HorseData {

    private HorseColor color = HorseColors.BLACK;
    private HorseStyle style = HorseStyles.BLACK_DOTS;
    private HorseVariant variant = HorseVariants.HORSE;

    public SpongeHorseData() {
        super(HorseData.class);
    }

    @Override
    public HorseStyle getStyle() {
        return this.style;
    }

    @Override
    public HorseData setStyle(HorseStyle style) {
        this.style = checkNotNull(style, "Style is null!");
        return this;
    }

    @Override
    public HorseColor getColor() {
        return this.color;
    }

    @Override
    public HorseData setColor(HorseColor color) {
        this.color = checkNotNull(color, "Color is null!");
        return this;
    }

    @Override
    public HorseVariant getVariant() {
        return this.variant;
    }

    @Override
    public HorseData setVariant(HorseVariant variant) {
        this.variant = checkNotNull(variant, "Variant is null!");
        return this;
    }

    @Override
    public HorseData copy() {
        return new SpongeHorseData()
                .setColor(this.color)
                .setStyle(this.style)
                .setVariant(this.variant);
    }

    @Override
    public int compareTo(HorseData o) {
        return o.getColor().getId().compareTo(this.getColor().getId()) - o.getStyle().getId().compareTo(this.getStyle().getId())
                - o.getVariant().getId().compareTo(this.getVariant().getId());
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(of("Color"), this.getColor().getId())
                .set(of("Style"), this.getStyle().getId())
                .set(of("Variant"), this.getVariant().getId());
    }
}
