package com.stickms.messaging;

import com.stickms.messaging.commands.BroadcastCommand;
import com.stickms.messaging.commands.MessageCommand;
import com.stickms.messaging.commands.ReplyCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Messaging extends JavaPlugin implements Listener {
    private HashMap<UUID, UUID> recentMessages;

    @Override
    public void onEnable() {
        recentMessages = new HashMap<>();

        new MessageCommand(this);
        new ReplyCommand(this);
        new BroadcastCommand(this);

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public HashMap<UUID, UUID> getRecentMessages() {return recentMessages;}

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e){e.setMessage(ChatUtil.translateHexCode(e.getMessage()));}
}
