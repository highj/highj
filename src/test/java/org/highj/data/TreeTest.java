package org.highj.data;

import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.data.Tree.newTree;
import static org.highj.data.Tree.unfold;

public class TreeTest {
    @Test
    public void testToString() {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.toString()).isEqualTo("Tree(1,[Tree(2,[Tree(3,[])]),Tree(4,[])])");
    }

    @Test
    public void testDrawTree() {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.drawTree()).isEqualTo("1\n|\n+- 2\n|  |\n|  `- 3\n|\n`- 4");
    }

    @Test
    public void testFlatten() {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.flatten().toString()).isEqualTo("List(1,2,3,4)");
    }

    @Test
    public void testLevels() {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(tree.levels().toString()).isEqualTo("List(List(1),List(2,4),List(3))");
    }

    @Test
    public void testUnfoldTree() {
        Tree<Integer> tree = newTree(1, newTree(2, newTree(3)), newTree(4));
        assertThat(unfold(t -> T2.of(t.rootLabel, t.subForest()), tree)).isEqualTo(tree);
    }

}
