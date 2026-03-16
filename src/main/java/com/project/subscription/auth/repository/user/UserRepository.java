package com.project.subscription.auth.repository.user;

import com.project.subscription.auth.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByDeletedFalse(); // 삭제 여부가 null인 모든 사용자 조회

    Optional<User> findByIdAndDeletedFalse(Long id); // 삭제 여부가 null인 단일 사용자 조회

}
