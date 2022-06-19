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
package org.spongepowered.common.mixin.api.minecraft.world.level.levelgen.structure.pools;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.api.world.generation.structure.jigsaw.JigsawPool;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.SpongeCommon;
import org.spongepowered.common.mixin.api.minecraft.server.level.ServerLevelMixin_API;
import org.spongepowered.common.util.VecHelper;

import javax.annotation.Nullable;

@Mixin(StructurePoolElement.class)
public abstract class StructurePoolElementMixin_API implements JigsawPool.Element {

    // @formatter:off
    @Shadow @Nullable private volatile StructureTemplatePool.Projection projection;

    @Shadow public abstract boolean shadow$place(final StructureTemplateManager var1,
            final WorldGenLevel var2, final StructureManager var3,
            final ChunkGenerator var4, final BlockPos var5, final BlockPos var6,
            final Rotation var7, final BoundingBox var8,
            final RandomSource var9, final boolean var10);
    // @formatter:on

    @Override
    public JigsawPool.Projection projection() {
        return (JigsawPool.Projection) (Object) this.projection;
    }

    @Override
    public boolean place(final ServerLocation location) {
        final StructureTemplateManager stm = SpongeCommon.server().getStructureManager();
        final ServerLevel level = (ServerLevel) location.world();
        final boolean keepJigsaws = true;
        return this.shadow$place(stm, level, level.structureManager(), level.getChunkSource().getGenerator(),
                VecHelper.toBlockPos(location.blockPosition()), BlockPos.ZERO, Rotation.NONE, BoundingBox.infinite(), level.getRandom(), keepJigsaws);
    }
}
