import java.io.*;
import java.util.*;

public class MergeSortSequencial {

    public static void main(String[] args) {
        String inputFile = "input/A.in";
        String outputFile = "output/sequencial_a.out";

        try {
            // Leitura do arquivo de entrada
            Scanner scanner = new Scanner(new File(inputFile));
            List<Integer> list = new ArrayList<>();
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
            scanner.close();

            // Conversão da lista para array
            int[] arr = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i);
            }

            // Ordenação e medição do tempo de execução
            long startTime = System.currentTimeMillis();
            mergeSort(arr);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Escrita do arquivo de saída
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
            for (int i = 0; i < arr.length; i++) {
                writer.println(arr[i]);
            }
            writer.close();

            // Exibição do tempo de execução
            System.out.println("Tempo de execução: " + duration + "ms");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeSort(int[] arr) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] left = Arrays.copyOfRange(arr, 0, mid);
            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

            mergeSort(left);
            mergeSort(right);

            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                }
                k++;
            }

            while (i < left.length) {
                arr[k] = left[i];
                i++;
                k++;
            }

            while (j < right.length) {
                arr[k] = right[j];
                j++;
                k++;
            }
        }
    }
}
