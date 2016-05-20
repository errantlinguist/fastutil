/**
 *
 */
package com.github.errantlinguist.fastutil.ints;

import java.util.stream.StreamSupport;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

/**
 * A utility class for manipulating {@link IntCollection} elements.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-30
 *
 */
public final class IntCollections {

	public static final IntSet createAllElementSet(final Iterable<? extends IntCollection> collections) {
		final int resultSize = StreamSupport.stream(collections.spliterator(), false)
				.mapToInt(collection -> collection.size()).sum();
		final IntSet result = new IntOpenHashSet(resultSize);
		collections.forEach(collection -> result.addAll(collection));
		return result;
	}

	private IntCollections() {
		// Avoid instantiation
	}

}
