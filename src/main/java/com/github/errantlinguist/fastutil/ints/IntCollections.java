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

import java.util.stream.StreamSupport;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

/**
 * A utility class for manipulating {@link IntCollection} elements.
 *
 * @author <a href="mailto:errantlinguist@gmail.com">Todd Shore</a>
 * @since 2013-10-30
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
