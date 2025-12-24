package org.framework.core;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Request {

    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    private static void logResponse(HttpResponse<String> response) {

        System.out.println("========== HTTP LOGGER ==========");
        System.out.println("URL: " + response.uri());

        String query = response.request().uri().getQuery();
        System.out.println("Params: " + (query != null ? query : "-"));

        System.out.println("Headers:");
        response.request().headers().map()
                .forEach((k, v) -> System.out.println("  " + k + ": " + v));

        System.out.println("Status Code: " + response.statusCode());

        System.out.println("Body:");
        System.out.println(response.body());

        System.out.println("===================================");
    }



    public static class GetRequest {

        private final String baseUrl;
        private final Map<String, String> params = new HashMap<>();
        private final Map<String, String> headers = new HashMap<>();

        private GetRequest(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public GetRequest param(String key, String value) {
            params.put(key, value);
            return this;
        }

        public GetRequest header(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public HttpResponse<String> send() {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(buildUri())
                        .GET()
                        .headers(flattenHeaders())
                        .build();

                HttpResponse<String> response =
                        CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                logResponse(response);
                return response;

            } catch (Exception e) {
                throw new RuntimeException(
                        "Error executing GET request to: " + baseUrl, e
                );
            }
        }


        private URI buildUri() {
            if (params.isEmpty()) {
                return URI.create(baseUrl);
            }

            StringBuilder query = new StringBuilder();
            params.forEach((k, v) -> {
                if (!query.isEmpty()) query.append("&");
                query.append(encode(k)).append("=").append(encode(v));
            });

            return URI.create(baseUrl + "?" + query);
        }

        private String[] flattenHeaders() {
            return headers.entrySet()
                    .stream()
                    .flatMap(e -> java.util.stream.Stream.of(e.getKey(), e.getValue()))
                    .toArray(String[]::new);
        }

        private String encode(String value) {
            return URLEncoder.encode(value, StandardCharsets.UTF_8);
        }
    }

}
