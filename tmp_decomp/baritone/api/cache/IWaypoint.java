/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.cache;

import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public interface IWaypoint {
    public String getName();

    public Tag getTag();

    public long getCreationTimestamp();

    public BetterBlockPos getLocation();

    public static enum Tag {
        HOME("home", "base"),
        DEATH("death"),
        BED("bed", "spawn"),
        USER("user");

        private static final List<Tag> TAG_LIST;
        public final String[] names;

        private Tag(String ... stringArray) {
            this.names = stringArray;
        }

        public final String getName() {
            return this.names[0];
        }

        public static Tag getByName(String string) {
            for (Tag tag : Tag.values()) {
                String[] stringArray = tag.names;
                int n2 = tag.names.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    if (!stringArray[i2].equalsIgnoreCase(string)) continue;
                    return tag;
                }
            }
            return null;
        }

        public static String[] getAllNames() {
            HashSet<String> hashSet = new HashSet<String>();
            for (Tag tag : Tag.values()) {
                hashSet.addAll(Arrays.asList(tag.names));
            }
            return hashSet.toArray(new String[0]);
        }

        static {
            TAG_LIST = Collections.unmodifiableList(Arrays.asList(Tag.values()));
        }
    }
}

