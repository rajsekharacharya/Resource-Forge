package com.app.resourceforge.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.app.resourceforge.model.LoginHistory;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.LoginHistoryRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;

@Component
public class MyAuthenticationMySuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        String redirectUrl;
        String logMessage;

        if (user.getAuthorities().contains(new SimpleGrantedAuthority("VARELI"))) {
            redirectUrl = "/home-company";
            logMessage = user.getUser().getName() + " has logged in.";
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("SUPERADMIN"))) {
            redirectUrl = "/home-super-admin";
            logMessage = user.getUser().getName() + " has logged in.";
        } else if (user.getAuthorities().stream().anyMatch(authority ->
                authority.getAuthority().equals("ADMIN") ||
                authority.getAuthority().equals("MANAGER") ||
                authority.getAuthority().equals("PURCHASE") ||
                authority.getAuthority().equals("SERVICE") ||
                authority.getAuthority().equals("FINANCE") ||
                authority.getAuthority().equals("LOGISTICS") ||
                authority.getAuthority().equals("SERVICEENGINEER") ||
                authority.getAuthority().equals("BACKOFFICE"))) {

            if (subscriptionPlanRepository.existsByStatusAndSuperCompanyId(true, user.getSuperCompanyId())) {
                redirectUrl = "/home";
                logMessage = user.getUser().getName() + " has logged in.";
            } else {
                redirectUrl = "/error-subscription-end";
                logMessage = "Not Authorize";
            }

        } else {
            redirectUrl = "/403";
            logMessage = "Not Authorize";
        }

        // Log the login
        logLogin(user, logMessage);

        // Redirect to the appropriate URL
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void logLogin(MyUserDetails user, String logMessage) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        LoginHistory login = new LoginHistory();
        login.setUserId(user.getUser().getId());
        login.setName(user.getUser().getName());
        login.setSessionId(sessionId);
        login.setLoginTime(LocalDateTime.now());
        login.setCoId(user.getCompanyId());
        loginHistoryRepository.save(login);
    }
}
