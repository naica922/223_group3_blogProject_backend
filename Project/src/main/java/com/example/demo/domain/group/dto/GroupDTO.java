package com.example.demo.domain.group.dto;

import com.example.demo.core.generic.AbstractDTO;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class GroupDTO extends AbstractDTO {

    private String groupName;
    private String motto;
    private String logo;
    private List<UserDTO> members;
    public GroupDTO(UUID id, String groupName, String motto, String logo, List<UserDTO> members) {
        super(id);
        this.groupName = groupName;
        this.motto = motto;
        this.logo = logo;
        this.members = members;
    }
}
