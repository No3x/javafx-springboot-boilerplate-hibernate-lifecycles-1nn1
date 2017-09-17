package hello.data.adapter;

import com.google.common.collect.Sets;

/**
 * Created by No3x on 07.02.2017.
 */
public class ListCompare<T> {

    private Iterable<? extends T> gui;
    private Iterable<? extends T> database;
    private IChangeAction<T> changeAction;

    public ListCompare(Iterable<? extends T> gui, Iterable<? extends T> database, IChangeAction<T> action) {
        this.gui = gui;
        this.database = database;
        this.changeAction = action;
    }

    public void manageChanges() {
        changeAction.added( addedItems() );
        changeAction.removed( removedItems() );
    }

    private Iterable<T> addedItems() {
        return Sets.difference(Sets.newHashSet(gui), Sets.newHashSet(database));
    }

    private Iterable<T> removedItems() {
        return Sets.difference(Sets.newHashSet(database), Sets.newHashSet(gui));
    }

    public interface IChangeAction<T> {
        void added(Iterable<? extends T> added);
        void removed(Iterable<? extends T> removed);
    }
}
