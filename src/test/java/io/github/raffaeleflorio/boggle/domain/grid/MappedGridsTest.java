package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.nullValue;

class MappedGridsTest {
  @Test
  void assertMapping() {
    assertThat(
      new MappedGrids<>(
        new Grids.Fake<>(x -> Uni.createFrom().item(new Grid.Fake<>())),
        grid -> new Grid.Fake<>(new Dice.Fake<>(), x -> false, new Description.Fake("mapped", "grid"))
      ).grid(new Description.Fake())
        .onItem().transform(Grid::description)
        .onItem().transform(description -> description.feature("mapped")),
      emits(
        contains("grid")
      )
    );
  }

  @Test
  void assertMappingOfEmptyUni() {
    assertThat(
      new MappedGrids<>(
        new Grids.Fake<>(x -> Uni.createFrom().nullItem()),
        grid -> new Grid.Fake<>()
      ).grid(new Description.Fake()),
      emits(nullValue())
    );
  }
}