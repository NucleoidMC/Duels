package io.github.redcreeper14385.duels.game;

import io.github.redcreeper14385.duels.game.map.DuelsMapBuilder;
import net.minecraft.util.ActionResult;
import xyz.nucleoid.plasmid.game.*;
import xyz.nucleoid.plasmid.game.event.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import io.github.redcreeper14385.duels.game.map.DuelsMap;
import xyz.nucleoid.fantasy.BubbleWorldConfig;

public class DuelsWaiting {
    private final GameSpace gameSpace;
    private final DuelsMap map;
    private final DuelsConfig config;
    private final DuelsSpawnLogic spawnLogic;

    private DuelsWaiting(GameSpace gameSpace, DuelsMap map, DuelsConfig config) {
        this.gameSpace = gameSpace;
        this.map = map;
        this.config = config;
        this.spawnLogic = new DuelsSpawnLogic(gameSpace, map);
    }

    public static GameOpenProcedure open(GameOpenContext<DuelsConfig> context) {
        DuelsConfig config = context.getConfig();
        DuelsMap map = new DuelsMapBuilder(config.mapConfig).create();

        BubbleWorldConfig worldConfig = new BubbleWorldConfig()
                .setGenerator(map.asGenerator(context.getServer()))
                .setDefaultGameMode(GameMode.SPECTATOR);

        return context.createOpenProcedure(worldConfig, game -> {
            DuelsWaiting waiting = new DuelsWaiting(game.getSpace(), map, context.getConfig());

            GameWaitingLobby.applyTo(game, config.playerConfig);

            game.on(RequestStartListener.EVENT, waiting::requestStart);
            game.on(PlayerAddListener.EVENT, waiting::addPlayer);
            game.on(PlayerDeathListener.EVENT, waiting::onPlayerDeath);
        });
    }

    private StartResult requestStart() {
        DuelsActive.open(this.gameSpace, this.map, this.config);
        return StartResult.OK;
    }

    private void addPlayer(ServerPlayerEntity player) {
        this.spawnPlayer(player);
    }

    private ActionResult onPlayerDeath(ServerPlayerEntity player, DamageSource source) {
        player.setHealth(20.0f);
        this.spawnPlayer(player);
        return ActionResult.FAIL;
    }

    private void spawnPlayer(ServerPlayerEntity player) {
        this.spawnLogic.resetPlayer(player, GameMode.ADVENTURE);
        this.spawnLogic.spawnPlayer(player);
    }
}
