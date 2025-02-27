package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupDTO;
import com.example.demo.domain.group.dto.GroupMapper;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/groups")
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
        return new ResponseEntity<>(groupMapper.toDTOs(groupService.findAll()), HttpStatus.OK);
    }

    @GetMapping({"", "/"})
    @PreAuthorize("hasAuthority('GROUP_READ')")
    public ResponseEntity<List<GroupDTO>> retrieveGroupByUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getByUsername(userDetails.getUsername());
        List<Group> group = List.of(user.getGroup());
        return new ResponseEntity<>(groupMapper.toDTOs(group), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_READ_ALL') || @userPermissionEvaluator.isMember(authentication.principal.user, #id)")
    public ResponseEntity<GroupDTO> retrieveById(@PathVariable UUID id) {
        GroupDTO groupDTO = groupMapper.toDTO(groupService.findById(id));
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<GroupDTO> create(@Valid @RequestBody GroupDTO groupDTO) {
        Group group = groupService.save(groupMapper.fromDTO(groupDTO));
        for (User user : group.getMembers()) {
            userService.updateById(user.getId(), user.setGroup(group));
        }
        return ResponseEntity.ok().body(groupMapper.toDTO(group));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<Group> updateById(@PathVariable UUID id, @Valid @RequestBody GroupDTO groupDTO) {
        Group group1 = groupService.updateById(id, groupMapper.fromDTO(groupDTO));
        return ResponseEntity.ok().body(group1);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        groupService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
