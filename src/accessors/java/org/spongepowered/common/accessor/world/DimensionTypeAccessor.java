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
package org.spongepowered.common.accessor.world;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.IBiomeMagnifier;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.common.UntransformedAccessorError;
import org.spongepowered.common.UntransformedInvokerError;

import java.util.OptionalLong;

@Mixin(DimensionType.class)
public interface DimensionTypeAccessor {

    @Invoker("<init>") static DimensionType invoker$new(final OptionalLong p_i241973_1_, final boolean p_i241973_2_, final boolean p_i241973_3_,
            final boolean p_i241973_4_, final boolean p_i241973_5_, final double p_i241973_6_, final boolean p_i241973_8_, final boolean p_i241973_9_, final boolean p_i241973_10_,
            final boolean p_i241973_11_, final boolean p_i241973_12_, final int p_i241973_13_, final IBiomeMagnifier p_i241973_14_, final ResourceLocation p_i241973_15_,
            final ResourceLocation p_i241973_16_, final float p_i241973_17_
    ) {
        throw new UntransformedInvokerError();
    }

    @Accessor("DEFAULT_OVERWORLD") static DimensionType accessor$DEFAULT_OVERWORLD() {
        throw new UntransformedAccessorError();
    }

    @Accessor("DEFAULT_OVERWORLD_CAVES") static DimensionType accessor$DEFAULT_OVERWORLD_CAVES() {
        throw new UntransformedAccessorError();
    }

    @Accessor("DEFAULT_NETHER") static DimensionType accessor$DEFAULT_NETHER() {
        throw new UntransformedAccessorError();
    }

    @Accessor("DEFAULT_END") static DimensionType accessor$DEFAULT_END() {
        throw new UntransformedAccessorError();
    }

    @Accessor("fixedTime") OptionalLong accessor$fixedTime();

    @Accessor("effectsLocation") ResourceLocation accessor$effectsLocation();

    @Accessor("ambientLight") float accessor$ambientLight();

    @Invoker("defaultEndGenerator") static ChunkGenerator invoker$defaultEndGenerator(final Registry<Biome> p_242717_0_,
        final Registry<DimensionSettings> p_242717_1_, final long p_242717_2_) {
        throw new UntransformedInvokerError();
    }

    @Invoker("defaultNetherGenerator") static ChunkGenerator invoker$defaultNetherGenerator(final Registry<Biome> p_242717_0_,
        final Registry<DimensionSettings> p_242717_1_, final long p_242717_2_) {
        throw new UntransformedInvokerError();
    }
}
