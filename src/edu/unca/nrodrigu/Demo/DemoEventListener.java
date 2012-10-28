package edu.unca.nrodrigu.Demo;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class DemoEventListener implements Listener {
	private final Demo plugin;
	
	/*
	 * This listener needs to know about the plugin which it came from
	 */
	public DemoEventListener(Demo plugin) {
		// Register the listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		this.plugin = plugin;
	}

	/*
	 * Send a welcome message and inform others of new players
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.logger.info("Give a warm welcome to " + event.getPlayer().getName());
		event.getPlayer().sendMessage("Welcome " + event.getPlayer().getName() + " enjoy your stay!");
		Player fred = event.getPlayer();
		plugin.setMetadata(fred, "midas", false, plugin);
	}

	/*
	 * Walk on liquid (change liquid blocks to dirt)
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void walkOnLiquid(PlayerMoveEvent event) {
		Player fred = event.getPlayer();
		Location loc = fred.getLocation();
		World w = loc.getWorld();
		loc.setY(loc.getY() - 1);
		Block b = w.getBlockAt(loc);
		if (b.isLiquid()) {
			b.setTypeId(3);
			plugin.logger.info(event.getPlayer().getName() + " is walking on liquid.");
		}
	}
	
	/*
	 * update log when new chunk is loaded
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void chunkLog(ChunkLoadEvent event) {
		plugin.logger.info("A new chunk was loaded...");
	}

	/*
	 * when the player left clicks a block and midas is enabled, it turns to gold
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void midasTouch(PlayerInteractEvent event) {
		if ((Boolean) plugin.getMetadata(event.getPlayer(), "midas", plugin)) {
			if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				Block b = event.getClickedBlock();
				if (b != null) {
					b.setTypeId(41);
					event.getPlayer().sendMessage("Everything you touch turns to gold!");
					plugin.logger.info(event.getPlayer().getName() + " is turning things to gold again...");
				}
			}
		}
	}
	
	/*
	 * if midas is enabled, blocks the player steps on turn to gold
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void midas(PlayerMoveEvent event) {
		if ((Boolean) plugin.getMetadata(event.getPlayer(), "midas", plugin)) {
			Player fred = event.getPlayer();
			Location loc = fred.getLocation();
			World w = loc.getWorld();
			loc.setY(loc.getY() - 1);
			Block b = w.getBlockAt(loc);
			if (!b.isLiquid() && !b.isEmpty()) {
				b.setTypeId(41);
			}
		}
	}
}
