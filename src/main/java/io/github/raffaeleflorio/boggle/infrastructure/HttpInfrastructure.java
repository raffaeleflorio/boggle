package io.github.raffaeleflorio.boggle.infrastructure;

import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.match.Matches;
import io.github.raffaeleflorio.boggle.infrastructure.description.JsonAsDescription;
import io.github.raffaeleflorio.boggle.infrastructure.dice.JsonDice;
import io.github.raffaeleflorio.boggle.infrastructure.match.MatchAsJson;
import io.github.raffaeleflorio.boggle.infrastructure.player.JsonPlayer;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.http.HttpServer;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.AuthenticationHandler;
import io.vertx.mutiny.ext.web.handler.CorsHandler;
import io.vertx.mutiny.ext.web.openapi.RouterBuilder;

import java.util.Set;
import java.util.UUID;

import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;

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
      .onItem().transform(this::passhThruCors)
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

  private RouterBuilder passhThruCors(final RouterBuilder routerBuilder) {
    return routerBuilder.rootHandler(CorsHandler.create("*").allowedMethods(Set.of(GET, POST)));
  }

  private RouterBuilder binded(final RouterBuilder routerBuilder) {
    routerBuilder.operation("createMatch").handler(this::createMatch);
    routerBuilder.operation("getMatch").handler(this::getMatch);
    routerBuilder.operation("getMatchScore").handler(this::getMatchScore);
    routerBuilder.operation("sendWord").handler(this::sendWord);
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

  private void getMatchScore(final RoutingContext ctx) {
    matches
      .match(UUID.fromString(ctx.pathParam("match")))
      .onItem().ifNotNull().transformToMulti(Match::score)
      .collect().asList()
      .onItem().ifNotNull().transform(JsonArray::new)
      .onItem().ifNotNull().transform(JsonArray::encodePrettily)
      .onItem().ifNotNull().<Runnable>transform(json -> () -> ctx.response().setStatusCode(200).endAndForget(json))
      .onFailure().recoverWithItem(() -> ctx.response().setStatusCode(404).endAndForget())
      .subscribe().with(Runnable::run);
  }

  private void sendWord(final RoutingContext ctx) {
    var body = ctx.getBodyAsJson();
    matches
      .match(UUID.fromString(ctx.pathParam("match")))
      .onItem().ifNotNull().transformToUni(match -> match.sheet(new JsonPlayer(body).id()))
      .onItem().ifNotNull().transformToUni(sheet -> sheet.word(new JsonDice(body)))
      .onItem().<Runnable>transform(x -> () -> ctx.response().setStatusCode(202).endAndForget())
      .onFailure().recoverWithItem(() -> ctx.response().setStatusCode(404).endAndForget())
      .subscribe().with(Runnable::run);
  }

  private Uni<HttpServer> listeningHttpServer(final Router router) {
    return vertx.createHttpServer()
      .requestHandler(router)
      .listen(37608, "127.0.0.1");
  }

  private final Matches<CharSequence> matches;
}
