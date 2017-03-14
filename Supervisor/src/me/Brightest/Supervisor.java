package me.Brightest;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Supervisor extends JavaPlugin {
  private static Plugin plugin;
  
  public Supervisor() {}
  
  public void onEnable() {
	  
	  Commands commands = new Commands(this);
	  
	Bukkit.getServer().getPluginManager().registerEvents(new cSpyEvent(commands), this);
	Bukkit.getServer().getPluginManager().registerEvents(new StaffChatEvent(commands), this);
    getCommand("sv").setExecutor(new Commands(this));
    
    FileConfiguration config = getConfig();
    getConfig().options().copyDefaults(true);
    saveConfig();
    plugin = this;

  }
  
  public void onDisable() {
	  
  }
}