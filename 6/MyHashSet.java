
import java.util.*;
import java.security.*;
import java.io.*;

public class MyHashSet {

	// bit positions for 3rd hash function
	private static final int[] BITS = {2, 7,17,29,41,53,67,79,97,107,127,139,157,
			173,191, 199,227,239};

	
	private ArrayList<LinkedList<String>> myHashSet;
	private int hashMethod;
	private int collisions;

	
	// constructor
	public MyHashSet(int hashMethod, int tableSize) {
		myHashSet = new ArrayList<>();
		
		// create hash set
		for (int i = 0; i < tableSize; i++) {
			myHashSet.add(new LinkedList<>());
		}
		
		this.hashMethod = hashMethod;

	}

	
	// add word
	public void add(String word) {
		// get index
		int index = index(word);
		
		// get list
		LinkedList<String> list = myHashSet.get(index);
		
		// if collision
		if(!list.isEmpty())
			collisions++;
		
		// add word
		list.add(word);
		
	}

	
	// get index of word based on hash method
	private int index(String word) {
		if(hashMethod == 1)
			return hash1(word);
		else if(hashMethod == 2)
			return hash2(word);
		else
			return hash3(word);
	}
	
	
	// hash methods

	private int hash1(String word) {
		return Math.abs(word.hashCode()) % myHashSet.size();
	}

	private int hash2(String word) {
		int h;
		return ((word == null) ? 0 : (h = Math.abs(word.hashCode())) ^ (h >>> 16)) % myHashSet.size();
	}

	public int hash3(String word) {
		byte[] sb = word.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] key = md.digest(sb);
			BitSet bs = BitSet.valueOf(key);
			
			String binary = "";
			
			for (int i = 0; i < BITS.length; i++) {
				binary += bs.get(BITS[i]) ? 1 : 0;
			}
			
			
			return Integer.parseInt(binary, 2);
			
		} catch (NoSuchAlgorithmException e) {
			return -1;
		}

	}
	
	
	// get collisions
	public int getCollisions() {
		return collisions;
	}
	
	
	// get average list length
	public double getAverageListLength() {
		double sum = 0, count = 0;
		
		
		
		for (LinkedList<String> linkedList : myHashSet) {
			sum += linkedList.size();
			if(!linkedList.isEmpty())
				count++;
		}
		
		return sum / count;
	}

}
