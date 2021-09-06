package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.grid.Grids;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class InMemoryMatchesTest {
  @Test
  void testMatchCreation() {
    assertThat(
      new InMemoryMatches<>(
        new Grids.Fake<>(
          x -> Uni.createFrom().item(new Grid.Fake<>())
        )
      ).match(new Description.Fake()),
      emits(notNullValue())
    );
  }

  @Test
  void testExistingMatch() {
    var matches = new InMemoryMatches<>(x -> Uni.createFrom().item(new Grid.Fake<>()));
    assertThat(
      matches.match(new Description.Fake())
        .onItem().transform(Match::id)
        .onItem().transform(matches::match),
      emits(notNullValue())
    );
  }

  @Test
  void testMissingMatch() {
    assertThat(
      new InMemoryMatches<>(new Grids.Fake<>()).match(UUID.randomUUID()),
      emits(nullValue())
    );
  }
}
