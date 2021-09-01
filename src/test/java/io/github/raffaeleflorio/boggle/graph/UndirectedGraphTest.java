package io.github.raffaeleflorio.boggle.graph;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class UndirectedGraphTest {
  @Test
  void testConnection() {
    assertThat(
      new UndirectedGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .edge(3, 4)
        .connected(3, 4),
      equalTo(true)
    );
  }

  @Test
  void testAdjacency() {
    assertThat(
      new UndirectedGraph<>()
        .edge(1, 2)
        .adjacent(2, 1),
      equalTo(true)
    );
  }
}
