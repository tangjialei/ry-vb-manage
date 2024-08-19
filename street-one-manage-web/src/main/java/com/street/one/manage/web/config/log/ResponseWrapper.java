package com.street.one.manage.web.config.log;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.log
 * @ClassName: ResponseWrapper
 * @Author: tjl
 * @Description:
 * @Date: 2023/6/21 19:06
 * @modified modify person name
 * @Version: 1.0
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream buffer;

    private ServletOutputStream out;

    private boolean usingWriter;

    public ResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        buffer = new ByteArrayOutputStream();
        out = new WrapperOutputStream(buffer);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public void flushBuffer()
            throws IOException {
        if (out != null) {
            out.flush();
        }
    }

    public byte[] getContent() throws IOException {
        // flushBuffer();
        return buffer.toByteArray();
    }

    class WrapperOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos;

        public WrapperOutputStream(ByteArrayOutputStream bos) {
            this.bos = bos;
        }

        @Override
        public void write(int b)
                throws IOException {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            return false;

        }

        @Override
        public void setWriteListener(WriteListener arg0) {
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        this.usingWriter = true;
        return super.getWriter();
    }

    public boolean isUsingWriter() {
        return usingWriter;
    }
}
