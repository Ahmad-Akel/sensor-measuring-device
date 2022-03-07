package com.example.akel_semprace.Logic;

public class LeftHeapNode<E> {
    E element;
    LeftHeapNode<E> left;
    LeftHeapNode<E> right;
    int npl;

    // Constructors
    LeftHeapNode(E theElement, LeftHeapNode<E> lt, LeftHeapNode<E> rt) {
        element = theElement;
        left = lt;
        right = rt;
        npl = 0;
    }

    LeftHeapNode(E theElement) {
        this(theElement, null, null);
    }


}
