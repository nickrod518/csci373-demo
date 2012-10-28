package edu.unca.nrodrigu.Demo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*
 * This is a sample CommandExectuor
 */
public class DemoCommandExecutor implements CommandExecutor {
	private final Demo plugin;

	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public DemoCommandExecutor(Demo plugin) {
		this.plugin = plugin;
	}

	/*
	 * On command set the sample message
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
			
			// player receives cake
		} else if (command.getName().equalsIgnoreCase("cake")) {
			Player fred = (Player) sender;
			fred.getInventory().addItem(new ItemStack(Material.CAKE, 1));
			return true;
			
			// changes the next 10 blocks below player to air
		} else if (command.getName().equalsIgnoreCase("pit")) {
			sender.sendMessage("Ahhhhhh!!!");
			Player fred = (Player) sender;
			Location loc = fred.getLocation();
			World w = loc.getWorld();
			for (int i = 0; i < 10; i++) {
				loc.setY(loc.getY() - 1);
				Block b = w.getBlockAt(loc);
				b.setTypeId(0);
			}
			return true;
			
			// the block below the player turns to gold
		} else if (command.getName().equalsIgnoreCase("midas")) {
			Player fred = (Player) sender;
			if (!(Boolean) plugin.getMetadata(fred, "midas", plugin)) {
				plugin.setMetadata(fred, "midas", true, plugin);
				sender.sendMessage("You have the Midas touch!");
				plugin.logger.info(fred.getName() + " has the Midas touch");
			} else {
				plugin.setMetadata(fred, "midas", false, plugin);
				sender.sendMessage("You no longer have the Midas touch!");
				plugin.logger.info(fred.getName() + " no longer has the Midas touch");
			}
			return true;
		
			// spawn player specified number of zombies in random locations around 
			// the player and spawn a diamond sword in the player's inventory
		} else if (command.getName().equalsIgnoreCase("zdefend")){
			sender.sendMessage("Look out for the Zombies!");
			Player fred = (Player) sender;
			for (int i = 0; i < Integer.parseInt(args[0]); ++i) {
				Location loc = fred.getLocation();
				World w = loc.getWorld();
				int x = (-20) + (int)(Math.random() * ((20 - (-20)) + 1));
				int y = (-20) + (int)(Math.random() * ((20 - (-20)) + 1));
				loc.setX(loc.getX() + x);
				loc.setZ(loc.getZ() + y);
				w.spawnEntity(loc, EntityType.ZOMBIE);
			}
			fred.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
			// log who initiates zdefend commands
			plugin.logger.info(fred.getName() + " initiated a zombie attack");
			return true;
			
			// create a pit under a specified player, if op
		} else if (command.getName().equalsIgnoreCase("pitplayer")
				&& sender.hasPermission("demo.pitplayer")) {
			Player fred = plugin.getServer().getPlayer(args[0]);
			if (fred != null) {
				Location loc = fred.getLocation();
				World w = loc.getWorld();
				for (int i = 0; i < 10; i++) {
					loc.setY(loc.getY() - 1);
					Block b = w.getBlockAt(loc);
					b.setTypeId(0);
				}
				sender.sendMessage(ChatColor.RED + args[0] + " fell in to a pit");
				plugin.logger.info(args[0] + " isn't very well liked...");
			} else {
				sender.sendMessage(ChatColor.RED + args[1] + " is not logged on");
				plugin.logger.info(sender.getName() + "tried pitting a player that wasn't logged on");
			}
			return true;
		} else {
			return false;
		}
	}
}