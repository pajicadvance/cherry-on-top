package me.pajic.cherryontop.network;

import me.pajic.cherryontop.util.CoTUtil;
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
    public static final ResourceLocation OPEN_ENDER_CONTAINER = ResourceLocation.parse("cherry-on-top:open_ender_container");

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

    public record C2SOpenEnderContainerPayload() implements CustomPacketPayload {
        public static final Type<C2SOpenEnderContainerPayload> TYPE = new Type<>(OPEN_ENDER_CONTAINER);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SOpenEnderContainerPayload> CODEC =
                StreamCodec.of(C2SOpenEnderContainerPayload::encode, C2SOpenEnderContainerPayload::decode);

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        private static void encode(FriendlyByteBuf buf, C2SOpenEnderContainerPayload payload) {
        }

        private static C2SOpenEnderContainerPayload decode(FriendlyByteBuf buf) {
            return new C2SOpenEnderContainerPayload();
        }
    }

    public static void initNetworking() {
        PayloadTypeRegistry.playC2S().register(C2SWormholeTeleportPayload.TYPE, C2SWormholeTeleportPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(C2SOpenEnderContainerPayload.TYPE, C2SOpenEnderContainerPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(C2SWormholeTeleportPayload.TYPE, (payload, context) -> context.server().execute(() -> {
            Player playerToTpTo = context.player().level().getPlayerByUUID(payload.playerToTpTo);
            context.player().teleportTo(playerToTpTo.getX(), playerToTpTo.getY(), playerToTpTo.getZ());
        }));

        ServerPlayNetworking.registerGlobalReceiver(C2SOpenEnderContainerPayload.TYPE, (payload, context) -> context.server().execute(() -> {
            System.out.println("packet received");
            CoTUtil.openEnderBackpack(context.player());
        }));
    }
}
