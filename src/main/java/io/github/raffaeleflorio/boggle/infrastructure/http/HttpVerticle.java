package io.github.raffaeleflorio.boggle.infrastructure.http;

import io.github.raffaeleflorio.boggle.infrastructure.http.auth.PassThruAuthenticationHandler;
import io.github.raffaeleflorio.boggle.infrastructure.http.openapi.*;
import io.github.raffaeleflorio.boggle.infrastructure.http.server.CreatedHttpServer;
import io.github.raffaeleflorio.boggle.infrastructure.http.server.HandledHttpServer;
import io.github.raffaeleflorio.boggle.infrastructure.http.server.ListeningHttpServer;
import io.github.raffaeleflorio.boggle.infrastructure.http.server.RouterAsConsumer;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;

import java.util.Map;

/**
 * Verticle responsible of HTTP endpoints
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class HttpVerticle extends AbstractVerticle {
  @Override
  public Uni<Void> asyncStart() {
    return new ListeningHttpServer(
      new HandledHttpServer(
        new CreatedHttpServer(vertx),
        new RouterAsConsumer(
          new SwaggerUIRouter(
            new RouterBuilderAsRouter(
              new SecuredRouterBuilder(
                new BindedRouterBuilder(
                  new OpenAPIRouterBuilder(
                    config().getString("openapi"),
                    vertx
                  ),
                  Map.of()
                ),
                Map.of(
                  "xApiKey", new PassThruAuthenticationHandler()
                )
              )
            ),
            config().getString("swagger-ui")
          )
        )
      ),
      config()
    ).replaceWithVoid();
  }
}
