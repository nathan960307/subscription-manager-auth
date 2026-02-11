package com.project.subscription.auth.repository.user;

import com.project.subscription.auth.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
