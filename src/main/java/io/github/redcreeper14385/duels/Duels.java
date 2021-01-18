package io.github.redcreeper14385.duels;

import net.fabricmc.api.ModInitializer;
import xyz.nucleoid.plasmid.game.GameType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.redcreeper14385.duels.game.DuelsConfig;
import io.github.redcreeper14385.duels.game.DuelsWaiting;

public class Duels implements ModInitializer {

    public static final String ID = "duels";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final GameType<DuelsConfig> TYPE = GameType.register(
            new Identifier(ID, "duels"),
            DuelsWaiting::open,
            DuelsConfig.CODEC
    );

    @Override
    public void onInitialize() {}
}
