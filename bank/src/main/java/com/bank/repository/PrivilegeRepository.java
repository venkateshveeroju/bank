package com.bank.repository;

import com.bank.entity.Privilege;
import com.bank.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
