package io.github.raffaeleflorio.boggle.infrastructure;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.mutiny.core.Vertx;

import java.util.function.Supplier;

/**
 * Emitter of a deployment ID of a {@link Verticle}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeployedVerticle extends AbstractUni<String> {
  /**
   * Builds an emitter
   *
   * @param vertx      The vertx instance
   * @param verticleFn The function that supply a verticle
   * @param options    The deployment options
   */
  public DeployedVerticle(
    final Vertx vertx,
    final Supplier<Verticle> verticleFn,
    final DeploymentOptions options
  ) {
    this(vertx, verticleFn, Uni.createFrom().item(options));
  }

  /**
   * Builds an emitter
   *
   * @param vertx      The vertx instance
   * @param verticleFn The function that supply a verticle
   * @param options    The deployment options
   */
  public DeployedVerticle(
    final Vertx vertx,
    final Supplier<Verticle> verticleFn,
    final Uni<DeploymentOptions> options
  ) {
    this.vertx = vertx;
    this.verticleFn = verticleFn;
    this.options = options;
  }

  @Override
  public void subscribe(final UniSubscriber<? super String> uniSubscriber) {
    options
      .onItem().transformToUni(x -> vertx.deployVerticle(verticleFn, x))
      .subscribe().withSubscriber(uniSubscriber);
  }

  private final Vertx vertx;
  private final Supplier<Verticle> verticleFn;
  private final Uni<DeploymentOptions> options;
}
