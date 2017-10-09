package edu.immutables.list.impl;

import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ImmutableList<T> extends Iterable<T> {
    T get(int ind);
    T getHead();
    ImmutableList<T> getTail();


    default ImmutableList<T> add(T t) {
        return new Cons<>(t, this);
    }

    ImmutableList<T> removeAt(int ind);

    ImmutableList<T> remove(T t);

    <U> ImmutableList<U> map(Function<? super T, ? extends U> t);
    <U> ImmutableList<U> flatMap(Function<? super T, ImmutableList<U>> f);

    ImmutableList<T> takeWhile(Predicate<? super T> p);
    ImmutableList<T> dropWhile(Predicate<? super T> p);

    ImmutableList<T> take(int length);
    ImmutableList<T> drop(int length);
    ImmutableList<T> append(ImmutableList<T> t);
    ImmutableList<T> filter(Predicate<? super T> t);

    <U> U reduce(Supplier<? extends U> sup, BiFunction<? super U, ? super T, ? extends U> redFn);

    boolean anyMatch(Predicate<? super T> t);
    boolean allMatch(Predicate<? super T> t);

    default boolean nonMatch(Predicate<? super T> t) {
        return !anyMatch(t);
    }


    static<T> ImmutableList<T> of(Collections t) {
        return null;
    }

    static<T> ImmutableList<T> of(T... t) {
        return null;
    }

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    static<T> ImmutableList<T> reverse(final ImmutableList<T> list) {
        return reverse(list, new Empty<>());

    }

    static <T> ImmutableList<T> reverse(ImmutableList<T> toBeReversed, ImmutableList<T> currentlyReversed) {
        if (toBeReversed.isEmpty())
            return currentlyReversed;
        return reverse(toBeReversed.getTail(), new Cons<> (toBeReversed.getHead(), currentlyReversed));
    }
    /**
     *  list
     *  rev(a -> b -> c -> d)
     *  rev(b -> c -> d, a)
     *  rev(c -> d, b -> a)
     *  rev(d, c -> b -> a)
     *  d -> c -> b -> a
     */
}
