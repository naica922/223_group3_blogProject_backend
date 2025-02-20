package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupDTO;
import com.example.demo.domain.group.dto.GroupMapper;
import com.example.demo.domain.user.dto.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @GetMapping({"", "/"})
    @PreAuthorize("hasAuthority('GROUP_READ')")
    public ResponseEntity<List<GroupDTO>> retrieveAll() {
        return new ResponseEntity<>(groupMapper.toDTOs(groupService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_READ')")
    public ResponseEntity<GroupDTO> retrieveById(@PathVariable UUID id) {
        GroupDTO groupDTO = groupMapper.toDTO(groupService.findById(id));
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('GROUP_MODIFY')")
    public ResponseEntity<Group> create(@Valid @RequestBody GroupDTO groupDTO) {
        Group group = groupService.save(groupMapper.fromDTO(groupDTO));
        return ResponseEntity.ok().body(group);
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
