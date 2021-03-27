package io.github.redcreeper14385.duels.game.map;

import io.github.redcreeper14385.duels.Duels;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;
import xyz.nucleoid.plasmid.game.GameOpenException;
import xyz.nucleoid.plasmid.map.template.MapTemplate;
import xyz.nucleoid.plasmid.map.template.MapTemplateSerializer;
import xyz.nucleoid.plasmid.util.BlockBounds;

import java.io.IOException;

public class DuelsMapBuilder {
    private final DuelsMapConfig config;

    public DuelsMapBuilder(@NotNull DuelsMapConfig config){
        this.config = config;
    }

    public @NotNull DuelsMap create() throws GameOpenException {
        MapTemplate template;
        try {
            template = MapTemplateSerializer.INSTANCE.loadFromResource(this.config.id);
        } catch (IOException e) {
            throw new GameOpenException(new TranslatableText("duels.error.load_map", this.config.id.toString()), e);
        }

        BlockBounds spawn1 = template.getMetadata().getFirstRegionBounds("spawn1");
        BlockBounds spawn2 = template.getMetadata().getFirstRegionBounds("spawn2");
        if (spawn1 == null || spawn2 == null) {
            Duels.LOGGER.error("Insufficient spawn data! Game will not work.");
            throw new GameOpenException(new LiteralText("Insufficient spawn data!"));
        }

        return new DuelsMap(template, spawn1, spawn2, config);
    }
}
