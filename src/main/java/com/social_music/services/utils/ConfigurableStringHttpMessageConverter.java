package com.social_music.services.utils;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class ConfigurableStringHttpMessageConverter extends AbstractHttpMessageConverter<String> {
    private Charset defaultCharset;

    public Charset getDefaultCharset() {
        return defaultCharset;
    }

    private final List<Charset> availableCharsets;

    private boolean writeAcceptCharset = true;

    public ConfigurableStringHttpMessageConverter() {
        super(new MediaType("text", "plain", StringHttpMessageConverter.DEFAULT_CHARSET), MediaType.ALL);
        defaultCharset = StringHttpMessageConverter.DEFAULT_CHARSET;
        this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
    }

    public ConfigurableStringHttpMessageConverter(String charsetName) {
        super(new MediaType("text", "plain", Charset.forName(charsetName)), MediaType.ALL);
        defaultCharset = Charset.forName(charsetName);
        this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
    }

    /**
     * Indicates whether the {@code Accept-Charset} should be written to any outgoing request.
     * <p>Default is {@code true}.
     */
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    protected String readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        return FileCopyUtils.copyToString(new InputStreamReader(inputMessage.getBody(), charset));
    }

    @Override
    protected Long getContentLength(String s, MediaType contentType) {
        Charset charset = getContentTypeCharset(contentType);
        try {
            return (long) s.getBytes(charset.name()).length;
        }
        catch (UnsupportedEncodingException ex) {
            // should not occur
            throw new InternalError(ex.getMessage());
        }
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        if (writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset));
    }

    /**
     * Return the list of supported {@link Charset}.
     *
     * <p>By default, returns {@link Charset#availableCharsets()}. Can be overridden in subclasses.
     *
     * @return the list of accepted charsets
     */
    protected List<Charset> getAcceptedCharsets() {
        return this.availableCharsets;
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        }
        else {
            return defaultCharset;
        }
    }
}
