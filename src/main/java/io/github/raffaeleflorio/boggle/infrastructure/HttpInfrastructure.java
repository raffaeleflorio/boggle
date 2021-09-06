package io.github.raffaeleflorio.boggle.infrastructure;

import io.github.raffaeleflorio.boggle.domain.match.Matches;
import io.github.raffaeleflorio.boggle.infrastructure.description.JsonAsDescription;
import io.github.raffaeleflorio.boggle.infrastructure.match.MatchAsJson;
import io.github.raffaeleflorio.boggle.infrastructure.sheet.SheetAsJson;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.http.HttpServer;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.AuthenticationHandler;
import io.vertx.mutiny.ext.web.openapi.RouterBuilder;

import java.util.UUID;

// TODO refactoring

/**
 * The HTTP boggle infrastructure
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class HttpInfrastructure extends AbstractVerticle {
  /**
   * Builds the HTTP infrastructure
   *
   * @param matches The matches
   * @since 1.0.0
   */
  HttpInfrastructure(final Matches<CharSequence> matches) {
    this.matches = matches;
  }

  @Override
  public Uni<Void> asyncStart() {
    return RouterBuilder
      .create(vertx, "openapi.yml")
      .onItem().transform(this::passThruAuth)
      .onItem().transform(this::binded)
      .onItem().transform(RouterBuilder::createRouter)
      .onItem().transformToUni(this::listeningHttpServer)
      .replaceWithVoid();
  }

  private RouterBuilder passThruAuth(final RouterBuilder routerBuilder) {
    return routerBuilder.securityHandler("xApiKey", new AuthenticationHandler() {
      @Override
      public io.vertx.ext.web.handler.AuthenticationHandler getDelegate() {
        return io.vertx.ext.web.RoutingContext::next;
      }

      @Override
      public void handle(final RoutingContext routingContext) {
        routingContext.next();
      }
    });
  }

  private RouterBuilder binded(final RouterBuilder routerBuilder) {
    routerBuilder.operation("buildSheet").handler(ctx -> {
      var player = UUID.fromString(ctx.getBodyAsJson().getString("id"));
      matches
        .match(UUID.fromString(ctx.pathParam("match")))
        .onItem().ifNotNull().transformToUni(match -> match.sheet(player))
        .onItem().ifNotNull().transform(SheetAsJson::new)
        .onItem().ifNotNull().transform(JsonObject::encodePrettily)
        .onItem().ifNotNull().<Runnable>transform(json -> () -> ctx.response().setStatusCode(200).endAndForget(json))
        .onItem().ifNull().continueWith(() -> ctx.response().setStatusCode(404).endAndForget())
        .subscribe().with(Runnable::run);
    });
    routerBuilder.operation("createMatch").handler(ctx -> {
      var description = new JsonAsDescription(ctx.getBodyAsJson());
      matches
        .match(description)
        .onItem().ifNotNull().transform(MatchAsJson::new)
        .onItem().ifNotNull().transform(JsonObject::encodePrettily)
        .onItem().ifNotNull().<Runnable>transform(json -> () -> ctx.response().setStatusCode(200).endAndForget(json))
        .onItem().ifNull().continueWith(() -> ctx.response().setStatusCode(404).endAndForget())
        .subscribe().with(Runnable::run);
    });
    return routerBuilder;
  }

  private Uni<HttpServer> listeningHttpServer(final Router router) {
    return vertx.createHttpServer()
      .requestHandler(router)
      .listen(37608, "127.0.0.1");
  }

  private final Matches<CharSequence> matches;
}
