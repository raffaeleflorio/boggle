package io.github.raffaeleflorio.boggle.infrastructure.http.server;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.http.HttpServer;

/**
 * Listening {@link HttpServer} emitter
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class ListeningHttpServer extends AbstractUni<HttpServer> {
  /**
   * Builds an emitter
   *
   * @param origin The http server to decorate
   * @param config The config with host and port defined
   * @since 1.0.0
   */
  public ListeningHttpServer(final HttpServer origin, final JsonObject config) {
    this(Uni.createFrom().item(origin), config);
  }

  /**
   * Builds an emitter
   *
   * @param origin The http server to decorate
   * @param config The config with host and port defined
   * @since 1.0.0
   */
  public ListeningHttpServer(final Uni<HttpServer> origin, final JsonObject config) {
    this(origin, Uni.createFrom().item(config));
  }

  /**
   * Builds an emitter
   *
   * @param origin The http server to decorate
   * @param config The config with host and port defined
   * @since 1.0.0
   */
  public ListeningHttpServer(final Uni<HttpServer> origin, final Uni<JsonObject> config) {
    this.origin = origin;
    this.config = config;
  }

  @Override
  public void subscribe(final UniSubscriber<? super HttpServer> uniSubscriber) {
    Uni.combine().all().unis(origin, config).asTuple()
      .onItem().transformToUni(tuple -> tuple.getItem1().listen(port(tuple.getItem2()), host(tuple.getItem2())))
      .subscribe().withSubscriber(uniSubscriber);
  }

  private Integer port(final JsonObject config) {
    return config.getInteger("port");
  }

  private String host(final JsonObject config) {
    return config.getString("host");
  }

  private final Uni<HttpServer> origin;
  private final Uni<JsonObject> config;
}
