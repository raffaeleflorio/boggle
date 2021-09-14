package io.github.raffaeleflorio.boggle.domain.graph;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GraphTest {
  @Nested
  class FakeTest {
    @Test
    void testConnected() {
      assertThat(
        new Graph.Fake<>(
          (first, second) -> true,
          (x, y) -> false
        ).connected("x", "y"),
        equalTo(true)
      );
    }

    @Test
    void testAdjacent() {
      assertThat(
        new Graph.Fake<>(
          (x, y) -> false,
          (first, second) -> true
        ).adjacent("x", "y"),
        equalTo(true)
      );
    }

    @Test
    void testEdgeIgnored() {
      assertThat(
        new Graph.Fake<>(
          (first, second) -> false,
          (x, y) -> true
        ).edge("one", "two").connected("one", "two"),
        equalTo(false)
      );
    }
  }
}
