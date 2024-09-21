package com.gmenegatto.wallet_api.repository;

import com.gmenegatto.wallet_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
