

package java.nio;


final class ReadWriteLongArrayBuffer extends LongArrayBuffer {

	static ReadWriteLongArrayBuffer copy (LongArrayBuffer other, int markOfOther) {
		ReadWriteLongArrayBuffer buf = new ReadWriteLongArrayBuffer(other.capacity(), other.backingArray, other.offset);
		buf.limit = other.limit();
		buf.position = other.position();
		buf.mark = markOfOther;
		return buf;
	}

	ReadWriteLongArrayBuffer (long[] array) {
		super(array);
	}

	ReadWriteLongArrayBuffer (int capacity) {
		super(capacity);
	}

	ReadWriteLongArrayBuffer (int capacity, long[] backingArray, int arrayOffset) {
		super(capacity, backingArray, arrayOffset);
	}

	public LongBuffer asReadOnlyBuffer () {
		return ReadOnlyLongArrayBuffer.copy(this, mark);
	}

	public LongBuffer compact () {
		System.arraycopy(backingArray, position + offset, backingArray, offset, remaining());
		position = limit - position;
		limit = capacity;
		mark = UNSET_MARK;
		return this;
	}

	public LongBuffer duplicate () {
		return copy(this, mark);
	}

	public boolean isReadOnly () {
		return false;
	}

	protected long[] protectedArray () {
		return backingArray;
	}

	protected int protectedArrayOffset () {
		return offset;
	}

	protected boolean protectedHasArray () {
		return true;
	}

	public LongBuffer put (long c) {
		if (position == limit) {
			throw new BufferOverflowException();
		}
		backingArray[offset + position++] = c;
		return this;
	}

	public LongBuffer put (int index, long c) {
		if (index < 0 || index >= limit) {
			throw new IndexOutOfBoundsException();
		}
		backingArray[offset + index] = c;
		return this;
	}

	public LongBuffer put (long[] src, int off, int len) {
		int length = src.length;
		if (off < 0 || len < 0 || (long)off + (long)len > length) {
			throw new IndexOutOfBoundsException();
		}
		if (len > remaining()) {
			throw new BufferOverflowException();
		}
		System.arraycopy(src, off, backingArray, offset + position, len);
		position += len;
		return this;
	}

	public LongBuffer slice () {
		return new ReadWriteLongArrayBuffer(remaining(), backingArray, offset + position);
	}

}
