package com.demo.configure.login;

import com.demo.annotation.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author weilu
 * @create 2022/9/5
 */
@Slf4j
@Service
public class LoginRequiredArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginRequired.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        LoginRequired loginRequired = methodParameter.getParameterAnnotation(LoginRequired.class);
        Object attribute = nativeWebRequest.getAttribute(loginRequired.sessionKey(), NativeWebRequest.SCOPE_SESSION);
        if(attribute == null){
            log.error("接口 {} 非法调用！", methodParameter.getMethod().toString());
            throw new RuntimeException("请先登录！");
        }
        return attribute;
    }
}
