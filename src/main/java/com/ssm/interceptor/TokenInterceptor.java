package com.ssm.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.common.token.Audience;
import com.ssm.common.tool.JwtHelper;
import com.ssm.model.Login;
import com.ssm.service.LoginService;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 拦截器类（拦截所有 /api/* 下的路径）
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private Audience audience;
    @Resource
    private LoginService loginService;


    private static final  int AL=7;

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        ResultMsg resultMsg;
        String auth = request.getHeader("Authorization");
        String bearer="bearer";
        if ((auth != null) && (auth.length() > AL)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(bearer) == 0) {
                auth = auth.substring(7, auth.length());
                Claims claims = JwtHelper.parseJWT(auth, audience.getBase64Secret());
                if (claims != null) {
                    if(claims.containsKey("aud")) {
                        String clientId=String.valueOf(claims.get("aud"));
                        if(clientId.equals(audience.getClientId())) {  //判断客户端id是否相等
                            if (claims.containsKey("unique_name")) {
                                String userName = String.valueOf(claims.get("unique_name"));
                                Login login = loginService.selectUserByName(userName);

//                                if (user != null && user.getStatus() == '0') {  //0启用，1禁用
                                    if (claims.containsKey("role")) {
                                        String password = String.valueOf(claims.get("role"));
                                        if (password.equals(login.getPassword())) {  //判断密码是否正确
                                            return true;
                                        }
                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        resultMsg = new ResultMsg(ResultStatusCode.INVALID_TOKEN.getErrcode(), ResultStatusCode.INVALID_TOKEN.getErrmsg(), "被拦截了（原先返回null）");
        response.getWriter().write(mapper.writeValueAsString(resultMsg));
        return false;
    }








    /**********************************************************分割线**********************************************************/
    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
