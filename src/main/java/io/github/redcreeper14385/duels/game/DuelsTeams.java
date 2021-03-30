package io.github.redcreeper14385.duels.game;

import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.DyeColor;
import org.apache.commons.lang3.RandomStringUtils;
import xyz.nucleoid.plasmid.game.GameSpace;
import xyz.nucleoid.plasmid.game.player.GameTeam;

public class DuelsTeams implements AutoCloseable {
    public static final GameTeam RED_TEAM = new GameTeam("red", "Red", DyeColor.RED);
    public static final GameTeam BLUE_TEAM = new GameTeam("blue", "blue", DyeColor.BLUE);

    private final ServerScoreboard scoreboard;

    final Team red_team;
    final Team blue_team;

    public DuelsTeams(GameSpace gameSpace) {
        this.scoreboard = gameSpace.getServer().getScoreboard();
        this.red_team = this.createTeam(RED_TEAM);
        this.blue_team = this.createTeam(BLUE_TEAM);
    }

    private Team createTeam(GameTeam team) {
        Team scoreboardTeam = this.scoreboard.addTeam(RandomStringUtils.randomAlphanumeric(16));
        scoreboardTeam.setDisplayName(new LiteralText(team.getDisplay()).formatted(team.getFormatting()));
        scoreboardTeam.setColor(team.getFormatting());
        scoreboardTeam.setFriendlyFireAllowed(false);
        scoreboardTeam.setCollisionRule(AbstractTeam.CollisionRule.NEVER);
        return scoreboardTeam;
    }

    public void addPlayer(ServerPlayerEntity player, GameTeam team) {
        Team scoreboardTeam = this.getScoreboardTeam(team);
        this.scoreboard.addPlayerToTeam(player.getEntityName(), scoreboardTeam);
    }

    public void removePlayer(ServerPlayerEntity player, GameTeam team) {
        Team scoreboardTeam = this.getScoreboardTeam(team);
        this.scoreboard.removePlayerFromTeam(player.getEntityName(), scoreboardTeam);
    }

    private Team getScoreboardTeam(GameTeam team) {
        return team == RED_TEAM ? this.red_team : this.blue_team;
    }

    @Override
    public void close() {
        this.scoreboard.removeTeam(this.red_team);
        this.scoreboard.removeTeam(this.blue_team);
    }
}
