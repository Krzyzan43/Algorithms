public class RadixSorter implements ISorter {
    private final IChecker checker;

    public RadixSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int maxVal = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > maxVal) {
                maxVal = values[i];
            }
        }

        int steps = (int) Math.ceil(Math.log10(maxVal));

        for (int i = 0; i < steps; i++) {
            countingSort(values, (int) Math.pow(10, i));
            checker.check(values);
        }
    }

    private static void countingSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(array[i] / exp) % 10]++;
        }

        count[0]--;
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int lastDigit = (array[i] / exp) % 10;
            output[count[lastDigit]] = array[i];
            count[lastDigit]--;
        }

        for (int i = 0; i < n; i++) {
            array[i] = output[i];
        }
    }
}
