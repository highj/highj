package org.highj.data.instance.tree;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.Tree;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.Hkt.asTree;

public class TreeMonad implements Monad<Tree.µ> {
    @Override
    public <A, B> Tree<B> bind(__<Tree.µ, A> nestedA, Function<A, __<Tree.µ, B>> fn) {
        //Node x ts >>= f = Node x' (ts' ++ map (>>= f) ts)
        //  where Node x' ts' = f x
        Tree<A> treeA = asTree(nestedA);
        A x = treeA.rootLabel;
        List<Tree<A>> ts = treeA.subForest();
        Tree<B> treeB = asTree(fn.apply(x));
        return Tree.newLazyTree(treeB.rootLabel, () -> List.append(treeB.subForest(), ts.map(t -> bind(t, fn))));
    }

    @Override
    public <A> Tree<A> pure(A a) {
        return Tree.newTree(a, List.empty());
    }

    @Override
    public <A, B> Tree<B> map(Function<A, B> fn, __<Tree.µ, A> nestedA) {
        Tree<A> tree = asTree(nestedA);
        return Tree.newLazyTree(fn.apply(tree.rootLabel), () -> tree.subForest().map(t -> asTree(map(fn, t))));
    }

    @Override
    public <A, B> Tree<B> ap(__<Tree.µ, Function<A, B>> fn, __<Tree.µ, A> nestedA) {
        //Node f tfs <*> tx@(Node x txs) =
        //  Node (f x) (map (f <$>) txs ++ map (<*> tx) tfs)
        Tree<Function<A, B>> treeFn = asTree(fn);
        Function<A, B> f = treeFn.rootLabel;
        List<Tree<Function<A, B>>> tfs = treeFn.subForest();
        Tree<A> tx = asTree(nestedA);
        A x = tx.rootLabel;
        List<Tree<A>> txs = tx.subForest();
        return Tree.newLazyTree(f.apply(x),
                () -> List.<Tree<B>>append(txs.map(as -> map(f, as)),
                        tfs.map(fun -> ap(fun, tx)))
        );
    }
}

