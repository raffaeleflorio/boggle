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
 * A {@link io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary} backed by Treccani (https://www.treccani.it)
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class TreccaniVocabulary implements Vocabulary<CharSequence> {
  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @since 1.0.0
   */
  public TreccaniVocabulary(final WebClient webClient) {
    this(webClient, "https://www.treccani.it/vocabolario/");
  }

  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @param baseUrl   The base url
   * @since 1.0.0
   */
  TreccaniVocabulary(final WebClient webClient, final String baseUrl) {
    this(webClient, baseUrl, elements -> String.join("", elements));
  }

  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @param baseUrl   The base url
   * @param concatFn  The function to concatenate string
   * @since 1.0.0
   */
  TreccaniVocabulary(
    final WebClient webClient,
    final String baseUrl,
    final Function<List<CharSequence>, String> concatFn
  ) {
    this.webClient = webClient;
    this.baseUrl = baseUrl;
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
      .onItem().transform(response -> !response.contains("La tua ricerca non ha prodotto risultati in nessun documento"));
  }

  private String url(final Dice<CharSequence> word) {
    return baseUrl.concat(word(word)).concat("/");
  }

  private String word(final Dice<CharSequence> word) {
    return concatFn.apply(word.values()).toLowerCase();
  }

  private final WebClient webClient;
  private final String baseUrl;
  private final Function<List<CharSequence>, String> concatFn;
}
