package io.github.raffaeleflorio.boggle.infrastructure.http.server;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.mutiny.core.http.HttpServer;
import io.vertx.mutiny.core.http.HttpServerRequest;

import java.util.function.Consumer;

/**
 * {@link HttpServer} with request handler assigned
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class HandledHttpServer extends AbstractUni<HttpServer> {
  /**
   * Builds an emitter
   *
   * @param origin  The http server to decorate
   * @param handler The handler
   * @since 1.0.0
   */
  public HandledHttpServer(final HttpServer origin, final Consumer<HttpServerRequest> handler) {
    this(Uni.createFrom().item(origin), handler);
  }

  /**
   * Builds an emitter
   *
   * @param origin  The http server to decorate
   * @param handler The handler
   * @since 1.0.0
   */
  public HandledHttpServer(final Uni<HttpServer> origin, final Consumer<HttpServerRequest> handler) {
    this(origin, Uni.createFrom().item(handler));
  }

  /**
   * Builds an emitter
   *
   * @param origin  The http server to decorate
   * @param handler The handler
   * @since 1.0.0
   */
  public HandledHttpServer(final Uni<HttpServer> origin, final Uni<Consumer<HttpServerRequest>> handler) {
    this.origin = origin;
    this.handler = handler;
  }

  @Override
  public void subscribe(final UniSubscriber<? super HttpServer> uniSubscriber) {
    Uni.combine().all().unis(origin, handler).asTuple()
      .onItem().transform(tuple -> tuple.getItem1().requestHandler(tuple.getItem2()))
      .subscribe().withSubscriber(uniSubscriber);
  }

  private final Uni<HttpServer> origin;
  private final Uni<Consumer<HttpServerRequest>> handler;
}
