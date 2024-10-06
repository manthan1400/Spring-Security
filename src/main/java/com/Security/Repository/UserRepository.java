package com.Security.Repository;

import com.Security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM User")
    void deleteAllUsers();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName IN :roleName")
    List<User> findUsersByRoleName(@Param("roleName") List<String> roleName);


}
