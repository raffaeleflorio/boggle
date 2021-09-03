package io.github.raffaeleflorio.boggle.infrastructure.http.openapi;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.openapi.RouterBuilder;

/**
 * {@link RouterBuilder} as {@link Router}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class RouterBuilderAsRouter extends AbstractUni<Router> {
  /**
   * Builds an emitter
   *
   * @param origin The router builder to decorate
   * @since 1.0.0
   */
  public RouterBuilderAsRouter(final RouterBuilder origin) {
    this(Uni.createFrom().item(origin));
  }

  /**
   * Builds an emitter
   *
   * @param origin The router builder to decorate
   * @since 1.0.0
   */
  public RouterBuilderAsRouter(final Uni<RouterBuilder> origin) {
    this.origin = origin;
  }

  @Override
  public void subscribe(final UniSubscriber<? super Router> uniSubscriber) {
    origin
      .onItem().transform(RouterBuilder::createRouter)
      .subscribe().withSubscriber(uniSubscriber);
  }

  private final Uni<RouterBuilder> origin;
}
