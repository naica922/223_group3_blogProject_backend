package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractRepository;
import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.group.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl extends AbstractServiceImpl<Group> implements GroupService {
    @Autowired
    public GroupServiceImpl(AbstractRepository<Group> repository) {
        super(repository);
    }
}
