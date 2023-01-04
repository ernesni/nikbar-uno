package com.ean.promo.backend.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ean.promo.backend.entity.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Serializable>{
	
	public abstract Role findByRole(String role);
	

}
