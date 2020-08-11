package br.com.marketlist.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.com.marketlist.api.utils.TokenAuthenticationUtils;
import io.jsonwebtoken.ExpiredJwtException;


public class JWTAuthenticationFilter  extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
			try {
				Authentication authentication = TokenAuthenticationUtils
					.getAuthentication((HttpServletRequest) request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
			}catch (ExpiredJwtException e) {
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");
	            return;
			}
	}
}
