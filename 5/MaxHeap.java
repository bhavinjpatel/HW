package javaapplication;

import java.util.*;
public class MaxHeap<E extends Comparable<E>> extends ArrayList<E>   {
    private ArrayList<E> heap;
    private int size;
    // construct an empty Heap using ArrayList
    // with root at index 0.
    // don't use binary tree for implementing the heap.
    public MaxHeap(){
            heap = new ArrayList<>();
            size = 0;
    }
    private void heapify(int ind) {
        int lchild = (2 * ind) + 1;
        int rchild = (2 * ind) + 2;
        if (heap.get(ind).compareTo(heap.get(lchild)) < 0  || heap.get(ind).compareTo(heap.get(rchild)) < 0) {
            if (heap.get(lchild).compareTo(heap.get(rchild)) > 0) {
                E temp = heap.get(ind);
                heap.add(ind, heap.get(lchild));
                heap.add(lchild, temp);
                heapify(lchild);
            }
            else {
                E temp = heap.get(ind);
                heap.add(ind, heap.get(rchild));
                heap.add(rchild, temp);
                heapify(rchild);
            }
        }
    }
    // returns max value
    public E findMax() {
        E max = heap.get(0);
        return max;
    }
    
    // adds a new value to the heap at the end of the Heap and 
    // adjusts values up to the root to ensure Max heap property is satisfied.
    // parent of node at i is given by the formula (i-1)/2
    // throw appropriate exception
    public void addHeap(E val) {
        heap.add(size, val);
        int curr = size;
        int par = (curr - 1) / 2;
        while (heap.get(curr).compareTo(heap.get(par)) > 0) {
            E temp = heap.get(curr);
            heap.add(curr, heap.get(par));
            heap.add(par, temp);
            curr = (curr - 1) / 2;
        }
        size++;
    }
    
    //returns the max value at the root of the heap by swapping the last value 
    // and percolating the value down from the root to preserve max heap property
    // children of node at i are given by the formula 2i+1,2i+2, to not exceed the
    // bounds of the Heap index, namely, 0 ... size()-1.
    // throw appropriate exception
    public E removeHeap() {
        E rem = heap.get(0);
        heap.remove(0);
        heap.add(0, heap.get(size-1));
        heap.remove(size-1);
        size--;
        heapify(0);
        return rem;
    }
    
    // takes a list of items E and builds the heap and then prints 
    // decreasing values of E with calls to removeHeap().  
    public void heapSort(List<E> list){
        this.buildHeap(list);
        int s = size;
        for(int i=0;i<s/2;i++){
            System.out.print(removeHeap()+" ");
        }
        System.out.println();
    }
    
    // merges the other maxheap with this maxheap to produce a new maxHeap.  
    public MaxHeap<E> heapMerge(MaxHeap<E> other){
        MaxHeap<E> max = new MaxHeap<>();
        max.heap.addAll(this.heap);
        max.heap.removeAll(other.heap);
        max.heap.addAll(other.heap);
        max.heapify(0);
        return max;
    }
    
    //takes a list of items E and builds the heap by calls to addHeap(..)
    public void buildHeap(List<E> list) {
        for(int i=0;i<list.size();i++){
            addHeap(list.get(i));
        }
    }
    
    public void print(){
        for(int i=0;i<size/2;i++){
            System.out.println("Parent: " + heap.get(i));
            System.out.println("Left Child: " + heap.get((i*2)+1));
            System.out.println("Right Child: " + heap.get((i*2)+2));
        }
        System.out.println();
    }
    public static void main(String args[]){
        MaxHeap<Integer> obj = new MaxHeap<>();
        for(Integer i=1;i<=10;i++){
            obj.addHeap(i);
        }
        obj.print();
    }
}