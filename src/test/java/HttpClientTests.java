
import io.netty.handler.codec.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientTests {

	// This test fails
	@Test
	void noContentH2c() {
		HttpClient client = HttpClient.create().protocol(HttpProtocol.H2C);
		Mono<HttpClientResponse> responseMono = client.get().uri("http://localhost:8080/").response();
		StepVerifier.create(responseMono).assertNext(response -> {
			assertThat(response.responseHeaders().names()).doesNotContain("Transfer-Encoding", "transfer-encoding");
		}).verifyComplete();
	}

	@Test
	void noContentHttp11() {
		HttpClient client = HttpClient.create().protocol(HttpProtocol.HTTP11);
		Mono<HttpClientResponse> responseMono = client.get().uri("http://localhost:8080/").response();
		StepVerifier.create(responseMono).assertNext(response -> {
			assertThat(response.responseHeaders().names()).doesNotContain("Transfer-Encoding", "transfer-encoding");
		}).verifyComplete();
	}
}
