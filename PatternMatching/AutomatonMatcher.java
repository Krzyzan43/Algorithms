import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class AutomatonMatcher implements IStringMatcher {
    HashMap<Character, Integer> charMap;
    int[][] stateMap;

    @Override
    public List<Integer> validShifts(String textToSearch, String patternToFind) {
        indexCharacters(textToSearch, patternToFind);
        createStateMap(patternToFind);

        return findValidShifts(textToSearch);
    }

    private void indexCharacters(String s1, String s2) {
        HashSet<Character> alphabet = new HashSet<>();
        alphabet.addAll(toCharList(s1));
        alphabet.addAll(toCharList(s2));

        charMap = new HashMap<>(alphabet.size());
        int index = 0;
        for (Character character : alphabet) {
            charMap.put(character, index);
            index++;
        }
    }

    private void createStateMap(String pattern) {
        String[] states = new String[pattern.length() + 1];
        stateMap = new int[states.length][charMap.size()];
        for (int i = 0; i < states.length; i++) {
            states[i] = pattern.substring(0, i);
        }

        for (int q = 0; q < states.length; q++) {
            for (Entry<Character, Integer> entry : charMap.entrySet()) {
                String nextPattern = states[q] + entry.getKey();
                int k = Integer.min(q + 1, states.length - 1);

                stateMap[q][entry.getValue()] = 0;
                while (k > 0) {
                    if (nextPattern.endsWith(states[k])) {
                        stateMap[q][entry.getValue()] = k;
                        break;
                    }
                    k--;
                }
            }
        }
    }

    private List<Integer> findValidShifts(String textToSearch) {
        int state = 0;
        int lastState = stateMap.length - 1;
        ArrayList<Integer> shifts = new ArrayList<>();

        for (int i = 0; i < textToSearch.length(); i++) {
            int c = charMap.get(textToSearch.charAt(i));

            state = stateMap[state][c];
            if (state == lastState)
                shifts.add(i - lastState + 1);
        }
        return shifts;
    }

    private List<Character> toCharList(String s) {
        Character[] characters = new Character[s.length()];
        for (int i = 0; i < s.length(); i++) {
            characters[i] = s.charAt(i);
        }
        return Arrays.asList(characters);
    }
}
