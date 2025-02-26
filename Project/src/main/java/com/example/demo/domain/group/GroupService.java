package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractService;
import com.example.demo.domain.group.dto.GroupDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface GroupService extends AbstractService<Group> {
    List<Group> getGroupsForCurrentUser(UUID userId);
    List<Group> getGroupByUserId(UUID id);

}
