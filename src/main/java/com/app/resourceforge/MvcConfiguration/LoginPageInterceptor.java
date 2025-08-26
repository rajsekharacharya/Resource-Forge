package com.app.resourceforge.MvcConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

import com.app.resourceforge.model.MyUserDetails;

@Component
public class LoginPageInterceptor implements HandlerInterceptor {

    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = urlPathHelper.getLookupPathForRequest(request);

        if (isLoginOrRootPath(path) && isAuthenticated()) {
            redirectToHome(response);
            return false;
        }
        return true;
    }

    private boolean isLoginOrRootPath(String path) {
        return "/login".equals(path) || "/".equals(path);
    }

    private void redirectToHome(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        String redirectURL = response.encodeRedirectURL(determineRedirectURL(user));
        response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        response.setHeader("Location", redirectURL);
    }

    private String determineRedirectURL(MyUserDetails user) {
        if (hasRole(user, "VARELI")) return "home-company";
        if (hasRole(user, "SUPERADMIN")) return "home-super-admin";
        if (hasRole(user, "ADMIN")) return "home";
        if (hasRole(user, "MANAGER")) return "home";
        if (hasRole(user, "PURCHASE")) return "home";
        if (hasRole(user, "FINANCE")) return "home";
        if (hasRole(user, "SERVICE")) return "home";
        if (hasRole(user, "BACKOFFICE")) return "home";
        if (hasRole(user, "SERVICEENGINEER")) return "home";
        return "/403";
    }

    private boolean hasRole(MyUserDetails user, String role) {
        return user.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
               authentication.isAuthenticated() &&
               !(authentication instanceof AnonymousAuthenticationToken);
    }
}
