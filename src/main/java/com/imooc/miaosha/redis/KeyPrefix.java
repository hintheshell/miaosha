package com.imooc.miaosha.redis;

public interface KeyPrefix {

	//失效时间
	public int expireSeconds();
	//获取前缀
	public String getPrefix();
	
}
