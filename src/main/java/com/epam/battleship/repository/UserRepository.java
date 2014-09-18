package com.epam.battleship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.battleship.domain.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.status = :status and u.id <> :id")
	List<User> getOpponents(@Param("status") String status, @Param("id")Long id);
}
