package io.github.raffaeleflorio.boggle.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A graph traversed with Depth-first search
 *
 * @param <T> The vertx type
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
    final Supplier<Set<T>> setFn,
    final Function<Set<T>, Set<T>> setCloneFn
  ) {
    this.adjList = adjList;
    this.mapCloneFn = mapCloneFn;
    this.setFn = setFn;
    this.setCloneFn = setCloneFn;
  }

  @Override
  public Boolean connected(final T first, final T second) {
    return dfs(first).contains(second);
  }

  private Set<T> dfs(final T root) {
    return dfs(setFn.get(), root, x -> {
    });
  }

  private Set<T> dfs(final Set<T> discovered, final T root, final Consumer<T> discoverFn) {
    discoverFn.accept(root);
    for (var adj : adjList.getOrDefault(root, setFn.get())) {
      if (!discovered.contains(adj)) {
        dfs(discovered, adj, discovered::add);
      }
    }
    return discovered;
  }

  @Override
  public Graph<T> edge(final T one, final T two) {
    var newSet = setCloneFn.apply(adjList.getOrDefault(one, setFn.get()));
    newSet.add(two);
    var newAdjList = mapCloneFn.apply(adjList);
    newAdjList.put(one, newSet);
    return new DFSGraph<>(newAdjList, mapCloneFn, setFn, setCloneFn);
  }

  private final Map<T, Set<T>> adjList;
  private final Function<Map<T, Set<T>>, Map<T, Set<T>>> mapCloneFn;
  private final Supplier<Set<T>> setFn;
  private final Function<Set<T>, Set<T>> setCloneFn;
}
