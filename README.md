
With the current application, trying to get the request body with upgraded H2C requests fail:
```
➜  ~ curl  --http2 http://localhost:8080/ -vvv
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET / HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> Connection: Upgrade, HTTP2-Settings
> Upgrade: h2c
> HTTP2-Settings: AAMAAABkAARAAAAAAAIAAAAA
>
< HTTP/1.1 101
< Connection: Upgrade
< Upgrade: h2c
< Date: Mon, 06 Dec 2021 13:54:51 GMT
* Received 101
* Using HTTP2, server supports multi-use
* Connection state changed (HTTP/2 confirmed)
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
* Connection state changed (MAX_CONCURRENT_STREAMS == 100)!
< HTTP/2 500
< content-type: text/html;charset=utf-8
< content-language: en
< content-length: 1846
< date: Mon, 06 Dec 2021 13:54:51 GMT
<
<!doctype html><html lang="en"><head><title>HTTP Status 500 – Internal Server Error</title><style type="text/css">body {font-family:Tahoma,Arial,sans-serif;} h1, h2, h3, b {color:white;background-color:#525D76;} h1 {font-size:22px;} h2 {font-size:16px;} h3 {font-size:14px;} p {font-size:12px;} a {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style></head><body><h1>HTTP Status 500 – Internal Server Error</h1><hr class="line" /><p><b>Type</b> Exception Report</p><p><b>Message</b> Cannot invoke &quot;org.apache.coyote.InputBuffer.doRead(org.apache.tomcat.util.net.ApplicationBufferHandler)&quot; because &quot;this.inputBuffer&quot; is null</p><p><b>Description</b> The server encountered an unexpected condition that prevented it from fulfilling the request.</p><p><b>Exception</b></p><pre>java.lang.NullPointerException: Cannot invoke &quot;org.apache.coyote.InputBuffer.doRead(org.apache.tomcat.util.net.ApplicationBufferHandler)&quot; because &quot;this.inputBuffer&quot; is null
	org.apache.coyote.Request.doRead(Request.java:640)
	org.apache.catalina.connector.InputBuffer.realReadBytes(InputBuffer.java:317)
	org.apache.catalina.connector.InputBuffer.checkByteBufferEof(InputBuffer.java:600)
	org.apache.catalina.connector.InputBuffer.read(InputBuffer.java:340)
	org.apache.catalina.connector.CoyoteInputStream.read(CoyoteInputStream.java:132)
	java.base&#47;java.io.InputStream.readNBytes(InputStream.java:409)
	java.base&#47;java.io.InputStream.readAllBytes(InputStream.java:346)
	h2c.H2cServlet.doGet(H2cServlet.java:18)
	javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
	javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
* Connection #0 to host localhost left intact
</pre><p><b>Note</b> The full stack trace of the root cause is available in the server logs.</p><hr class="line" /><h3>Apache Tomcat/9.0.55</h3></body></html>* Closing connection 0
```

The same works in prior knowledge mode:
```
curl  --http2-prior-knowledge http://localhost:8080/ -vvv
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
* Using HTTP2, server supports multi-use
* Connection state changed (HTTP/2 confirmed)
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
* Using Stream ID: 1 (easy handle 0x7fa217008200)
> GET / HTTP/2
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
>
* Connection state changed (MAX_CONCURRENT_STREAMS == 100)!
< HTTP/2 200
< date: Mon, 06 Dec 2021 13:56:43 GMT
<
* Connection #0 to host localhost left intact
hello* Closing connection 0
```