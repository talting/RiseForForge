package com.alan.clients.module.impl.combat;

import com.alan.clients.event.RightClickEvent;
import com.alan.clients.module.impl.combat.autoclicker.DragClickAutoClicker;
import com.alan.clients.module.impl.combat.autoclicker.IAutoClicker;
import com.alan.clients.module.impl.combat.autoclicker.NormalAutoClicker;
import com.alan.clients.module.impl.combat.autoclicker.RecordAutoClicker;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.module.setting.impl.ModeValue;
import com.alan.clients.utility.BlockUtils;
import com.alan.clients.utility.ContainerUtils;
import com.alan.clients.utility.CoolDown;
import com.alan.clients.utility.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RightClicker extends IAutoClicker {
    private final ModeValue mode;
    private final ButtonSetting onlyBlocks;
    private final ModeSetting clickSound;

    private final CoolDown coolDown = new CoolDown(100);

    public RightClicker() {
        super("RightClicker", category.combat);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new NormalAutoClicker("Normal", this, false, false))
                .add(new DragClickAutoClicker("Drag Click", this, false, false))
                .add(new RecordAutoClicker("Record", this, false, false))
                .setDefaultValue("Normal")
        );
        this.registerSetting(onlyBlocks = new ButtonSetting("Only blocks", false));
        this.registerSetting(clickSound = new ModeSetting("Click sound", new String[]{"None", "Standard", "Double", "Alan"}, 0));
    }

    @Override
    public void onEnable() {
        mode.enable();
    }

    @Override
    public void onDisable() {
        mode.disable();
    }

    @SubscribeEvent
    public void onClick(RightClickEvent event) {
        coolDown.start();

        if (clickSound.getInput() != 0) {
            mc.thePlayer.playSound(
                    "rise:click." + clickSound.getOptions()[(int) clickSound.getInput()].toLowerCase()
                    , 1, 1
            );
        }
    }

    @Override
    public boolean click() {
        ItemStack item = SlotHandler.getHeldItem();
        if (onlyBlocks.isToggled() && (item == null
                || !(item.getItem() instanceof ItemBlock)
                || !ContainerUtils.canBePlaced(((ItemBlock) item.getItem())))) {
            return false;
        }

        Utils.sendClick(1, true);
        return true;
    }
}
