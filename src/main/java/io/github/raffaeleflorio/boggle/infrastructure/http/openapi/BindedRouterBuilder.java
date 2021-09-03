package io.github.raffaeleflorio.boggle.infrastructure.http.openapi;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.openapi.RouterBuilder;

import java.util.Map;
import java.util.function.Consumer;

/**
 * {@link RouterBuilder} emitter with handlers assigned eventually to its operations
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class BindedRouterBuilder extends AbstractUni<RouterBuilder> {
  /**
   * Builds an emitter
   *
   * @param origin   The router builder to decorate
   * @param handlers The operation to handler map
   * @since 1.0.0
   */
  public BindedRouterBuilder(final RouterBuilder origin, final Map<String, Consumer<RoutingContext>> handlers) {
    this(Uni.createFrom().item(origin), handlers);
  }

  /**
   * Builds an emitter
   *
   * @param origin   The router builder to decorate
   * @param handlers The operation to handler map
   * @since 1.0.0
   */
  public BindedRouterBuilder(final Uni<RouterBuilder> origin, final Map<String, Consumer<RoutingContext>> handlers) {
    this(origin, Uni.createFrom().item(handlers));
  }

  /**
   * Builds an emitter
   *
   * @param origin   The router builder to decorate
   * @param handlers The operation to handler map
   * @since 1.0.0
   */
  public BindedRouterBuilder(final Uni<RouterBuilder> origin, final Uni<Map<String, Consumer<RoutingContext>>> handlers) {
    this.origin = origin;
    this.handlers = handlers;
  }

  @Override
  public void subscribe(final UniSubscriber<? super RouterBuilder> uniSubscriber) {
    Uni.combine().all().unis(origin, handlers).asTuple()
      .onItem().transform(this::bind)
      .subscribe().withSubscriber(uniSubscriber);
  }

  private RouterBuilder bind(final Tuple2<RouterBuilder, Map<String, Consumer<RoutingContext>>> tuple) {
    var rb = tuple.getItem1();
    for (var entry : tuple.getItem2().entrySet()) {
      rb.operation(entry.getKey()).handler(entry.getValue());
    }
    return rb;
  }

  private final Uni<RouterBuilder> origin;
  private final Uni<Map<String, Consumer<RoutingContext>>> handlers;
}
