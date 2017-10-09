package edu.immutables.list.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Interface for representing an Immutable List
 *
 * @param <T> type of elements held by list
 * @author vontman
 * @author tarek-nawara
 */
public interface ImmutableList<T> extends Iterable<T> {

    /**
     * Get element at the given index.
     *
     * <p>
     * The running time for this operation is O(n)
     * </p>
     * @param ind index of the element to retrieve from the list
     * @return the target element
     * @throws IndexOutOfBoundsException
     */
    T get(int ind);

    /**
     * Get the first element in the list.
     *
     * <p>
     * Running time of this operation is O(1)
     * </p>
     * @return head of the list
     * @throws IndexOutOfBoundsException
     */
    T getHead();

    /**
     * Get a new list after removing the head of the current list.
     *
     * <p>
     * Running time of this operation is O(1)
     * </p>
     * @return rest of the list
     * @throws IndexOutOfBoundsException
     */
    ImmutableList<T> getTail();

    /**
     * Add element to the beginning of the list.
     *
     * @param t element to add to the head of the list
     * @return a new list with this element added to the beginning
     */
    default ImmutableList<T> add(T t) {
        return new Cons<>(t, this);
    }

    /**
     * Remove element at the given index.
     *
     * @param ind index of element to remove
     * @return a new list with the element at the given index is removed
     * @throws IndexOutOfBoundsException
     */
    ImmutableList<T> removeAt(int ind);

    /**
     * Remove the first element equals a given target from the list.
     *
     * <p>
     * This method will remove the first occurrence of the given element,
     * if the element doesn't exist in the list nothing happens
     * </p>
     * @param t element to remove
     * @return a new list with this element removed
     */
    ImmutableList<T> remove(T t);

    /**
     * Apply a transformation function over the elements of the list
     * and return a new list with the transformed elements.
     *
     * @param f transformation function
     * @param <U> type of new elements
     * @return a new list with the transformed elements
     */
    <U> ImmutableList<U> map(Function<? super T, ? extends U> f);

    /**
     * Apply a transformation function over the elements of the list,
     * then it will flatten the resulting list to one single list.
     *
     * @param f transformation function
     * @param <U> type of elements in the new list
     * @return a new list after applying transformations and flattening the list
     */
    <U> ImmutableList<U> flatMap(Function<? super T, ImmutableList<U>> f);

    /**
     * Get the first elements that matches certain condition.
     *
     * @param p condition to match
     * @return a new list of elements matching the given condition
     */
    ImmutableList<T> takeWhile(Predicate<? super T> p);

    /**
     * Remove the elements that matches a given condition
     * from the beginning from the list.
     *
     * @param p condition to match
     * @return a list with the matching elements removed from its beginning
     */
    ImmutableList<T> dropWhile(Predicate<? super T> p);

    /**
     * Take certain amount of elements from the beginning
     * of the list.
     *
     * @param length elements count to get from the beginning of the list
     * @return a list containing the given count elements taken from the beginning
     *         of this list
     */
    ImmutableList<T> take(int length);

    /**
     * Remove a given count from the beginning of the list.
     *
     * @param length elements count to remove
     * @return a new list with the given count removed from the beginning
     */
    ImmutableList<T> drop(int length);

    /**
     * Append a given list to the end of this one.
     *
     * @param other other list to append
     * @return a new list resulting from the concatenation of the two lists.
     */
    ImmutableList<T> append(ImmutableList<T> other);

    /**
     * Get all the elements from the list matching
     * a given condition.
     *
     * @param p the given condition
     * @return a new list with the elements matching the given condition
     */
    ImmutableList<T> filter(Predicate<? super T> p);

    /**
     * Reduce the given list to a certain value.
     *
     * @param zero the first element to start the reduce with.
     * @param redFn reducing function
     * @param <U> type of the reduced result
     * @return a final result of the reduction of the list
     */
    <U> U reduce(U zero, BiFunction<? super U, ? super T,? extends U> redFn);

    /**
     * Find if any element in the list matches a given condition.
     *
     * @param p condition to test
     * @return true if any element in the list matches the given
     *         condition, false otherwise
     */
    boolean anyMatch(Predicate<? super T> p);

    /**
     * Test if all the elements in the list matches a given condition.
     *
     * @param p condition to test
     * @return true if all the elements in the list matches the condition,
     *         false otherwise.
     */
    boolean allMatch(Predicate<? super T> p);

    /**
     * Test if all the elements in the list not matching the given
     * condition.
     * @param p condition to test with
     * @return true if no element matches the given condition, false otherwise
     */
    default boolean nonMatch(Predicate<? super T> p) {
        return !anyMatch(p);
    }

    /**
     * Factory method to build an ImmutableList from a given collection.
     *
     * @param collection collection to build from
     * @param <T> type of elements in the collection
     * @return a new ImmutableList of the elements in the given
     *         collection
     */
    static <T> ImmutableList<T> of(Collection<T> collection) {
        final ImmutableList<T> empty = new Empty<>();
        return collection.stream()
                         .reduce(empty,
                                 ImmutableList::add,
                                 ImmutableList::append);
    }

    /**
     * Factory method to build an ImmutableList from a multiple arguments.
     *
     * @param t multiple arguments
     * @param <T> type of multiple arguments
     * @return a new ImmutableList of the given elements
     */
    @SafeVarargs
    static <T> ImmutableList<T> of(T... t) {
        return ImmutableList.of(Arrays.asList(t));
    }

    /**
     *
     * @return size of the list
     */
    int size();

    /**
     *
     * @return true if the list is empty, false otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Reverse a given list.
     *
     * @param list list to reverse
     * @param <T> type of elements in the list
     * @return a new ImmutableList with the same elements as the
     *         given list but in a reverse order
     */
    static <T> ImmutableList<T> reverse(final ImmutableList<T> list) {
        return reverse(list, new Empty<>());

    }

    // TODO should be private since java 9 now support private static methods in interfaces
    static <T> ImmutableList<T> reverse(ImmutableList<T> toBeReversed, ImmutableList<T> currentlyReversed) {
        if (toBeReversed.isEmpty())
            return currentlyReversed;
        return reverse(toBeReversed.getTail(), new Cons<>(toBeReversed.getHead(), currentlyReversed));
    }

}
