package com.stickms.messaging.commands;

import com.stickms.messaging.ChatUtil;
import com.stickms.messaging.CommandManager;
import com.stickms.messaging.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReplyCommand extends CommandManager {
    private Messaging plugin;
    public ReplyCommand(Messaging plugin){
        super("reply", new String[]{},
                "Replies to the last person you privately messaged.",
                "/reply <message>");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }
        Player player = (Player) sender;
        if (args.length < 1){
            player.sendMessage(ChatColor.RED + "Please use the format '/reply <message>'.");
            return;
        }
        if (!plugin.getRecentMessages().containsKey(player.getUniqueId())){
            player.sendMessage(ChatColor.RED + "You have not sent any message to anyone yet.");
            return;
        }
        Player target = Bukkit.getPlayer(plugin.getRecentMessages().get(player.getUniqueId()));
        if (target == null){
            player.sendMessage(ChatColor.RED + "The player you have message has logged off.");
            return;
        }

        String message = ChatUtil.translateHexCode(ChatUtil.getMessage(args, 0));

        if (target.getUniqueId().equals(player.getUniqueId())){
            player.sendMessage("You -> You: " + message);
        }else {
            target.sendMessage(player.getName() + " -> You: " + message);
            player.sendMessage("You -> " + target.getName() + ": " + message);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.singletonList("<message>");
    }
}
