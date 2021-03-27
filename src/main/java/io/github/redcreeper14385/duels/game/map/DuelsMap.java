package io.github.redcreeper14385.duels.game.map;

import net.minecraft.server.MinecraftServer;
import xyz.nucleoid.plasmid.map.template.MapTemplate;
import xyz.nucleoid.plasmid.map.template.TemplateChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import xyz.nucleoid.plasmid.util.BlockBounds;

public class DuelsMap {
    private final MapTemplate template;
    private final DuelsMapConfig config;

    public BlockBounds getSpawn1() {
        return spawn1;
    }

    public BlockBounds getSpawn2() {
        return spawn2;
    }

    private final BlockBounds spawn1;
    private final BlockBounds spawn2;
    public BlockPos spawn;

    public DuelsMap(MapTemplate template, BlockBounds spawn1, BlockBounds spawn2, DuelsMapConfig config) {
        this.template = template;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.config = config;
    }

    public ChunkGenerator asGenerator(MinecraftServer server) {
        return new TemplateChunkGenerator(server, this.template);
    }
}
