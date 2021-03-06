 package com.stackroute.favoriteservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");

        if("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            if(authHeader==null || !(authHeader.startsWith("Bearer "))){
                throw new ServletException("Token Not Present");
            }

            final String token =authHeader.substring(7);

            Claims claims = Jwts.parser().setSigningKey("mysecretkey").parseClaimsJws(token).getBody();

            request.setAttribute("claims", claims);

            request.setAttribute("user" , servletRequest.getParameter("email"));

        }
        filterChain.doFilter(request, response);
    }
        }


