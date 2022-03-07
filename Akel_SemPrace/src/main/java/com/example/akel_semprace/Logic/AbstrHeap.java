package com.example.akel_semprace.Logic;


public class AbstrHeap<E extends Comparable<? super E>> {
    private LeftHeapNode<E> root;
    private int size;

    /**
     * Construct the leftist heap.
     */
    public AbstrHeap() {
        root = null;
    }

    /**
     * Merge rhs into the priority queue.
     * rhs becomes empty. rhs must be different from this.
     *
     * @param rhs the other leftist heap.
     */
    public void merge(AbstrHeap<E> rhs) {
        if (this == rhs)    // Avoid aliasing problems
            return;

        root = merge(root, rhs.root);
        size += rhs.getSize();
        rhs.root = null;
    }

    /**
     * Internal static method to merge two roots.
     */
    private static <T extends Comparable<? super T>> LeftHeapNode<T>
    merge(LeftHeapNode<T> h1, LeftHeapNode<T> h2) {
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;
        if (h1.element.compareTo(h2.element) > 0)
        // Swap the references of h1 and h2
        {
            LeftHeapNode<T> temp = h1;
            h1 = h2;
            h2 = temp;
        }
        if (h1.left == null)   // Single node
            h1.left = h2;
        else {
            h1.right = merge(h1.right, h2);
            if (h1.left.npl < h1.right.npl) { // Swap h1's children
                LeftHeapNode<T> temp = h1.left;
                h1.left = h1.right;
                h1.right = temp;
            }
            h1.npl = h1.right.npl + 1;// counting root in npl
        }
        return h1;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     *
     * @param x the item to insert.
     */
    public void insert(E x) {
        root = merge(root, new LeftHeapNode<E>(x));
        size++;
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or null, if empty.
     */
    public E findMin() {
        return root == null ? null : root.element;
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or null, if empty.
     */
    public E deleteMin() {
        if (root == null)
            return null;

        E minItem = root.element;
        root = merge(root.left, root.right);
        size--;
        return minItem;
    }

    /**
     * Return this heap as an ordered array.
     *
     * @param incr the flag to control order.
     */

    @SuppressWarnings("unchecked")
    public E[] toArray(E[] a, boolean incr) {

        int capacity = size;// We must copy the size first
        int i;

        if (a.length < capacity)
            a = (E[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), capacity);
        if (incr) // Incremental order
            for (i = 0; i < capacity; i++)
                a[i] = deleteMin();
        else // Decremental order
            for (i = capacity; i-- > 0; )
                a[i] = deleteMin();

        if (a.length > capacity)
            a[capacity] = null;

        return a;
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return the size of this heap.
     */
    public int getSize() {
        return size;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

}