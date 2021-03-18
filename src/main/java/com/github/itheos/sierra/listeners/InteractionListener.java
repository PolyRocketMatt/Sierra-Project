package com.github.itheos.sierra.listeners;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.generator.ChunkSchema;
import com.github.itheos.sierra.factories.ItemFactory;
import com.github.itheos.sierra.handlers.EngineHandler;
import com.github.itheos.sierra.handlers.GuiHandler;
import com.github.itheos.sierra.utils.ItemUtils;
import com.github.itheos.sierra.utils.MathUtils;
import com.github.itheos.sierra.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Listener that listens for interactions.
 */

public class InteractionListener implements Listener {

    /**
     * We use a HIGH event priority since
     * we want the some items to be the first
     * to trigger!
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() != null){
            if (ItemUtils.isItem(event.getItem(), ItemFactory.DEBUG_GUI)) {
                event.setCancelled(true);

                Player player = event.getPlayer();
                Sierra.getHandlerManager().getGuiHandler().getInterface(player, GuiHandler.GUI.DEBUG).show(player);
            }

            if (ItemUtils.isItem(event.getItem(), ItemFactory.DEBUG_STICK)) {
                event.setCancelled(true);

                Player player = event.getPlayer();
                Chunk chunk = player.getWorld().getChunkAt(player.getLocation());
                ChunkSchema schema = Sierra.getHandlerManager().getEngineHandler()
                        .get(event.getPlayer().getWorld().getName()).getChunkSchema(chunk.getX() * 16, chunk.getZ() * 16);

                if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK)
                        && event.hasBlock()) {
                    //  If clicking the debug stick on land, give information about
                    //  The current biome, y-values for generators
                    Block block = Objects.requireNonNull(event.getClickedBlock());

                    int x = block.getX();
                    int y = block.getY();
                    int z = block.getZ();
                    int relX = (int) MathUtils.abs(block.getX() - (chunk.getX() * 16));
                    int relZ = (int) MathUtils.abs(block.getZ() - (chunk.getZ() * 16));

                    float[][][] base = schema.getBase();
                    int[][] heightMap = schema.getHeightMap();

                    try {
                        float value = base[relX][relZ][0];
                        int relY = heightMap[relX][relZ];

                        String biome;
                        if (value > 0.98f)
                            biome = "Wheat Field";
                        else {
                            if (relY > 64)
                                biome = "Plains";
                            else
                                biome = "Ocean";
                        }

                        player.sendMessage(Sierra.getPrefix() + ChatColor.GRAY + "Displaying information for " + StringUtils.toCoordinateString(x, y, z));
                        player.sendMessage(Sierra.getPrefix() + ChatColor.GRAY + "Biome: " + ChatColor.GOLD + biome);
                        player.sendMessage(Sierra.getPrefix() + ChatColor.GRAY + "Base Value: " + ChatColor.GOLD + value);
                        player.sendMessage(Sierra.getPrefix() + ChatColor.GRAY + "Height Value: " + ChatColor.GOLD + relY);
                    } catch (Exception ex) { ex.printStackTrace(); }
                } else {
                    //  TODO: Open some debug menu
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

}
