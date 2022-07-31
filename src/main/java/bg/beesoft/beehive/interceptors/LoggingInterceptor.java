package bg.beesoft.beehive.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        StringBuilder sb = new StringBuilder();
        String requestUrl = request.getRequestURL().toString();
        if (!requestUrl.substring(requestUrl.length() - 4).contains(".")) {

            sb.append(LocalDateTime.now());
            sb.append("\tmethod: " + request.getMethod());
            sb.append("\trequest URL: " + requestUrl);
            sb.append("\tremote address: " + request.getRemoteAddr());
            sb.append("\tstatus: " + response.getStatus());
            if (request.getUserPrincipal() != null) {
                sb.append("\tuser details: " + request.getUserPrincipal().getName());
            }
            sb.append("\tserver port:" + request.getServerPort());

            LOGGER.info(sb.toString());
        }
    }

}
