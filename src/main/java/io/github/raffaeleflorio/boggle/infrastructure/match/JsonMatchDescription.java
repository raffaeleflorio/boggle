package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * {@link Match} {@link Description} from JSON
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class JsonMatchDescription implements Description {
  /**
   * Builds a description
   *
   * @param origin The JSON
   * @since 1.0.0
   */
  public JsonMatchDescription(final JsonObject origin) {
    this.origin = origin;
  }

  @Override
  public List<CharSequence> feature(final CharSequence name) {
    return List.of(origin.getString(name.toString()));
  }

  @Override
  public Description feature(final CharSequence name, final List<CharSequence> values) {
    return new JsonMatchDescription(origin.copy().put(name.toString(), values.get(0)));
  }

  private final JsonObject origin;
}
