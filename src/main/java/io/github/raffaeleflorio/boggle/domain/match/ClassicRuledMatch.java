package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.score.Score;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * {@link Match} with score assigned only to unique words
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class ClassicRuledMatch<T> implements Match<T> {
  /**
   * Builds a match
   *
   * @param origin The match to decorate
   * @param score  The score function
   * @since 1.0.0
   */
  ClassicRuledMatch(final Match<T> origin, final Score<T> score) {
    this(origin, score, (o1, o2) -> o1.values().equals(o2.values()) ? 0 : 1);
  }

  /**
   * Builds a match
   *
   * @param origin      The match to decorate
   * @param score       The score function
   * @param equalityCmp The equality comparator
   * @since 1.0.0
   */
  ClassicRuledMatch(final Match<T> origin, final Score<T> score, final Comparator<Dice<T>> equalityCmp) {
    this.origin = origin;
    this.score = score;
    this.equalityCmp = equalityCmp;
  }

  @Override
  public UUID id() {
    return origin.id();
  }

  @Override
  public Uni<Sheet<T>> sheet(final UUID id) {
    return origin.sheet(id);
  }

  @Override
  public Multi<Map.Entry<UUID, Integer>> score() {
    return sheetPerPlayer()
      .onItem().transformToUniAndMerge(this::uniqueWordsPerPlayer)
      .onItem().transformToUniAndMerge(this::scorePerPlayer);
  }

  private Multi<Map.Entry<UUID, Sheet<T>>> sheetPerPlayer() {
    return players()
      .onItem().transformToUniAndMerge(player -> sheet(player).onItem().transform(sheet -> Map.entry(player, sheet)));
  }

  private Uni<Map.Entry<UUID, List<Dice<T>>>> uniqueWordsPerPlayer(final Map.Entry<UUID, Sheet<T>> playerSheet) {
    return playerSheet.getValue().words()
      .select().when(playerWord -> notInOtherPlayerSheet(playerSheet.getKey(), playerWord))
      .select().distinct(equalityCmp).collect().asList()
      .onItem().transform(words -> Map.entry(playerSheet.getKey(), words));
  }

  private Uni<Boolean> notInOtherPlayerSheet(final UUID player, final Dice<T> word) {
    return otherPlayerWords(player)
      .map(otherWord -> equals(word, otherWord))
      .filter(Boolean::booleanValue).collect().first()
      .onItem().ifNotNull().transform(x -> false)
      .onItem().ifNull().continueWith(true);
  }

  private Multi<Dice<T>> otherPlayerWords(final UUID player) {
    return sheetPerPlayer()
      .filter(entry -> !entry.getKey().equals(player))
      .onItem().transformToMultiAndMerge(sheet -> sheet.getValue().words());
  }

  private Boolean equals(final Dice<T> one, final Dice<T> two) {
    return equalityCmp.compare(one, two) == 0;
  }

  private Uni<Map.Entry<UUID, Integer>> scorePerPlayer(final Map.Entry<UUID, List<Dice<T>>> playerWords) {
    return Multi.createFrom().items(playerWords.getValue()::stream)
      .onItem().transformToUniAndMerge(score::score)
      .collect().with(Collectors.summingInt(x -> x))
      .onItem().transform(score -> Map.entry(playerWords.getKey(), score));
  }

  @Override
  public Description description() {
    return origin.description();
  }

  @Override
  public Uni<Grid<T>> grid() {
    return origin.grid();
  }

  @Override
  public Multi<UUID> players() {
    return origin.players();
  }

  private final Match<T> origin;
  private final Score<T> score;
  private final Comparator<Dice<T>> equalityCmp;
}
