/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.level.Level
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.IElytraProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ak
extends Command {
    public ak(a a2) {
        super(a2, "elytra");
    }

    @Override
    public final void execute(String object, IArgConsumer object2) {
        object = this.baritone.getCustomGoalProcess();
        IElytraProcess iElytraProcess = this.baritone.getElytraProcess();
        if (object2.hasExactlyOne() && object2.peekString().equals("supported")) {
            this.logDirect(iElytraProcess.isLoaded() ? "yes" : ak.a());
            return;
        }
        if (!iElytraProcess.isLoaded()) {
            throw new CommandInvalidStateException(ak.a());
        }
        if (!object2.hasAny()) {
            if (((Boolean)a.a().elytraTermsAccepted.value).booleanValue()) {
                if (this.a()) {
                    long l2;
                    object2 = this;
                    if (((Boolean)a.a().elytraPredictTerrain.value).booleanValue() && (l2 = ((Long)a.a().elytraNetherSeed.value).longValue()) != 146008555100680L && l2 != -4100785268875389365L) {
                        object2.logDirect(new Component[]{Component.literal((String)"It looks like you're on 2b2t, but elytraNetherSeed is incorrect.")});
                        object2.logDirect(ak.a());
                    }
                }
            } else {
                object2 = this;
                MutableComponent mutableComponent = Component.literal((String)"");
                mutableComponent.append("To disable this message, enable the setting elytraTermsAccepted\n");
                mutableComponent.append("Baritone Elytra is an experimental feature. It is only intended for long distance travel in the Nether using fireworks for vanilla boost. It will not work with any other mods (\"hacks\") for non-vanilla boost. ");
                MutableComponent mutableComponent2 = Component.literal((String)"If you want Baritone to attempt to take off from the ground for you, you can enable the elytraAutoJump setting (not advisable on laggy servers!). ");
                mutableComponent2.setStyle(mutableComponent2.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)((String)a.a().prefix.value + "set elytraAutoJump true")))));
                mutableComponent.append((Component)mutableComponent2);
                MutableComponent mutableComponent3 = Component.literal((String)"If you want Baritone to go slower, enable the elytraConserveFireworks setting and/or decrease the elytraFireworkSpeed setting. ");
                mutableComponent3.setStyle(mutableComponent3.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)((String)a.a().prefix.value + "set elytraConserveFireworks true\n" + (String)a.a().prefix.value + "set elytraFireworkSpeed 0.6\n(the 0.6 number is just an example, tweak to your liking)")))));
                mutableComponent.append((Component)mutableComponent3);
                mutableComponent3 = Component.literal((String)"Baritone Elytra ");
                MutableComponent mutableComponent4 = Component.literal((String)"wants to know the seed");
                mutableComponent4.setStyle(mutableComponent4.getStyle().withColor(ChatFormatting.RED).withUnderlined(Boolean.TRUE).withBold(Boolean.TRUE));
                mutableComponent3.append((Component)mutableComponent4);
                mutableComponent3.append(" of the world you are in. If it doesn't have the correct seed, it will frequently backtrack. It uses the seed to generate terrain far beyond what you can see, since terrain obstacles in the Nether can be much larger than your render distance. ");
                mutableComponent.append((Component)mutableComponent3);
                mutableComponent.append("\n");
                if (((ak)object2).a()) {
                    mutableComponent3 = Component.literal((String)"It looks like you're on 2b2t. ");
                    mutableComponent3.append(ak.a());
                    if (!((Boolean)a.a().elytraPredictTerrain.value).booleanValue()) {
                        mutableComponent3.append((String)a.a().prefix.value + "elytraPredictTerrain is currently disabled. ");
                    } else if ((Long)a.a().elytraNetherSeed.value == 146008555100680L) {
                        mutableComponent3.append("You are using the newer seed. ");
                    } else if ((Long)a.a().elytraNetherSeed.value == -4100785268875389365L) {
                        mutableComponent3.append("You are using the older seed. ");
                    } else {
                        mutableComponent3.append("Defaulting to the newer seed. ");
                        a.a().elytraNetherSeed.value = 146008555100680L;
                    }
                    mutableComponent.append((Component)mutableComponent3);
                } else if ((Long)a.a().elytraNetherSeed.value == 146008555100680L) {
                    mutableComponent3 = Component.literal((String)("Baritone doesn't know the seed of your world. Set it with: " + (String)a.a().prefix.value + "set elytraNetherSeed seedgoeshere\n"));
                    mutableComponent3.append("For the time being, elytraPredictTerrain is defaulting to false since the seed is unknown.");
                    mutableComponent.append((Component)mutableComponent3);
                    a.a().elytraPredictTerrain.value = Boolean.FALSE;
                } else if (((Boolean)a.a().elytraPredictTerrain.value).booleanValue()) {
                    mutableComponent3 = Component.literal((String)("Baritone Elytra is predicting terrain assuming that " + String.valueOf(a.a().elytraNetherSeed.value) + " is the correct seed. Change that with " + (String)a.a().prefix.value + "set elytraNetherSeed seedgoeshere, or disable it with " + (String)a.a().prefix.value + "set elytraPredictTerrain false"));
                    mutableComponent.append((Component)mutableComponent3);
                } else {
                    mutableComponent3 = Component.literal((String)("Baritone Elytra is not predicting terrain. If you don't know the seed, this is the correct thing to do. If you do know the seed, input it with " + (String)a.a().prefix.value + "set elytraNetherSeed seedgoeshere, and then enable it with " + (String)a.a().prefix.value + "set elytraPredictTerrain true"));
                    mutableComponent.append((Component)mutableComponent3);
                }
                object2.logDirect(new Component[]{mutableComponent});
            }
            if ((object = object.mostRecentGoal()) == null) {
                throw new CommandInvalidStateException("No goal has been set");
            }
            if (this.ctx.world().dimension() != Level.NETHER) {
                throw new CommandInvalidStateException("Only works in the nether");
            }
            try {
                iElytraProcess.pathTo((Goal)object);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new CommandInvalidStateException(illegalArgumentException.getMessage());
            }
        }
        switch (object2.getString()) {
            case "reset": {
                iElytraProcess.resetState();
                this.logDirect("Reset state but still flying to same goal");
                return;
            }
            case "repack": {
                iElytraProcess.repackChunks();
                this.logDirect("Queued all loaded chunks for repacking");
                return;
            }
        }
        throw new CommandInvalidStateException("Invalid action");
    }

    private static Component a() {
        MutableComponent mutableComponent = Component.literal((String)"");
        mutableComponent.append("Within a few hundred blocks of spawn/axis/highways/etc, the terrain is too fragmented to be predictable. Baritone Elytra will still work, just with backtracking. ");
        mutableComponent.append("However, once you get more than a few thousand blocks out, you should try ");
        MutableComponent mutableComponent2 = Component.literal((String)"the older seed (click here)");
        mutableComponent2.setStyle(mutableComponent2.getStyle().withUnderlined(Boolean.TRUE).withBold(Boolean.TRUE).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)((String)a.a().prefix.value + "set elytraNetherSeed -4100785268875389365")))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set elytraNetherSeed -4100785268875389365")));
        mutableComponent.append((Component)mutableComponent2);
        mutableComponent.append(". Once you're further out into newer terrain generation (this includes everything up through 1.12), you should try ");
        mutableComponent2 = Component.literal((String)"the newer seed (click here)");
        mutableComponent2.setStyle(mutableComponent2.getStyle().withUnderlined(Boolean.TRUE).withBold(Boolean.TRUE).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)((String)a.a().prefix.value + "set elytraNetherSeed 146008555100680")))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set elytraNetherSeed 146008555100680")));
        mutableComponent.append((Component)mutableComponent2);
        mutableComponent.append(". Once you get into 1.19 terrain, the terrain becomes unpredictable again, due to custom non-vanilla generation, and you should set #elytraPredictTerrain to false. ");
        return mutableComponent;
    }

    private boolean a() {
        ServerData serverData = this.ctx.minecraft().getCurrentServer();
        return serverData != null && serverData.ip.toLowerCase().contains("2b2t.org");
    }

    @Override
    public final Stream<String> tabComplete(String object, IArgConsumer iArgConsumer) {
        object = new TabCompleteHelper();
        if (iArgConsumer.hasExactlyOne()) {
            ((TabCompleteHelper)object).append("reset", "repack", "supported");
        }
        return ((TabCompleteHelper)object).filterPrefix(iArgConsumer.getString()).stream();
    }

    @Override
    public final String getShortDesc() {
        return "elytra time";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The elytra command tells baritone to, in the nether, automatically fly to the current goal.", "", "Usage:", "> elytra - fly to the current goal", "> elytra reset - Resets the state of the process, but will try to keep flying to the same goal.", "> elytra repack - Queues all of the chunks in render distance to be given to the native library.", "> elytra supported - Tells you if baritone ships a native library that is compatible with your PC.");
    }

    private static String a() {
        String string = System.getProperty("os.arch");
        String string2 = System.getProperty("os.name");
        return String.format("Failed loading native library. Your CPU is %s and your operating system is %s. Supported architectures are 64 bit x86, and 64 bit ARM. Supported operating systems are Windows, Linux, and Mac", string, string2);
    }
}

