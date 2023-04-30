import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 3, 8, 4, 0, 1, 6, 7};

        long startTime = System.currentTimeMillis();
        ConcurrentMergeSort.sort(arr, 8);
        long endTime = System.currentTimeMillis();

        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }
}

