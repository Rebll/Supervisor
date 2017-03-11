package me.Brightest;

import net.md_5.bungee.api.ChatColor;
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
  




  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    Player player = (Player)sender;
    
    if (commandLabel.equalsIgnoreCase("sv")) {
      if (args.length == 0) {
        if (sender.hasPermission("sv.sv")) {
          sender.sendMessage(ChatColor.RED + "To find out all available commands type. /sv help");
          return true;

        }
        

      }
      else if (args[0].equalsIgnoreCase("help")) {
        if (sender.hasPermission("sv.help")) {
          sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "== Supervisor help page 1/1 ==");
          sender.sendMessage(ChatColor.RED + "     Correct syntax. /sv {command}");
          sender.sendMessage("     ");
          sender.sendMessage(ChatColor.RED + "- help: (Shows the help page of Supervisor plugin.)");
          sender.sendMessage(ChatColor.RED + "- spy: (Turns you into a spy!)");
          sender.sendMessage(ChatColor.RED + "- spyexit: (Exits spy-mode!)");
          sender.sendMessage(ChatColor.RED + "- freeze: (Forces player to freeze for screenshare.)");
          sender.sendMessage(ChatColor.RED + "- unfreeze: (Unfreezes player.)");
          
          return true;
        }
        

      }
      else if ((args.length == 1) && (args[0].equalsIgnoreCase("spy"))) {
        if (sender.hasPermission("sv.spy")) {
          sender.sendMessage(ChatColor.RED + "Usage: /sv spy {player}");
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
          sender.sendMessage(ChatColor.AQUA + "You are now spying on " + ChatColor.RED + args[1]);
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
          player.teleport(back);
          player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1, 1));
          sender.sendMessage(ChatColor.AQUA + "You are no longer spying!");
          return true;
        }
        
      }
      else if ((args.length == 1) && (args[0].equalsIgnoreCase("freeze"))) {
        if (sender.hasPermission("sv.freeze")) {
          sender.sendMessage(ChatColor.RED + "Usage: /sv freeze {player}");
          return true;
        }
        
      }
      else if ((args.length == 2) && (args[0].equalsIgnoreCase("freeze")))
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
          Bukkit.getServer().broadcastMessage(ChatColor.AQUA + player2.getName() + ChatColor.RED + " has been frozen by: " + ChatColor.BOLD + sender.getName());
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
          sender.sendMessage(ChatColor.RED + "Usage: /sv unfreeze {player}");
          return true;
        }
        
      }
      else if ((args.length == 2) && (args[0].equalsIgnoreCase("unfreeze")))
      {
        Player player2 = Bukkit.getPlayer(args[1]);
        
        if (player2 == null) {
          if (sender.hasPermission("sv.unfreeze")) {
            sender.sendMessage(ChatColor.RED + "" + player2 + " is offline.");
          }
        }
        else {
          player2.sendMessage(ChatColor.RED + "You have been unfrozen by: " + ChatColor.AQUA + sender.getName());
          player2.setInvulnerable(false);
          player2.setGameMode(GameMode.SURVIVAL);
          player2.setAllowFlight(false);
          player2.setWalkSpeed(0.2F);
          player2.setFlySpeed(0.1F);
          player2.setBanned(false);
        }
      }
    }
    







    return false;
  }
}