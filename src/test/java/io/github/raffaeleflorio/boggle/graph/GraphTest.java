package io.github.raffaeleflorio.boggle.graph;

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
        new Graph.Fake<>((x, y) -> true).connected("x", "y"),
        equalTo(true)
      );
    }

    @Test
    void testEdgeWithoutConnectedFn() {
      assertThat(
        new Graph.Fake<>().edge("x", "y").connected("x", "y"),
        equalTo(false)
      );
    }
  }
}
