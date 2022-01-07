package com.github.cassioamartim;

import com.github.cassioamartim.command.ScreenshareCommand;
import com.github.cassioamartim.gamer.GamerListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Screenshare extends JavaPlugin {

    @Getter
    private static FileConfiguration cfg;

    @Getter
    private static World world;
    @Getter
    private static World ss_world;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        cfg = getConfig();
        world = Bukkit.getWorld(cfg.getString("world"));
        ss_world = Bukkit.getWorld(cfg.getString("ss_world"));

        if (world == null || ss_world == null) {
            getServer().getConsoleSender().sendMessage("Mundo normal/ou de screenshare não encontrado, configure-o em config.yml");
            Bukkit.shutdown();
            return;
        }

        getServer().getCommandMap().register("screenshare", new ScreenshareCommand());
        getServer().getPluginManager().registerEvents(new GamerListener(), this);

        getServer().getConsoleSender().sendMessage("Plugin initialized!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("Plugin discontinued!");
    }
}
