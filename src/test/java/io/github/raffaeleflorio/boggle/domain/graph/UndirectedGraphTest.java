package io.github.raffaeleflorio.boggle.domain.graph;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class UndirectedGraphTest {
  @Test
  void testUndirectedConnection() {
    assertThat(
      new UndirectedGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .edge(3, 4)
        .connected(4, 3),
      equalTo(true)
    );
  }

  @Test
  void testDirectedConnection() {
    assertThat(
      new UndirectedGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .connected(2, 3),
      equalTo(true)
    );
  }

  @Test
  void testMissingConnection() {
    assertThat(
      new UndirectedGraph<>().connected(7, 8),
      equalTo(false)
    );
  }

  @Test
  void testUndirectedAdjacency() {
    assertThat(
      new UndirectedGraph<>()
        .edge(1, 2)
        .adjacent(2, 1),
      equalTo(true)
    );
  }

  @Test
  void testDirectedAdjacency() {
    assertThat(
      new UndirectedGraph<>()
        .edge(1, 2)
        .edge(42, -42)
        .adjacent(42, -42),
      equalTo(true)
    );
  }

  @Test
  void testMissingAdjacency() {
    assertThat(
      new UndirectedGraph<>().adjacent("A", "C"),
      equalTo(false)
    );
  }
}
