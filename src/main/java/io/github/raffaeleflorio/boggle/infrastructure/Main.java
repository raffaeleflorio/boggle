package io.github.raffaeleflorio.boggle.infrastructure;

import io.github.raffaeleflorio.boggle.infrastructure.config.ConfigAsDeploymentOptions;
import io.github.raffaeleflorio.boggle.infrastructure.config.FullConfig;
import io.github.raffaeleflorio.boggle.infrastructure.http.HttpVerticle;
import io.vertx.mutiny.core.Vertx;

/**
 * Entry point
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class Main {
  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    new DeployedVerticle(
      vertx,
      HttpVerticle::new,
      new ConfigAsDeploymentOptions(
        new FullConfig(vertx)
      )
    ).await().indefinitely();
  }
}
