

package com.google.common.hash;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkPositionIndexes;

import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


@Immutable
abstract class AbstractHashFunction implements HashFunction {
  @Override
  public <T> HashCode hashObject(T instance, Funnel<? super T> funnel) {
    return newHasher().putObject(instance, funnel).hash();
  }

  @Override
  public HashCode hashUnencodedChars(CharSequence input) {
    int len = input.length();
    return newHasher(len * 2).putUnencodedChars(input).hash();
  }

  @Override
  public HashCode hashString(CharSequence input, Charset charset) {
    return newHasher().putString(input, charset).hash();
  }

  @Override
  public HashCode hashInt(int input) {
    return newHasher(4).putInt(input).hash();
  }

  @Override
  public HashCode hashLong(long input) {
    return newHasher(8).putLong(input).hash();
  }

  @Override
  public HashCode hashBytes(byte[] input) {
    return hashBytes(input, 0, input.length);
  }

  @Override
  public HashCode hashBytes(byte[] input, int off, int len) {
    checkPositionIndexes(off, off + len, input.length);
    return newHasher(len).putBytes(input, off, len).hash();
  }

  @Override
  public HashCode hashBytes(ByteBuffer input) {
    return newHasher(input.remaining()).putBytes(input).hash();
  }

  @Override
  public Hasher newHasher(int expectedInputSize) {
    checkArgument(
        expectedInputSize >= 0, "expectedInputSize must be >= 0 but was %s", expectedInputSize);
    return newHasher();
  }
}
