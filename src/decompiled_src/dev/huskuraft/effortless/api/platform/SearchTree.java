/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.text.Text;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface SearchTree<T> {
    public static <T> SearchTree<T> empty() {
        return query -> List.of();
    }

    public static <T> SearchTree<T> of(List<T> list, Function<T, Stream<String>> keyExtractor, boolean caseSensitive) {
        return query -> list.stream().filter(t -> ((Stream)keyExtractor.apply(t)).anyMatch(text -> caseSensitive ? text.contains(query) : text.toLowerCase().contains(query.toLowerCase()))).toList();
    }

    public static <T> SearchTree<T> of(List<T> list, Function<T, Stream<String>> keyExtractor) {
        return SearchTree.of(list, keyExtractor, false);
    }

    public static <T> SearchTree<T> ofText(List<T> list, Function<T, Stream<Text>> keyExtractor) {
        return SearchTree.of(list, item -> ((Stream)keyExtractor.apply(item)).map(Text::getString));
    }

    public List<T> search(String var1);
}
