package io.github.raffaeleflorio.boggle.domain.player;

import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;

import java.util.UUID;

/**
 * Sheet of a match emitted according a player requests
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SheetOf<T> extends AbstractUni<Sheet<T>> {
  /**
   * Builds an emitter
   *
   * @param id    The player requesting the sheet
   * @param match The match
   * @since 1.0.0
   */
  public SheetOf(final UUID id, final Match<T> match) {
    this.id = id;
    this.match = match;
  }

  @Override
  public void subscribe(final UniSubscriber<? super Sheet<T>> uniSubscriber) {
    match.sheet(id).subscribe().withSubscriber(uniSubscriber);
  }

  private final UUID id;
  private final Match<T> match;
}
