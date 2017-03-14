package me.Brightest;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;




public class Commands
implements CommandExecutor
{

	Supervisor plugin;
	FileConfiguration config;

	public Commands(Supervisor supervisor)
	{
		plugin = supervisor;
		config = plugin.getConfig();
	}

	public static boolean cSpyEnabled = false;
	public String message = "";
	public static ArrayList<Player> staffchat = new ArrayList<Player>();
	public List<String> frozen = new ArrayList<String>();
	public List<String> cspy = new ArrayList<String>();


	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		Player player = (Player)sender;

		if (commandLabel.equalsIgnoreCase("sv")) {
			if (args.length == 0) {
				if (sender.hasPermission("sv.sv")) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "To find out all available commands type. " + ChatColor.RED + "/sv help");
					return true;

				}


			}
			else if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("sv.help")) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "== Supervisor help page 1/1 ==");
					sender.sendMessage(ChatColor.AQUA + "     Correct syntax. " + ChatColor.RED + "/sv {command}");
					sender.sendMessage("     ");
					sender.sendMessage(ChatColor.RED + "- help: " + ChatColor.AQUA + "(Shows the help page of Supervisor plugin.)");
					sender.sendMessage(ChatColor.RED + "- spy: " + ChatColor.AQUA + "(Allows you to spy on players.)");
					sender.sendMessage(ChatColor.RED + "- spyexit: " + ChatColor.AQUA + "(Exits spy-mode!)");
					sender.sendMessage(ChatColor.RED + "- cspy: " + ChatColor.AQUA + "(Allows you to sniff /whispers works by toggle.)");
					sender.sendMessage(ChatColor.RED + "- freeze: " + ChatColor.AQUA + "(Forces a player to freeze for screenshare.)");
					sender.sendMessage(ChatColor.RED + "- unfreeze: " + ChatColor.AQUA + "(Unfreezes a frozen player.)");

					return true;
				}


			}
			else if ((args.length == 1) && (args[0].equalsIgnoreCase("spy"))) {
				if (sender.hasPermission("sv.spy")) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "Usage: " + ChatColor.RED + "/sv spy {player}");
					return true;
				}


			}
			else if ((args.length == 2) && (args[0].equalsIgnoreCase("spy")))
			{
				Player player2 = Bukkit.getPlayer(args[1]);

				if (player2 == null) {
					if (sender.hasPermission("sv.spy")) {
						sender.sendMessage(ChatColor.RED + args[1] + " is offline.");
						return true;
					}

				}
				else
				{
					Location currentLoc = player.getLocation();



					config.set("inventory.Armor", player.getInventory().getArmorContents());
					config.set("inventory.Content", player.getInventory().getContents());
					config.set("location.World", currentLoc.getWorld().getName());
					config.set("location.X", Double.valueOf(currentLoc.getX()));
					config.set("location.Y", Double.valueOf(currentLoc.getY()));
					config.set("location.Z", Double.valueOf(currentLoc.getZ()));
					config.set("location.Yaw", Float.valueOf(currentLoc.getYaw()));
					config.set("location.Pitch", Float.valueOf(currentLoc.getPitch()));
					plugin.saveConfig();

					Location loc = player2.getLocation();

					player.teleport(player2);
					player.getInventory().clear();
					player.hidePlayer(player);
					player.setGameMode(GameMode.SPECTATOR);
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, true, false));
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "You are now spying on " + ChatColor.RED + args[1]);
					return true;

				}



			}
			else if (args[0].equalsIgnoreCase("spyexit"))
			{
				int x = config.getInt("location.X");
				int y = config.getInt("location.Y");
				int z = config.getInt("location.Z");



				World world = Bukkit.getServer().getWorld(config.getString("location.World"));
				Location back = new Location(world, x, y, z);
				if (sender.hasPermission("sv.spyexit")) {
					player.setGameMode(GameMode.SURVIVAL);
					player.teleport(back);
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1, 1));
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "You are no longer spying!");
					return true;
				}
				
				if (player == null) {
					player.setGameMode(GameMode.SURVIVAL);
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1, 1));
				}

			}
			else if ((args.length == 1) && (args[0].equalsIgnoreCase("freeze"))) {
				if (sender.hasPermission("sv.freeze")) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "Usage: " + ChatColor.RED + "/sv freeze {player}");
					return true;
				}

			}
			else if ((args.length == 2) && (args[0].equalsIgnoreCase("freeze") && !frozen.contains(Bukkit.getPlayer(args[1]).getName())))
			{
				Player player2 = Bukkit.getPlayer(args[1]);

				if (player2 == null) {
					if (sender.hasPermission("sv.freeze")) {
						sender.sendMessage(ChatColor.RED + args[1] + " is offline.");
						return true;
					}
				}
				else
				{
					
					frozen.add(player2.getName());
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "You have frozen: " + ChatColor.RED + player2.getName());
					Bukkit.getServer().broadcastMessage(ChatColor.RED + player2.getName() + ChatColor.AQUA + " has been frozen by: " + ChatColor.RED + "" + ChatColor.BOLD + sender.getName());
					player2.sendMessage(ChatColor.RED + "You have been frozen by: " + ChatColor.AQUA + sender.getName());
					player2.setInvulnerable(true);
					player2.setGameMode(GameMode.SPECTATOR);
					player2.setAllowFlight(true);
					player2.setWalkSpeed(0.0F);
					player2.setFlySpeed(0.0F);

					if (player2 == null) {}
					player2.setBanned(true);
				}


			}
			else if ((args.length == 1) && (args[0].equalsIgnoreCase("unfreeze"))) {
				if (sender.hasPermission("sv.unfreeze")) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "Usage: " + ChatColor.RED + "/sv unfreeze {player}");
					return true;
				}

			}
			else if ((args.length == 2) && (args[0].equalsIgnoreCase("unfreeze") && frozen.contains(Bukkit.getPlayer(args[1]).getName())))
			{
				Player player2 = Bukkit.getPlayer(args[1]);

				if (player2 == null) {
					if (sender.hasPermission("sv.unfreeze")) {
						sender.sendMessage(ChatColor.RED + "" + player2 + " is offline.");
					}
				}
				
				else {
					frozen.remove(player2.getName());
					player2.sendMessage(ChatColor.RED + "You have been unfrozen by: " + ChatColor.AQUA + sender.getName());
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "You have unfrozen: " + ChatColor.RED + player2.getName());
					player2.setInvulnerable(false);
					player2.setGameMode(GameMode.SURVIVAL);
					player2.setAllowFlight(false);
					player2.setWalkSpeed(0.2F);
					player2.setFlySpeed(0.1F);
					player2.setBanned(false);

				}
			} else {
				if ((args.length == 1) && (args[0].equalsIgnoreCase("cspy") && cSpyEnabled == false)) {
					if (sender.hasPermission("sv.cspy")) {
						cspy.add(player.getName());
						sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.RED + "Command-Spy " + ChatColor.AQUA + "has been enabled!");
						cSpyEnabled = true;
						return true;

					}
				} else {

					if(cspy.contains(player.getName())) {
						cspy.remove(player.getName());
						sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.RED + "Command-Spy " + ChatColor.AQUA + "has been disabled!");
						cSpyEnabled = false;
						return true;

				} else {
				}         
					
					if((args.length == 1) && (args[0].equalsIgnoreCase("sc"))) {
						
						if(!(player.hasPermission("sv.sc"))) {
							player.sendMessage(ChatColor.RED + "Insufficent permissions!");
							return true;
						}
						
						if(staffchat.contains(player)) {
							staffchat.remove(player);
							player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.RED + "Staff-Chat " + ChatColor.AQUA + "has been disabled.");
							return true;
						} else
							
							staffchat.add(player);
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.RED + "Staff-Chat " + ChatColor.AQUA + "has been enabled.");
						return true;
					}
					
					if(args.length >= 1) {
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SV] " + ChatColor.AQUA + "Usage: " + ChatColor.RED + "/sv sc");
						return true;
					}
				}
			}
		}
		return true;
	}
}