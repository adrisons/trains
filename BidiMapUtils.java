import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class BidiMapUtils {

    /**
     * Get Map's key from value
     *
     * @param map
     * @param value
     * @param <K>
     * @param <V>
     * @return the key associated with value
     */
    public static <K, V> K getKey(BidiMap<K, V> map, V value) {
        return map.inverseBidiMap().get(value);
    }

    public static void main(String[] args) {
        BidiMap<String, Integer> bimap = new DualHashBidiMap<>();
        bimap.put("A", 1);
        bimap.put("B", 2);
        bimap.put("C", 3);

        System.out.println(getKey(bimap, 2));
    }

}
