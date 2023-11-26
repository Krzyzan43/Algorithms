import java.util.ArrayList;
import java.util.List;

public class KMPMatcher implements IStringMatcher {
    int[] prefixFunction;

    @Override
    public List<Integer> validShifts(String text, String pattern) {
        int[] prefixFunction = computePrefixFunction(pattern);
        int i = 0; // Index for text[]
        int j = 0; // Index for pattern[]
        List<Integer> result = new ArrayList<>();

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;

                if (j == pattern.length()) {
                    result.add(i - j);
                    j = prefixFunction[j - 1];
                }
            } else {
                if (j != 0) {
                    j = prefixFunction[j - 1];
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    private int[] computePrefixFunction(String pattern) {
        int[] prefixFunction = new int[pattern.length()];
        int m = 0;

        for (int i = 1; i < pattern.length(); i++) {
            while (m > 0 && pattern.charAt(m) != pattern.charAt(i))
                m = prefixFunction[m - 1];

            if (pattern.charAt(m) == pattern.charAt(i))
                m++;

            prefixFunction[i] = m;
        }

        return prefixFunction;
    }

}
