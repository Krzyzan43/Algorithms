public class InsertionSorter implements ISorter {
    private final IChecker checker;

    public InsertionSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        int sortedAmount = 1;

        while (sortedAmount < values.length) {
            int insertedDigitIndex = sortedAmount;
            int insertDigit = values[insertedDigitIndex];

            // Search where digit should be inserted
            int swapIndex = 0;
            while (values[swapIndex] < insertDigit && swapIndex < insertedDigitIndex) {
                swapIndex++;
            }

            // Move digits to the right
            for (int i = insertedDigitIndex; i > swapIndex; i--) {
                values[i] = values[i - 1];
            }

            // insert value
            values[swapIndex] = insertDigit;
            sortedAmount++;
            checker.check(values);
        }
    }
}
