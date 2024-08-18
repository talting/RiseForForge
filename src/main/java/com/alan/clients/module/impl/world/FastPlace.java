package com.alan.clients.module.impl.world;

import com.alan.clients.event.RightClickEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.utility.Reflection;
import com.alan.clients.utility.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

public class FastPlace extends Module {
    public SliderSetting tickDelay;
    public ButtonSetting blocksOnly, pitchCheck;

    public FastPlace() {
        super("FastPlace", Module.category.world, 0);
        this.registerSetting(tickDelay = new SliderSetting("Tick delay", 1, 0, 3, 1));
        this.registerSetting(blocksOnly = new ButtonSetting("Blocks only", true));
        this.registerSetting(pitchCheck = new ButtonSetting("Pitch check", false));
    }

    @SubscribeEvent
    public void a(@NotNull PlayerTickEvent e) {
        if (e.phase == Phase.END) {
            if (ModuleManager.scaffold.stopFastPlace()) {
                return;
            }
            if (Utils.nullCheck() && mc.inGameHasFocus && Reflection.rightClickDelayTimerField != null) {
                if (blocksOnly.isToggled()) {
                    ItemStack item = SlotHandler.getHeldItem();
                    if (item == null || !(item.getItem() instanceof ItemBlock)) {
                        return;
                    }
                }

                try {
                    int c = (int) tickDelay.getInput();
                    if (c == 0) {
                        Reflection.rightClickDelayTimerField.set(mc, 0);
                    } else {
                        if (c == 4) {
                            return;
                        }

                        int d = Reflection.rightClickDelayTimerField.getInt(mc);
                        if (d == 4) {
                            Reflection.rightClickDelayTimerField.set(mc, c);
                        }
                    }
                } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(RightClickEvent event) {
        try {
            int c = (int) tickDelay.getInput();
            if (c == 0) {
                Reflection.rightClickDelayTimerField.set(mc, 0);
            }
        } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {
        }
    }
}
