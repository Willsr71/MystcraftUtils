package net.willsr71.mystcraftutils.commands;

import net.willsr71.mystcraftutils.MystcraftUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelete {
    private MystcraftUtils plugin;

    public CommandDelete(MystcraftUtils plugin){
        this.plugin = plugin;
    }

    public void run(CommandSender cs, String[] args){
        if(!(cs instanceof Player)){
            cs.sendMessage(plugin.chatUtils.getString("noConsoleMessage"));
            return;
        }
        Player player = (Player) cs;
        if(MystcraftUtils.instance.config.getStringList("blacklistedDimensions").contains(player.getWorld().getName())){
            player.sendMessage(plugin.chatUtils.getString("blacklistMessage"));
            return;
        }

        String dimension = player.getWorld().getName();

        if(!MystcraftUtils.instance.dimensions.containsKey(dimension)){
            player.sendMessage(plugin.chatUtils.replaceDim(plugin.chatUtils.getString("dimNotRegisteredMessage"), dimension));
            return;
        }

        MystcraftUtils.instance.dimensions.remove(dimension);
        MystcraftUtils.instance.save();

        player.sendMessage(plugin.chatUtils.replaceDim(plugin.chatUtils.getString("deleteMessage"), dimension));
    }
}
