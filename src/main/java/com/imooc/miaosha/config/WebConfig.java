package com.imooc.miaosha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	UserArgumentResolver userArgumentResolver;
	
//	@Autowired
//	AccessInterceptor accessInterceptor;

	//直接往方法里传入所需对象 比如往方法里直接传httpServletResponse 就是通过这个方法  框架会回调这个方法 往controller方法中赋值
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);//用于处理登陆
	}
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(accessInterceptor);
//	}
	
}
