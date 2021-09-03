package io.github.raffaeleflorio.boggle.domain.graph;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GraphTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultConnected() {
      assertThat(
        new Graph.Fake<>().connected("any", "vertices"),
        equalTo(false)
      );
    }

    @Test
    void testConnectedWithCustomFn() {
      assertThat(
        new Graph.Fake<>(
          (x, y) -> true,
          (x, y) -> false
        ).connected("x", "y"),
        equalTo(true)
      );
    }

    @Test
    void testConnectedWithoutConnectedFn() {
      assertThat(
        new Graph.Fake<>().edge("x", "y").connected("x", "y"),
        equalTo(false)
      );
    }

    @Test
    void testDefaultAdjacent() {
      assertThat(
        new Graph.Fake<>().adjacent("any", "vertices"),
        equalTo(false)
      );
    }

    @Test
    void testAdjacentWithoutCustomFn() {
      assertThat(
        new Graph.Fake<>().edge("x", "y").adjacent("x", "y"),
        equalTo(false)
      );
    }

    @Test
    void testAdjacentWithCustomFn() {
      assertThat(
        new Graph.Fake<>(
          (x, y) -> false,
          (x, y) -> true
        ).edge("x", "y").adjacent("x", "y"),
        equalTo(true)
      );
    }
  }
}
