package com.stickms.messaging.commands;

import com.stickms.messaging.ChatUtil;
import com.stickms.messaging.CommandManager;
import com.stickms.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class BroadcastCommand extends CommandManager {
    private Messaging plugin;
    public BroadcastCommand(Messaging plugin){
        super("broadcast", new String[]{"bcast"},
                "Broadcasts a message to all players using a boss bar.",
                "/broadcast <message>",
                "messaging.broadcast");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("messaging.broadcast")){
            sender.sendMessage(ChatColor.RED + "You do not have permission to broadcast messages.");
            return;
        }
        if (Bukkit.getOnlinePlayers().size() <= 1){
            sender.sendMessage(ChatColor.RED + "There is no one else on the server.");
            return;
        }

        String message = ChatUtil.translateHexCode(ChatUtil.getMessage(args, 0));
        KeyedBossBar bossBar = Bukkit.createBossBar(
                new NamespacedKey(plugin, "broadcast"),
                "Broadcast: " + message, BarColor.YELLOW, BarStyle.SOLID);
        bossBar.setVisible(true);

        for (Player player : Bukkit.getOnlinePlayers()) bossBar.addPlayer(player);

        Bukkit.getScheduler().runTaskLater(plugin,
                ()->Bukkit.removeBossBar(new NamespacedKey(plugin, "broadcast"))
                , 200);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.singletonList("<message>");
    }
}
