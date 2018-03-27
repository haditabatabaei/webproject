

package com.google.common.collect;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collector;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;


@Beta
@GwtCompatible
public final class MoreCollectors {

  
  private static final Collector<Object, ?, Optional<Object>> TO_OPTIONAL =
      Collector.of(
          ToOptionalState::new,
          ToOptionalState::add,
          ToOptionalState::combine,
          ToOptionalState::getOptional,
          Collector.Characteristics.UNORDERED);

  
  @SuppressWarnings("unchecked")
  public static <T> Collector<T, ?, Optional<T>> toOptional() {
    return (Collector) TO_OPTIONAL;
  }

  private static final Object NULL_PLACEHOLDER = new Object();

  private static final Collector<Object, ?, Object> ONLY_ELEMENT =
      Collector.of(
          ToOptionalState::new,
          (state, o) -> state.add((o == null) ? NULL_PLACEHOLDER : o),
          ToOptionalState::combine,
          state -> {
            Object result = state.getElement();
            return (result == NULL_PLACEHOLDER) ? null : result;
          },
          Collector.Characteristics.UNORDERED);

  
  @SuppressWarnings("unchecked")
  public static <T> Collector<T, ?, T> onlyElement() {
    return (Collector) ONLY_ELEMENT;
  }

  
  private static final class ToOptionalState {
    static final int MAX_EXTRAS = 4;

    @NullableDecl Object element;
    @NullableDecl List<Object> extras;

    ToOptionalState() {
      element = null;
      extras = null;
    }

    IllegalArgumentException multiples(boolean overflow) {
      StringBuilder sb =
          new StringBuilder().append("expected one element but was: <").append(element);
      for (Object o : extras) {
        sb.append(", ").append(o);
      }
      if (overflow) {
        sb.append(", ...");
      }
      sb.append('>');
      throw new IllegalArgumentException(sb.toString());
    }

    void add(Object o) {
      checkNotNull(o);
      if (element == null) {
        this.element = o;
      } else if (extras == null) {
        extras = new ArrayList<>(MAX_EXTRAS);
        extras.add(o);
      } else if (extras.size() < MAX_EXTRAS) {
        extras.add(o);
      } else {
        throw multiples(true);
      }
    }

    ToOptionalState combine(ToOptionalState other) {
      if (element == null) {
        return other;
      } else if (other.element == null) {
        return this;
      } else {
        if (extras == null) {
          extras = new ArrayList<>();
        }
        extras.add(other.element);
        if (other.extras != null) {
          this.extras.addAll(other.extras);
        }
        if (extras.size() > MAX_EXTRAS) {
          extras.subList(MAX_EXTRAS, extras.size()).clear();
          throw multiples(true);
        }
        return this;
      }
    }

    Optional<Object> getOptional() {
      if (extras == null) {
        return Optional.ofNullable(element);
      } else {
        throw multiples(false);
      }
    }

    Object getElement() {
      if (element == null) {
        throw new NoSuchElementException();
      } else if (extras == null) {
        return element;
      } else {
        throw multiples(false);
      }
    }
  }

  private MoreCollectors() {}
}
