/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.collection;

import java.util.Arrays;

/**
 *
 * @author clintonselke
 */
public class IntMap<A> {
    private static final int NUM_BRANCHING_BITS = 5;
    private static final int NUM_BRACHES = 1 << NUM_BRANCHING_BITS;
    private static final int MASK = (1 << NUM_BRANCHING_BITS) - 1;
    
    private final Node<A> root;
    
    private IntMap(Node<A> root) {
        this.root = root;
    }
    
    private static final IntMap<?> EMPTY = new IntMap<>(Empty.EMPTY);
    
    public static <A> IntMap<A> empty() {
        return (IntMap<A>)EMPTY;
    }
    
    private static abstract class Node<A> {
        public interface Cases<R,A> {
            R empty();
            R leaf(A value);
            R branch(Node<A>[] nodes);
        }
        
        public abstract boolean isEmpty();
        public abstract <R> R match(Cases<R,A> cases);
    }
    
    private static class Empty<A> extends Node<A> {
        private static final Empty<?> EMPTY = new Empty<>();
        
        public <A> Empty<A> empty() {
            return (Empty<A>)EMPTY;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public <R> R match(Cases<R, A> cases) {
            return cases.empty();
        }
    }
    
    private static class Leaf<A> extends Node<A> {
        private final A value;
        
        public Leaf(A value) {
            this.value = value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public <R> R match(Cases<R, A> cases) {
            return cases.leaf(value);
        }
    }
    
    private static class Branch<A> extends Node<A> {
        private final Node<A>[] nodes;
        
        public Branch(Node<A>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public <R> R match(Cases<R, A> cases) {
            return cases.branch(nodes);
        }
        
        public Branch<A> set(int idx, Node<A> node) {
            Node<A>[] nodes2 = Arrays.copyOf(nodes, nodes.length);
            nodes2[idx] = node;
            return new Branch<>(nodes2);
        }
    }
}
