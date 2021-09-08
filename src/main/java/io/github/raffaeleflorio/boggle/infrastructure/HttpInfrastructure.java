package io.github.raffaeleflorio.boggle.infrastructure;

import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.match.Matches;
import io.github.raffaeleflorio.boggle.infrastructure.description.JsonAsDescription;
import io.github.raffaeleflorio.boggle.infrastructure.match.MatchAsJson;
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
    routerBuilder.operation("createMatch").handler(this::createMatch);
    routerBuilder.operation("getMatch").handler(this::getMatch);
    return routerBuilder;
  }

  private void createMatch(final RoutingContext ctx) {
    reply(ctx, matches.match(new JsonAsDescription(ctx.getBodyAsJson())));
  }

  private void reply(final RoutingContext ctx, final Uni<Match<CharSequence>> match) {
    match
      .onItem().ifNotNull().transformToUni(MatchAsJson::new)
      .onItem().ifNotNull().transform(JsonObject::encodePrettily)
      .onItem().ifNotNull().<Runnable>transform(json -> () -> ctx.response().setStatusCode(200).endAndForget(json))
      .onItem().ifNull().continueWith(() -> ctx.response().setStatusCode(404).endAndForget())
      .subscribe().with(Runnable::run);
  }

  private void getMatch(final RoutingContext ctx) {
    reply(ctx, matches.match(UUID.fromString(ctx.pathParam("match"))));
  }

  private Uni<HttpServer> listeningHttpServer(final Router router) {
    return vertx.createHttpServer()
      .requestHandler(router)
      .listen(37608, "127.0.0.1");
  }

  private final Matches<CharSequence> matches;
}
