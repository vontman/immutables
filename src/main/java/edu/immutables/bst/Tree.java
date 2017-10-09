package edu.immutables.bst;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface representing Immutable Trees.
 *
 * @param <T> type of elements held by the tree
 * @author tarek-nawara
 */
public interface Tree<T extends Comparable<T>> extends Iterable<T> {

    /**
     * Add a new element to the tree.
     *
     * @param element element to add
     * @return a new tree with the element added
     */
    Tree<T> add(T element);

    /**
     * Remove element from the tree.
     *
     * @param element element to remove
     * @return a new tree with the element removed
     */
    Tree<T> remove(T element);

    /**
     * Apply a transformation function over the tree.
     *
     * @param f transformation function
     * @param <U> type of the new elements
     * @return a new tree with the mapped elements
     */
    <U extends Comparable<U>> Tree<U> map(Function<? super T, ? extends U> f);

    /**
     * Apply a transformation function over the tree
     * then fusing it.
     *
     * @param f transformation function
     * @param <U> type of the new elements
     * @return a new tree of the mapped elements and fusing the tree
     */
    <U extends Comparable<U>> Tree<U> flatMap(Function<? super T, ? extends Tree<U>> f);

    /**
     * Apply an action over the elements of the tree.
     *
     * @param consumer action to apply
     */
    void foreach(Consumer<? super T> consumer);

    /**
     * Test if the current tree contains the target element.
     *
     * @param element target element
     * @return true if the tree contains the element, false otherwise
     */
    boolean contains(T element);

    /**
     * @return the size of the current tree
     */
    int size();

    /**
     * @return true if the tree is empty, false otherwise
     */
    default boolean isEmpty() {
        return size() != 0;
    }

    /**
     * @return true if the tree is not empty, false otherwise
     */
    default boolean nonEmpty() {
        return !isEmpty();
    }

    /**
     * Union two trees.
     *
     * @param other other tree to union with
     * @return a new tree consist of the elements of both trees
     *         without any duplicate elements
     */
    Tree<T> union(Tree<T> other);

    /**
     * Convert the given tree to a list.
     *
     * @return list representing the tree
     */
    List<T> toList();

    /**
     * Factory method to build a tree from a list.
     *
     * @param list list to build from
     * @param <T> type of elements held by the list
     * @return a new tree of the elements of the list
     */
    static <T extends Comparable<T>> Tree<T> of(final List<T> list) {
        Collections.sort(list);
        return of(list, 0, list.size() - 1);
    }

    /**
     * Build a tree from a given slice of a list.
     *
     * @param list list to build from
     * @param lo starting point inclusive
     * @param hi ending point inclusive
     * @param <T> type of elements held by the list
     * @return a new tree of the given slice
     */
    static <T extends Comparable<T>> Tree<T> of(final List<T> list,
                                                final int lo,
                                                final int hi) {
        if (lo > hi) return new Tip<>();
        int mid = (lo + hi) >>> 1;
        return new Branch<>(list.get(mid), of(list, lo, mid - 1), of(list, mid + 1, hi));
    }

    /**
     * Factory method to build a tree from multiple arguments.
     *
     * @param a elements to build from
     * @param <T> type of elements
     * @return tree of the given elements
     */
    @SafeVarargs
    static <T extends Comparable<T>> Tree<T> of(final T... a) {
        return Tree.of(Arrays.asList(a));
    }

    /**
     * Return an empty instance of the tree.
     *
     * @param <T> type of the returned tree
     * @return a new instance of tree
     */
    static <T extends Comparable<T>> Tree<T> empty() {
        return new Tip<>();
    }

    /**
     * Factory method to build a tree of a single element.
     *
     * @param element element to build from
     * @param <T> type of element
     * @return a new tree containing that element
     */
    static <T extends Comparable<T>> Tree<T> singleton(final T element) {
        return new Branch<>(element, new Tip<>(), new Tip<>());
    }
}
