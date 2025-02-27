package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupDTO;
import com.example.demo.domain.group.dto.GroupMapper;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/groups")
@Log4j2
public class GroupController {

    private final GroupService groupService;

    private final GroupMapper groupMapper;

    private final UserService userService;

    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper, UserService userService) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('GROUP_READ_ALL')")
    public ResponseEntity<List<GroupDTO>> retrieveAll() {
        log.info("Get all groups, only for admin");
        return new ResponseEntity<>(groupMapper.toDTOs(groupService.findAll()), HttpStatus.OK);
    }

    //endpoint accessible for admin or member of group
    @GetMapping({"", "/"})
    @PreAuthorize("hasAuthority('GROUP_READ')")
    public ResponseEntity<GroupDTO> retrieveUserIsMember(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Get groups user is part of, empty for admin");
        User user = userService.getByUsername(userDetails.getUsername());
        Set<Role> role = user.getRoles();
        Group group = user.getGroup();
        return new ResponseEntity<>(groupMapper.toDTO(group), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_READ_ALL') || @userPermissionEvaluator.isMember(authentication.principal.user, #id)")
    public ResponseEntity<GroupDTO> retrieveById(@PathVariable UUID id) {
        log.info("Get group by id");
        GroupDTO groupDTO = groupMapper.toDTO(groupService.findById(id));
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<GroupDTO> create(@Valid @RequestBody GroupDTO groupDTO) {
        //logging group since id will be changed when loading into DB
        log.info("creating new group " + groupDTO.getGroupName());
        Group group = groupService.save(groupMapper.fromDTO(groupDTO));
        //Go through the list of members added to the group and individually set the group for every user, due to it not working otherwise.
        for (User user : group.getMembers()) {
            userService.updateById(user.getId(), user.setGroup(group));
            log.info("updating fk group_id for user: " + user.getEmail());
        }
        return ResponseEntity.ok().body(groupMapper.toDTO(group));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<Group> updateById(@PathVariable UUID id, @Valid @RequestBody GroupDTO groupDTO) {
        log.info("updating group with id" + groupDTO.getId());
        Group group1 = groupService.updateById(id, groupMapper.fromDTO(groupDTO));
        for (User user : group1.getMembers()) {
            userService.updateById(user.getId(), user.setGroup(group1));
            log.info("updating fk group_id for user: " + user.getEmail());
        }
        return ResponseEntity.ok().body(group1);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        log.info("deleting group with id: " + id.toString());
        groupService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
