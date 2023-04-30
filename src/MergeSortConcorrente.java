import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class MergeSortConcorrente {
    
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        // Lê o arquivo de entrada
        File inputFile = new File("input/A.in");
        Scanner scanner = new Scanner(inputFile);
        String[] values = scanner.nextLine().split(" ");
        int[] arr = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            arr[i] = Integer.parseInt(values[i]);
        }
        scanner.close();
        
        // Define o número de threads que serão utilizadas
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        // Realiza o merge sort concorrente
        long startTime = System.nanoTime();
        int[] sortedArr = mergeSortConcorrente(arr, executor);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Converte para milissegundos
        
        executor.shutdown(); // Encerra a execução das threads
        
        // Escreve o arquivo de saída
        File outputFile = new File("output/saida.out");
        FileWriter writer = new FileWriter(outputFile);
        for (int i = 0; i < sortedArr.length; i++) {
            writer.write(sortedArr[i] + " ");
        }
        writer.close();
        
        // Imprime o tempo de execução
        System.out.println("Tempo de execução: " + duration + "ms");
    }
    
    public static int[] mergeSortConcorrente(int[] arr, ExecutorService executor) throws InterruptedException, ExecutionException {
        int n = arr.length;
        if (n < 2) {
            return arr;
        }
        
        int mid = n / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, n);
        
        Future<int[]> leftResult = executor.submit(() -> mergeSortConcorrente(left, executor));
        Future<int[]> rightResult = executor.submit(() -> mergeSortConcorrente(right, executor));
        
        int[] sortedLeft = leftResult.get();
        int[] sortedRight = rightResult.get();
        
        return merge(sortedLeft, sortedRight);
    }
    
    public static int[] merge(int[] left, int[] right) {
        int n = left.length + right.length;
        int[] result = new int[n];
        
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        while (i < left.length) {
            result[k++] = left[i++];
        }
        
        while (j < right.length) {
            result[k++] = right[j++];
        }
        
        return result;
    }
}
