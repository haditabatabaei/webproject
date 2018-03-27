

package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.SortedSet;


@GwtIncompatible
interface SortedMultisetBridge<E> extends Multiset<E> {
  @Override
  SortedSet<E> elementSet();
}