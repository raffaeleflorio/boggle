package io.github.raffaeleflorio.boggle.domain.sandtimer;

/**
 * A sand timer
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface SandTimer {
  /**
   * Builds true if expired
   *
   * @return True if expired
   * @since 1.0.0
   */
  Boolean expired();

  /**
   * A sand timer useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements SandTimer {
    /**
     * Builds a fake
     *
     * @param expired True if expired
     * @since 1.0.0
     */
    public Fake(final Boolean expired) {
      this.expired = expired;
    }

    @Override
    public Boolean expired() {
      return expired;
    }

    private final Boolean expired;
  }
}
