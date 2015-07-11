package net.willsr71.mystcraftutils.commands;

import net.willsr71.mystcraftutils.DimensionData;
import net.willsr71.mystcraftutils.MystcraftUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandClaim {
    private MystcraftUtils plugin;

    public CommandClaim(MystcraftUtils plugin){
        this.plugin = plugin;
    }

    public void run(CommandSender cs, String[] args){
        if(!(cs instanceof Player)){
            cs.sendMessage(plugin.chatUtils.getString("noConsoleMessage"));
            return;
        }
        Player player = (Player) cs;
        String dimension = player.getWorld().getName();
        if(MystcraftUtils.instance.config.getStringList("blacklistedDimensions").contains(dimension)){
            player.sendMessage(plugin.chatUtils.getString("blacklistMessage"));
            return;
        }
        if(MystcraftUtils.instance.dimensions.containsKey(dimension)){
            DimensionData dimData = MystcraftUtils.instance.dimensions.get(dimension);
            Player owner = Bukkit.getPlayer(dimData.getOwners().get(0));
            player.sendMessage(plugin.chatUtils.replaceDim(plugin.chatUtils.replacePlayer(plugin.chatUtils.getString("alreadyClaimedMessage"), owner.getName()), dimension));
            return;
        }

        List<String> owners = new ArrayList<>();
        List<String> members = new ArrayList<>();
        owners.add(player.getName());

        MystcraftUtils.instance.dimensions.put(dimension, new DimensionData(dimension, owners, members));
        MystcraftUtils.instance.save();

        plugin.commandDispatcher.sendFromConfig("onClaim", player.getName(), dimension);

        cs.sendMessage(plugin.chatUtils.replaceDim(plugin.chatUtils.getString("claimMessage"), dimension));
    }
}