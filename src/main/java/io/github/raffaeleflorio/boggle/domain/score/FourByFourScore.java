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
package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Classical 4x4 {@link Score} based on word length:
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
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FourByFourScore<T> implements Score<T> {
  /**
   * Builds a 4x4 score
   *
   * @since 1.0.0
   */
  public FourByFourScore() {
    this(0);
  }

  /**
   * Builds a 4x4 score
   *
   * @param def The default score
   * @since 1.0.0
   */
  FourByFourScore(final Integer def) {
    this(
      Map.of(
        1, x -> x == 3 || x == 4,
        2, x -> x == 5,
        3, x -> x == 6,
        5, x -> x == 7,
        11, x -> x >= 8
      ),
      Uni.createFrom().item(def)
    );
  }

  private FourByFourScore(final Map<Integer, Predicate<Integer>> scoreMap, final Uni<Integer> def) {
    this.scoreMap = scoreMap;
    this.def = def;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return score(word.values().size()).map(Uni.createFrom()::item).orElse(def);
  }

  private Optional<Integer> score(final Integer length) {
    return scoreEntry(length).map(Map.Entry::getKey);
  }

  private Optional<Map.Entry<Integer, Predicate<Integer>>> scoreEntry(final Integer length) {
    return scoreMap.entrySet().stream()
      .filter(entry -> entry.getValue().test(length))
      .findAny();
  }

  private final Map<Integer, Predicate<Integer>> scoreMap;
  private final Uni<Integer> def;
}
