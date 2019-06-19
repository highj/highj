package org.highj.data.instance;

import org.highj.data.Tree;
import org.highj.data.tuple.T2;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.data.Tree.newTree;
import static org.highj.data.Tree.unfold;

public class TreeTest {
    @Test
    public void testToString() throws Exception {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.toString()).isEqualTo("Tree(1,[Tree(2,[Tree(3,[])]),Tree(4,[])])");
    }

    @Test
    public void testDrawTree() throws Exception {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.drawTree()).isEqualTo("1\n|\n+- 2\n|  |\n|  `- 3\n|\n`- 4");
    }

    @Test
    public void testFlatten() throws Exception {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.flatten().toString()).isEqualTo("List(1,2,3,4)");
    }

    @Test
    public void testLevels() throws Exception {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.levels().toString()).isEqualTo("List(List(1),List(2,4),List(3))");
    }

    @Test
    public void testUnfoldTree() throws Exception {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(unfold(t -> T2.of(t.rootLabel, t.subForest()), tree)).isEqualTo(tree);
    }

}
