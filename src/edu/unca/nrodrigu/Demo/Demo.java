package edu.unca.nrodrigu.Demo;

import org.bukkit.plugin.java.JavaPlugin;

public class Demo extends JavaPlugin {
	DemoLogger logger;
	
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
		this.getCommand("demo").setExecutor(new DemoCommandExecutor(this));
		
		// create the listener
		new DemoEventListener(this);
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		logger.info("plugin disabled");
	}
	
}