public class SelectionSorter implements ISorter {
    private final IChecker checker;

    public SelectionSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] array) {

        int selectionStart = 0;

        while (selectionStart < array.length - 1) {

            // look for min value
            int minValue = array[selectionStart];
            int minValueIndex = selectionStart;
            for (int i = selectionStart; i < array.length; i++) {
                if (array[i] < minValue) {
                    minValue = array[i];
                    minValueIndex = i;
                }
            }

            // swap first unsorted index with minimum value
            array[minValueIndex] = array[selectionStart];
            array[selectionStart] = minValue;

            selectionStart++;
            checker.check(array);
        }

    }
}
