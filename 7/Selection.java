import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Selection<E extends Comparable<E>> {
	int k;
	ArrayList<E> input; // this holds the values of type E from which your code will find kth largest.
// constructors

	public Selection(int k, ArrayList<E> input) {
		this.k = k;
		this.input = new ArrayList<>(input);

	}

// algorithm 1 methods -- implement 1B
	public E algo1B() {
		E[] array = (E[]) new Comparable[k];
		int index = 0;
		for (int i = 0; i < k; i++) {
			array[i] = input.get(index++);
		}
		
		
		Arrays.sort(array, Comparator.reverseOrder());
		
		
		for (int i = index; i < input.size(); i++) {
			E key = input.get(i);
			
			
			if(array[k-1].compareTo(key) < 0) {
				array[k-1] = key;
				
				int j = k - 2;
				 
	            while (j >= 0 && array[j].compareTo(key) < 0) {
	                array[j + 1] = array[j];
	                j = j - 1;
	            }
	            array[j + 1] = key;
			}
			
			
		}
		
		
		return array[k - 1];
		
	}
	
	


// algorithm 2 methods -- 6A -- change the algorithm to do kth largest not kth smallest that is described here

	public E algo6A() {
		BinaryHeap<E> heap = new BinaryHeap<>(input);
		
		
		for (int i = 0; i < k - 1; i++) {
			heap.deleteMax();
		}
		
		return heap.deleteMax();
	}

// algorithm 3 methods – 6B
	public E algo6B() {
		PriorityQueue<E> queue = new PriorityQueue<>(input.subList(0, k));
		
		
		
		for (int i = k; i < input.size(); i++) {
			E key = input.get(i);
			
			
			if(queue.peek().compareTo(key) < 0) {
				queue.remove();
				queue.add(key);
			}
			
			
			
			
		}
		
		
		return queue.remove();
		
		
	}
	
	
	
	
}