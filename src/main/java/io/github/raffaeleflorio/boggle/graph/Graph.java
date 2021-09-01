package io.github.raffaeleflorio.boggle.graph;

/**
 * A directed math graph
 *
 * @param <T> The vertx type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @see <a href="https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)">Wikipedia definition</a>
 * @since 1.0.0
 */
public interface Graph<T> {
  /**
   * Builds a true boolean if the first vertex is connected to second
   *
   * @param first  The first vertex
   * @param second The second vertex
   * @return True if connected
   * @since 1.0.0
   */
  Boolean connected(T first, T second);

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
