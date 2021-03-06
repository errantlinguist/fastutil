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
package com.github.errantlinguist.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A utility class for manipulating {@link List} indices.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-22
 */
public final class ListIndices {

	public static final <E> List<E> createListFromIndexMapping(
			final Collection<? extends Entry<? extends Integer, ? extends E>> elementIndices) {
		assert elementIndices != null;
		final List<E> result = new ArrayList<>(elementIndices.size());

		setIndexedElements(result, elementIndices);

		return result;
	}

	public static final <E> boolean ensureIndex(final List<E> list, final int index) {
		return CollectionSize.ensureSize(list, index + 1);
	}

	public static final <E> boolean ensureIndex(final List<E> list, final int index, final E defaultElement) {
		return CollectionSize.ensureSize(list, index + 1, defaultElement);
	}

	public static final <E> void setIndexedElements(final List<E> list,
			final Iterable<? extends Entry<? extends Integer, ? extends E>> elementIndices) {
		for (final Entry<? extends Integer, ? extends E> elementIndex : elementIndices) {
			final Integer index = elementIndex.getKey();
			final E element = elementIndex.getValue();
			list.set(index, element);
		}
	}

	public static final <E> void setIndexedElements(final List<E> list,
			final Map<? extends Integer, ? extends E> elementIndices) {
		assert list != null;
		assert elementIndices != null;

		// Find the maximum index in order to pre-set the list length
		final int maxIndex = Collections.max(elementIndices.keySet());
		ensureIndex(list, maxIndex);

		final Iterable<? extends Entry<? extends Integer, ? extends E>> elementIndexEntries = elementIndices.entrySet();
		setIndexedElements(list, elementIndexEntries);
	}

	private ListIndices() {
		// Avoid instantiation
	}

}
