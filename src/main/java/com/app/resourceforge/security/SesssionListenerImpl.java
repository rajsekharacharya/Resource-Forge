package com.app.resourceforge.security;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.resourceforge.model.LoginHistory;
import com.app.resourceforge.repository.LoginHistoryRepository;

@Component
public class SesssionListenerImpl implements HttpSessionListener {

	@Autowired
	LoginHistoryRepository loginHistoryRepository;

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		Optional<LoginHistory> findBySessionid = loginHistoryRepository.findBySessionId(httpSessionEvent.getSession().getId());
		if(findBySessionid.isPresent()) {
			findBySessionid.get().setLogoutTime(LocalDateTime.now());
			loginHistoryRepository.save(findBySessionid.get());
		}
		else {
			System.out.println("session mismatch");
		}
	}
}

