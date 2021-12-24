package com.github.cassioamartim.command;

import com.github.cassioamartim.Screenshare;
import com.github.cassioamartim.gamer.Gamer;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class ScreenshareCommand extends Command {

    public ScreenshareCommand() {
        super("screenshare", "", "", Collections.singletonList("ss"));
    }

    @Override
    public boolean execute(CommandSender sender, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVocê precisa ser um jogador para isto!");
            return true;
        }

        val player = (Player) sender;
        if (!player.hasPermission("ss.use")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando!");
            return true;
        }

        if (args.length <= 0) {
            player.sendMessage("§cSintaxe errada! Use o comando: /" + lb + " <user>.");
            return true;
        }

        val target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cO jogador informado está offline!");
            return true;
        }

        if (target.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage("§cVocê não pode colocar a si mesmo em screenshare.");
            return true;
        }

        val gamer = Gamer.get(target.getName());

        if (gamer.isInScreenshare()) {
            gamer.setInScreenshare(false);
            Gamer.update(gamer);

            player.teleport(Screenshare.getWorld().getSpawnLocation());
            target.teleport(player.getLocation());

            player.sendMessage("§cVocê removeu o jogador §e" + target.getName() + "§c da screenshare.");
            target.sendMessage("§cVocê foi removido da screenshare!");
        } else {
            gamer.setInScreenshare(true);
            Gamer.update(gamer);

            target.teleport(Screenshare.getSs_world().getSpawnLocation());
            player.teleport(target.getLocation());

            player.sendMessage("§aVocê adicionou o jogador §e" + target.getName() + "§a na screenshare.");
            target.sendMessage("§eVocê foi adicionado na screenshare!");
        }

        return false;
    }
}
