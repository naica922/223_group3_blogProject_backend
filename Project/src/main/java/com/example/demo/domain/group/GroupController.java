package com.example.demo.domain.group;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<Group>> retrieveAll() {
        return ResponseEntity.ok().body(groupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> retrieveById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(groupService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Group> create(@Valid @RequestBody Group group) {
        Group group1 = groupService.save(group);
        return ResponseEntity.ok().body(group1);
    }

    @PutMapping("{id}")
    public ResponseEntity<Group> updateById(@PathVariable UUID id, @Valid @RequestBody Group group) {
        Group group1 = groupService.updateById(id, group);
        return ResponseEntity.ok().body(group1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        groupService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
