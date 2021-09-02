package io.github.raffaeleflorio.boggle.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * A {@link Description} that eventually overwrites an existing feature
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class SimpleDescription implements Description {
  SimpleDescription() {
    this(Map.of(), HashMap::new);
  }

  SimpleDescription(
    final Map<CharSequence, List<CharSequence>> origin,
    final Function<Map<CharSequence, List<CharSequence>>, Map<CharSequence, List<CharSequence>>> cloneFn
  ) {
    this.origin = origin;
    this.cloneFn = cloneFn;
  }

  @Override
  public Description feature(final CharSequence id, final List<CharSequence> values) {
    var cloned = cloned();
    cloned.put(id, values);
    return new SimpleDescription(cloned, cloneFn);
  }

  private Map<CharSequence, List<CharSequence>> cloned() {
    return cloneFn.apply(origin);
  }

  @Override
  public List<CharSequence> feature(final CharSequence id) {
    return origin.getOrDefault(id, List.of());
  }

  private final Map<CharSequence, List<CharSequence>> origin;
  private final Function<Map<CharSequence, List<CharSequence>>, Map<CharSequence, List<CharSequence>>> cloneFn;
}
