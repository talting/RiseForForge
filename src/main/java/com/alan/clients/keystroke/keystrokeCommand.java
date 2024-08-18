package com.alan.clients.keystroke;

import com.alan.clients.Rise;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class keystrokeCommand extends CommandBase {
    public String getCommandName() {
        return "rise";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        Rise.toggleKeyStrokeConfigGui();
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/rise";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
