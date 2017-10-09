package edu.immutables.list.impl;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Empty<T> implements ImmutableList<T> {
    @Override
    public T get(int ind) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T getHead() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ImmutableList<T> getTail() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ImmutableList<T> add(T t) {
        return new Cons<>(t);
    }

    @Override
    public ImmutableList<T> removeAt(int ind) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ImmutableList<T> remove(T t) {
        return this;
    }

    @Override
    public <U> ImmutableList<U> map(Function<? super T, ? extends U> t) {
        return new Empty<>();
    }

    @Override
    public <U> ImmutableList<U> flatMap(Function<? super T, ImmutableList<U>> f) {
        return new Empty<>();
    }

    @Override
    public ImmutableList<T> takeWhile(Predicate<? super T> p) {
        return this;
    }

    @Override
    public ImmutableList<T> dropWhile(Predicate<? super T> p) {
        return this;
    }

    @Override
    public ImmutableList<T> take(int length) {
        if (length != 0)
            throw new IndexOutOfBoundsException();
        return this;
    }

    @Override
    public ImmutableList<T> drop(int length) {
        if (length != 0)
            throw new IndexOutOfBoundsException();
        return this;
    }

    @Override
    public ImmutableList<T> append(ImmutableList<T> t) {
        return t;
    }

    @Override
    public ImmutableList<T> filter(Predicate<? super T> t) {
        return this;
    }

    @Override
    public <U> U reduce(Supplier<? extends U> sup, BiFunction<? super U, ? super T, ? extends U> redFn) {
        return sup.get();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> t) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> t) {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
    }
}
