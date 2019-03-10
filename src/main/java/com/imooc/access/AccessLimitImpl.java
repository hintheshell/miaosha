package com.imooc.access;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.AccessKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用aop 代替
 */
@Component
@Aspect
public class AccessLimitImpl {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;

    @Pointcut(value = "@annotation(com.imooc.access.AccessLimit) ")
    private void cut() {
    }

    @Around(value = "cut() && @annotation(accessLimit)", argNames = "joinPoint,accessLimit")
    public Object Before(ProceedingJoinPoint joinPoint, AccessLimit accessLimit) throws Throwable {
        System.out.println("进入切面");
        //获取user
        MiaoshaUser user = UserContext.getUser();
        //获取注解参数
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        boolean needLogin = accessLimit.needLogin();
        String key = request.getRequestURI();
        if (needLogin) {
            if (user == null) {
                //render(.....); 不需要render方法往response中写返回值  直接返回
                return Result.error(CodeMsg.SESSION_ERROR);
            }
            key += "_" + user.getId();
        }
        AccessKey ak = AccessKey.withExpire(seconds);
        Integer count = redisService.get(ak, key, Integer.class);
        if (count == null) {
            redisService.set(ak, key, 1);
        } else if (count < maxCount) {
            redisService.incr(ak, key);
        } else {
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }
        return joinPoint.proceed();

    }


}
