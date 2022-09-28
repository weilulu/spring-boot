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
 *
 * 在需要的地方加上LoginRequired注解后就可以进行校验了
 * 参考：https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/Java%20%E4%B8%9A%E5%8A%A1%E5%BC%80%E5%8F%91%E5%B8%B8%E8%A7%81%E9%94%99%E8%AF%AF%20100%20%E4%BE%8B/27%20%E6%95%B0%E6%8D%AE%E6%BA%90%E5%A4%B4%EF%BC%9A%E4%BB%BB%E4%BD%95%E5%AE%A2%E6%88%B7%E7%AB%AF%E7%9A%84%E4%B8%9C%E8%A5%BF%E9%83%BD%E4%B8%8D%E5%8F%AF%E4%BF%A1%E4%BB%BB.md
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
