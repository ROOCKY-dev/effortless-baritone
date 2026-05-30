/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 */
package baritone;

import baritone.a;
import baritone.api.Settings;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeFile;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.helpers.Paginator;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.utils.SettingsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

public final class bm
extends Command {
    public bm(a a2) {
        super(a2, "set", "setting", "settings");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        Object object2 = object = iArgConsumer.hasAny() ? iArgConsumer.getString().toLowerCase(Locale.US) : "list";
        if (Arrays.asList("s", "save").contains(object)) {
            SettingsUtil.save(a.a());
            this.logDirect("Settings saved");
            return;
        }
        if (Arrays.asList("load", "ld").contains(object)) {
            String string = "settings.txt";
            if (iArgConsumer.hasAny()) {
                string = iArgConsumer.getString();
            }
            SettingsUtil.modifiedSettings(a.a()).forEach(Settings.Setting::reset);
            SettingsUtil.readAndApply(a.a(), string);
            this.logDirect("Settings reloaded from " + string);
            return;
        }
        boolean bl2 = Arrays.asList("m", "mod", "modified").contains(object);
        boolean bl3 = Arrays.asList("all", "l", "list").contains(object);
        if (bl2 || bl3) {
            String string = iArgConsumer.hasAny() && iArgConsumer.peekAsOrNull(Integer.class) == null ? iArgConsumer.getString() : "";
            iArgConsumer.requireMax(1);
            List list = (bl2 ? SettingsUtil.modifiedSettings(a.a()) : a.a().allSettings).stream().filter(setting -> !setting.isJavaOnly()).filter(setting -> setting.getName().toLowerCase(Locale.US).contains(string.toLowerCase(Locale.US))).sorted((setting, setting2) -> String.CASE_INSENSITIVE_ORDER.compare(setting.getName(), setting2.getName())).collect(Collectors.toList());
            Paginator.paginate(iArgConsumer, new Paginator(list), () -> {
                String string2;
                Object[] objectArray;
                String string3;
                if (!string.isEmpty()) {
                    string3 = "All %ssettings containing the string '%s':";
                    Object[] objectArray2 = new Object[2];
                    objectArray2[0] = bl2 ? "modified " : "";
                    v2 = objectArray2;
                    objectArray = objectArray2;
                    v4 = 1;
                    string2 = string;
                } else {
                    string3 = "All %ssettings:";
                    Object[] objectArray3 = new Object[1];
                    v2 = objectArray3;
                    objectArray = objectArray3;
                    v4 = 0;
                    string2 = bl2 ? "modified " : "";
                }
                v2[v4] = string2;
                this.logDirect(String.format(string3, objectArray));
            }, setting -> {
                MutableComponent mutableComponent = Component.literal((String)String.format(" (%s)", SettingsUtil.settingTypeToString(setting)));
                mutableComponent.setStyle(mutableComponent.getStyle().withColor(ChatFormatting.DARK_GRAY));
                MutableComponent mutableComponent2 = Component.literal((String)"");
                mutableComponent2.setStyle(mutableComponent2.getStyle().withColor(ChatFormatting.GRAY));
                mutableComponent2.append(setting.getName());
                mutableComponent2.append(String.format("\nType: %s", SettingsUtil.settingTypeToString(setting)));
                mutableComponent2.append(String.format("\n\nValue:\n%s", SettingsUtil.settingValueToString(setting)));
                mutableComponent2.append(String.format("\n\nDefault Value:\n%s", SettingsUtil.settingDefaultToString(setting)));
                String string = (String)a.a().prefix.value + String.format("set %s ", setting.getName());
                setting = Component.literal((String)setting.getName());
                setting.setStyle(setting.getStyle().withColor(ChatFormatting.GRAY));
                setting.append((Component)mutableComponent);
                Settings.Setting setting2 = setting;
                setting2.setStyle(setting2.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)mutableComponent2)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, string)));
                return setting;
            }, IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set " + (String)object + " " + string);
            return;
        }
        iArgConsumer.requireMax(1);
        bl3 = ((String)object).equalsIgnoreCase("reset");
        boolean bl4 = ((String)object).equalsIgnoreCase("toggle");
        boolean bl5 = bl2 = bl3 || bl4;
        if (bl3) {
            if (!iArgConsumer.hasAny()) {
                this.logDirect("Please specify 'all' as an argument to reset to confirm you'd really like to do this");
                this.logDirect("ALL settings will be reset. Use the 'set modified' or 'modified' commands to see what will be reset.");
                this.logDirect("Specify a setting name instead of 'all' to only reset one setting");
            } else if (iArgConsumer.peekString().equalsIgnoreCase("all")) {
                SettingsUtil.modifiedSettings(a.a()).forEach(Settings.Setting::reset);
                this.logDirect("All settings have been reset to their default values");
                SettingsUtil.save(a.a());
                return;
            }
        }
        if (bl4) {
            iArgConsumer.requireMin(1);
        }
        Object object3 = bl2 ? iArgConsumer.getString() : object;
        object3 = a.a().allSettings.stream().filter(arg_0 -> bm.a((String)object3, arg_0)).findFirst().orElse(null);
        if (object3 == null) {
            throw new CommandInvalidTypeException(iArgConsumer.consumed(), "a valid setting");
        }
        if (((Settings.Setting)object3).isJavaOnly()) {
            throw new CommandInvalidStateException(String.format("Setting %s can only be used via the api.", ((Settings.Setting)object3).getName()));
        }
        if (!bl2 && !iArgConsumer.hasAny()) {
            this.logDirect(String.format("Value of setting %s:", ((Settings.Setting)object3).getName()));
            this.logDirect(SettingsUtil.settingValueToString((Settings.Setting)object3));
        } else {
            String string;
            String string2 = SettingsUtil.settingValueToString((Settings.Setting)object3);
            if (bl3) {
                ((Settings.Setting)object3).reset();
            } else if (bl4) {
                if (((Settings.Setting)object3).getValueClass() != Boolean.class) {
                    throw new CommandInvalidTypeException(iArgConsumer.consumed(), "a toggleable setting", "some other setting");
                }
                ((Settings.Setting)object3).value = (Boolean)((Settings.Setting)object3).value ^ true;
                this.logDirect(String.format("Toggled setting %s to %s", ((Settings.Setting)object3).getName(), Boolean.toString((Boolean)((Settings.Setting)object3).value)));
            } else {
                string = iArgConsumer.getString();
                try {
                    SettingsUtil.parseAndApply(a.a(), (String)object, string);
                }
                catch (Throwable throwable) {
                    object = throwable;
                    throwable.printStackTrace();
                    throw new CommandInvalidTypeException(iArgConsumer.consumed(), "a valid value", (Throwable)object);
                }
            }
            if (!bl4) {
                this.logDirect(String.format("Successfully %s %s to %s", bl3 ? "reset" : "set", ((Settings.Setting)object3).getName(), SettingsUtil.settingValueToString((Settings.Setting)object3)));
            }
            string = Component.literal((String)String.format("Old value: %s", string2));
            string.setStyle(string.getStyle().withColor(ChatFormatting.GRAY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)"Click to set the setting back to this value"))).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, IBaritoneChatControl.FORCE_COMMAND_PREFIX + String.format("set %s %s", ((Settings.Setting)object3).getName(), string2))));
            this.logDirect(new Component[]{string});
            if (((Settings.Setting)object3).getName().equals("chatControl") && !((Boolean)((Settings.Setting)object3).value).booleanValue() && !((Boolean)a.a().chatControlAnyway.value).booleanValue() || ((Settings.Setting)object3).getName().equals("chatControlAnyway") && !((Boolean)((Settings.Setting)object3).value).booleanValue() && !((Boolean)a.a().chatControl.value).booleanValue()) {
                this.logDirect("Warning: Chat commands will no longer work. If you want to revert this change, use prefix control (if enabled) or click the old value listed above.", ChatFormatting.RED);
            } else if (((Settings.Setting)object3).getName().equals("prefixControl") && !((Boolean)((Settings.Setting)object3).value).booleanValue()) {
                this.logDirect("Warning: Prefixed commands will no longer work. If you want to revert this change, use chat control (if enabled) or click the old value listed above.", ChatFormatting.RED);
            }
        }
        SettingsUtil.save(a.a());
    }

    @Override
    public final Stream<String> tabComplete(String object, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasAny()) {
            object = iArgConsumer.getString();
            if (iArgConsumer.hasExactlyOne() && !Arrays.asList("s", "save").contains(iArgConsumer.peekString().toLowerCase(Locale.US))) {
                if (((String)object).equalsIgnoreCase("reset")) {
                    return new TabCompleteHelper().addModifiedSettings().prepend("all").filterPrefix(iArgConsumer.getString()).stream();
                }
                if (((String)object).equalsIgnoreCase("toggle")) {
                    return new TabCompleteHelper().addToggleableSettings().filterPrefix(iArgConsumer.getString()).stream();
                }
                if (Arrays.asList("ld", "load").contains(((String)object).toLowerCase(Locale.US))) {
                    return RelativeFile.tabComplete(iArgConsumer, Minecraft.getInstance().gameDirectory.toPath().resolve("baritone").toFile());
                }
                if ((object = a.a().byLowerName.get(((String)object).toLowerCase(Locale.US))) != null) {
                    if (((Settings.Setting)object).getType() == Boolean.class) {
                        TabCompleteHelper tabCompleteHelper = new TabCompleteHelper();
                        if (((Boolean)((Settings.Setting)object).value).booleanValue()) {
                            tabCompleteHelper.append("true", "false");
                        } else {
                            tabCompleteHelper.append("false", "true");
                        }
                        return tabCompleteHelper.filterPrefix(iArgConsumer.getString()).stream();
                    }
                    return Stream.of(SettingsUtil.settingValueToString((Settings.Setting)object));
                }
            } else if (!iArgConsumer.hasAny()) {
                return new TabCompleteHelper().addSettings().sortAlphabetically().prepend("list", "modified", "reset", "toggle", "save", "load").filterPrefix((String)object).stream();
            }
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "View or change settings";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Using the set command, you can manage all of Baritone's settings. Almost every aspect is controlled by these settings - go wild!", "", "Usage:", "> set - Same as `set list`", "> set list [page] - View all settings", "> set modified [page] - View modified settings", "> set <setting> - View the current value of a setting", "> set <setting> <value> - Set the value of a setting", "> set reset all - Reset ALL SETTINGS to their defaults", "> set reset <setting> - Reset a setting to its default", "> set toggle <setting> - Toggle a boolean setting", "> set save - Save all settings (this is automatic tho)", "> set load - Load settings from settings.txt", "> set load [filename] - Load settings from another file in your minecraft/baritone");
    }

    private static /* synthetic */ boolean a(String string, Settings.Setting setting) {
        return setting.getName().equalsIgnoreCase(string);
    }
}

