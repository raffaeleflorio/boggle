package io.github.raffaeleflorio.boggle.infrastructure.http.server;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.mutiny.core.http.HttpServerRequest;
import io.vertx.mutiny.ext.web.Router;

import java.util.function.Consumer;

/**
 * {@link Router} as {@link Consumer<HttpServerRequest>} emitter
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class RouterAsConsumer extends AbstractUni<Consumer<HttpServerRequest>> {
  /**
   * Builds an emitter
   *
   * @param origin The router
   * @since 1.0.0
   */
  public RouterAsConsumer(final Router origin) {
    this(Uni.createFrom().item(origin));
  }

  /**
   * Builds an emitter
   *
   * @param origin The router
   * @since 1.0.0
   */
  public RouterAsConsumer(final Uni<Router> origin) {
    this.origin = origin;
  }

  @Override
  public void subscribe(final UniSubscriber<? super Consumer<HttpServerRequest>> uniSubscriber) {
    origin.subscribe().withSubscriber(uniSubscriber);
  }

  private final Uni<Router> origin;
}
