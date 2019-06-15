package pl.sda.eventlift.stakeholders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

@Service
public class StakeholderContext {

    @Autowired
    StakeholderRepository stakeholderRepository;

    public String provideUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication.getName();
    }

    public Long provideId() {
        if(provideUsername() != null){
            String username = provideUsername();
            Stakeholder stakeholder = stakeholderRepository.findByEmail(username);
            return stakeholder.getId();
        }
        return null;
    }

    public boolean isLoggedIn() {
        return provideUsername() != null;
    }
}
