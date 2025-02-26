package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractRepository;
import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl extends AbstractServiceImpl<Group> implements GroupService {
    private final UserRepository userRepository;
    @Autowired
    public GroupServiceImpl(AbstractRepository<Group> repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }

    @Override
    public List<Group> getGroupsForCurrentUser(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (false) {
            return findAll();
        }
        if (user.get().getGroup() != null) {
            return List.of(findById(user.get().getGroup().getId()));
        }
        return Collections.emptyList();
    }

    @Override
    public List<Group> getGroupByUserId(UUID id) {
        return null;
    }
}
