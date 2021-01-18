package io.github.redcreeper14385.duels.game;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import xyz.nucleoid.plasmid.game.config.PlayerConfig;
import io.github.redcreeper14385.duels.game.map.DuelsMapConfig;

public class DuelsConfig {
    public static final Codec<DuelsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PlayerConfig.CODEC.fieldOf("players").forGetter(config -> config.playerConfig),
            DuelsMapConfig.CODEC.fieldOf("map").forGetter(config -> config.mapConfig),
            Codec.INT.fieldOf("time_limit_secs").forGetter(config -> config.timeLimitSecs)
    ).apply(instance, DuelsConfig::new));

    public final PlayerConfig playerConfig;
    public final DuelsMapConfig mapConfig;
    public final int timeLimitSecs;

    public DuelsConfig(PlayerConfig players, DuelsMapConfig mapConfig, int timeLimitSecs) {
        this.playerConfig = players;
        this.mapConfig = mapConfig;
        this.timeLimitSecs = timeLimitSecs;
    }
}
