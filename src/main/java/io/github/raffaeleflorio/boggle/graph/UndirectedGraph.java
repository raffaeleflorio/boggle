package io.github.raffaeleflorio.boggle.graph;

/**
 * An undirected {@link Graph}
 *
 * @param <T> The vertex type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class UndirectedGraph<T> implements Graph<T> {
  /**
   * Builds an empty undirected graph
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  public UndirectedGraph() {
    this(new DFSGraph<>());
  }

  /**
   * Builds an undirected graph
   *
   * @param origin The graph to decorate
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  public UndirectedGraph(final Graph<T> origin) {
    this.origin = origin;
  }

  @Override
  public Boolean connected(final T first, final T second) {
    return origin.connected(first, second) || origin.connected(second, first);
  }

  @Override
  public Boolean adjacent(final T first, final T second) {
    return origin.adjacent(first, second) || origin.adjacent(second, first);
  }

  @Override
  public Graph<T> edge(final T one, final T two) {
    return new UndirectedGraph<>(origin.edge(one, two));
  }

  private final Graph<T> origin;
}
