package com.example.demo.domain.group.dto;

import com.example.demo.core.generic.AbstractDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class GroupDTO extends AbstractDTO {

    private String groupName;
    private String motto;
    private String logo;
    public GroupDTO(UUID id, String groupName, String motto, String logo) {
        super(id);
        this.groupName = groupName;
        this.motto = motto;
        this.logo = logo;
    }
}
