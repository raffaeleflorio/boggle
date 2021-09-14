/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.domain.graph;

import java.util.function.BiFunction;

/**
 * A directed math graph
 *
 * @param <T> The vertex type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @see <a href="https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)">Wikipedia definition</a>
 * @since 1.0.0
 */
public interface Graph<T> {
  /**
   * Builds a true boolean if the first vertex is connected to second
   *
   * @param first  The first vertex
   * @param second The second vertex
   * @return True if connected
   * @since 1.0.0
   */
  Boolean connected(T first, T second);

  /**
   * Builds a true boolean if the first vertex is adjacent to the second
   *
   * @param first  The first vertex
   * @param second The second vertex
   * @return True if adjacent
   * @since 1.0.0
   */
  Boolean adjacent(T first, T second);

  /**
   * Builds a graph with a directed edge from one to two
   *
   * @param one The first vertex
   * @param two The second vertex
   * @return The new graph
   * @since 1.0.0
   */
  Graph<T> edge(T one, T two);

  /**
   * A {@link Graph} useful for testing
   *
   * @param <T> The vertex type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Graph<T> {
    /**
     * Builds a fake with custom connected and adjacent function
     *
     * @param connectedFn The function that verifies vertices connection
     * @param adjacentFn  The function that verifies vertices adjacency
     * @since 1.0.0
     */
    public Fake(
      final BiFunction<T, T, Boolean> connectedFn,
      final BiFunction<T, T, Boolean> adjacentFn
    ) {
      this.connectedFn = connectedFn;
      this.adjacentFn = adjacentFn;
    }

    @Override
    public Boolean connected(final T first, final T second) {
      return connectedFn.apply(first, second);
    }

    @Override
    public Boolean adjacent(final T first, final T second) {
      return adjacentFn.apply(first, second);
    }

    @Override
    public Graph<T> edge(final T one, final T two) {
      return this;
    }

    private final BiFunction<T, T, Boolean> connectedFn;
    private final BiFunction<T, T, Boolean> adjacentFn;
  }
}
