package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MatchesTest {
  @Nested
  class FakeTest {
    @Test
    void testMatchCreation() {
      var expected = UUID.randomUUID();
      assertThat(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(expected, description)),
          x -> Uni.createFrom().nullItem()
        ).match(
            new Description.Fake()
          )
          .onItem().transform(Match::id),
        emits(equalTo(expected))
      );
    }

    @Test
    void testExistingMatch() {
      var expected = UUID.randomUUID();
      assertThat(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        ).match(expected).onItem().transform(Match::id),
        emits(equalTo(expected))
      );
    }
  }
}
