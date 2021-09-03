package io.github.raffaeleflorio.boggle.infrastructure.http.openapi;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.StaticHandler;

/**
 * {@link Router} emitter with /swagger-ui path handled
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SwaggerUIRouter extends AbstractUni<Router> {
  /**
   * Builds an emitter
   *
   * @param origin  The router to decorate
   * @param webRoot The swagger-ui webroot
   * @since 1.0.0
   */
  public SwaggerUIRouter(final Router origin, final String webRoot) {
    this(Uni.createFrom().item(origin), webRoot);
  }

  /**
   * Builds an emitter
   *
   * @param origin  The router to decorate
   * @param webRoot The swagger-ui webroot
   * @since 1.0.0
   */
  public SwaggerUIRouter(final Uni<Router> origin, final String webRoot) {
    this(origin, Uni.createFrom().item(webRoot));
  }

  /**
   * Builds an emitter
   *
   * @param origin  The router to decorate
   * @param webRoot The swagger-ui webroot
   * @since 1.0.0
   */
  public SwaggerUIRouter(final Uni<Router> origin, final Uni<String> webRoot) {
    this.origin = origin;
    this.webRoot = webRoot;
  }

  @Override
  public void subscribe(final UniSubscriber<? super Router> uniSubscriber) {
    Uni.combine().all().unis(origin, webRoot).asTuple()
      .onItem().transform(this::handled)
      .subscribe().withSubscriber(uniSubscriber);
  }

  private Router handled(final Tuple2<Router, String> tuple) {
    var router = tuple.getItem1();
    var handler = StaticHandler.create(tuple.getItem2()).setCachingEnabled(false);
    router.route("/swagger-ui").handler(this::redirectToIndex);
    router.route("/swagger-ui/").handler(this::redirectToIndex);
    router.route("/swagger-ui/index.html").handler(this::indexHandler);
    router.route("/swagger-ui/*").handler(handler);
    return router;
  }

  private void redirectToIndex(final RoutingContext routingContext) {
    routingContext.redirectAndForget("/swagger-ui/index.html?url=openapi.yml");
  }

  private void indexHandler(final RoutingContext routingContext) {
    if (routingContext.request().params().contains("url")) {
      routingContext.next();
    } else {
      redirectToIndex(routingContext);
    }
  }

  private final Uni<Router> origin;
  private final Uni<String> webRoot;
}
