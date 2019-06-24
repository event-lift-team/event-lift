package pl.sda.eventlift.roles;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.sda.eventlift.configs.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    private String roleName;
}
