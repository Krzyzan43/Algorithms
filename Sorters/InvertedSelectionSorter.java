public class InvertedSelectionSorter implements ISorter {
    private final IChecker checker;

    public InvertedSelectionSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] array) {
        int selectionStart = array.length - 1;

        while (selectionStart > 0) {

            // look for max value
            int maxValue = array[selectionStart];
            int maxValueIndex = selectionStart;
            for (int i = selectionStart; i >= 0; i--) {
                if (array[i] > maxValue) {
                    maxValue = array[i];
                    maxValueIndex = i;
                }
            }

            // swap first unsorted index with max value
            array[maxValueIndex] = array[selectionStart];
            array[selectionStart] = maxValue;

            selectionStart--;
            checker.check(array);
        }
    }
}
