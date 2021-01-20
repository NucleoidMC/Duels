package io.github.redcreeper14385.duels.game;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.redcreeper14385.duels.game.map.DuelsMapConfig;
import net.minecraft.item.ItemStack;
import xyz.nucleoid.plasmid.game.config.PlayerConfig;

import java.util.Collections;
import java.util.List;

public class DuelsConfig {
    public static final Codec<DuelsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PlayerConfig.CODEC.fieldOf("players").forGetter(config -> config.playerConfig),
            DuelsMapConfig.CODEC.fieldOf("map").forGetter(config -> config.mapConfig),
            Codec.INT.fieldOf("time_limit_secs").forGetter(config -> config.timeLimitSecs),
            ItemStack.CODEC.listOf().optionalFieldOf("gear", Collections.emptyList()).forGetter(config -> config.gear),
            ItemStack.CODEC.optionalFieldOf("helmet", ItemStack.EMPTY).forGetter(config -> config.helmet),
            ItemStack.CODEC.optionalFieldOf("chestplate", ItemStack.EMPTY).forGetter(config -> config.chestplate),
            ItemStack.CODEC.optionalFieldOf("leggings", ItemStack.EMPTY).forGetter(config -> config.leggings),
            ItemStack.CODEC.optionalFieldOf("boots", ItemStack.EMPTY).forGetter(config -> config.boots)
    ).apply(instance, DuelsConfig::new));

    public final PlayerConfig playerConfig;
    public final DuelsMapConfig mapConfig;
    public final int timeLimitSecs;
    public final List<ItemStack> gear;
    public final ItemStack helmet;
    public final ItemStack chestplate;
    public final ItemStack leggings;
    public final ItemStack boots;


    public DuelsConfig(PlayerConfig players, DuelsMapConfig mapConfig, int timeLimitSecs, List<ItemStack> gear, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.playerConfig = players;
        this.mapConfig = mapConfig;
        this.timeLimitSecs = timeLimitSecs;
        this.gear = gear;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }
}
