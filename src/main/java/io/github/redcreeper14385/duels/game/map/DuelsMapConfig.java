package io.github.redcreeper14385.duels.game.map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import xyz.nucleoid.plasmid.map.template.MapTemplate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class DuelsMapConfig {
    public static final Codec<DuelsMapConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("spawn_block").forGetter(map -> map.spawnBlock)
    ).apply(instance, DuelsMapConfig::new));

    public final BlockState spawnBlock;

    public DuelsMapConfig(BlockState spawnBlock) {
        this.spawnBlock = spawnBlock;
    }
}
