package edu.immutables.list.impl;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static edu.immutables.list.impl.ImmutableList.reverse;

public class Cons<T> implements ImmutableList<T> {
    private final T head;
    private final ImmutableList<T> tail;
    private final int size;

    Cons(T head) {
        this(head, new Empty<>());
    }

    Cons(T head, ImmutableList<T> tail) {
        this.head = head;
        this.tail = tail;
        this.size = 1 + tail.size();
    }

    @Override
    public T get(int ind) {
        if (ind == 0)
            return getHead();
        return tail.get(ind - 1);
    }

    @Override
    public T getHead() {
        return head;
    }

    @Override
    public ImmutableList<T> getTail() {
        return tail;
    }

    @Override
    public ImmutableList<T> addHead(T t) {
        return new Cons<>(t, this);
    }

    @Override
    public ImmutableList<T> removeAt(int ind) {
        if (ind == 0)
            return tail;
        return new Cons<>(head, tail.removeAt(ind - 1));
    }

    @Override
    public ImmutableList<T> remove(T t) {
        if (head.equals(t))
            return tail;
        return new Cons<>(head, tail.remove(t));
    }

    @Override
    public <U> ImmutableList<U> map(Function<? super T, ? extends U> fn) {
        return new Cons<>(fn.apply(head), tail.map(fn));
    }

    @Override
    public <U> ImmutableList<U> flatMap(final Function<? super T, ImmutableList<U>> f) {
        return f.apply(head).append(tail.flatMap(f));
    }

    @Override
    public ImmutableList<T> takeWhile(Predicate<? super T> p) {
        if (p.test(head)) {
            return new Cons<>(head, tail.takeWhile(p));
        }
        return new Empty<>();
    }

    @Override
    public ImmutableList<T> dropWhile(Predicate<? super T> p) {
        return reverse(reverse(this).takeWhile(p.negate()));
    }

    @Override
    public ImmutableList<T> take(int length) {
        if (length == 0)
            return new Empty<> ();
        return new Cons<>(head, tail.take(length - 1));
    }

    @Override
    public ImmutableList<T> drop(int length) {
        return null;
    }

    @Override
    public ImmutableList<T> append(ImmutableList<T> t) {
        return new Cons<>(head, tail.append(t));
    }

    @Override
    public ImmutableList<T> filter(Predicate<? super T> t) {
        if (t.test(head))
            return new Cons<>(head, tail.filter(t));
        return tail.filter(t);
    }

    @Override
    public <U> U reduce(U zero, BiFunction<? super U, ? super T, ? extends U> redFn) {
        return tail.reduce(
                redFn.apply(zero, head),
                redFn);
    }

    @Override
    public boolean anyMatch(Predicate<? super T> t) {
        return t.test(head) || tail.anyMatch(t);
    }

    @Override
    public boolean allMatch(Predicate<? super T> t) {
        return t.test(head) && tail.allMatch(t);
    }

    @Override
    public Stream<T> stream() {
        return Stream.concat(
                Stream.of(head), tail.stream());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableIterator();
    }


    private class ImmutableIterator implements Iterator<T> {
        private ImmutableList<T> list = Cons.this;

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public T next() {
            final T temp = list.getHead();
            list = list.getTail();
            return temp;
        }
    }
}
