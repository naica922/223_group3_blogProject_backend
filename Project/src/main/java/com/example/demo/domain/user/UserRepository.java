package com.example.demo.domain.user;

import com.example.demo.core.generic.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends AbstractRepository<User> {

  Optional<User> findByEmail(String email);

  Optional<List<User>> findByGroup_Id(UUID group_id);

  // NEW: Fetch users who are not in any group
  @Query("SELECT u FROM User u WHERE u.group IS NULL")
  List<User> findByGroupIsNull();
}
