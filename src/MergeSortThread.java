import java.io.*;

public class MergeSortThread implements Runnable {

    private int[] array;
    private int inicio;
    private int fim;
    private int numThreads;

    public MergeSortThread(int[] array, int inicio, int fim, int numThreads) {
        this.array = array;
        this.inicio = inicio;
        this.fim = fim;
        this.numThreads = numThreads;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Lê o arquivo de entrada
        BufferedReader reader = new BufferedReader(new FileReader("input/A.in"));
        String[] values = reader.readLine().split(" ");
        int[] array = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = Integer.parseInt(values[i]);
        }
        reader.close();

        // Define o número de threads a serem usadas
        int numThreads = 10;

        // Executa o merge sort concorrente
        long startTime = System.currentTimeMillis();
        MergeSortThread mergeSortConcorrente = new MergeSortThread(array, 0, array.length - 1, numThreads);
        Thread t = new Thread(mergeSortConcorrente);
        t.start();
        t.join();
        long endTime = System.currentTimeMillis();

        // Escreve o resultado ordenado no arquivo de saída
        BufferedWriter writer = new BufferedWriter(new FileWriter("output/Thread_a.out"));
        for (int i = 0; i < array.length; i++) {
            writer.write(array[i] + " ");
        }
        writer.close();

        // Mostra o tempo de execução
        System.out.println("Tempo de execução: " + (endTime - startTime) + "ms");
    }

    @Override
    public void run() {
        mergeSort(array, inicio, fim);
    }

    private void mergeSort(int[] array, int inicio, int fim) {
        if (inicio < fim) {
            if (numThreads > 1) {
                // Divide o array em duas partes e cria novas threads para cada parte
                int meio = (inicio + fim) / 2;
                MergeSortThread threadEsquerda = new MergeSortThread(array, inicio, meio, numThreads / 2);
                Thread t1 = new Thread(threadEsquerda);
                MergeSortThread threadDireita = new MergeSortThread(array, meio + 1, fim, numThreads / 2);
                Thread t2 = new Thread(threadDireita);
                t1.start();
                t2.start();

                // Aguarda as threads terminarem antes de continuar
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Faz o merge das duas partes do array
                merge(array, inicio, meio, fim);
            } else {
                // Se não há threads disponíveis, faz o merge de forma sequencial
                int meio = (inicio + fim) / 2;
                mergeSort(array, inicio, meio);
                mergeSort(array, meio + 1, fim);
                merge(array, inicio, meio, fim);
            }
        }
    }

    private void merge(int[] array, int inicio, int meio, int fim) {
        int[] arrayAuxiliar = new int[fim - inicio + 1];
        for (int i = inicio; i <= fim; i++) {
            arrayAuxiliar[i - inicio] = array[i];
        }
        int i = inicio;
        int j = meio + 1;
        int k = inicio;
        while (i <= meio && j <= fim) {
            if (arrayAuxiliar[i - inicio] < arrayAuxiliar[j - inicio]) {
                array[k] = arrayAuxiliar[i - inicio];
                i++;
            } else {
                array[k] = arrayAuxiliar[j - inicio];
                j++;
            }
            k++;
        }
        while (i <= meio) {
            array[k] = arrayAuxiliar[i - inicio];
            i++;
            k++;
        }
        while (j <= fim) {
            array[k] = arrayAuxiliar[j - inicio];
            j++;
            k++;
        }
    }
}