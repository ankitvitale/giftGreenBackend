package com.giftGreenEcom.Repository;

import com.giftGreenEcom.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {

}