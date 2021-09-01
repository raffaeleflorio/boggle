package io.github.raffaeleflorio.boggle.graph;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DFSGraphTest {
  @Test
  void testConnectionInEmptyGraph() {
    assertThat(
      new DFSGraph<>().connected(1, 2),
      equalTo(false)
    );
  }

  @Test
  void testSimplestConnection() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .connected(1, 2),
      equalTo(true)
    );
  }

  @Test
  void testSimplestTransitiveConnection() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .connected(1, 3),
      equalTo(true)
    );
  }

  @Test
  void testConnectionBetweenInexistentVertices() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .connected(4, 5),
      equalTo(false)
    );
  }

  @Test
  void testConnectionInOppositeDirection() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .connected(3, 2),
      equalTo(false)
    );
  }

  @Test
  void testConnectionInASimpleCyclicGraph() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .edge(3, 1)
        .connected(3, 2),
      equalTo(true)
    );
  }

  @Test
  void testConnectionInAMoreComplexCyclicGraph() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .edge(2, 5)
        .edge(3, 4)
        .edge(5, 1)
        .edge(5, 6)
        .edge(7, 8)
        .edge(7, 9)
        .edge(7, 10)
        .edge(8, 1)
        .edge(10, 8)
        .connected(10, 6),
      equalTo(true)
    );
  }

  @Test
  void testConnectionWithOneVertexInAcyclicGraph() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .connected(1, 1),
      equalTo(true)
    );
  }

  @Test
  void testConnectionWithOneVertexAndCyclicGraph() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .edge(3, 1)
        .connected(1, 1),
      equalTo(true)
    );
  }

  @Test
  void testConnectionWithALoop() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 1)
        .connected(1, 1),
      equalTo(true)
    );
  }

  @Test
  void testAdjacency() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .adjacent(1, 2),
      equalTo(true)
    );
  }

  @Test
  void testUndirectedAdjacency() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .adjacent(2, 1),
      equalTo(false)
    );
  }

  @Test
  void testConnectedButNotAdjacent() {
    assertThat(
      new DFSGraph<>()
        .edge(1, 2)
        .edge(2, 3)
        .edge(3, 4)
        .edge(4, 1)
        .adjacent(1, 4),
      equalTo(false)
    );
  }
}
