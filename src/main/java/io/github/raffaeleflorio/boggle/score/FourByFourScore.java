package io.github.raffaeleflorio.boggle.score;

import io.github.raffaeleflorio.boggle.dice.Dice;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Classical score function about boggle, based on word length:
 * <table>
 *   <tr>
 *     <th>Word length</th>
 *     <th>Score</th>
 *   </tr>
 *   <tr>
 *     <td>3, 4</td>
 *     <td>1</td>
 *   </tr>
 *   <tr>
 *     <td>5</td>
 *     <td>2</td>
 *   </tr>
 *   <tr>
 *     <td>6</td>
 *     <td>3</td>
 *   </tr>
 *   <tr>
 *     <td>7</td>
 *     <td>5</td>
 *   </tr>
 *   <tr>
 *     <td>8+</td>
 *     <td>11</td>
 *   </tr>
 * </table>
 *
 * @param <T> The dice mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FourByFourScore<T> implements Score<T> {
  /**
   * Builds a score function
   *
   * @since 1.0.0
   */
  public FourByFourScore() {
    this(
      Map.of(
        1, x -> x == 3 || x == 4,
        2, x -> x == 5,
        3, x -> x == 6,
        5, x -> x == 7,
        11, x -> x >= 8
      )
    );
  }

  private FourByFourScore(final Map<Integer, Predicate<Integer>> scoreMap) {
    this.scoreMap = scoreMap;
  }

  @Override
  public Integer apply(final Dice<T> dice) {
    var size = dice.values().size();
    return scoreMap.entrySet()
      .stream()
      .filter(entry -> entry.getValue().test(size))
      .findAny()
      .map(Map.Entry::getKey)
      .orElse(0);
  }

  private final Map<Integer, Predicate<Integer>> scoreMap;
}
