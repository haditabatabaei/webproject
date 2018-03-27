

package com.google.common.collect.testing.google;

import static com.google.common.collect.testing.Helpers.copyToList;
import static com.google.common.collect.testing.Helpers.copyToSet;
import static com.google.common.collect.testing.features.CollectionSize.ZERO;
import static com.google.common.collect.testing.features.MapFeature.ALLOWS_NULL_VALUES;
import static com.google.common.collect.testing.features.MapFeature.SUPPORTS_PUT;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Ignore;


@GwtCompatible
@Ignore public class SetMultimapPutTester<K, V> extends AbstractMultimapTester<K, V, SetMultimap<K, V>> {
  
  @MapFeature.Require(SUPPORTS_PUT)
  @CollectionSize.Require(absent = ZERO)
  public void testPutDuplicateValuePreservesSize() {
    assertFalse(multimap().put(k0(), v0()));
    assertEquals(getNumElements(), multimap().size());
  }

  @MapFeature.Require(SUPPORTS_PUT)
  public void testPutDuplicateValue() {
    List<Entry<K, V>> entries = copyToList(multimap().entries());

    for (Entry<K, V> entry : entries) {
      resetContainer();
      K k = entry.getKey();
      V v = entry.getValue();

      Set<V> values = multimap().get(k);
      Set<V> expectedValues = copyToSet(values);

      assertFalse(multimap().put(k, v));
      assertEquals(expectedValues, values);
      assertGet(k, expectedValues);
    }
  }

  @MapFeature.Require({SUPPORTS_PUT, ALLOWS_NULL_VALUES})
  @CollectionSize.Require(absent = ZERO)
  public void testPutDuplicateValue_null() {
    initMultimapWithNullValue();
    assertFalse(multimap().put(getKeyForNullValue(), null));
    expectContents(createArrayWithNullValue());
  }
}
