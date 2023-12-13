package com.company.jmixpm.listener;

import com.company.jmixpm.security.FullAccessRole;
import io.jmix.core.session.SessionData;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

@Component
public class AuthenticationEventListener {
    private ObjectProvider<SessionData> sessionDataProvider;

    public AuthenticationEventListener(ObjectProvider<SessionData> sessionDataProvider) {
        this.sessionDataProvider = sessionDataProvider;
    }

    @EventListener
    public void onSuccess(final AuthenticationSuccessEvent event) {
        SessionData sessionData = sessionDataProvider.getIfAvailable();
        if (sessionData != null) {
            Authentication authentication = event.getAuthentication();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(FullAccessRole.CODE)) {
                    sessionData.setAttribute("isManager", true);
                    return;
                }
            }
            sessionData.setAttribute("isManager", false);
        }

    }
}