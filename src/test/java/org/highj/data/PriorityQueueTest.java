package org.highj.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriorityQueueTest {

    static final String[] STRINGS = "the quick brown fox jumps over the lazy dog".split("\\s");
    static final String[] STRINGS_IC = "The quIck Brown fox JUmps over The Lazy dog".split("\\s");

    @Test
    public void size() {
        assertThat(PriorityQueue.<String>minQueue().size()).isEqualTo(0);
        PriorityQueue<String> queue = PriorityQueue.<String>minQueue().plus(STRINGS);
        assertThat(queue.size()).isEqualTo(9);
        assertThat(queue.drop().size()).isEqualTo(8);
        assertThat(queue.drop().drop().size()).isEqualTo(7);
        assertThat(queue.plus("penguin").size()).isEqualTo(10);
    }

    @Test
    public void isEmpty() {
        assertThat(PriorityQueue.<String>minQueue().isEmpty()).isTrue();
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.isEmpty()).isFalse();
    }

    @Test
    public void minQueue() {
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.toList()).containsExactly(
            "brown", "dog", "fox", "jumps", "lazy", "over", "quick", "the", "the");
        assertThat(queue.balanced());
    }

    @Test
    public void maxQueue() {
        PriorityQueue<String> queue = PriorityQueue.maxQueue(STRINGS);
        assertThat(queue.toList()).containsExactly(
            "the", "the", "quick", "over", "lazy", "jumps", "fox", "dog", "brown");
        assertThat(queue.balanced());
    }

    @Test
    public void minQueueCmp() {
        PriorityQueue<String> queue = PriorityQueue.minQueueCmp(String::compareToIgnoreCase, STRINGS_IC);
        assertThat(queue.toList()).containsExactly(
            "Brown", "dog", "fox", "JUmps", "Lazy", "over", "quIck", "The", "The");
        assertThat(queue.balanced());
    }

    @Test
    public void maxQueueCmp() {
        PriorityQueue<String> queue = PriorityQueue.maxQueueCmp(String::compareToIgnoreCase, STRINGS_IC);
        assertThat(queue.toList()).containsExactly(
            "The", "The", "quIck", "over", "Lazy", "JUmps", "fox", "dog", "Brown");
        assertThat(queue.balanced());
    }

    @Test
    public void peek() {
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.peek()).isEqualTo("brown");
        assertThat(queue.drop().peek()).isEqualTo("dog");
    }

    @Test
    public void plus() {
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.toList()).containsExactly(
            "brown", "dog", "fox", "jumps", "lazy", "over", "quick", "the", "the");
    }

    @Test
    public void drop() {
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.drop().drop().drop()).containsExactly(
            "jumps", "lazy", "over", "quick", "the", "the");
        assertThat(queue.drop().drop().drop().balanced()).isTrue();
    }

    @Test
    public void pop() {
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.pop()._1()).isEqualTo("brown");
        assertThat(queue.pop()._2()).containsExactly(
            "dog", "fox", "jumps", "lazy", "over", "quick", "the", "the");
    }

    @Test
    public void toList() {
        PriorityQueue<String> queue = PriorityQueue.minQueue(STRINGS);
        assertThat(queue.toList()).containsExactly(
            "brown", "dog", "fox", "jumps", "lazy", "over", "quick", "the", "the");
    }

}