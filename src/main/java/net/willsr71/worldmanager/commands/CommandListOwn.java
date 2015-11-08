package net.willsr71.worldmanager.commands;

import net.willsr71.worldmanager.DimensionData;
import net.willsr71.worldmanager.WorldManager;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandListOwn {
    private WorldManager plugin;

    public CommandListOwn(WorldManager plugin) {
        this.plugin = plugin;
    }

    public void run(CommandSender cs, String[] args) {
        HashMap<String, DimensionData> dimensions = (HashMap) plugin.dimensions.clone();

        String owners = "&7Owner:";
        String members = "&7Member:";
        for (String dim : dimensions.keySet()) {
            DimensionData dimData = dimensions.get(dim);
            if (dimData.isOwner(cs.getName())) owners = (owners + " &6" + dimData.getName() + "&7,").trim();
            if (dimData.isMember(cs.getName())) members = (members + " &6" + dimData.getName() + "&7,").trim();
        }
        if (owners.equals("&7Owner:")) owners = owners + " &6None";
        else owners = owners.substring(0, owners.length() - 3);
        if (members.equals("&7Member:")) members = members + " &6None";
        else members = members.substring(0, members.length() - 3);

        cs.sendMessage(plugin.miscUtils.parse("&7Dimensions that you are part of:"));
        cs.sendMessage(plugin.miscUtils.parse(owners));
        cs.sendMessage(plugin.miscUtils.parse(members));
    }
}