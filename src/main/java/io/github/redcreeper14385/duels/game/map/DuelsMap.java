package io.github.redcreeper14385.duels.game.map;

import net.minecraft.server.MinecraftServer;
import xyz.nucleoid.plasmid.map.template.MapTemplate;
import xyz.nucleoid.plasmid.map.template.TemplateChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DuelsMap {
    private final MapTemplate template;
    private final DuelsMapConfig config;
    public BlockPos spawn;

    public DuelsMap(MapTemplate template, DuelsMapConfig config) {
        this.template = template;
        this.config = config;
    }

    public ChunkGenerator asGenerator(MinecraftServer server) {
        return new TemplateChunkGenerator(server, this.template);
    }
}
