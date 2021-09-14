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
package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.description.SimpleDescription;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.dice.StrictCountDice;
import io.github.raffaeleflorio.boggle.domain.dice.ValidatedDice;
import io.github.raffaeleflorio.boggle.domain.graph.DFSGraph;
import io.github.raffaeleflorio.boggle.domain.graph.Graph;
import io.github.raffaeleflorio.boggle.domain.graph.UndirectedGraph;

import java.util.List;
import java.util.stream.IntStream;

/**
 * A 4x4 boggle {@link Grid}
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FourByFourGrid<T> implements Grid<T> {
  /**
   * Builds a 4x4 grid
   *
   * @param dice The backed dice
   * @since 1.0.0
   */
  public FourByFourGrid(final Dice<T> dice) {
    this(dice, new DFSGraph<>());
  }

  /**
   * Builds a 4x4 grid
   *
   * @param dice  The backed dice
   * @param graph The empty graph
   * @since 1.0.0
   */
  public FourByFourGrid(final Dice<T> dice, final Graph<T> graph) {
    this(dice, graph, new SimpleDescription());
  }

  /**
   * Builds a 4x4 grid
   *
   * @param dice    The backed dice
   * @param graph   The empty graph
   * @param initial The initial description
   * @since 1.0.0
   */
  FourByFourGrid(final Dice<T> dice, final Graph<T> graph, final Description initial) {
    this(
      new StrictCountDice<>(dice, 16),
      new UndirectedGraph<>(graph),
      initial
    );
  }

  private FourByFourGrid(final ValidatedDice<T> dice, final Graph<T> graph, final Description initial) {
    this.dice = dice;
    this.graph = graph;
    this.initial = initial;
  }

  @Override
  public List<T> values() {
    return dice.values();
  }

  @Override
  public Grid<T> shuffled() {
    return new FourByFourGrid<>(dice.shuffled(), graph);
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return compatible(word.values());
  }

  private Boolean compatible(final List<T> word) {
    var graph = asGraph();
    return IntStream.range(0, word.size() - 1)
      .allMatch(i -> graph.adjacent(word.get(i), word.get(i + 1)));
  }

  private Graph<T> asGraph() {
    var values = values();
    return graph
      .edge(values.get(0), values.get(1))
      .edge(values.get(0), values.get(4))
      .edge(values.get(0), values.get(5))
      .edge(values.get(1), values.get(2))
      .edge(values.get(1), values.get(4))
      .edge(values.get(1), values.get(5))
      .edge(values.get(1), values.get(6))
      .edge(values.get(2), values.get(3))
      .edge(values.get(2), values.get(5))
      .edge(values.get(2), values.get(6))
      .edge(values.get(2), values.get(7))
      .edge(values.get(3), values.get(6))
      .edge(values.get(3), values.get(7))
      .edge(values.get(4), values.get(5))
      .edge(values.get(4), values.get(8))
      .edge(values.get(4), values.get(9))
      .edge(values.get(5), values.get(6))
      .edge(values.get(5), values.get(8))
      .edge(values.get(5), values.get(9))
      .edge(values.get(5), values.get(10))
      .edge(values.get(6), values.get(7))
      .edge(values.get(6), values.get(9))
      .edge(values.get(6), values.get(10))
      .edge(values.get(6), values.get(11))
      .edge(values.get(7), values.get(10))
      .edge(values.get(7), values.get(11))
      .edge(values.get(8), values.get(9))
      .edge(values.get(8), values.get(12))
      .edge(values.get(8), values.get(13))
      .edge(values.get(9), values.get(10))
      .edge(values.get(9), values.get(12))
      .edge(values.get(9), values.get(13))
      .edge(values.get(9), values.get(14))
      .edge(values.get(10), values.get(11))
      .edge(values.get(10), values.get(13))
      .edge(values.get(10), values.get(14))
      .edge(values.get(10), values.get(15))
      .edge(values.get(11), values.get(14))
      .edge(values.get(11), values.get(15))
      .edge(values.get(12), values.get(13))
      .edge(values.get(13), values.get(14))
      .edge(values.get(14), values.get(15));
  }

  @Override
  public Description description() {
    return initial.feature("size", List.of("4x4"));
  }

  private final ValidatedDice<T> dice;
  private final Graph<T> graph;
  private final Description initial;
}
