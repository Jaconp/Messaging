package com.stickms.messaging.commands;

import com.stickms.messaging.ChatUtil;
import com.stickms.messaging.CommandManager;
import com.stickms.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageCommand extends CommandManager {
    private Messaging plugin;
    public MessageCommand(Messaging plugin){
        super("message", new String[]{"msg", "dm", "pm"},
                "Lets you send a private message to someone.",
                "/message <player> <message>");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }
        Player player = (Player) sender;
        if (args.length < 2){
            player.sendMessage(ChatColor.RED + "Please use the format '/message <player> <message>'.");
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            player.sendMessage(ChatColor.RED + "The player you specified is not online.");
            return;
        }

        String message = ChatUtil.translateHexCode(ChatUtil.getMessage(args, 1));

        if (target.getUniqueId().equals(player.getUniqueId())){
            player.sendMessage("You -> You: " + message);
        }else {
            target.sendMessage(player.getName() + " -> You: " + message);
            player.sendMessage("You -> " + target.getName() + ": " + message);
        }
        plugin.getRecentMessages().put(player.getUniqueId(), target.getUniqueId());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 1){
            for (Player player : Bukkit.getOnlinePlayers()) results.add(player.getName());
            return StringUtil.copyPartialMatches(args[0], results, new ArrayList<>());
        }else {
            results.add("<message>");
            return results;
        }
    }
}
