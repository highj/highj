package org.highj.data.collection;

import org.highj.data.tuple.T2;
import org.junit.Test;

import static org.highj.data.collection.Tree.*;
import static org.junit.Assert.assertEquals;

public class TreeTest {
    @Test
    public void testToString() throws Exception {
        Tree<Integer> tree = Tree(1,Tree(2,Tree(3)), Tree(4));
        assertEquals("Tree(1,[Tree(2,[Tree(3,[])]),Tree(4,[])])", tree.toString());
    }

    @Test
    public void testDrawTree() throws Exception {
        Tree<Integer> tree = Tree(1,Tree(2,Tree(3)), Tree(4));
        assertEquals("1\n|\n+- 2\n|  |\n|  `- 3\n|\n`- 4", tree.drawTree());
    }

    @Test
    public void testFlatten() throws Exception {
        Tree<Integer> tree = Tree(1,Tree(2,Tree(3)), Tree(4));
        assertEquals("List(1,2,3,4)", tree.flatten().toString());
    }

    @Test
    public void testLevels() throws Exception {
        Tree<Integer> tree = Tree(1,Tree(2,Tree(3)), Tree(4));
        assertEquals("List(List(1),List(2,4),List(3))", tree.levels().toString());
    }

    @Test
    public void testUnfoldTree() throws Exception {
        Tree<Integer> tree = Tree(1,Tree(2,Tree(3)), Tree(4));
        assertEquals(tree, unfold(t -> T2.of(t.rootLabel, t.subForest()), tree));
    }

}
