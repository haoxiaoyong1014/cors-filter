package com.hxy.corsFilter;

import org.ebaysf.web.cors.CORSFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/6.
 * hxy
 */
@WebFilter(urlPatterns = {"/*"}, asyncSupported = true, filterName = "1",
        initParams = {
                //使用org.ebaysf.web.cors.CORSFilter的cors-filter的配置方式
                //@WebInitParam(name="CORS Filter",value = "org.ebaysf.web.cors.CORSFilter"),
                @WebInitParam(name = "cors.allowed.origins", value = "*"),
                @WebInitParam(name = "cors.allowed.methods", value = "GET,POST,HEAD,OPTIONS,PUT,DELETE,CONNECT,OPTIONS"),
                @WebInitParam(name = "cors.allowed.headers", value = "token,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers"),
                @WebInitParam(name = "cors.support.credentials", value = "true"),
                @WebInitParam(name = "cors.exposed.headers", value = "")
        })
public class CorsFilter_Demo implements Filter {

        CORSFilter corsFilter = new CORSFilter();

        public CORSFilter getCorsFilter() {
                return corsFilter;
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
                corsFilter.init(filterConfig);
        }

        @Override
        public void doFilter(ServletRequest reques, ServletResponse respons, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) reques;
                HttpServletResponse response = (HttpServletResponse) respons;
                corsFilter.doFilter(reques, respons, chain);
        }

        @Override
        public void destroy() {

        }
}