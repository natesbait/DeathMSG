package me.nathan.deathmessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class DeathMessageMain extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Entity killer = player.getKiller();

        if (!(killer instanceof Player)) return;

        int health = (int) ((Player) killer).getHealth();

        event.setDeathMessage(ChatColor.translateAlternateColorCodes('&', getCustomDeathMessage().replace("%victim%", player.getName())
                .replace("%killer%", killer.getName()).replace("%health%", String.valueOf(health))));
    }

    @Override
    public boolean onCommand(CommandSender caller, Command command, String label, String[] args) {

        if (!(caller.hasPermission("deathmessage.reload"))) return false;

        if (label.equalsIgnoreCase("msgreload")) {
            reloadConfig();
            caller.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Reloaded configuration:"));
            caller.sendMessage(ChatColor.translateAlternateColorCodes('&', getCustomDeathMessage()));
        }

        return false;
    }

    private String getCustomDeathMessage() {
        return getConfig().getString("death-message");
    }

}
