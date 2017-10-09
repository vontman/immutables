package edu.immutables.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Representation of empty tree.
 *
 * @param <T> type of the empty tree
 * @author tarek-nawara
 */
class Tip<T extends Comparable<T>> implements Tree<T> {
    @Override
    public Tree<T> add(T element) {
        return new Branch<>(element, Tree.empty(), Tree.empty());
    }

    @Override
    public Tree<T> remove(T element) {
        return this;
    }

    @Override
    public <U extends Comparable<U>> Tree<U> map(Function<? super T, ? extends U> f) {
        return new Tip<>();
    }

    @Override
    public <U extends Comparable<U>> Tree<U> flatMap(Function<? super T, ? extends Tree<U>> f) {
        return new Tip<>();
    }

    @Override
    public void foreach(Consumer<? super T> consumer) {
    }

    @Override
    public boolean contains(T element) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Tree<T> union(Tree<T> other) {
        return other;
    }

    @Override
    public List<T> toList() {
        return new ArrayList<>();
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>().iterator();
    }

    @Override
    public String toString() {
        return "";
    }
}
