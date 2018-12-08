

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Arrays;


@GwtCompatible
class LegacyComparable implements Comparable, Serializable {
  static final LegacyComparable X = new LegacyComparable("x");
  static final LegacyComparable Y = new LegacyComparable("y");
  static final LegacyComparable Z = new LegacyComparable("z");

  static final Iterable<LegacyComparable> VALUES_FORWARD = Arrays.asList(X, Y, Z);
  static final Iterable<LegacyComparable> VALUES_BACKWARD = Arrays.asList(Z, Y, X);

  private final String value;

  LegacyComparable(String value) {
    this.value = value;
  }

  @Override
  public int compareTo(Object object) {
        LegacyComparable that = (LegacyComparable) object;
    return this.value.compareTo(that.value);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof LegacyComparable) {
      LegacyComparable that = (LegacyComparable) object;
      return this.value.equals(that.value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  private static final long serialVersionUID = 0;
}