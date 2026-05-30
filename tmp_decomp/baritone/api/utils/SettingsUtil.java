/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 */
package baritone.api.utils;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.utils.BlockUtils;
import baritone.api.utils.Helper;
import baritone.api.utils.TypeUtils;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class SettingsUtil {
    public static final String SETTINGS_DEFAULT_NAME = "settings.txt";
    private static final Pattern SETTING_PATTERN = Pattern.compile("^(?<setting>[^ ]+) +(?<value>.+)");

    private static boolean isComment(String string) {
        return string.startsWith("#") || string.startsWith("//");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void forEachLine(Path object, Consumer<String> consumer) {
        object = Files.newBufferedReader((Path)object);
        try {
            String string;
            while ((string = ((BufferedReader)object).readLine()) != null) {
                if (string.isEmpty() || SettingsUtil.isComment(string)) continue;
                consumer.accept(string);
            }
            if (object == null) return;
        }
        catch (Throwable throwable) {
            if (object == null) throw throwable;
            try {
                ((BufferedReader)object).close();
                throw throwable;
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        ((BufferedReader)object).close();
    }

    public static void readAndApply(Settings settings, String string2) {
        try {
            SettingsUtil.forEachLine(SettingsUtil.settingsByName(string2), string -> {
                Object object = SETTING_PATTERN.matcher((CharSequence)string);
                if (!((Matcher)object).matches()) {
                    Helper.HELPER.logDirect("Invalid syntax in setting file: " + string);
                    return;
                }
                String string2 = ((Matcher)object).group("setting").toLowerCase();
                object = ((Matcher)object).group("value");
                if ("allowjumpat256".equals(string2)) {
                    string2 = "allowjumpatbuildlimit";
                }
                try {
                    SettingsUtil.parseAndApply(settings, string2, (String)object);
                    return;
                }
                catch (Exception exception) {
                    Helper.HELPER.logDirect("Unable to parse line " + string);
                    exception.printStackTrace();
                    return;
                }
            });
            return;
        }
        catch (NoSuchFileException noSuchFileException) {
            Helper.HELPER.logDirect("Baritone settings file not found, resetting.");
            return;
        }
        catch (Exception exception) {
            Helper.HELPER.logDirect("Exception while reading Baritone settings, some settings may be reset to default values!");
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static synchronized void save(Settings iterator) {
        try {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(SettingsUtil.settingsByName(SETTINGS_DEFAULT_NAME), new OpenOption[0]);){
                for (Settings.Setting setting : SettingsUtil.modifiedSettings(iterator)) {
                    bufferedWriter.write(SettingsUtil.settingToString(setting) + "\n");
                }
                if (bufferedWriter == null) return;
            }
            return;
        }
        catch (Exception exception) {
            Helper.HELPER.logDirect("Exception thrown while saving Baritone settings!");
            exception.printStackTrace();
            return;
        }
    }

    private static Path settingsByName(String string) {
        return Minecraft.getInstance().gameDirectory.toPath().resolve("baritone").resolve(string);
    }

    public static List<Settings.Setting> modifiedSettings(Settings object) {
        ArrayList<Settings.Setting> arrayList = new ArrayList<Settings.Setting>();
        for (Settings.Setting<?> setting : ((Settings)((Object)object)).allSettings) {
            if (setting.value == null) {
                System.out.println("NULL SETTING?" + setting.getName());
                continue;
            }
            if (setting.isJavaOnly() || setting.value == setting.defaultValue) continue;
            arrayList.add(setting);
        }
        return arrayList;
    }

    public static String settingTypeToString(Settings.Setting setting) {
        return setting.getType().getTypeName().replaceAll("(?:\\w+\\.)+(\\w+)", "$1");
    }

    public static <T> String settingValueToString(Settings.Setting<T> setting, T t2) {
        Parser parser = Parser.getParser(setting.getType());
        if (parser == null) {
            throw new IllegalStateException("Missing " + String.valueOf(setting.getValueClass()) + " " + setting.getName());
        }
        return parser.toString(setting.getType(), (Object)t2);
    }

    public static String settingValueToString(Settings.Setting setting) {
        Settings.Setting setting2 = setting;
        return SettingsUtil.settingValueToString(setting2, setting2.value);
    }

    public static String settingDefaultToString(Settings.Setting setting) {
        Settings.Setting setting2 = setting;
        return SettingsUtil.settingValueToString(setting2, setting2.defaultValue);
    }

    public static String maybeCensor(int n2) {
        if (((Boolean)BaritoneAPI.getSettings().censorCoordinates.value).booleanValue()) {
            return "<censored>";
        }
        return Integer.toString(n2);
    }

    public static String settingToString(Settings.Setting setting) {
        if (setting.isJavaOnly()) {
            return setting.getName();
        }
        return setting.getName() + " " + SettingsUtil.settingValueToString(setting);
    }

    @Deprecated
    public static boolean javaOnlySetting(Settings.Setting setting) {
        return setting.isJavaOnly();
    }

    public static void parseAndApply(Settings setting, String clazz, String string) {
        Parser parser;
        setting = ((Settings)((Object)setting)).byLowerName.get(clazz);
        if (setting == null) {
            throw new IllegalStateException("No setting by that name");
        }
        clazz = setting.getValueClass();
        if (!clazz.isInstance(string = (parser = Parser.getParser(setting.getType())).parse(setting.getType(), string))) {
            throw new IllegalStateException(String.valueOf(parser) + " parser returned incorrect type, expected " + String.valueOf(clazz) + " got " + String.valueOf(string) + " which is " + String.valueOf(string.getClass()));
        }
        setting.value = string;
    }

    static enum Parser implements ISettingParser
    {
        DOUBLE(Double.class, Double::parseDouble),
        BOOLEAN(Boolean.class, Boolean::parseBoolean),
        INTEGER(Integer.class, Integer::parseInt),
        FLOAT(Float.class, Float::parseFloat),
        LONG(Long.class, Long::parseLong),
        STRING(String.class, String::new),
        MIRROR(Mirror.class, Mirror::valueOf, Enum::name),
        ROTATION(Rotation.class, Rotation::valueOf, Enum::name),
        COLOR(Color.class, string -> new Color(Integer.parseInt(string.split(",")[0]), Integer.parseInt(string.split(",")[1]), Integer.parseInt(string.split(",")[2])), color -> color.getRed() + "," + color.getGreen() + "," + color.getBlue()),
        VEC3I(Vec3i.class, string -> new Vec3i(Integer.parseInt(string.split(",")[0]), Integer.parseInt(string.split(",")[1]), Integer.parseInt(string.split(",")[2])), vec3i -> vec3i.getX() + "," + vec3i.getY() + "," + vec3i.getZ()),
        BLOCK(Block.class, string -> BlockUtils.stringToBlockRequired(string.trim()), BlockUtils::blockToString),
        ITEM(Item.class, string -> (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse((String)string.trim())), item -> BuiltInRegistries.ITEM.getKey(item).toString()),
        LIST{

            @Override
            public final Object parse(Type type, String string2) {
                type = ((ParameterizedType)type).getActualTypeArguments()[0];
                Parser parser = Parser.getParser(type);
                return Stream.of(string2.split(",")).map(string -> parser.parse(type, (String)string)).collect(Collectors.toList());
            }

            @Override
            public final String toString(Type type, Object object2) {
                type = ((ParameterizedType)type).getActualTypeArguments()[0];
                Parser parser = Parser.getParser(type);
                return ((List)object2).stream().map(object -> parser.toString(type, object)).collect(Collectors.joining(","));
            }

            @Override
            public final boolean accepts(Type type) {
                return List.class.isAssignableFrom(TypeUtils.resolveBaseClass(type));
            }
        }
        ,
        MAPPING{

            @Override
            public final Object parse(Type type, String string2) {
                Type type2 = ((ParameterizedType)type).getActualTypeArguments()[0];
                type = ((ParameterizedType)type).getActualTypeArguments()[1];
                Parser parser = Parser.getParser(type2);
                Parser parser2 = Parser.getParser(type);
                return Stream.of(string2.split(",(?=[^,]*->)")).map(string -> string.split("->")).collect(Collectors.toMap(stringArray -> parser.parse(type2, stringArray[0]), stringArray -> parser2.parse(type, stringArray[1])));
            }

            @Override
            public final String toString(Type type, Object object) {
                Type type2 = ((ParameterizedType)type).getActualTypeArguments()[0];
                type = ((ParameterizedType)type).getActualTypeArguments()[1];
                Parser parser = Parser.getParser(type2);
                Parser parser2 = Parser.getParser(type);
                return ((Map)object).entrySet().stream().map(entry -> parser.toString(type2, entry.getKey()) + "->" + parser2.toString(type, entry.getValue())).collect(Collectors.joining(","));
            }

            @Override
            public final boolean accepts(Type type) {
                return Map.class.isAssignableFrom(TypeUtils.resolveBaseClass(type));
            }
        };

        private final Class<?> cla$$;
        private final Function<String, Object> parser;
        private final Function<Object, String> toString;

        Parser() {
            this.cla$$ = null;
            this.parser = null;
            this.toString = null;
        }

        private <T> Parser(Class<T> clazz, Function<String, T> function) {
            this(clazz, function, Object::toString);
        }

        private <T> Parser(Class<T> clazz, Function<String, T> function, Function<T, String> function2) {
            this.cla$$ = clazz;
            this.parser = function::apply;
            this.toString = object -> (String)function2.apply(object);
        }

        public Object parse(Type object, String string) {
            object = this.parser.apply(string);
            Objects.requireNonNull(object);
            return object;
        }

        public String toString(Type type, Object object) {
            return this.toString.apply(object);
        }

        @Override
        public boolean accepts(Type type) {
            return type instanceof Class && this.cla$$.isAssignableFrom((Class)type);
        }

        public static Parser getParser(Type type) {
            return Stream.of(Parser.values()).filter(parser -> parser.accepts(type)).findFirst().orElse(null);
        }
    }

    static interface ISettingParser<T> {
        public T parse(Type var1, String var2);

        public String toString(Type var1, T var2);

        public boolean accepts(Type var1);
    }
}

