package io.github.redcreeper14385.duels.game;

import net.minecraft.util.math.Vec3d;
import xyz.nucleoid.plasmid.game.GameSpace;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import io.github.redcreeper14385.duels.Duels;
import io.github.redcreeper14385.duels.game.map.DuelsMap;
import xyz.nucleoid.plasmid.game.TeamSelectionLobby;
import xyz.nucleoid.plasmid.game.player.TeamAllocator;
import xyz.nucleoid.plasmid.util.BlockBounds;

public class DuelsSpawnLogic {
    private final GameSpace gameSpace;
    private final DuelsMap map;

    public DuelsSpawnLogic(GameSpace gameSpace, DuelsMap map) {
        this.gameSpace = gameSpace;
        this.map = map;
    }

    public void resetPlayer(ServerPlayerEntity player, GameMode gameMode) {
        player.setGameMode(gameMode);
        player.setVelocity(Vec3d.ZERO);
        player.fallDistance = 0.0f;

        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.NIGHT_VISION,
                20 * 60 * 60,
                1,
                true,
                false
        ));
    }

    public void spawnPlayer(ServerPlayerEntity player) {
        ServerWorld world = this.gameSpace.getWorld();

        BlockBounds spawn1 = map.getSpawn1();
        BlockBounds spawn2 = map.getSpawn2();

        if (true) {
            player.teleport(world, spawn1.getMin().getX(), spawn1.getMin().getY(), spawn1.getMin().getZ(), 0, 0);
        }
        else {
            player.teleport(world, spawn2.getMin().getX(), spawn2.getMin().getY(), spawn2.getMin().getZ(), 0, 0);
        }
    }
}
