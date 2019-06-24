package pl.sda.eventlift.stakeholders.services;

import com.google.common.collect.Sets;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.roles.RoleRepository;
import pl.sda.eventlift.stakeholders.dto.StakeholderDTO;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

@Service
public class StakeholderRegistrationService {

    private PasswordEncoder passwordEncoder;
    private StakeholderRepository stakeholderRepository;
    private RoleRepository roleRepository;

    public StakeholderRegistrationService(PasswordEncoder passwordEncoder,
                                          StakeholderRepository stakeholderRepository,
                                          RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.stakeholderRepository = stakeholderRepository;
        this.roleRepository = roleRepository;
    }

    public void registerStakeholder(StakeholderDTO dto) {
        Stakeholder stakeholder = stakeholderToEntity(dto);
        if (stakeholderRepository.existsByEmail(stakeholder.getEmail())) {
            throw new RuntimeException(("User with email: " + stakeholder.getEmail() + "exists"));
        } else {
            stakeholderRepository.save(stakeholder);
        }
    }

    private Stakeholder stakeholderToEntity(StakeholderDTO dto) {
        return Stakeholder.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .roles(Sets.newHashSet(roleRepository.findByRoleName("ROLE_USER")))
                .passwordHash(passwordEncoder.encode(dto.getPassword())).build();
    }
}
