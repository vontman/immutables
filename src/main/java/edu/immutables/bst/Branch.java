package edu.immutables.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Representation of Inner node in the tree.
 *
 * @param <T> type of elements held by the tree
 * @author tarek-nawara
 */
class Branch<T extends Comparable<T>> implements Tree<T> {
    private final Tree<T> left;
    private final Tree<T> right;
    private final T value;
    private final int size;

    Branch(final T value, final Tree<T> left, final Tree<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.size = 1 + left.size() + right.size();
    }

    @Override
    public Tree<T> add(T element) {
        int cmp = element.compareTo(value);
        if (cmp < 0) return new Branch<>(value, left.add(element), right);
        else if (cmp > 0) return new Branch<>(value, left, right.add(element));
        return this;
    }

    @Override
    public Tree<T> remove(T element) {
        int cmp = element.compareTo(value);
        if (cmp < 0) return new Branch<>(value, left.remove(element), right);
        else if (cmp > 0) return new Branch<>(value, left, right.remove(element));
        return left.union(right);
    }

    @Override
    public <U extends Comparable<U>> Tree<U> map(Function<? super T, ? extends U> f) {
        final List<U> result = this.toList().stream().map(e -> f.apply((T) e))
                .collect(Collectors.toList());
        return Tree.of(result);
    }

    @Override
    public <U extends Comparable<U>> Tree<U> flatMap(Function<? super T, ? extends Tree<U>> f) {
        return Tree.of(this.toList().stream()
                .flatMap(e -> f.apply((T) e).toList().stream())
                .collect(Collectors.toList()));
    }

    @Override
    public void foreach(Consumer<? super T> consumer) {
        consumer.accept(value);
        left.foreach(consumer);
        right.foreach(consumer);
    }

    @Override
    public boolean contains(T element) {
        final int cmp = element.compareTo(value);
        if (cmp < 0) return left.contains(element);
        else if (cmp > 0) return right.contains(element);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Tree<T> union(Tree<T> other) {
        return left.union(right.union(other.add(value)));
    }

    @Override
    public List<T> toList() {
        final List<T> result = new ArrayList<>();
        result.addAll(left.toList());
        result.add(value);
        result.addAll(right.toList());
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return this.toList().iterator();
    }

}
