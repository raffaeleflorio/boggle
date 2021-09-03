package io.github.raffaeleflorio.boggle.infrastructure.http.auth;

import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.AuthenticationHandler;

/**
 * A passthru {@link AuthenticationHandler}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class PassThruAuthenticationHandler implements AuthenticationHandler {
  @Override
  public io.vertx.ext.web.handler.AuthenticationHandler getDelegate() {
    return io.vertx.ext.web.RoutingContext::next;
  }

  @Override
  public void handle(final RoutingContext routingContext) {
    routingContext.next();
  }
}
