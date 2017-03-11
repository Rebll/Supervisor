package me.Brightest;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Supervisor
  extends JavaPlugin
{
  private static Plugin plugin;
  
  public Supervisor() {}
  
  public void onEnable()
  {
	Bukkit.getServer().getPluginManager().registerEvents(new Events(null), this);
	  
    plugin = this;
    
    FileConfiguration config = getConfig();
    
    getConfig().options().copyDefaults(true);
    saveConfig();
    
    getCommand("sv").setExecutor(new Commands(this));
  }
  
  public void onDisable()
  {
    plugin = null;
  }
}