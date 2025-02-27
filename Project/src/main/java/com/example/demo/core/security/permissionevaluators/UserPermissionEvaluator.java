package com.example.demo.core.security.permissionevaluators;

import com.example.demo.domain.group.Group;
import com.example.demo.domain.group.GroupRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@NoArgsConstructor
public class UserPermissionEvaluator {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private UserRepository userRepository;

  public boolean exampleEvaluator(User principal, UUID id) {
    //your code here
    return true;
  }

  //Check if the user signed in is part of the group he wants to view.
  public boolean isMember(User principal, UUID id) {
    Optional<Group> group = groupRepository.findById(id);
    Optional<User> user = userRepository.findById(principal.getId());
    //unnecessary to check if the user is present, since he already signed in, so we've already verified he exists.
    if (group.isPresent() && user.isPresent()) {
      boolean isMember = group.get().getMembers().contains(user.get());
      return isMember;
    }
    return false;
  }

}
