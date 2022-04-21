
With the current application, trying to get the request body with upgraded H2C requests fails:

```
➜  ~ curl --http2 http://localhost:8080/test --data "bla" -vvv -H 'Content-Type:text/plain'
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /test HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.79.1
> Accept: */*
> Connection: Upgrade, HTTP2-Settings
> Upgrade: h2c
> HTTP2-Settings: AAMAAABkAAQCAAAAAAIAAAAA
> Content-Type:text/plain
> Content-Length: 3
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 101
< Connection: Upgrade
< Upgrade: h2c
< Date: Thu, 21 Apr 2022 14:51:12 GMT
* Received 101
* Using HTTP2, server supports multiplexing
* Connection state changed (HTTP/2 confirmed)
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
< HTTP/2 500
< content-type: text/html;charset=utf-8
< content-language: en
< content-length: 2138
< date: Thu, 21 Apr 2022 14:51:12 GMT
<
<!doctype html><html lang="en"><head><title>HTTP Status 500 – Internal Server Error</title><style type="text/css">body {font-family:Tahoma,Arial,sans-serif;} h1, h2, h3, b {color:white;background-color:#525D76;} h1 {font-size:22px;} h2 {font-size:16px;} h3 {font-size:14px;} p {font-size:12px;} a {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style></head><body><h1>HTTP Status 500 – Internal Server Error</h1><hr class="line" /><p><b>Type</b> Exception Report</p><p><b>Message</b> Cannot invoke &quot;org.apache.coyote.http2.Stream$StreamInputBuffer.available()&quot; because the return value of &quot;org.apache.coyote.http2.Stream.getInputBuffer()&quot; is null</p><p><b>Description</b> The server encountered an unexpected condition that prevented it from fulfilling the request.</p><p><b>Exception</b></p><pre>java.lang.NullPointerException: Cannot invoke &quot;org.apache.coyote.http2.Stream$StreamInputBuffer.available()&quot; because the return value of &quot;org.apache.coyote.http2.Stream.getInputBuffer()&quot; is null
	org.apache.coyote.http2.StreamProcessor.available(StreamProcessor.java:260)
	org.apache.coyote.AbstractProcessor.action(AbstractProcessor.java:410)
	org.apache.coyote.Request.action(Request.java:517)
	org.apache.catalina.connector.InputBuffer.available(InputBuffer.java:218)
	org.apache.catalina.connector.CoyoteInputStream.available(CoyoteInputStream.java:104)
	java.base&#47;sun.nio.cs.StreamDecoder.inReady(StreamDecoder.java:351)
	java.base&#47;sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:311)
	java.base&#47;sun.nio.cs.StreamDecoder.read(StreamDecoder.java:188)
	java.base&#47;java.io.InputStreamReader.read(InputStreamReader.java:176)
	java.base&#47;java.io.Reader.read(Reader.java:250)
	h2c.H2cServlet.copyToString(H2cServlet.java:45)
	h2c.H2cServlet.doPost(H2cServlet.java:21)
	javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
	javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
* Connection #0 to host localhost left intact
</pre><p><b>Note</b> The full stack trace of the root cause is available in the server logs.</p><hr class="line" /><h3>Apache Tomcat/9.0.62</h3></body></html>%
```