import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTableTest {

	public static void main(String[] args) throws FileNotFoundException {
		int[] TABLE_SIZES = {262127, 262127, 262144};
		
		
		// read words
		ArrayList<String> words = readWords("EnglishWordList.txt");
		
		
		// for each hash method
		for (int hashMethod = 1; hashMethod <= 3; hashMethod++) {
			
			// create hash set
			MyHashSet table = new MyHashSet(hashMethod, TABLE_SIZES[hashMethod - 1]);
			
			
			// time the insertion
			
			long start = System.currentTimeMillis();
			
			for (String word : words) {
				table.add(word);
			}
			
			long end = System.currentTimeMillis();
			
			
			// print results
			System.out.println("Hash method: #"+hashMethod);
			System.out.println("Total collisions: " + table.getCollisions());
			System.out.println("Avg size of linked list: " + table.getAverageListLength());
			System.out.println("Time taken: " + (end - start) + " ms");
			System.out.println();
		}
		
		
		
	}
	
	
	
	private static ArrayList<String>  readWords(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));
		
		ArrayList<String> words = new ArrayList<>();
		while(scanner.hasNextLine()) {
			words.add(scanner.nextLine());
		}
		
		scanner.close();
		
		return words;
	}
}
