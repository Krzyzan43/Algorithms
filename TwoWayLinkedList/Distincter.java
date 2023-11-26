public class Distincter {
    public static TwoWayLinkedList<Integer> distinct(TwoWayLinkedList<Integer> list)
    {
        // TODO: Zwróć nową listę zawierającą unikalne wartości w liście źródłowej.
        // Możesz założyć, że lista na wejściu jest posortowana.
        // Przykład: [1, 1, 2, 3, 3] -> [1, 2, 3]

        TwoWayLinkedList<Integer> distinctList = new TwoWayLinkedList<>();
        Integer lastValue = null;

        for (Integer value : list) {
            if(value != lastValue) {
                distinctList.add(value);
                lastValue = value;
            }
        }

        
        return distinctList;
    }
}
