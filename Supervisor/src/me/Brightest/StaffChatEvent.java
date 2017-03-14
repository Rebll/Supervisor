package me.Brightest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;

public class StaffChatEvent implements Listener {
	
Commands plugin;
	
	public StaffChatEvent(Commands instance)
	{
	    plugin = instance;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		String msg = e.getMessage();
		
		if(plugin.staffchat.contains(p)) {
			
			e.setCancelled(true);
				
				for(Player staff : Bukkit.getServer().getOnlinePlayers()) {
					
					if(staff.hasPermission("sv.scvision")) {
						
						staff.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SC] " + ChatColor.RED + "" + p.getDisplayName() + ChatColor.RED + ": " + ChatColor.AQUA + msg );
					}
 			}
		}
	}

}