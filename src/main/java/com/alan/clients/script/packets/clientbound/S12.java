package com.alan.clients.script.packets.clientbound;

import com.alan.clients.script.classes.Vec3;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class S12 extends SPacket {
    public int entityId;
    public Vec3 motion;
    public S12(S12PacketEntityVelocity packet) {
        super(packet);
        this.entityId = packet.getEntityID();
        this.motion = new Vec3(packet.getMotionX(), packet.getMotionY(), packet.getMotionZ());
    }
}
