package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmittedFailure;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmitted;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmittedFailure;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class DeadlineMatchesTest {
  @Test
  void testScoreOfInProgressMatch() {
    var future = Instant.now().plus(Duration.ofMinutes(10));
    assertThat(
      new DeadlineMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(
            new Match.Fake<>(id, new Description.Fake("deadline", future.toString()))
          )
        )
      ).match(
          UUID.randomUUID()
        )
        .onItem().transformToMulti(Match::score),
      AreEmittedFailure.emits(
        IllegalStateException.class, "Unable to build score of an in progress match"
      )
    );
  }

  @Test
  void testNewWordOfExpiredMatch() {
    assertThat(
      new DeadlineMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(
            new Match.Fake<>(id, new Description.Fake("deadline", "1970-01-01T00:00:00Z"))
          )
        )
      ).match(
          UUID.randomUUID()
        )
        .onItem().transformToUni(x -> x.sheet(UUID.randomUUID()))
        .onItem().transformToUni(sheet -> sheet.word(new Dice.Fake<>())),
      IsEmittedFailure.emits(
        IllegalStateException.class, "Deadline reached"
      )
    );
  }

  @Test
  void testBuildingMatch() {
    assertThat(
      new DeadlineMatches<>(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(UUID.randomUUID(), description)),
          id -> Uni.createFrom().nullItem()
        )
      ).match(
        new Description.Fake("deadline", "1970-01-01T00:00:00Z")
      ),
      IsEmitted.emits(notNullValue())
    );
  }

  @Test
  void testMissingMatch() {
    assertThat(
      new DeadlineMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          x -> Uni.createFrom().nullItem()
        )
      ).match(UUID.randomUUID()),
      IsEmitted.emits(nullValue())
    );
  }
}
