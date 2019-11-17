package com.sparrow.servicetester.warehouse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.sparrow.servicetester.warehouse.filter.STOncePerRequestFilter;
import com.sparrow.servicetester.warehouse.filter.STXssFilter;
import com.sparrow.servicetester.warehouse.websocket.WebsocketEndPoint;

@SpringBootApplication
public class ServiceTesterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTesterApplication.class, args);
    }

    /***
     * CORS 처리를 위한 설정을 추가한다.
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /***
     * 메세지 정보를 셋팅한다.
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //WEB-INF 밑에 해당 폴더에서 properties를 찾는다.
        messageSource.addBasenames("words/words");
        messageSource.addBasenames("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /***
     * websocket용 endpoint 정보를 등록한다.
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
      return new ServerEndpointExporter();
    }

    /***
     * Websocket를 받고 수행 할 클래스를 등록한다.
     * @return
     */
    @Bean
    public WebsocketEndPoint websocketEndPoint() {
      return new WebsocketEndPoint();
    }

    /***
     * filter를 설정한다.(request, response 로그)
     * @return
     */
    @Bean
    public STOncePerRequestFilter sTOncePerRequestFilter() {
    	return new STOncePerRequestFilter();
    }

    /***
     * filter를 설정한다.(XSS 특수문자 치환 처리)
     * @return
     */
    @Bean
    public FilterRegistrationBean httpHeaderSecurityFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new STXssFilter());
        return filterRegistrationBean;
    }


}
