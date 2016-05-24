/*
 * 	Copyright 2013 Todd Shore
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.github.errantlinguist.fastutil.ints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Supplier;

import com.github.errantlinguist.collections.ListIndices;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/**
 * A utility class for manipulating {@link List} indices.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-22
 */
public final class ListIntIndices {

	public static final <E> List<E> createListFromIndexMapping(
			final Collection<? extends Entry<? extends E>> elementIndices) {
		assert elementIndices != null;
		final List<E> result = new ArrayList<E>(elementIndices.size());

		setIndexedElements(result, elementIndices);

		return result;
	}

	public static final <E, C extends IntCollection> MultiValueObject2IntMap<E, C> createListIndexMap(
			final List<? extends E> list, final Supplier<? extends C> valueCollectionFactory) {
		assert list != null;
		final Map<E, C> decoratedMap = new HashMap<>(list.size() + 1);
		final MultiValueObject2IntMap<E, C> result = new MultiValueObject2IntMap<>(decoratedMap,
				valueCollectionFactory);

		for (final ListIterator<? extends E> listIter = list.listIterator(); listIter.hasNext();) {
			final int nextIndex = listIter.nextIndex();
			final E nextElement = listIter.next();
			result.putValue(nextElement, nextIndex);
		}

		return result;
	}

	public static final <E> Object2IntMap<E> createOrderedSetIndexMap(final Collection<? extends E> orderedSet) {
		assert orderedSet != null;
		final Object2IntMap<E> result = new Object2IntOpenHashMap<>(orderedSet.size() + 1);
		result.defaultReturnValue(ListIndices.UNSEEN_ELEMENT_INDEX);

		int nextIndex = -1;
		// TODO: Refactor this logic below into its own method
		for (final E nextElement : orderedSet) {
			++nextIndex;
			final int oldValue = result.put(nextElement, nextIndex);
			if (oldValue != result.defaultReturnValue()) {
				throw new IllegalArgumentException(String.format(
						"List passed as argument contains already-seen (i.e. non-unique) element at index %d.",
						nextIndex));
			}
		}

		return result;
	}

	public static final <E> void setIndexedElements(final List<E> list,
			final Int2ObjectMap<? extends E> elementIndices) {
		assert list != null;
		assert elementIndices != null;

		// Find the maximum index in order to pre-set the list length
		final int maxIndex = Collections.max(elementIndices.keySet());
		ListIndices.ensureIndex(list, maxIndex);

		final Iterable<? extends Entry<? extends E>> elementIndexEntries = elementIndices.int2ObjectEntrySet();
		setIndexedElements(list, elementIndexEntries);
	}

	public static final <E> void setIndexedElements(final List<E> list,
			final Iterable<? extends Entry<? extends E>> elementIndices) {
		for (final Entry<? extends E> elementIndex : elementIndices) {
			final int index = elementIndex.getIntKey();
			final E element = elementIndex.getValue();
			list.set(index, element);
		}
	}

	private ListIntIndices() {
		// Avoid instantiation
	}

}
