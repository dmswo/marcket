package carrotMarcket.marcket.global.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // 로그인 하지않고 접근시 anonymousUser로 됨
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of("anonymousUser");
        } else {
            User principal = (User) authentication.getPrincipal();
            return Optional.of(principal.getUsername());
        }
    }
}
