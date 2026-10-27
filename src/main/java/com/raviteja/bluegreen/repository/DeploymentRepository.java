package com.raviteja.bluegreen.repository;

import com.raviteja.bluegreen.entity.Deployment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeploymentRepository extends JpaRepository<Deployment, Long> {
    Optional<Deployment> findByEnvironment(String env);
}