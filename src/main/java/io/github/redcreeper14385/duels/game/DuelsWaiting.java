package io.github.redcreeper14385.duels.game;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import io.github.redcreeper14385.duels.game.map.DuelsMapBuilder;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import org.apache.logging.log4j.core.jmx.Server;
import xyz.nucleoid.plasmid.game.*;
import xyz.nucleoid.plasmid.game.event.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import io.github.redcreeper14385.duels.game.map.DuelsMap;
import xyz.nucleoid.fantasy.BubbleWorldConfig;
import xyz.nucleoid.plasmid.game.player.GameTeam;
import xyz.nucleoid.plasmid.game.player.TeamAllocator;

import java.util.ArrayList;
import java.util.List;

public class DuelsWaiting {
    private final GameSpace gameSpace;
    private final DuelsMap map;
    private final DuelsConfig config;
    private final DuelsSpawnLogic spawnLogic;
    private final TeamSelectionLobby teamSelection;

    private DuelsWaiting(GameSpace gameSpace, DuelsMap map, DuelsConfig config, TeamSelectionLobby teamSelection) {
        this.gameSpace = gameSpace;
        this.map = map;
        this.config = config;
        this.spawnLogic = new DuelsSpawnLogic(gameSpace, map);
        this.teamSelection = teamSelection;
    }

    public static GameOpenProcedure open(GameOpenContext<DuelsConfig> context) {
        DuelsConfig config = context.getConfig();
        DuelsMap map = new DuelsMapBuilder(config.mapConfig).create();

        List<GameTeam> teams = ImmutableList.of(DuelsTeams.BLUE_TEAM, DuelsTeams.RED_TEAM);

        BubbleWorldConfig worldConfig = new BubbleWorldConfig()
                .setGenerator(map.asGenerator(context.getServer()))
                .setDefaultGameMode(GameMode.SPECTATOR);

        return context.createOpenProcedure(worldConfig, game -> {
            TeamSelectionLobby teamSelection = TeamSelectionLobby.applyTo(game, teams);
            DuelsWaiting waiting = new DuelsWaiting(game.getSpace(), map, context.getConfig(), teamSelection);

            GameWaitingLobby.applyTo(game, config.playerConfig);

            game.on(RequestStartListener.EVENT, waiting::requestStart);
            game.on(PlayerAddListener.EVENT, waiting::addPlayer);
            game.on(PlayerDeathListener.EVENT, waiting::onPlayerDeath);
        });
    }

    private StartResult requestStart() {
        Multimap<GameTeam, ServerPlayerEntity> players = HashMultimap.create();
        this.teamSelection.allocate(players::put);
        DuelsActive.open(this.gameSpace, this.map, this.config, players);
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
