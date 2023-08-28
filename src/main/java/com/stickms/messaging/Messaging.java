package com.stickms.messaging;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Messaging extends JavaPlugin {
    private HashMap<UUID, UUID> recentMessages;

    @Override
    public void onEnable() {
        recentMessages = new HashMap<>();
        new MessageCommand(this);
        new ReplyCommand(this);
    }

    public HashMap<UUID, UUID> getRecentMessages() {return recentMessages;}

    private static final Pattern COLOR_PATTERN = Pattern.compile("(#[A-Fa-f0-9]{6})");
    public static String translateHexCode(String string){
        Matcher matcher = COLOR_PATTERN.matcher(string);
        while (matcher.find()){
            string = string.replace(matcher.group(), net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }
        return string;
    }
}
