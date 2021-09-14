/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.infrastructure;

import io.github.raffaeleflorio.boggle.domain.description.FeatureEqualityPredicate;
import io.github.raffaeleflorio.boggle.domain.dice.it.SixteenItalianDice;
import io.github.raffaeleflorio.boggle.domain.grid.FourByFourGrid;
import io.github.raffaeleflorio.boggle.domain.grid.LangGrid;
import io.github.raffaeleflorio.boggle.domain.grid.LayoutGrid;
import io.github.raffaeleflorio.boggle.domain.grid.MappedGrids;
import io.github.raffaeleflorio.boggle.domain.match.ClassicRuledMatches;
import io.github.raffaeleflorio.boggle.domain.match.DeadlineMatches;
import io.github.raffaeleflorio.boggle.domain.match.DurationMatches;
import io.github.raffaeleflorio.boggle.domain.score.FourByFourScore;
import io.github.raffaeleflorio.boggle.domain.score.IfInGrid;
import io.github.raffaeleflorio.boggle.domain.score.IfInVocabulary;
import io.github.raffaeleflorio.boggle.infrastructure.grid.InMemoryGrids;
import io.github.raffaeleflorio.boggle.infrastructure.match.InMemoryMatches;
import io.github.raffaeleflorio.boggle.infrastructure.vocabulary.DizionarioItalianoIt;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseUnsignedInt;

public final class Main {
  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticleAndAwait(
      new HttpInfrastructure(
        new DurationMatches<>(
          new DeadlineMatches<>(
            new ClassicRuledMatches<>(
              new InMemoryMatches<>(
                new MappedGrids<>(
                  new InMemoryGrids<>(
                    Map.of(
                      new FeatureEqualityPredicate(
                        Map.of(
                          "lang", List.of("it"),
                          "size", List.of("4x4")
                        )
                      ),
                      new LangGrid<>(
                        new FourByFourGrid<>(new SixteenItalianDice()), "it"
                      )
                    )
                  ),
                  LayoutGrid::new
                )
              ),
              Map.of(
                new FeatureEqualityPredicate(
                  Map.of(
                    "lang", List.of("it"),
                    "size", List.of("4x4")
                  )
                ),
                match -> new IfInGrid<>(
                  new IfInVocabulary<>(
                    new FourByFourScore<>(),
                    new DizionarioItalianoIt(WebClient.create(vertx))
                  ),
                  match.grid()
                )
              )
            )
          ),
          Duration::ofMinutes
        ),
        parseUnsignedInt(args[0]),
        args[1]
      )
    );
  }
}
