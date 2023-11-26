public class BubbleSorter implements ISorter {
    private final IChecker checker;

    public BubbleSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        // sort_2 jest bardziej optymalną wersją tego algorytmu
        // Ale ta wersja jest czytelniejsza

        boolean anySwapsMade = true;
        int lastUnsortedIndex = values.length - 1;

        while (anySwapsMade) {
            anySwapsMade = false;

            for (int left = 0; left < lastUnsortedIndex; left++) {
                if (values[left] > values[left + 1]) {
                    int buffer = values[left];
                    values[left] = values[left + 1];
                    values[left + 1] = buffer;
                    anySwapsMade = true;
                }
            }
            lastUnsortedIndex--;

            checker.check(values);
        }

    }

    void sort_2(int[] array) {
        int lastUnsortedIndex = array.length - 1;
        while (lastUnsortedIndex > 0) {
            int end = lastUnsortedIndex;
            lastUnsortedIndex = 0;
            for (int left = 0; left < end; ++left) {
                if (array[left] > array[left + 1]) {
                    int temp = array[left];
                    while (left < end && temp > array[left + 1]) {
                        array[left] = array[left + 1];
                        left++;
                    }
                    lastUnsortedIndex = left;
                    array[left] = temp;
                }
            }
            checker.check(array);
        }
    }
}
