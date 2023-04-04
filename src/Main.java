import java.util.Random;
// Write a program that implements a parallel array sum, comparing the performance between a single and multi-thread.
// Make an array of 200 million random numbers between 1-10. Computer the sum as well as the time it takes to compute
// each sum.
public class Main {
    // Variables created, arrays listed
    private static int count = 0;
    private static int count2 = 0;

    private static long singleThreadSum = 0;
    private static long multiThreadSum1 = 0;
    private static long multiThreadSum2 = 0;

    private static long multiThreadSumFinal = 0;

    public static int[] oneMillion = new int [100000000];
    public static int[] twoMillion = new int [200000000];
    public static synchronized void inccount() {
        count++;
    }
    public static synchronized void inccount2() {
        count2++;
    }

    // Calculates the sum of our single thread
    public static long singleThreadSum(int[] twoMillion) {
        long sum = 0;
        for (int i = 0; i < twoMillion.length; i++) {
            sum += twoMillion[i];
        }
        return sum;
    }
    // Calculates the sum of the first thread in our multi-thread
    public static long multiThreadSum1(int[] oneMillion) {
        long sum = 0;
        for (int i = 0; i < oneMillion.length; i++) {
            sum += oneMillion[i];
        }
        return sum;
    }
    // Calculates the sum of the second thread in our multi-thread
    public static long multiThreadSum2(int[] oneMillion) {
        long sum = 0;
        for (int i = 0; i < oneMillion.length; i++) {
            sum += oneMillion[i];
        }
        return sum;
    }
    public static void main(String[] args) {
        long timeStarted = System.currentTimeMillis();
        singleThreadSum = singleThreadSum(twoMillion);
        multiThreadSum1 = multiThreadSum1(oneMillion);
        multiThreadSum2 = multiThreadSum2(oneMillion);

        long singleThreadTime;
        Random random = new Random();
        // First of two threads for our Multi-threaded solution
        // Runs a for loop that generates a random number for each element in a 1 million element array
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < oneMillion.length; i++) {
                    oneMillion[i] = random.nextInt(10) + 1;
                    inccount();
                }
                multiThreadSum2 = multiThreadSum2(oneMillion);
            }
        });
        // Second of two threads for our Multi-threaded solution
        // Runs a for loop that generates a random number for each element in a 1 million element array
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < oneMillion.length; i++) {
                    oneMillion[i] = random.nextInt(10) + 1;
                    inccount();
                }
                multiThreadSum1 = multiThreadSum1(oneMillion);
            }
        });
        // Single thread
        // Runs a for-loop that generates a random number for each element in a 2 million element array
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < twoMillion.length; i++) {
                    twoMillion[i] = random.nextInt(10) + 1;
                    inccount2();

                }
                singleThreadSum = singleThreadSum(twoMillion);
                System.out.println("Single Thread Sum: " + singleThreadSum);
            }
        });
        // Initiates all of our threads: Our two multi-threads and a single thread
        t1.start();
        t2.start();
        t3.start();

        // Attempts to join the two single-threads together
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Prints our final sums
        System.out.println("Single Thread Value: " + count2);
        multiThreadSumFinal = multiThreadSum2 + multiThreadSum1;
        System.out.println("Multi Thread Value: " + multiThreadSumFinal);

    }
}




