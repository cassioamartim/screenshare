package com.github.cassioamartim.gamer;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Gamer {

    private static final Set<Gamer> gamers = new LinkedHashSet<>();

    UUID uniqueId;
    String name;

    @Setter
    boolean inScreenshare = false;

    public Gamer(Player player) {
        uniqueId = player.getUniqueId();
        name = player.getName();

        if (get(player.getName()) == null)
            gamers.add(this);
    }

    public static Gamer get(@NonNull String name) {
        return gamers.stream().filter(gamer -> gamer.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static void remove(@NonNull String name) {
        gamers.removeIf(gamer -> gamer.getName().equalsIgnoreCase(name));
    }

    public static void update(@NonNull Gamer gamer) {
        remove(gamer.getName());
        gamers.add(gamer);
    }
}
