package pl.sda.eventlift.configs;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.roles.Role;
import pl.sda.eventlift.roles.RoleRepository;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

@Component
public class DataInitializer implements InitializingBean {

    private StakeholderRepository stakeholderRepository;
    private EventRepository eventRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public DataInitializer(StakeholderRepository stakeholderRepository,
                           EventRepository eventRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.stakeholderRepository = stakeholderRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void afterPropertiesSet() {
        if(roleRepository.count() == 0){
            Role role_user = roleRepository.save(new Role("ROLE_USER"));
            Role role_admin = roleRepository.save(new Role("ROLE_ADMIN"));
            Stakeholder stakeholder_user = Stakeholder.builder()
                    .firstName("Przemek")
                    .lastName("Slowik")
                    .email("przemek2829@wp.pl")
                    .passwordHash(passwordEncoder.encode("pass"))
                    .roles(Sets.newHashSet(role_user)).build();
            Stakeholder stakeholder_admin = Stakeholder.builder()
                    .firstName("Przemyslaw")
                    .lastName("Slowik")
                    .email("przemyslaw.slowik@19e.pl")
                    .passwordHash(passwordEncoder.encode("pass"))
                    .roles(Sets.newHashSet(role_admin)).build();
            if (stakeholderRepository.count() == 0 || eventRepository.count() == 0) {
                stakeholderRepository.save(stakeholder_admin);
                stakeholderRepository.save(stakeholder_user);
            }
        }
    }
}
