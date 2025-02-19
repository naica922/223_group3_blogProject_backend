package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Group extends AbstractEntity {

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "motto")
    private String motto;

    //logo is saved as an URL to the image
    @Column(name = "logo")
    private String logo;

    public Group(UUID id, String groupName, String motto, String logo) {
        super(id);
        this.groupName = groupName;
        this.motto = motto;
        this.logo = logo;
    }

}
