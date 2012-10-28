package edu.unca.nrodrigu.Demo;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Demo extends JavaPlugin {
	DemoLogger logger;
	DemoCommandExecutor executor;
	
	/*
	 * This is called when your plug-in is enabled
	 */
	@Override
	public void onEnable() {
		// create a logger and use it
		logger = new DemoLogger(this);
		logger.info("plugin enabled");
		
		// save the configuration file
		saveDefaultConfig();

		// set the command executor for sample
		executor = new DemoCommandExecutor(this);
		
		// create the listener
		new DemoEventListener(this);
		
		// set the command executors
		this.getCommand("cake").setExecutor(executor);
		this.getCommand("pit").setExecutor(executor);
		this.getCommand("midas").setExecutor(executor);
		this.getCommand("zdefend").setExecutor(executor);
		this.getCommand("pitplayer").setExecutor(executor);
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		logger.info("plugin disabled");
	}
	
	public void setMetadata(Player player, String key, Object value, Demo plugin) {
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public Object getMetadata(Player player, String key, Demo plugin) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(plugin.getDescription().getName())) {
				return (value.asBoolean());
			}
		}
		return null;
	}
	
}