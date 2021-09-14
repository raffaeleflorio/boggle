package io.github.raffaeleflorio.boggle.domain.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A graph traversed with Depth-first search
 *
 * @param <T> The vertex type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @see <a href="https://en.wikipedia.org/wiki/Depth-first_search">Wikipedia definition</a>
 * @since 1.0.0
 */
public final class DFSGraph<T> implements Graph<T> {
  /**
   * Builds an empty graph
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  public DFSGraph() {
    this(new HashMap<>(), HashMap::new, HashSet::new, HashSet::new);
  }

  private DFSGraph(
    final Map<T, Set<T>> adjList,
    final Function<Map<T, Set<T>>, Map<T, Set<T>>> mapCloneFn,
    final Supplier<Set<T>> newSetFn,
    final Function<Set<T>, Set<T>> setCloneFn
  ) {
    this.adjList = adjList;
    this.mapCloneFn = mapCloneFn;
    this.newSetFn = newSetFn;
    this.setCloneFn = setCloneFn;
  }

  @Override
  public Boolean connected(final T first, final T second) {
    return dfs(newSetFn.get(), first).contains(second);
  }

  private Set<T> dfs(final Set<T> discovered, final T root) {
    discovered.add(root);
    for (var adj : adjacents(root)) {
      if (!discovered.contains(adj)) {
        dfs(discovered, adj);
      }
    }
    return discovered;
  }

  private Set<T> adjacents(final T first) {
    return adjList.getOrDefault(first, newSetFn.get());
  }

  @Override
  public Boolean adjacent(final T first, final T second) {
    return adjacents(first).contains(second);
  }

  @Override
  public Graph<T> edge(final T one, final T two) {
    var newSet = setCloneFn.apply(adjacents(one));
    newSet.add(two);
    var newAdjList = mapCloneFn.apply(adjList);
    newAdjList.put(one, newSet);
    return new DFSGraph<>(newAdjList, mapCloneFn, newSetFn, setCloneFn);
  }

  private final Map<T, Set<T>> adjList;
  private final Function<Map<T, Set<T>>, Map<T, Set<T>>> mapCloneFn;
  private final Supplier<Set<T>> newSetFn;
  private final Function<Set<T>, Set<T>> setCloneFn;
}
