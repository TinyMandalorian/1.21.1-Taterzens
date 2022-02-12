package org.samo_lego.taterzens.fabric.platform;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.samo_lego.taterzens.fabric.mixin.MappedRegistryAccessor;
import org.samo_lego.taterzens.npc.TaterzenNPC;
import org.samo_lego.taterzens.platform.Platform;

import java.nio.file.Path;

import static net.minecraft.core.Registry.ITEM;
import static org.samo_lego.taterzens.Taterzens.NPC_ID;

public class FabricPlatform extends Platform {

    private static final int REGISTRY_ITEMS_SIZE = ((MappedRegistryAccessor) ITEM).getById().size();

    @Override
    public Path getConfigDirPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public int getItemRegistrySize() {
        return REGISTRY_ITEMS_SIZE;
    }

    /**
     * Checks permission of commandSource using Lucko's
     * permission API.
     * If permission isn't set, it will require the commandSource
     * to have permission level set in the config.
     *
     * @param commandSource commandSource to check permission for.
     * @param permission permission node to check.
     * @param fallbackLevel level to require if permission isn't set
     * @return true if commandSource has the permission, otherwise false
     */
    @Override
    public boolean checkPermission(CommandSourceStack commandSource, String permission, int fallbackLevel) {
        // Enable command blocks, therefore null check
        return commandSource.getEntity() == null || Permissions.check(commandSource, permission, fallbackLevel);
    }


    @Override
    public EntityType<TaterzenNPC> registerTaterzenType() {
        return Registry.register(
                Registry.ENTITY_TYPE,
                NPC_ID,
                FabricEntityTypeBuilder
                        .create(MobCategory.MISC, TaterzenNPC::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 1.8F))
                        .build()
        );
    }
}