package com.terracis.helpdeskmanagementapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terracis.helpdeskmanagementapp.entity.HardwareType;

@Repository
public interface HardwareTypeRepository extends JpaRepository<HardwareType, Long>
{

}
