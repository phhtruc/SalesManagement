package com.skyline.SalesManager.aditing;

import com.skyline.SalesManager.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAudiAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        UserEntity userPrincipal = (UserEntity) authentication.getPrincipal(); // tra ve nguoi dung hien tai
        return Optional.ofNullable(userPrincipal.getFullName());
    }
}
