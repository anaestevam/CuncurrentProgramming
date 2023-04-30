import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentMergeSort {

    private static ExecutorService executorService;
    private static int numThreads;

    public static void sort(int[] arr, int numThreads) {
        executorService = Executors.newFixedThreadPool(numThreads);
        ConcurrentMergeSort.numThreads = numThreads;
        mergeSort(arr, 0, arr.length - 1);
        executorService.shutdown();
    }

    private static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;

            // Classifica a metade esquerda do array usando um novo thread
            if (numThreads > 1) {
                executorService.execute(() -> mergeSort(arr, start, mid));
            } else {
                mergeSort(arr, start, mid);
            }

            // Classifica a metade direita do array usando um novo thread
            if (numThreads > 1) {
                executorService.execute(() -> mergeSort(arr, mid + 1, end));
            } else {
                mergeSort(arr, mid + 1, end);
            }

            // Espera que ambas as threads terminem
            if (numThreads > 1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Mescla as duas metades classificadas
            merge(arr, start, mid, end);
        }
    }

    private static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];

        int i = start;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= end) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= end) {
            temp[k++] = arr[j++];
        }

        for (i = start; i <= end; i++) {
            arr[i] = temp[i - start];
        }
    }
}
