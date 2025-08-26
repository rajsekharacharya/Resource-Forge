package com.app.resourceforge.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.SubscriptionPlanRepository;

public class AfterLoginFilter implements Filter {
    private static final List<String> ALLOWED_ROLES = Arrays.asList("ADMIN", "MANAGER", "PURCHASE","SERVICE","FINANCE","BACKOFFICE","LOGISTICS","SERVICEENGINEER");
    private static final String ERROR_URL = "/error-subscription-end"; // Customize the error URL as per your application

    private final AccessDeniedHandler accessDeniedHandler;

    public AfterLoginFilter() {
        this.accessDeniedHandler = new AccessDeniedHandlerImpl();
    }

    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            boolean hasAllowedRole = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(ALLOWED_ROLES::contains);

            if (hasAllowedRole) {
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                boolean hasSubscriptionPlan = checkSubscriptionPlan(user.getSuperCompanyId());

                if (!hasSubscriptionPlan) {
                    // Clear authentication and redirect to error URL
                    new SecurityContextLogoutHandler().logout(httpRequest, httpResponse, auth);
                    accessDeniedHandler.handle(httpRequest, httpResponse, null);
                    httpResponse.sendRedirect(ERROR_URL);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean checkSubscriptionPlan(Integer superCoId) {
        return subscriptionPlanRepository.existsByStatusAndSuperCompanyId(true, superCoId);
    }
}
