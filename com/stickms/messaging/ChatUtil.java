package com.stickms.messaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    private static final Pattern COLOR_PATTERN = Pattern.compile("(#[A-Fa-f0-9]{6})");
    public static String translateHexCode(String string){
        Matcher matcher = COLOR_PATTERN.matcher(string);
        while (matcher.find()){
            string = string.replace(matcher.group(), net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }
        return string;
    }

    public static String getMessage(String[] args, int index){
        StringBuilder builder = new StringBuilder();
        for (int i=index;i<args.length;i++) builder.append(args[i]);
        return builder.toString();
    }
}
