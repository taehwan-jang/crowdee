package team.crowdee.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter Init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String URI = httpRequest.getRequestURL().toString();
        String key = UUID.randomUUID().toString();
        log.info("REQUEST  [{}][{}]", key, URI);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.info("filter Destroy");
    }
}
