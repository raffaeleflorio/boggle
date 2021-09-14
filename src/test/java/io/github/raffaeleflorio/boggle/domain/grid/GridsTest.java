package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class GridsTest {
  @Nested
  class FakeTest {
    @Test
    void testEmptyRepo() {
      assertThat(
        new Grids.Fake<>().grid(new Description.Fake()),
        emits(nullValue())
      );
    }

    @Test
    void testFullRepo() {
      assertThat(
        new Grids.Fake<>(description -> Uni.createFrom().item(new Grid.Fake<>()))
          .grid(new Description.Fake()),
        emits(notNullValue())
      );
    }
  }
}
