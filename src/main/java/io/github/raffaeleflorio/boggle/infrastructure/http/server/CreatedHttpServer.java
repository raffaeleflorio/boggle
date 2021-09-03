package io.github.raffaeleflorio.boggle.infrastructure.http.server;

import io.smallrye.mutiny.operators.uni.builders.UniCreateFromKnownItem;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.http.HttpServer;

/**
 * Created {@link HttpServer} emitter
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class CreatedHttpServer extends UniCreateFromKnownItem<HttpServer> {
  /**
   * Builds an emitter
   *
   * @param vertx The vertx instance
   * @since 1.0.0
   */
  public CreatedHttpServer(final Vertx vertx) {
    this(vertx.createHttpServer());
  }

  private CreatedHttpServer(final HttpServer item) {
    super(item);
  }
}
