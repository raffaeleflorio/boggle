package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.infrastructure.grid.GridAsJson;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.core.json.JsonObject;

import java.util.function.Function;

/**
 * {@link Match} as JSON emitter
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class MatchAsJson extends AbstractUni<JsonObject> {
  /**
   * Builds the emitter
   *
   * @param origin The match
   * @since 1.0.0
   */
  public MatchAsJson(final Match<?> origin) {
    this(origin, GridAsJson::new);
  }

  /**
   * Builds the emitter
   *
   * @param origin The match
   * @param gridFn The function to build JSON
   * @since 1.0.0
   */
  MatchAsJson(final Match<?> origin, final Function<Grid<?>, JsonObject> gridFn) {
    this.origin = origin;
    this.gridFn = gridFn;
  }

  @Override
  public void subscribe(final UniSubscriber<? super JsonObject> uniSubscriber) {
    origin.grid()
      .onItem().transform(gridFn)
      .onItem().transform(grid -> json().put("grid", grid))
      .subscribe().withSubscriber(uniSubscriber);
  }

  private JsonObject json() {
    return new JsonObject()
      .put("id", origin.description().feature("id").get(0))
      .put("deadline", origin.description().feature("deadline").get(0));
  }

  private final Match<?> origin;
  private final Function<Grid<?>, JsonObject> gridFn;
}
