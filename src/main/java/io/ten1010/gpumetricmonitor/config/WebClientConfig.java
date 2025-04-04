package io.ten1010.gpumetricmonitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
    private final String prometheusUrl;
    private final int maxInMemorySize;

    public WebClientConfig(@Value("${prometheus.url}") String prometheusUrl,
                           @Value("${webclient.max-in-memory-size}") int maxInMemorySize) {
        this.prometheusUrl = prometheusUrl;
        this.maxInMemorySize = maxInMemorySize;
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(maxInMemorySize))
                .baseUrl(prometheusUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }
}
