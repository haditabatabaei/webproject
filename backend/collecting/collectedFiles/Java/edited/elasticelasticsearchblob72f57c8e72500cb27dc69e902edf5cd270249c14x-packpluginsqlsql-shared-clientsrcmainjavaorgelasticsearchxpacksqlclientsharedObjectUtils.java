
package org.elasticsearch.xpack.sql.client.shared;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public abstract class ObjectUtils {

    public static boolean isEmpty(int[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(byte[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    public static <K, E extends Enum<E>> Map<K, E> mapEnum(Class<E> clazz, Function<E, K> mapper) {
        return Arrays.stream(clazz.getEnumConstants()).collect(toMap(mapper, Function.identity()));
    }
}
