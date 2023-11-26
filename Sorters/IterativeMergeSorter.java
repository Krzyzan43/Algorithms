public class IterativeMergeSorter implements ISorter {
    private final IChecker checker;

    // at any time one buffer is holding reference to input array
    // so additional memory complexity is still O(n)
    private int[] readBuffer;
    private int[] writeBuffer;

    public IterativeMergeSorter(IChecker checker) {
        this.checker = checker;
    }

    @Override
    public void sort(int[] values) {
        readBuffer = values;
        writeBuffer = new int[values.length];

        for (int arrSize = 2; arrSize < values.length; arrSize *= 2) {
            int halfSize = arrSize / 2;

            for (int left = 0; left < values.length; left += arrSize) {
                int middle = Math.min(left + halfSize, values.length);
                int right = Math.min(left + arrSize, values.length);

                merge(left, middle, right);
            }

            checker.check(writeBuffer);
            swapBuffers();
        }

        if (writeBuffer != values) {
            for (int i = 0; i < values.length; i++) {
                values[i] = writeBuffer[i];
            }
        }
    }

    private void merge(int start, int middle, int end) {
        int left = start, right = middle;
        int index = start;

        // Merge until one pointer is at the end of its side
        while (left < middle && right < end) {
            if (readBuffer[left] <= readBuffer[right]) {
                writeBuffer[index++] = readBuffer[left++];
            } else {
                writeBuffer[index++] = readBuffer[right++];
            }
        }

        // flush left side
        while (left < middle) {
            writeBuffer[index++] = readBuffer[left++];
        }

        // flush right side
        while (right < end) {
            writeBuffer[index++] = readBuffer[right++];
        }
    }

    private void swapBuffers() {
        int[] temp = writeBuffer;
        writeBuffer = readBuffer;
        readBuffer = temp;
    }
}
