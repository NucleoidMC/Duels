package io.github.redcreeper14385.duels.game.map;

import xyz.nucleoid.plasmid.map.template.MapTemplate;
import xyz.nucleoid.plasmid.util.BlockBounds;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import io.github.redcreeper14385.duels.game.DuelsConfig;

import java.util.concurrent.CompletableFuture;

public class DuelsMapGenerator {

    private final DuelsMapConfig config;

    public DuelsMapGenerator(DuelsMapConfig config) {
        this.config = config;
    }

    public DuelsMap build() {
        MapTemplate template = MapTemplate.createEmpty();
        DuelsMap map = new DuelsMap(template, this.config);

        this.buildSpawn(template);
        map.spawn = new BlockPos(0,65,0);

        return map;
    }

    private void buildSpawn(MapTemplate builder) {
        BlockPos min = new BlockPos(-10, 64, -10);
        BlockPos max = new BlockPos(10, 64, 10);

        for (BlockPos pos : BlockPos.iterate(min, max)) {
            builder.setBlockState(pos, this.config.spawnBlock);
        }
    }
}
