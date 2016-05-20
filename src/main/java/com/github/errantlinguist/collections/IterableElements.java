/**
 *
 */
package com.github.errantlinguist.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class for manipulating {@link Collection} elements.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-30
 *
 */
public final class IterableElements {

	/**
	 * Checks if all the elements in a given {@link Collection} are unique,
	 * i.e.&nbsp;that no element occurs more than once.
	 *
	 * @param collection
	 *            The {@code Collection} to check.
	 * @return {@code} true iff there are no elements with more than one
	 *         reference in the {@code Collection}.
	 */
	public static final boolean areElementsUnique(final Collection<?> collection) {
		assert collection != null;
		final Set<Object> uniqueElements = new HashSet<Object>(collection.size());
		return areElementsUnique(collection, uniqueElements);
	}

	/**
	 * Checks if all the elements in a given {@link Iterable} are unique,
	 * i.e.&nbsp;that no element occurs more than once.
	 *
	 * @param iterable
	 *            The {@code Iterable} to check.
	 * @return {@code} true iff there are no elements with more than one
	 *         reference in the {@code Iterable}.
	 */
	public static final boolean areElementsUnique(final Iterable<?> iterable) {
		assert iterable != null;
		final Set<Object> uniqueElements = new HashSet<Object>();
		return areElementsUnique(iterable, uniqueElements);
	}

	public static final <E> Set<E> createAllElementSet(final Collection<? extends Collection<E>> collections) {
		final int resultSize = collections.stream().mapToInt(collection -> collection.size()).sum();
		final Iterable<? extends Collection<E>> upcast = collections;
		return createAllElementSet(upcast, resultSize);
	}

	public static final <E> Set<E> createAllElementSet(final Collection<? extends Collection<E>> collections,
			final int individualCollectionSize) {
		final int totalElementCount = individualCollectionSize * collections.size();
		final Iterable<? extends Collection<E>> upcast = collections;
		return createAllElementSet(upcast, totalElementCount);
	}

	public static final <E> Set<E> createAllElementSet(final Iterable<? extends Collection<E>> collections,
			final int totalElementCount) {
		final Set<E> result = new HashSet<E>(totalElementCount);
		collections.forEach(coll -> result.addAll(coll));
		return result;
	}

	/**
	 * Checks if all the elements in a given {@link Iterable} are unique,
	 * i.e.&nbsp;that no element occurs more than once.
	 *
	 * @param iterable
	 *            The {@code Iterable} to check.
	 * @param uniqueElements
	 *            The {@link Set} instance to use for counting unique elements.
	 * @return {@code} true iff there are no elements with more than one
	 *         reference in the {@code Iterable}.
	 */
	private static final <E> boolean areElementsUnique(final Iterable<? extends E> iterable,
			final Set<E> uniqueElements) {
		assert iterable != null;
		assert uniqueElements != null;
		boolean result = true;

		for (final E element : iterable) {
			if (!uniqueElements.add(element)) {
				result = false;
				break;
			}
		}

		return result;
	}

	private IterableElements() {
		// Avoid instantiation
	}

}
