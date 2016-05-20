/*
 * Copyright (c) the Department of Informatics, Technische Universität Darmstadt. All Rights Reserved.
 *
 * Unauthorized distribution of this file via any medium is strictly prohibited.
 */
package com.github.errantlinguist.fastutil.ints;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.errantlinguist.collections.ListIndices;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSet;

/**
 * @author <a href="mailto:shore@ukp.informatik.tu-darmstadt.de">Todd Shore</a>
 * @since May 2, 2016
 *
 */
public final class ReverseLookupInt2ObjectMap<V> implements Int2ObjectMap<V> {

	private final Int2ObjectMap<V> decorated;
	private final List<V> indexedValues;

	/**
	 *
	 */
	public ReverseLookupInt2ObjectMap(final Int2ObjectMap<V> decorated) {
		this.decorated = decorated;
		this.indexedValues = ListIndices.createListFromIndexMap(decorated);
	}

	@Override
	public void clear() {
		decorated.clear();
		indexedValues.clear();
	}

	@Override
	public boolean containsKey(final int key) {
		return decorated.containsKey(key);
	}

	@Override
	public boolean containsKey(final Object key) {
		return decorated.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return decorated.containsValue(value);
	}

	@Override
	public V defaultReturnValue() {
		return decorated.defaultReturnValue();
	}

	@Override
	public void defaultReturnValue(final V rv) {
		decorated.defaultReturnValue(rv);
	}

	@Override
	public ObjectSet<java.util.Map.Entry<Integer, V>> entrySet() {
		return decorated.entrySet();
	}

	@Override
	public V get(final int key) {
		return decorated.get(key);
	}

	@Override
	public V get(final Object key) {
		return decorated.get(key);
	}

	@Override
	public ObjectSet<it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
		return decorated.int2ObjectEntrySet();
	}

	@Override
	public boolean isEmpty() {
		return decorated.isEmpty();
	}

	@Override
	public IntSet keySet() {
		return decorated.keySet();
	}

	@Override
	public V put(final int key, final V value) {
		final V putValue = decorated.put(key, value);

		ListIndices.ensureIndex(indexedValues, key);
		final V result = indexedValues.set(key, value);
		assert Objects.equals(putValue, result);
		return result;
	}

	@Override
	public V put(final Integer key, final V value) {
		assert key != null;
		return put(key.intValue(), value);
	}

	@Override
	public void putAll(final Map<? extends Integer, ? extends V> m) {
		assert m != null;

		decorated.putAll(m);

		// Find the maximum index in order to pre-set the list length
		final int maxIndex = Collections.max(m.keySet());
		ListIndices.ensureIndex(indexedValues, maxIndex);

		ListIndices.setIndexedElements(indexedValues, m);

		throw new RuntimeException(new UnsupportedOperationException("Not yet implemented"));
		// TODO Auto-generated method stub
	}

	@Override
	public V remove(final int key) {
		final V removedValue = decorated.remove(key);
		final V result = indexedValues.remove(key);
		assert Objects.equals(removedValue, result);
		return result;
	}

	@Override
	public V remove(final Object key) {
		final V result;

		if (key instanceof Integer) {
			result = remove(((Integer) key).intValue());
		} else {
			result = null;
		}

		return result;
	}

	@Override
	public int size() {
		return decorated.size();
	}

	@Override
	public ObjectCollection<V> values() {
		return decorated.values();
	}

}
