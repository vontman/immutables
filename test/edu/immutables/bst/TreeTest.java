package edu.immutables.bst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class TreeTest {

    private static class Console {
        final List<String> buffer = new ArrayList<>();

        <T> void out(final T object) {
            buffer.add(object.toString());
        }
    }

    @Test
    public void add() throws Exception {
        final Tree<Integer> tree = Tree.empty();
        final Tree<Integer> newTree = tree.add(1);
        assertEquals(1, newTree.size());
        assertEquals(Collections.singletonList(1), newTree.toList());
    }

    @Test
    public void remove() throws Exception {
        final Tree<Integer> tree = Tree.of(1, 2, 3);
        final Tree<Integer> newTree = tree.remove(1);
        assertEquals(2, newTree.size());
        assertEquals(Arrays.asList(2, 3), newTree.toList());
    }

    @Test
    public void map() throws Exception {
        final Tree<Integer> tree = Tree.of(1, 2, 3);
        final Tree<String> newTree = tree.map(String::valueOf);
        assertEquals(3, newTree.size());
        assertEquals(Arrays.asList("1", "2", "3"), newTree.toList());
    }

    @Test
    public void flatMap() throws Exception {
        final Tree<Integer> tree = Tree.of(1, 2, 3);
        final Tree<Integer> newTree = tree.flatMap(e ->
                IntStream.range(1, e + 1).mapToObj(x -> e).reduce(Tree.empty(), Tree::add, Tree::union));
        assertEquals(3, newTree.size());
        assertEquals(Arrays.asList(1, 2, 3), newTree.toList());
    }

    @Test
    public void foreach() throws Exception {
        final Console console = new Console();
        final Tree<Integer> tree = Tree.of(1, 2, 3);
        tree.foreach(console::out);
        assertEquals(Arrays.asList("1", "2", "3"), console.buffer);
    }

    @Test
    public void contains() throws Exception {
        final Tree<Integer> tree = Tree.of(1, 2, 3);
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertFalse(tree.contains(4));
    }

    @Test
    public void size() throws Exception {
        final Tree<Integer> tree = Tree.of(1, 2, 3);
        assertEquals(3, tree.size());
    }

    @Test
    public void union() throws Exception {
        final Tree<Integer> treeOne = Tree.of(1, 2, 3);
        final Tree<Integer> treeTwo = Tree.of(2, 3, 4);
        final Tree<Integer> unionTree = treeOne.union(treeTwo);
        assertEquals(4, unionTree.size());
        assertEquals(Arrays.asList(1, 2, 3, 4), unionTree.toList());
    }

}