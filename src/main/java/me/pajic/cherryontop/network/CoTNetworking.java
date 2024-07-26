package me.pajic.cherryontop.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CoTNetworking {

    public static final ResourceLocation WORMHOLE_TELEPORT = ResourceLocation.parse("cherry-on-top:wormhole_teleport");

    public record C2SWormholeTeleportPayload(UUID playerToTpTo) implements CustomPacketPayload {
        public static final Type<C2SWormholeTeleportPayload> TYPE = new Type<>(WORMHOLE_TELEPORT);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SWormholeTeleportPayload> CODEC =
                StreamCodec.of(C2SWormholeTeleportPayload::encode, C2SWormholeTeleportPayload::decode);

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        private static void encode(FriendlyByteBuf buf, C2SWormholeTeleportPayload payload) {
            buf.writeUUID(payload.playerToTpTo);
        }

        private static C2SWormholeTeleportPayload decode(FriendlyByteBuf buf) {
            return new C2SWormholeTeleportPayload(buf.readUUID());
        }
    }

    public static void initNetworking() {
        PayloadTypeRegistry.playC2S().register(C2SWormholeTeleportPayload.TYPE, C2SWormholeTeleportPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(C2SWormholeTeleportPayload.TYPE, (payload, context) -> context.server().execute(() -> {
            Player playerToTpTo = context.player().level().getPlayerByUUID(payload.playerToTpTo);
            context.player().teleportTo(playerToTpTo.getX(), playerToTpTo.getY(), playerToTpTo.getZ());
        }));
    }
}
