/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.web.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: FastJsonWithFilterHttpMessageConverter.java, v 1.0 2015年4月18日 下午7:03:24 tmser Exp $
 */
public class FastJsonWithFilterHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public final static Charset UTF8     = Charset.forName("UTF-8");

    private Charset             charset  = UTF8;

    private SerializerFeature[] features = new SerializerFeature[0];
    
    private SerializeFilter serializeFilter;

    public FastJsonWithFilterHttpMessageConverter(){
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    }

	/** 
	 * Getter method for property <tt>serializeFilter</tt>. 
	 * @return property value of serializeFilter 
	 */
	public SerializeFilter getSerializeFilter() {
		return serializeFilter;
	}

	/**
	 * Setter method for property <tt>serializeFilter</tt>.
	 * @param serializeFilter value to be assigned to property serializeFilter
	 */
	public void setSerializeFilter(SerializeFilter serializeFilter) {
		this.serializeFilter = serializeFilter;
	}
	
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
                                                                                               HttpMessageNotReadableException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();

        byte[] buf = new byte[1024];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }

            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
        return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
                                                                             HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, serializeFilter, features);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
    }

}
