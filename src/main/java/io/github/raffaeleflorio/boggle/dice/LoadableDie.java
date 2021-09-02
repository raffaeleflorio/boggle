package io.github.raffaeleflorio.boggle.dice;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/**
 * A loadable {@link Die}, so some sides could be more probable than others
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class LoadableDie<T> implements Die<T> {
  /**
   * Builds a loadable die
   *
   * @param sides The sides
   * @since 1.0.0
   */
  public LoadableDie(final List<T> sides) {
    this(
      sides,
      max -> ThreadLocalRandom.current().nextInt(max)
    );
  }

  /**
   * Builds a loadable die
   *
   * @param sides    The sides
   * @param randomFn The function to build bounded random ints
   * @since 1.0.0
   */
  public LoadableDie(final List<T> sides, final Function<Integer, Integer> randomFn) {
    this(sides, randomFn, 0);
  }

  /**
   * Builds a loadable die
   *
   * @param sides    The sides
   * @param randomFn The function to build a bounded random value
   * @param current  The current side
   * @since 1.0.0
   */
  LoadableDie(final List<T> sides, final Function<Integer, Integer> randomFn, final Integer current) {
    this.sides = sides;
    this.current = current;
    this.randomFn = randomFn;
  }

  @Override
  public T value() {
    return sides.get(current);
  }

  @Override
  public Die<T> rolled() {
    return new LoadableDie<>(sides, randomFn, randomFn.apply(sides.size()));
  }

  private final List<T> sides;
  private final Function<Integer, Integer> randomFn;
  private final Integer current;
}
