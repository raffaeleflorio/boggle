package io.github.raffaeleflorio.boggle.infrastructure.dice;

import io.github.raffaeleflorio.boggle.domain.dice.AlignedDice;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.dice.LoadableDie;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Dice} that animates a JSON
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class JsonDice implements Dice<CharSequence> {
  /**
   * Builds a dice
   *
   * @param origin The JSON
   * @since 1.0.0
   */
  public JsonDice(final JsonObject origin) {
    this(
      new AlignedDice<>(
        origin
          .getJsonArray("dice").stream()
          .map(CharSequence.class::cast).map(List::of)
          .map(LoadableDie::new)
          .collect(Collectors.toUnmodifiableList()))
    );
  }

  private JsonDice(final Dice<CharSequence> origin) {
    this.origin = origin;
  }

  @Override
  public List<CharSequence> values() {
    return origin.values();
  }

  @Override
  public Dice<CharSequence> shuffled() {
    return origin.shuffled();
  }

  private final Dice<CharSequence> origin;
}
