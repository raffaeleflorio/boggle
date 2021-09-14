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
package io.github.raffaeleflorio.boggle.infrastructure.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.ext.web.client.predicate.ResponsePredicate;

import java.util.List;
import java.util.function.Function;

/**
 * A {@link Vocabulary} backed by a HTTP server
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class HttpVocabulary implements Vocabulary<CharSequence> {
  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @param fmtUrl    The fmt string url
   * @param nokTag    The tag that signal not found words
   * @since 1.0.0
   */
  HttpVocabulary(final WebClient webClient, final String fmtUrl, final String nokTag) {
    this(webClient, fmtUrl, nokTag, elements -> String.join("", elements));
  }

  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @param fmtUrl    The base url
   * @param nokTag    The tag that signal not found words
   * @param concatFn  The function to concatenate string
   * @since 1.0.0
   */
  HttpVocabulary(
    final WebClient webClient,
    final String fmtUrl,
    final String nokTag, final Function<List<CharSequence>, String> concatFn
  ) {
    this.webClient = webClient;
    this.fmtUrl = fmtUrl;
    this.nokTag = nokTag;
    this.concatFn = concatFn;
  }

  @Override
  public Uni<Boolean> contains(final Dice<CharSequence> word) {
    return webClient
      .getAbs(url(word))
      .expect(ResponsePredicate.SC_OK)
      .expect(ResponsePredicate.contentType("text/html"))
      .send()
      .onItem().transform(HttpResponse::bodyAsString)
      .onItem().transform(response -> !response.contains(nokTag))
      .onFailure().retry().atMost(5);
  }

  private String url(final Dice<CharSequence> word) {
    return String.format(fmtUrl, word(word));
  }

  private String word(final Dice<CharSequence> word) {
    return concatFn.apply(word.values()).toLowerCase();
  }

  private final WebClient webClient;
  private final String fmtUrl;
  private final String nokTag;
  private final Function<List<CharSequence>, String> concatFn;
}
