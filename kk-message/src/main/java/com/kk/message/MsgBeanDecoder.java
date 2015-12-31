/**
 * 
 */
package com.kk.message;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;

import kafka.serializer.Decoder;

/**
 * @author kk
 *
 */
public class MsgBeanDecoder implements Decoder<MsgBean<String,String>> {
	
	@Override
	public MsgBean<String,String> fromBytes(byte[] bytes) {
		
		String json;
		try {
			json = new String(bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Fail to decode msgbean " + e.getMessage() ,e);
		}
		@SuppressWarnings("unchecked")
		MsgBean<String,String> msgBean = JSON.parseObject( json,MsgBean.class );
		return msgBean;
	}
}
