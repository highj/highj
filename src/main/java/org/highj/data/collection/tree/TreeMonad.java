package org.highj.data.collection.tree;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Tree;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

public class TreeMonad implements Monad<Tree.µ> {
    @Override
    public <A, B> Tree<B> bind(_<Tree.µ, A> nestedA, Function<A, _<Tree.µ, B>> fn) {
        //Node x ts >>= f = Node x' (ts' ++ map (>>= f) ts)
        //  where Node x' ts' = f x
        Tree<A> treeA = Tree.narrow(nestedA);
        A x = treeA.rootLabel;
        List<Tree<A>> ts = treeA.subForest();
        Tree<B> treeB = Tree.narrow(fn.apply(x));
        return Tree.<B>TreeLazy(treeB.rootLabel, () -> List.append(treeB.subForest(), ts.map(t -> bind(t,fn))));
    }

    @Override
    public <A> Tree<A> pure(A a) {
        return Tree.Tree(a, List.empty());
    }

    @Override
    public <A, B> Tree<B> map(Function<A, B> fn, _<Tree.µ, A> nestedA) {
        Tree<A> tree = Tree.narrow(nestedA);
        return Tree.<B>TreeLazy(fn.apply(tree.rootLabel), () -> tree.subForest().map(t -> Tree.narrow(map(fn, t))));
    }

    @Override
    public <A, B> Tree<B> ap(_<Tree.µ, Function<A, B>> fn, _<Tree.µ, A> nestedA) {
        //Node f tfs <*> tx@(Node x txs) =
        //  Node (f x) (map (f <$>) txs ++ map (<*> tx) tfs)
        Tree<Function<A, B>> treeFn = Tree.narrow(fn);
        Function<A, B> f = treeFn.rootLabel;
        List<Tree<Function<A, B>>> tfs = treeFn.subForest();
        Tree<A> tx = Tree.narrow(nestedA);
        A x = tx.rootLabel;
        List<Tree<A>> txs = tx.subForest();
        return Tree.<B>TreeLazy(f.apply(x),
                () -> List.<Tree<B>>append(txs.map(as -> map(a -> f.apply(a), as)),
                        tfs.map(fun -> ap(fun, tx)))
        );
    }
}

