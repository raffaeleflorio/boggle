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
}
