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
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return false;
		} else if (!(sender instanceof Player)) {
			return false;
			
			// player receives cake
		} else if (args[0].equalsIgnoreCase("cake")) {
			Player fred = (Player) sender;
			fred.getInventory().addItem(new ItemStack(Material.CAKE, 1));
			return true;
			
			// changes the next 10 blocks below player to air
		} else if (args[0].equalsIgnoreCase("pit")) {
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
		} else if (args[0].equalsIgnoreCase("midas")) {
			sender.sendMessage("The floor below you turns to gold!");
			Player fred = (Player) sender;
			Location loc = fred.getLocation();
			World w = loc.getWorld();
			loc.setY(loc.getY() - 1);
			Block b = w.getBlockAt(loc);
			b.setTypeId(41);
			return true;
		
			// spawn 10 zombies in random locations around the player
			// and spawn a diamond sword in the player's inventory
		} else if (args[0].equalsIgnoreCase("zdefend")){
			sender.sendMessage("Look out for the Zombies!");
			Player fred = (Player) sender;
			for (int i = 0; i < 10; ++i) {
				Location loc = fred.getLocation();
				World w = loc.getWorld();
				int x = (-20) + (int)(Math.random() * ((20 - (-20)) + 1));
				int y = (-20) + (int)(Math.random() * ((20 - (-20)) + 1));
				loc.setX(loc.getX() + x);
				loc.setZ(loc.getZ() + y);
				w.spawnCreature(loc, EntityType.ZOMBIE);
			}
			fred.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
			// log who initiates zdefend commands
			plugin.logger.info(fred + " initiated a zombie attack");
			return true;
			
			// create a pit under a specified player, if op
		} else if (args[0].equalsIgnoreCase("pitplayer")
				&& sender.hasPermission("demo.pitplayer")) {
			Player fred = plugin.getServer().getPlayer(args[1]);
			if (fred != null) {
				
				Location loc = fred.getLocation();
				World w = loc.getWorld();
				for (int i = 0; i < 10; i++) {
					loc.setY(loc.getY() - 1);
					Block b = w.getBlockAt(loc);
					b.setTypeId(0);
				}
				sender.sendMessage(ChatColor.RED + args[1] + " fell in to a pit");
				plugin.logger.info(args[1] + " isn't very well liked...");
			} else {
				sender.sendMessage(ChatColor.RED + args[1] + " is not logged on");
				plugin.logger.info(sender + "tried pitting a player that wasn't logged on");
			}
			return true;
		} else {
			return false;
		}
	}

}