package io.github.raffaeleflorio.boggle.infrastructure.config;

import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.config.ConfigRetriever;
import io.vertx.mutiny.core.Vertx;

/**
 * Full {@link Vertx} configuration emitter
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FullConfig extends AbstractUni<JsonObject> {
  /**
   * Builds a full config emitter
   *
   * @param vertx The vertx instance
   * @since 1.0.0
   */
  public FullConfig(final Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void subscribe(final UniSubscriber<? super JsonObject> uniSubscriber) {
    ConfigRetriever.create(vertx).getConfig().subscribe().withSubscriber(uniSubscriber);
  }

  private final Vertx vertx;
}
