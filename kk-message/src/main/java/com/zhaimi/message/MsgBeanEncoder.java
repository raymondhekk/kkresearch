package com.zhaimi.message;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;

import kafka.serializer.Encoder;

/**
 * 
 * @author kk
 *
 */
public class MsgBeanEncoder implements Encoder<MsgBean<String,String>> {
	
	@Override
	public byte[] toBytes(MsgBean<String,String> msgBean) {
		String json = JSON.toJSONString(msgBean);
		try {
			return json.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Fail to encode msgbean " + e.getMessage() ,e);
		}
	}
}
