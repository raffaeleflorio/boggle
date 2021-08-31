package io.github.raffaeleflorio.boggle.graph;

import java.util.List;

/**
 * A math graph
 *
 * @param <T> The vertx type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @see <a href="https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)">Wikipedia definition</a>
 * @since 1.0.0
 */
public interface Graph<T> {
  /**
   * Builds a true boolean if the path is traversable
   *
   * @param path The path
   * @return True if valid
   * @since 1.0.0
   */
  Boolean path(List<T> path);

  /**
   * Builds a graph with a directed edge from one to two
   *
   * @param one The first vertex
   * @param two The second vertex
   * @return The new graph
   * @since 1.0.0
   */
  Graph<T> edge(T one, T two);
}
