package com.zhaimi.message;

public interface MessageCenter {

	MsgBean<String, String>  takeMessage();

	void putMessage(MsgBean<String, String>  msg);

}
