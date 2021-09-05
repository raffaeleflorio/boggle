package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

class FixedDurationMatchesTest {
  @Test
  void testDeadlineCalculation() {
    assertThat(
      new FixedDurationMatches<>(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(UUID.randomUUID(), description)),
          x -> Uni.createFrom().nullItem()
        ),
        Duration::ofMinutes,
        () -> Instant.EPOCH
      )
        .match(new Description.Fake("duration", "3"))
        .onItem().transform(x -> x.description().feature("deadline")),
      emits(
        contains("1970-01-01T00:03:00Z")
      )
    );
  }

  @Test
  void testBuildingExisting() {
    assertThat(
      new FixedDurationMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        )
      ).match(UUID.randomUUID()),
      emits(notNullValue())
    );
  }
}
