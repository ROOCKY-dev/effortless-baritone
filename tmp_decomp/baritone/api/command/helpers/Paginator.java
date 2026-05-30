/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 */
package baritone.api.command.helpers;

import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.utils.Helper;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class Paginator<E>
implements Helper {
    public final List<E> entries;
    public int pageSize = 8;
    public int page = 1;

    public Paginator(List<E> list) {
        this.entries = list;
    }

    public Paginator(E ... EArray) {
        this.entries = Arrays.asList(EArray);
    }

    public Paginator<E> setPageSize(int n2) {
        this.pageSize = n2;
        return this;
    }

    public int getMaxPage() {
        return (this.entries.size() - 1) / this.pageSize + 1;
    }

    public boolean validPage(int n2) {
        return n2 > 0 && n2 <= this.getMaxPage();
    }

    public Paginator<E> skipPages(int n2) {
        this.page += n2;
        return this;
    }

    /*
     * Unable to fully structure code
     */
    public void display(Function<E, Component> var1_1, String var2_4) {
        for (var4_7 = var3_5 = (this.page - 1) * this.pageSize; var4_7 < var3_5 + this.pageSize; ++var4_7) {
            if (var4_7 < this.entries.size()) {
                this.logDirect(new Component[]{var1_1.apply(this.entries.get(var4_7))});
                continue;
            }
            this.logDirect("--", ChatFormatting.DARK_GRAY);
        }
        if (var2_4 == null) ** GOTO lbl-1000
        v0 = this;
        if (v0.validPage(v0.page - 1)) {
            v1 = 1;
        } else lbl-1000:
        // 2 sources

        {
            v1 = var4_7 = 0;
        }
        if (var2_4 == null) ** GOTO lbl-1000
        v2 = this;
        if (v2.validPage(v2.page + 1)) {
            v3 = true;
        } else lbl-1000:
        // 2 sources

        {
            v3 = false;
        }
        var1_2 = v3;
        var3_6 = Component.literal((String)"<<");
        if (var4_7 != 0) {
            v4 = var3_6;
            v4.setStyle(v4.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s %d", new Object[]{var2_4, this.page - 1}))).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)"Click to view previous page"))));
        } else {
            v5 = var3_6;
            v5.setStyle(v5.getStyle().withColor(ChatFormatting.DARK_GRAY));
        }
        var4_8 = Component.literal((String)">>");
        if (var1_2) {
            v6 = var4_8;
            v6.setStyle(v6.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%s %d", new Object[]{var2_4, this.page + 1}))).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Object)Component.literal((String)"Click to view next page"))));
        } else {
            v7 = var4_8;
            v7.setStyle(v7.getStyle().withColor(ChatFormatting.DARK_GRAY));
        }
        var1_3 = Component.literal((String)"");
        var1_3.setStyle(var1_3.getStyle().withColor(ChatFormatting.GRAY));
        var1_3.append((Component)var3_6);
        var1_3.append(" | ");
        var1_3.append((Component)var4_8);
        var1_3.append(String.format(" %d/%d", new Object[]{this.page, this.getMaxPage()}));
        this.logDirect(new Component[]{var1_3});
    }

    public void display(Function<E, Component> function) {
        this.display(function, null);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, Paginator<T> paginator, Runnable runnable, Function<T, Component> function, String string) {
        int n2 = 1;
        iArgConsumer.requireMax(1);
        if (iArgConsumer.hasAny() && !paginator.validPage(n2 = iArgConsumer.getAs(Integer.class).intValue())) {
            throw new CommandInvalidTypeException(iArgConsumer.consumed(), String.format("a valid page (1-%d)", paginator.getMaxPage()), iArgConsumer.consumed().getValue());
        }
        paginator.skipPages(n2 - paginator.page);
        if (runnable != null) {
            runnable.run();
        }
        paginator.display(function, string);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, List<T> list, Runnable runnable, Function<T, Component> function, String string) {
        Paginator.paginate(iArgConsumer, new Paginator<T>(list), runnable, function, string);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, T[] TArray, Runnable runnable, Function<T, Component> function, String string) {
        Paginator.paginate(iArgConsumer, Arrays.asList(TArray), runnable, function, string);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, Paginator<T> paginator, Function<T, Component> function, String string) {
        Paginator.paginate(iArgConsumer, paginator, null, function, string);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, List<T> list, Function<T, Component> function, String string) {
        Paginator.paginate(iArgConsumer, new Paginator<T>(list), null, function, string);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, T[] TArray, Function<T, Component> function, String string) {
        Paginator.paginate(iArgConsumer, Arrays.asList(TArray), null, function, string);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, Paginator<T> paginator, Runnable runnable, Function<T, Component> function) {
        Paginator.paginate(iArgConsumer, paginator, runnable, function, null);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, List<T> list, Runnable runnable, Function<T, Component> function) {
        Paginator.paginate(iArgConsumer, new Paginator<T>(list), runnable, function, null);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, T[] TArray, Runnable runnable, Function<T, Component> function) {
        Paginator.paginate(iArgConsumer, Arrays.asList(TArray), runnable, function, null);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, Paginator<T> paginator, Function<T, Component> function) {
        Paginator.paginate(iArgConsumer, paginator, null, function, null);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, List<T> list, Function<T, Component> function) {
        Paginator.paginate(iArgConsumer, new Paginator<T>(list), null, function, null);
    }

    public static <T> void paginate(IArgConsumer iArgConsumer, T[] TArray, Function<T, Component> function) {
        Paginator.paginate(iArgConsumer, Arrays.asList(TArray), null, function, null);
    }
}

