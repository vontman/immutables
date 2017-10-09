package edu.immutables.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
        return this.toList().stream().map(f)
                .reduce(Tree.empty(), Tree::add, Tree::union);
    }

    @Override
    public <U extends Comparable<U>> Tree<U> flatMap(Function<? super T, ? extends Tree<U>> f) {
        return this.toList().stream().map(f).reduce(Tree.empty(), Tree::union, Tree::union);
    }

    @Override
    public void foreach(Consumer<? super T> consumer) {
        left.foreach(consumer);
        consumer.accept(value);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Branch<?> branch = (Branch<?>) o;
        return size == branch.size &&
                Objects.equals(left, branch.left) &&
                Objects.equals(right, branch.right) &&
                Objects.equals(value, branch.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, value, size);
    }

    @Override
    public String toString() {
        return left + " " + value + " " + right;
    }
}
