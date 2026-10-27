package com.raviteja.bluegreen;

import com.raviteja.bluegreen.repository.DeploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SpringBootApplication
@RestController
public class BlueGreenApplication {

    @Autowired
    private DeploymentRepository repo;

    @Autowired
    private com.raviteja.bluegreen.service.BlueGreenService service;

    public static void main(String[] args) {
        SpringApplication.run(BlueGreenApplication.class, args);
    }

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> status = new HashMap<>();
        try {
            status = service.status();
        } catch (Exception e) {
            status.put("activeEnv", "blue");
            status.put("totalDeployments", repo.count());
        }

        return Map.of(
            "project", "92-blue-green-deploy",
            "status", "Live - Blue-Green Zero Downtime",
            "activeEnv", status.getOrDefault("activeEnv", "blue"),
            "traffic", status.getOrDefault("traffic", Map.of("blue", 100, "green", 0)),
            "owner", "Raviteja",
            "tier", "9 - DevOps + Cloud - Blue-Green",
            "totalDeployments", repo.count(),
            "endpoints", List.of(
                "GET /",
                "GET /health",
                "GET /deploy/init",
                "GET /deploy/status",
                "GET /deploy/blue?version=v1.1",
                "GET /deploy/green?version=v2.0",
                "GET /deploy/switch?target=green",
                "GET /deploy/traffic",
                "GET /deploy/versions",
                "GET /deploy/rollback"
            )
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "project", "92-blue-green-deploy",
            "deployment", "Blue-Green Ready",
            "zeroDowntime", "Enabled"
        );
    }
}