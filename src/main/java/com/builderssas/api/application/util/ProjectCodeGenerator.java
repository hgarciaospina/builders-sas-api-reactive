package com.builderssas.api.application.util;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.security.SecureRandom;
import java.util.Arrays;

@Component
public final class ProjectCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private ProjectCodeGenerator() {}

    public static Mono<String> generate(String projectName, Mono<Long> seq) {

        return buildPrefix(projectName)
                .zipWith(seq.map(ProjectCodeGenerator::formatSequence),
                        (prefix, sequence) -> prefix + sequence
                );
    }

    private static Mono<String> buildPrefix(String name) {

        Mono<String> initials =
                Flux.fromIterable(Arrays.asList(name.trim().split("\\s+")))
                        .map(word -> word.substring(0, 1).toUpperCase())
                        .reduce("", String::concat);

        Mono<String> oneWordPrefix =
                Mono.just(name.substring(0, Math.min(3, name.length())).toUpperCase());

        Mono<String> basePrefix =
                initials.zipWith(oneWordPrefix,
                        (letters, fallback) ->
                                letters.length() == 1 ? fallback : letters
                );

        return basePrefix.flatMap(prefix ->
                Flux.range(0, 6)
                        .map(i ->
                                i < prefix.length()
                                        ? prefix.substring(i, i + 1)
                                        : randomLetter()
                        )
                        .reduce("", String::concat)
        );
    }

    private static String randomLetter() {
        return String.valueOf(ABC.charAt(RANDOM.nextInt(ABC.length())));
    }

    private static String formatSequence(Long seq) {
        return String.format("%04d", seq);
    }
}
