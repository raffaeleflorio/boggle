package io.github.raffaeleflorio.boggle.infrastructure.config;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.AbstractUni;
import io.smallrye.mutiny.subscription.UniSubscriber;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

/**
 * A configuration as a {@link DeploymentOptions} emitter
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class ConfigAsDeploymentOptions extends AbstractUni<DeploymentOptions> {
  /**
   * Builds an emitter
   *
   * @param config The config
   * @since 1.0.0
   */
  public ConfigAsDeploymentOptions(final JsonObject config) {
    this(Uni.createFrom().item(config));
  }

  /**
   * Builds an emitter
   *
   * @param config The config
   * @since 1.0.0
   */
  public ConfigAsDeploymentOptions(final Uni<JsonObject> config) {
    this.config = config;
  }

  @Override
  public void subscribe(final UniSubscriber<? super DeploymentOptions> uniSubscriber) {
    config
      .onItem().transform(x -> new DeploymentOptions().setConfig(x))
      .subscribe().withSubscriber(uniSubscriber);
  }

  private final Uni<JsonObject> config;
}
