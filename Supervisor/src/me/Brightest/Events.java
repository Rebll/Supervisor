package me.Brightest;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;

public class Events implements Listener {
	
	Commands plugin;
	
	public Events(Commands instance)
	{
	    plugin = instance;
	}
	
	@EventHandler
    public void commandSee(PlayerCommandPreprocessEvent e)
    {
        Player p = e.getPlayer();
        for(Player ol : Bukkit.getOnlinePlayers())
        {
            if(ol.isOp() && plugin.cSpyEnabled == true)
            {
                if(e.getMessage().contains("/w"))
                {
                	if (ol.hasPermission("sv.cspy")) {
                    ol.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV]" + ChatColor.AQUA + p.getName() + " sent the command: " + ChatColor.RED  + e.getMessage());
                	}
                }
            }
        }
    }
}