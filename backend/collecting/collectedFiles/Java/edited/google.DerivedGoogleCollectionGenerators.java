

package com.google.common.collect.testing.google;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.BiMap;
import com.google.common.collect.testing.DerivedGenerator;
import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.OneSizeTestContainerGenerator;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestMapGenerator;
import com.google.common.collect.testing.TestSetGenerator;
import com.google.common.collect.testing.TestSubjectGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


@GwtCompatible
public final class DerivedGoogleCollectionGenerators {
  public static class MapGenerator<K, V> implements TestMapGenerator<K, V>, DerivedGenerator {

    private final OneSizeTestContainerGenerator<BiMap<K, V>, Entry<K, V>> generator;

    public MapGenerator(
        OneSizeTestContainerGenerator<BiMap<K, V>, Entry<K, V>> oneSizeTestContainerGenerator) {
      this.generator = oneSizeTestContainerGenerator;
    }

    @Override
    public SampleElements<Entry<K, V>> samples() {
      return generator.samples();
    }

    @Override
    public Map<K, V> create(Object... elements) {
      return generator.create(elements);
    }

    @Override
    public Entry<K, V>[] createArray(int length) {
      return generator.createArray(length);
    }

    @Override
    public Iterable<Entry<K, V>> order(List<Entry<K, V>> insertionOrder) {
      return generator.order(insertionOrder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public K[] createKeyArray(int length) {
      return (K[]) new Object[length];
    }

    @SuppressWarnings("unchecked")
    @Override
    public V[] createValueArray(int length) {
      return (V[]) new Object[length];
    }

    public TestSubjectGenerator<?> getInnerGenerator() {
      return generator;
    }
  }

  public static class InverseBiMapGenerator<K, V>
      implements TestBiMapGenerator<V, K>, DerivedGenerator {

    private final OneSizeTestContainerGenerator<BiMap<K, V>, Entry<K, V>> generator;

    public InverseBiMapGenerator(
        OneSizeTestContainerGenerator<BiMap<K, V>, Entry<K, V>> oneSizeTestContainerGenerator) {
      this.generator = oneSizeTestContainerGenerator;
    }

    @Override
    public SampleElements<Entry<V, K>> samples() {
      SampleElements<Entry<K, V>> samples = generator.samples();
      return new SampleElements<>(
          reverse(samples.e0()),
          reverse(samples.e1()),
          reverse(samples.e2()),
          reverse(samples.e3()),
          reverse(samples.e4()));
    }

    private Entry<V, K> reverse(Entry<K, V> entry) {
      return Helpers.mapEntry(entry.getValue(), entry.getKey());
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiMap<V, K> create(Object... elements) {
      Entry<?, ?>[] entries = new Entry<?, ?>[elements.length];
      for (int i = 0; i < elements.length; i++) {
        entries[i] = reverse((Entry<K, V>) elements[i]);
      }
      return generator.create((Object[]) entries).inverse();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Entry<V, K>[] createArray(int length) {
      return new Entry[length];
    }

    @Override
    public Iterable<Entry<V, K>> order(List<Entry<V, K>> insertionOrder) {
      return insertionOrder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V[] createKeyArray(int length) {
      return (V[]) new Object[length];
    }

    @SuppressWarnings("unchecked")
    @Override
    public K[] createValueArray(int length) {
      return (K[]) new Object[length];
    }

    public TestSubjectGenerator<?> getInnerGenerator() {
      return generator;
    }
  }

  public static class BiMapValueSetGenerator<K, V>
      implements TestSetGenerator<V>, DerivedGenerator {
    private final OneSizeTestContainerGenerator<BiMap<K, V>, Entry<K, V>> mapGenerator;
    private final SampleElements<V> samples;

    public BiMapValueSetGenerator(
        OneSizeTestContainerGenerator<BiMap<K, V>, Entry<K, V>> mapGenerator) {
      this.mapGenerator = mapGenerator;
      final SampleElements<Entry<K, V>> mapSamples = this.mapGenerator.samples();
      this.samples =
          new SampleElements<V>(
              mapSamples.e0().getValue(),
              mapSamples.e1().getValue(),
              mapSamples.e2().getValue(),
              mapSamples.e3().getValue(),
              mapSamples.e4().getValue());
    }

    @Override
    public SampleElements<V> samples() {
      return samples;
    }

    @Override
    public Set<V> create(Object... elements) {
      @SuppressWarnings("unchecked")
      V[] valuesArray = (V[]) elements;

            Collection<Entry<K, V>> originalEntries = mapGenerator.getSampleElements(elements.length);

            Collection<Entry<K, V>> entries = new ArrayList<>(elements.length);
      int i = 0;
      for (Entry<K, V> entry : originalEntries) {
        entries.add(Helpers.mapEntry(entry.getKey(), valuesArray[i++]));
      }

      return mapGenerator.create(entries.toArray()).values();
    }

    @Override
    public V[] createArray(int length) {
      final V[] vs =
          ((TestBiMapGenerator<K, V>) mapGenerator.getInnerGenerator()).createValueArray(length);
      return vs;
    }

    @Override
    public Iterable<V> order(List<V> insertionOrder) {
      return insertionOrder;
    }

    public TestSubjectGenerator<?> getInnerGenerator() {
      return mapGenerator;
    }
  }

  private DerivedGoogleCollectionGenerators() {}
}
