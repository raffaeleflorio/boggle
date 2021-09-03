package io.github.raffaeleflorio.boggle.infrastructure.http.openapi;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.ext.web.handler.AuthenticationHandler;
import io.vertx.mutiny.ext.web.openapi.RouterBuilder;

import java.util.Map;

/**
 * {@link RouterBuilder} emitter with its security schemes handled
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SecuredRouterBuilder extends AbstractUni<RouterBuilder> {
  /**
   * Builds an emitter
   *
   * @param origin   The router builder to decorate
   * @param handlers The security scheme to handlers map
   * @since 1.0.0
   */
  public SecuredRouterBuilder(final RouterBuilder origin, final Map<String, AuthenticationHandler> handlers) {
    this(Uni.createFrom().item(origin), handlers);
  }

  /**
   * Builds an emitter
   *
   * @param origin   The router builder to decorate
   * @param handlers The security scheme to handlers map
   * @since 1.0.0
   */
  public SecuredRouterBuilder(final Uni<RouterBuilder> origin, final Map<String, AuthenticationHandler> handlers) {
    this(origin, Uni.createFrom().item(handlers));
  }

  /**
   * Builds an emitter
   *
   * @param origin   The router builder to decorate
   * @param handlers The security scheme to handlers map
   * @since 1.0.0
   */
  public SecuredRouterBuilder(final Uni<RouterBuilder> origin, final Uni<Map<String, AuthenticationHandler>> handlers) {
    this.origin = origin;
    this.handlers = handlers;
  }

  @Override
  public void subscribe(final UniSubscriber<? super RouterBuilder> uniSubscriber) {
    Uni.combine().all().unis(origin, handlers).asTuple()
      .onItem().transform(this::secured)
      .subscribe().withSubscriber(uniSubscriber);
  }

  private RouterBuilder secured(final Tuple2<RouterBuilder, Map<String, AuthenticationHandler>> tuple) {
    var rb = tuple.getItem1();
    for (var handler : tuple.getItem2().entrySet()) {
      rb.securityHandler(handler.getKey(), handler.getValue());
    }
    return rb;
  }

  private final Uni<RouterBuilder> origin;
  private final Uni<Map<String, AuthenticationHandler>> handlers;
}
