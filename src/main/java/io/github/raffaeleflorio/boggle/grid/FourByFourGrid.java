package io.github.raffaeleflorio.boggle.grid;

import io.github.raffaeleflorio.boggle.dice.Dice;
import io.github.raffaeleflorio.boggle.dice.StrictDiceCount;
import io.github.raffaeleflorio.boggle.dice.ValidatedDice;
import io.github.raffaeleflorio.boggle.graph.DFSGraph;
import io.github.raffaeleflorio.boggle.graph.Graph;
import io.github.raffaeleflorio.boggle.graph.UndirectedGraph;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A 4x4 boggle {@link Grid}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FourByFourGrid implements Grid<CharSequence> {
  /**
   * Builds a 4x4 grid
   *
   * @param dice The backed dice
   * @since 1.0.0
   */
  public FourByFourGrid(final Dice<CharSequence> dice) {
    this(dice, new DFSGraph<>());
  }

  /**
   * Builds a 4x4 grid
   *
   * @param dice  The backed dice
   * @param graph An empty graph
   * @since 1.0.0
   */
  FourByFourGrid(final Dice<CharSequence> dice, final Graph<CharSequence> graph) {
    this(dice, graph, elements -> String.join("", elements));
  }

  /**
   * Builds a 4x4 grid
   *
   * @param dice     The backed dice
   * @param graph    An empty graph
   * @param concatFn A function to concatenate strings
   * @since 1.0.0
   */
  FourByFourGrid(
    final Dice<CharSequence> dice,
    final Graph<CharSequence> graph,
    final Function<Iterable<CharSequence>, String> concatFn
  ) {
    this(
      new StrictDiceCount<>(dice, 16),
      new UndirectedGraph<>(graph),
      concatFn
    );
  }

  private FourByFourGrid(
    final ValidatedDice<CharSequence> dice,
    final Graph<CharSequence> graph,
    final Function<Iterable<CharSequence>, String> concatFn
  ) {
    this.dice = dice;
    this.graph = graph;
    this.concatFn = concatFn;
  }

  @Override
  public Grid<CharSequence> shuffled() {
    return new FourByFourGrid(dice.shuffled(), graph, concatFn);
  }

  @Override
  public Integer score(final Dice<CharSequence> word) {
    var values = word.values();
    return contained(asList(values)) ? score(values.size()) : 0;
  }

  private Integer score(final Integer size) {
    return Map.<Integer, Predicate<Integer>>of(
        1, x -> x == 3 || x == 4,
        2, x -> x == 5,
        3, x -> x == 6,
        5, x -> x == 7,
        11, x -> x >= 8
      )
      .entrySet()
      .stream()
      .filter(entry -> entry.getValue().test(size))
      .map(Map.Entry::getKey)
      .findFirst()
      .orElse(0);
  }

  private <T> List<T> asList(final Collection<T> from) {
    return from.stream().collect(Collectors.toUnmodifiableList());
  }

  private Boolean contained(final List<CharSequence> word) {
    var graph = asGraph();
    return IntStream
      .range(0, word.size() - 1)
      .allMatch(i -> graph.adjacent(word.get(i), word.get(i + 1)));
  }

  private Graph<CharSequence> asGraph() {
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

  private List<CharSequence> values() {
    return asList(dice.values());
  }

  @Override
  public Map<CharSequence, CharSequence> description() {
    return Map.of(
      "size", "4x4",
      "layout", concatFn.apply(values())
    );
  }

  private final ValidatedDice<CharSequence> dice;
  private final Graph<CharSequence> graph;
  private final Function<Iterable<CharSequence>, String> concatFn;
}
