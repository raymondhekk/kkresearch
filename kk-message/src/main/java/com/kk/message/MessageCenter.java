package com.kk.message;

public interface MessageCenter {

	Message<String, String>  takeMessage();

	void putMessage(Message<String, String>  msg);

}
