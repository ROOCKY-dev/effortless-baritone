/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package baritone.api.command.helpers;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.command.manager.ICommandManager;
import baritone.api.utils.SettingsUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public class TabCompleteHelper {
    private Stream<String> stream;

    public TabCompleteHelper(String[] stringArray) {
        this.stream = Stream.of(stringArray);
    }

    public TabCompleteHelper(List<String> list) {
        this.stream = list.stream();
    }

    public TabCompleteHelper() {
        this.stream = Stream.empty();
    }

    public TabCompleteHelper append(Stream<String> stream) {
        this.stream = Stream.concat(this.stream, stream);
        return this;
    }

    public TabCompleteHelper append(String ... stringArray) {
        return this.append(Stream.of(stringArray));
    }

    public TabCompleteHelper append(Class<? extends Enum<?>> clazz) {
        return this.append(Stream.of(clazz.getEnumConstants()).map(Enum::name).map(String::toLowerCase));
    }

    public TabCompleteHelper prepend(Stream<String> stream) {
        this.stream = Stream.concat(stream, this.stream);
        return this;
    }

    public TabCompleteHelper prepend(String ... stringArray) {
        return this.prepend(Stream.of(stringArray));
    }

    public TabCompleteHelper prepend(Class<? extends Enum<?>> clazz) {
        return this.prepend(Stream.of(clazz.getEnumConstants()).map(Enum::name).map(String::toLowerCase));
    }

    public TabCompleteHelper map(Function<String, String> function) {
        this.stream = this.stream.map(function);
        return this;
    }

    public TabCompleteHelper filter(Predicate<String> predicate) {
        this.stream = this.stream.filter(predicate);
        return this;
    }

    public TabCompleteHelper sort(Comparator<String> comparator) {
        this.stream = this.stream.sorted(comparator);
        return this;
    }

    public TabCompleteHelper sortAlphabetically() {
        return this.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public TabCompleteHelper filterPrefix(String string) {
        return this.filter(string2 -> string2.toLowerCase(Locale.US).startsWith(string.toLowerCase(Locale.US)));
    }

    public TabCompleteHelper filterPrefixNamespaced(String string) {
        if ((string = ResourceLocation.tryParse((String)string)) == null) {
            this.stream = Stream.empty();
            return this;
        }
        return this.filterPrefix(string.toString());
    }

    public String[] build() {
        return (String[])this.stream.toArray(String[]::new);
    }

    public Stream<String> stream() {
        return this.stream;
    }

    public TabCompleteHelper addCommands(ICommandManager iCommandManager) {
        return this.append(iCommandManager.getRegistry().descendingStream().flatMap(iCommand -> iCommand.getNames().stream()).distinct());
    }

    public TabCompleteHelper addSettings() {
        return this.append(BaritoneAPI.getSettings().allSettings.stream().filter((? super T setting) -> !setting.isJavaOnly()).map(Settings.Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER));
    }

    public TabCompleteHelper addModifiedSettings() {
        return this.append(SettingsUtil.modifiedSettings(BaritoneAPI.getSettings()).stream().map(Settings.Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER));
    }

    public TabCompleteHelper addToggleableSettings() {
        return this.append(BaritoneAPI.getSettings().getAllValuesByType(Boolean.class).stream().map(Settings.Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER));
    }
}

