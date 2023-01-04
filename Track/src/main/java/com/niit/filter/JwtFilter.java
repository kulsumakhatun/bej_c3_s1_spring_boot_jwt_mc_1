package com.niit.filter;

import com.niit.domain.Track;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authHeader = httpServletRequest.getHeader("Authorization");
        System.out.println("Authorization " + authHeader);
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
            servletOutputStream.close();
        } else {
            String jwtToken = authHeader.substring(7);
            String trackName = Jwts.parser().setSigningKey("securitykey").parseClaimsJws(jwtToken).getBody().getSubject();
            httpServletRequest.setAttribute("TrackName", trackName);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
