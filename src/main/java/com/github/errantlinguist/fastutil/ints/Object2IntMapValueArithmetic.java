/*
 * Copyright (c) the Department of Informatics, Technische Universität Darmstadt. All Rights Reserved.
 *
 * Unauthorized distribution of this file via any medium is strictly prohibited.
 */
package com.github.errantlinguist.fastutil.ints;

import java.util.Collection;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

/**
 * @author <a href="mailto:shore@ukp.informatik.tu-darmstadt.de">Todd Shore</a>
 * @since May 17, 2016
 *
 */
public final class Object2IntMapValueArithmetic {

	/**
	 * Increments the values for a given key which occur within a given range.
	 *
	 * @param map
	 *            The {@link Object2IntMap} to add to.
	 * @param keysToIncrement
	 *            The keys to increment the values of.
	 * @param increment
	 *            The amount to increment the values by.
	 * @param fromValue
	 *            The inclusive minimum of the key values to increment.
	 * @param toValue
	 *            The exclusive maximum of the key values to increment.
	 */
	public static final <K, C extends IntSortedSet> void incrementValues(final Object2IntMap<K> map,
			final Collection<? extends K> keysToIncrement, final int increment, final int fromValue,
			final int toValue) {
		assert keysToIncrement != null;
		final IntCollection incrementedValues = new IntOpenHashSet(keysToIncrement.size());
		for (final K keyToIncrement : keysToIncrement) {
			incrementValues(map, keyToIncrement, increment, fromValue, toValue, incrementedValues);
		}
	}

	/**
	 * Adds one value for each given key, incrementing the value added for the
	 * next key by one.
	 *
	 * @param map
	 *            The {@link Object2IntMap} to add to.
	 * @param keysToAdd
	 *            The keys to add.
	 * @param startValue
	 *            The integer value to start at.
	 * @return If at least one value was added to the map.
	 */
	public static final <K, C extends IntCollection> void putIncrementingValues(final Object2IntMap<K> map,
			final Iterable<? extends K> keysToAdd, int startValue) {
		assert keysToAdd != null;

		for (final K keyToAdd : keysToAdd) {
			map.put(keyToAdd, startValue++);
		}
	}

	/**
	 * Increments the values for a given key which occur within a given range.
	 *
	 * @param map
	 *            The {@link Object2IntMap} to add to.
	 * @param keysToIncrement
	 *            The keys to increment the values of.
	 * @param increment
	 *            The amount to increment the values by.
	 * @param fromValue
	 *            The inclusive minimum of the key values to increment.
	 * @param toValue
	 *            The exclusive maximum of the key values to increment.
	 * @param incrementedValues
	 *            A {@link IntCollection} of elements in the original mapping
	 *            which were incremented.
	 */
	private static final <K, C extends IntSortedSet> void incrementValues(final Object2IntMap<K> map,
			final K keyToIncrement, final int increment, final int fromValue, final int toValue,
			final IntCollection incrementedValues) {
		final int valueToIncrement = map.getInt(keyToIncrement);
		// Filter out the values outside the specified range of values to update
		if (fromValue <= valueToIncrement && valueToIncrement <= toValue
				&& !incrementedValues.contains(valueToIncrement)) {
			final int incrementedValue = valueToIncrement + increment;
			// Put the new value into the map
			final int oldValue = map.put(keyToIncrement, incrementedValue);
			assert oldValue == valueToIncrement;

			// Put the old value in the set of already-incremented
			// values
			final boolean wasAdded = incrementedValues.add(valueToIncrement);
			assert wasAdded;
		}
	}

	private Object2IntMapValueArithmetic() {
	}

}