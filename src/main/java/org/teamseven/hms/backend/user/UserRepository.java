package org.teamseven.hms.backend.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface  UserRepository extends JpaRepository<User, UUID> {
    @Query(value = """
      select u from User u\s
      where u.email = :email and u.is_active = 1\s
      """)
    User findByEmail(String email);


    @Query(value = "select u from User u where u.role = 'DOCTOR' or u.role = 'STAFF' or u.role = 'LAB_SUPPORT_STAFF' ")
    Page<User> getAllStaffAccounts(Pageable pageable);
}
