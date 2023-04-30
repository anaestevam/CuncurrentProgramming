import java.io.*;
import java.util.*;

public class MergeSortThread {
    
    private static class MergeSortTask implements Runnable {
        private int[] arr;
        private int start;
        private int end;
        
        public MergeSortTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }
        
        @Override
        public void run() {
            if (end - start <= 1) {
                return;
            }
            int mid = (start + end) / 2;
            MergeSortTask left = new MergeSortTask(arr, start, mid);
            MergeSortTask right = new MergeSortTask(arr, mid, end);
            Thread leftThread = new Thread(left);
            Thread rightThread = new Thread(right);
            leftThread.start();
            rightThread.start();
            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            merge(arr, start, mid, end);
        }
        
        private void merge(int[] arr, int start, int mid, int end) {
            int[] temp = new int[end - start];
            int i = start;
            int j = mid;
            int k = 0;
            while (i < mid && j < end) {
                if (arr[i] < arr[j]) {
                    temp[k] = arr[i];
                    i++;
                } else {
                    temp[k] = arr[j];
                    j++;
                }
                k++;
            }
            while (i < mid) {
                temp[k] = arr[i];
                i++;
                k++;
            }
            while (j < end) {
                temp[k] = arr[j];
                j++;
                k++;
            }
            System.arraycopy(temp, 0, arr, start, temp.length);
        }
    }
    
    public static void main(String[] args) {
        String inputFile = "input/A.in";
        String outputFile = "output/saida_a.out";
        try {
            Scanner scanner = new Scanner(new File(inputFile));
            String[] tokens = scanner.nextLine().split(" ");
            int[] arr = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                arr[i] = Integer.parseInt(tokens[i]);
            }
            MergeSortTask task = new MergeSortTask(arr, 0, arr.length);
            Thread thread = new Thread(task);
            long startTime = System.currentTimeMillis();
            thread.start();
            thread.join();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (int i = 0; i < arr.length; i++) {
                writer.write(Integer.toString(arr[i]) + " ");
            }
            writer.close();

         // Registrando o tempo de término da execução e calculando a duração total
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Imprimindo a duração total no console
            System.out.println("Tempo de execução: " + duration + "ms");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
