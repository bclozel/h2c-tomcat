With the current application, the Tomcat root endpoint replies with HTTP 204 and no body, with HTTP11 and H2C:
```
curl --http2 --head http://localhost:8080/
HTTP/1.1 101
Connection: Upgrade
Upgrade: h2c
Date: Mon, 23 Jan 2023 10:13:00 GMT

HTTP/2 204
date: Mon, 23 Jan 2023 10:13:00 GMT
```

The `HttpClientTests` implements several test cases with the Reactor Netty `HttpClient`.
With HTTP 1.1, the client behaves as expected. But with H2C, the client decodes a `transfer-encoding` response header that is not sent by the Tomcat server.
This has been checked with a Wireshark traffic capture session.

This behavior with H2C can confuse clients with a response body of zero length vs. an expected missing response body.
See https://github.com/spring-projects/spring-framework/issues/29845.

Oddly, this behavior does not happen with the Reactor Netty client when the server is Undertow.
Undertow is responding a bit differently with a `Transfer-Encoding` header in the Upgrade response:

```
curl --http2 --head http://localhost:8080/dto
HTTP/1.1 101 Switching Protocols
Connection: Upgrade
Upgrade: h2c
Transfer-Encoding: chunked
Date: Mon, 23 Jan 2023 10:47:16 GMT

HTTP/2 204
date: Mon, 23 Jan 2023 10:47:16 GMT
```