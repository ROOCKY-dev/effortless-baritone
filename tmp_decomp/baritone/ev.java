/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package baritone;

import baritone.a;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.et;
import baritone.ew;
import java.util.LinkedList;
import java.util.ListIterator;
import net.minecraft.core.Direction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ev
implements ISelectionManager {
    private final LinkedList<ISelection> a;
    private ISelection[] a = new ISelection[0];

    public ev(a a2) {
        new ew(a2, this);
    }

    private void a() {
        this.a = this.a.toArray(new ISelection[0]);
    }

    @Override
    public final synchronized ISelection addSelection(ISelection iSelection) {
        this.a.add(iSelection);
        this.a();
        return iSelection;
    }

    @Override
    public final ISelection addSelection(BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        return this.addSelection(new et(betterBlockPos, betterBlockPos2));
    }

    @Override
    public final synchronized ISelection removeSelection(ISelection iSelection) {
        this.a.remove(iSelection);
        this.a();
        return iSelection;
    }

    @Override
    public final synchronized ISelection[] removeAllSelections() {
        ISelection[] iSelectionArray = this.getSelections();
        this.a.clear();
        this.a();
        return iSelectionArray;
    }

    @Override
    public final ISelection[] getSelections() {
        return this.a;
    }

    @Override
    public final synchronized ISelection getOnlySelection() {
        if (this.a.size() == 1) {
            return this.a.peekFirst();
        }
        return null;
    }

    @Override
    public final ISelection getLastSelection() {
        return this.a.peekLast();
    }

    @Override
    public final synchronized ISelection expand(ISelection iSelection, Direction direction, int n2) {
        ListIterator<ISelection> listIterator = this.a.listIterator();
        while (listIterator.hasNext()) {
            ISelection iSelection2 = (ISelection)listIterator.next();
            if (iSelection2 != iSelection) continue;
            listIterator.remove();
            listIterator.add(iSelection2.expand(direction, n2));
            this.a();
            return (ISelection)listIterator.previous();
        }
        return null;
    }

    @Override
    public final synchronized ISelection contract(ISelection iSelection, Direction direction, int n2) {
        ListIterator<ISelection> listIterator = this.a.listIterator();
        while (listIterator.hasNext()) {
            ISelection iSelection2 = (ISelection)listIterator.next();
            if (iSelection2 != iSelection) continue;
            listIterator.remove();
            listIterator.add(iSelection2.contract(direction, n2));
            this.a();
            return (ISelection)listIterator.previous();
        }
        return null;
    }

    @Override
    public final synchronized ISelection shift(ISelection iSelection, Direction direction, int n2) {
        ListIterator<ISelection> listIterator = this.a.listIterator();
        while (listIterator.hasNext()) {
            ISelection iSelection2 = (ISelection)listIterator.next();
            if (iSelection2 != iSelection) continue;
            listIterator.remove();
            listIterator.add(iSelection2.shift(direction, n2));
            this.a();
            return (ISelection)listIterator.previous();
        }
        return null;
    }
}

