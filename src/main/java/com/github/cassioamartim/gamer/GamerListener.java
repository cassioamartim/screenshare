package com.github.cassioamartim.gamer;

import com.github.cassioamartim.Screenshare;
import lombok.NonNull;
import lombok.val;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class GamerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    void join(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        new Gamer(event.getPlayer());
    }

    @EventHandler
    void quit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        @NonNull
        val gamer = Gamer.get(event.getPlayer().getName());

        if (gamer.isInScreenshare()) {
            Gamer.remove(event.getPlayer().getName());
            Bukkit.getOnlinePlayers().forEach(player ->
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Screenshare.getCfg().getString("disconnected-message"))));

            if (Screenshare.getCfg().getBoolean("disconnect-ban"))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Screenshare.getCfg().getString("ban-command").replace("/", "").replace("{player}", event.getPlayer().getName()));
        }
    }

    @EventHandler
    void command(PlayerCommandPreprocessEvent event) {
        @NonNull
        val gamer = Gamer.get(event.getPlayer().getName());

        if (gamer.isInScreenshare() && event.getMessage().toLowerCase().startsWith("/")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Screenshare.getCfg().getString("command-screenshare")));
        }
    }
}
