import java.util.Random;
public class summer extends Thread {
        private int[] numberArr;
        private int low, high, partial;
    public summer(int[] numberArr, int low, int high){
            this.numberArr = numberArr;
            this.low = low;
            this.high = Math.min(high, numberArr.length);
    }
    public int getPartialSum()
        {
            return partial;
        }
        public void run()
        {
            partial = sum(numberArr, low, high);
        }
    public static int sum(int[] arr)

        {
            return sum(arr, 0, arr.length);
        }

    public static int sum(int[] numberArr, int low, int high){
            int total = 0;
            for (int i = low; i < high; i++) {
                total += numberArr[i];
            }
            return total;
        }
    public static int parallelSum(int[] numberArr)
        {
            return parallelSum(numberArr, Runtime.getRuntime().availableProcessors());
        }
    public static int parallelSum(int[] numberArr, int threads){
            int size = (int) Math.ceil(numberArr.length * 1.0 / threads);
            summer[] sums = new summer[threads];
            for (int i = 0; i < threads; i++) {
                sums[i] = new summer(numberArr, i * size, (i + 1) * size);
                sums[i].start();

            }
            try {
                for (summer sum : sums) {
                    sum.join();
                }
            } catch (InterruptedException e) { }
            int total = 0;
            for (summer sum : sums) {
                total += sum.getPartialSum();
            }
            return total;
        }

    public static void main(String[] args) {
        int[] numberArr = new int[200000000];

        Random randam = new Random();

        for (int i = 0; i < numberArr.length; i++) {
            numberArr[i] = randam.nextInt(10) + 1;
        }
        long start = System.currentTimeMillis();
        System.out.println(summer.sum(numberArr) + " (Single Thread)");

        System.out.println("Single: " + (System.currentTimeMillis() - start) + " in milliseconds");
            start = System.currentTimeMillis();
            System.out.println(summer.parallelSum(numberArr) + " (Parallel Thread)");
            System.out.println("Parallel: " + (System.currentTimeMillis() - start) + " in milliseconds");

        }

    }
