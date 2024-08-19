package com.street.one.manage.web.config.log;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.log
 * @ClassName: RequestWrapper
 * @Author: tjl
 * @Description: 日志打印类
 * @Date: 2024/5/13 16:40
 * @modified modify person name
 * @Version: 1.0
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private final String body;
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body =read(request);
    }
    public String getBody() {
        return body;
    }
    private static final int BUFFER_SIZE = 1024 * 8;

    public static String read(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = request.getReader();
        StringWriter writer = new StringWriter();
        write(bufferedReader, writer);
        return writer.getBuffer().toString();
    }
    public static long write(Reader reader, Writer writer) throws IOException {
        return write(reader, writer, BUFFER_SIZE);
    }

    public static long write(Reader reader, Writer writer, int bufferSize) throws IOException {
        int read;
        long total = 0;
        char[] buf = new char[bufferSize];
        while( ( read = reader.read(buf) ) != -1 ) {
            writer.write(buf, 0, read);
            total += read;
        }
        return total;
    }
    @Override
    public ServletInputStream getInputStream()  {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read(){
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
