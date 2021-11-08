import java.util.ArrayList;


public class SelectionTest {

	public static void main(String[] args) {
		
		final int SIZE  = 1000000;
		final int k = 100000;
		
		ArrayList<Integer> input = new ArrayList<>();
		for (int i = 0; i < SIZE; i++) {
			input.add((int) (Math.random() * SIZE));
		}
		
		
		Selection<Integer> selection = new Selection<>(k, input);
		
		
		long start = System.currentTimeMillis();
		System.out.println(selection.algo1B());
		long end = System.currentTimeMillis();
		System.out.println("Time by 1B = " + (end - start) / 1000.0 + " sec");
		
		
		 start = System.currentTimeMillis();
		System.out.println(selection.algo6A());
		 end = System.currentTimeMillis();
		System.out.println("Time by 6A = " + (end - start) / 1000.0 + " sec");
		
		 start = System.currentTimeMillis();
		System.out.println(selection.algo6B());
		end = System.currentTimeMillis();
		System.out.println("Time by 6B = " + (end - start) / 1000.0 + " sec");
		
		
		
		
	}
	
}
