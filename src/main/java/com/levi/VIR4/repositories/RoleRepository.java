
package com.levi.VIR4.repositories;

import com.levi.VIR4.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
    
}
