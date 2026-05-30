/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.session.config.ConstraintConfig;

public class ConstraintConfigSerializer
implements NetByteBufSerializer<ConstraintConfig> {
    @Override
    public ConstraintConfig read(NetByteBuf byteBuf) {
        return new ConstraintConfig(byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(rec$ -> rec$.readBoolean()), byteBuf.readNullable(NetByteBuf::readVarInt), byteBuf.readNullable(NetByteBuf::readVarInt), byteBuf.readNullable(NetByteBuf::readVarInt), byteBuf.readNullable(NetByteBuf::readVarInt), byteBuf.readNullable(NetByteBuf::readVarInt), byteBuf.readNullable(buffer1 -> buffer1.readList(NetByteBuf::readResourceLocation)), byteBuf.readNullable(buffer1 -> buffer1.readList(NetByteBuf::readResourceLocation)));
    }

    @Override
    public void write(NetByteBuf byteBuf, ConstraintConfig constraintConfig) {
        byteBuf.writeNullable(constraintConfig.useCommands(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.allowUseMod(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.allowBreakBlocks(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.allowPlaceBlocks(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.allowInteractBlocks(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.allowCopyPasteStructures(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.useProperToolsOnly(), (rec$, x$0) -> rec$.writeBoolean((boolean)x$0));
        byteBuf.writeNullable(constraintConfig.maxReachDistance(), NetByteBuf::writeVarInt);
        byteBuf.writeNullable(constraintConfig.maxBlockBreakVolume(), NetByteBuf::writeVarInt);
        byteBuf.writeNullable(constraintConfig.maxBlockPlaceVolume(), NetByteBuf::writeVarInt);
        byteBuf.writeNullable(constraintConfig.maxBlockInteractVolume(), NetByteBuf::writeVarInt);
        byteBuf.writeNullable(constraintConfig.maxStructureCopyPasteVolume(), NetByteBuf::writeVarInt);
        byteBuf.writeNullable(constraintConfig.whitelistedItems(), (buffer1, list) -> buffer1.writeList(list, NetByteBuf::writeResourceLocation));
        byteBuf.writeNullable(constraintConfig.blacklistedItems(), (buffer1, list) -> buffer1.writeList(list, NetByteBuf::writeResourceLocation));
    }
}
