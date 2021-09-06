package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.score.Score;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class ClassicRuledMatchesTest {
  @Test
  void testExistingMatch() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        ),
        Map.of(
          x -> true,
          match -> new Score.Fake<>()
        )
      ).match(UUID.randomUUID()),
      emits(notNullValue())
    );
  }

  @Test
  void testExistingMatchWithoutRule() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        ),
        Map.of()
      ).match(UUID.randomUUID()),
      emits(nullValue())
    );
  }

  @Test
  void testMissingMatch() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          x -> Uni.createFrom().nullItem()
        ),
        Map.of()
      ).match(UUID.randomUUID()),
      emits(nullValue())
    );
  }

  @Test
  void testMatchCreationWithoutRule() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(UUID.randomUUID(), description)),
          id -> Uni.createFrom().nullItem()
        ),
        Map.of()
      ).match(new Description.Fake()),
      emits(nullValue())
    );
  }

  @Test
  void testMatchCreationWithRule() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(UUID.randomUUID(), description)),
          id -> Uni.createFrom().nullItem()
        ),
        Map.of(
          x -> true,
          match -> new Score.Fake<>()
        )
      ).match(new Description.Fake()),
      emits(notNullValue())
    );
  }
}
