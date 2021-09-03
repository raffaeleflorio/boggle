package io.github.raffaeleflorio.boggle.infrastructure.http.openapi;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.openapi.RouterBuilder;

/**
 * {@link RouterBuilder} emitter built from an openapi specification
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class OpenAPIRouterBuilder extends AbstractUni<RouterBuilder> {
  /**
   * Builds an emitter
   *
   * @param path  The specification path
   * @param vertx The vertx instance
   * @since 1.0.0
   */
  public OpenAPIRouterBuilder(final String path, final Vertx vertx) {
    this(Uni.createFrom().item(path), vertx);
  }

  /**
   * Builds an emitter
   *
   * @param path  The specification path
   * @param vertx The vertx instance
   * @since 1.0.0
   */
  public OpenAPIRouterBuilder(final Uni<String> path, final Vertx vertx) {
    this.path = path;
    this.vertx = vertx;
  }

  @Override
  public void subscribe(final UniSubscriber<? super RouterBuilder> uniSubscriber) {
    path
      .onItem().transformToUni(x -> RouterBuilder.create(vertx, x))
      .subscribe().withSubscriber(uniSubscriber);
  }

  private final Uni<String> path;
  private final Vertx vertx;
}
