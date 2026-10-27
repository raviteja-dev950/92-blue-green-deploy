package com.raviteja.bluegreen.service;

import com.raviteja.bluegreen.entity.Deployment;
import com.raviteja.bluegreen.repository.DeploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BlueGreenService {

    @Autowired
    private DeploymentRepository repo;

    @Value("${bluegreen.active-env:blue}")
    private String activeEnv;

    private int switchCount = 0;

    public Map<String, Object> init() {
        repo.deleteAll();
        Deployment blue = new Deployment("blue", "v1.0", "ACTIVE", 100);
        Deployment green = new Deployment("green", "v2.0", "INACTIVE", 0);
        repo.save(blue);
        repo.save(green);
        activeEnv = "blue";
        return status();
    }

    public Map<String, Object> status() {
        Optional<Deployment> blueOpt = repo.findByEnvironment("blue");
        Optional<Deployment> greenOpt = repo.findByEnvironment("green");

        Deployment blue = blueOpt.orElse(new Deployment("blue", "v1.0", "INACTIVE", 0));
        Deployment green = greenOpt.orElse(new Deployment("green", "v2.0", "INACTIVE", 0));

        Map<String, Object> traffic = Map.of(
            "blue", blue.getTrafficWeight(),
            "green", green.getTrafficWeight()
        );

        return Map.of(
            "activeEnv", activeEnv,
            "traffic", traffic,
            "blueVersion", blue.getVersion(),
            "greenVersion", green.getVersion(),
            "blueStatus", blue.getStatus(),
            "greenStatus", green.getStatus(),
            "switchCount", switchCount,
            "zeroDowntime", true,
            "strategy", "Blue-Green Deployment - 0 Downtime",
            "totalDeployments", repo.count()
        );
    }

    public Deployment deployBlue(String version) {
        Deployment blue = repo.findByEnvironment("blue").orElse(new Deployment());
        blue.setEnvironment("blue");
        blue.setVersion(version);
        blue.setDeployedAt(LocalDateTime.now());
        blue.setHealth("HEALTHY");

        if (activeEnv.equals("blue")) {
            blue.setStatus("ACTIVE");
            blue.setTrafficWeight(100);
        } else {
            blue.setStatus("INACTIVE");
            blue.setTrafficWeight(0);
        }

        return repo.save(blue);
    }

    public Deployment deployGreen(String version) {
        Deployment green = repo.findByEnvironment("green").orElse(new Deployment());
        green.setEnvironment("green");
        green.setVersion(version);
        green.setDeployedAt(LocalDateTime.now());
        green.setHealth("HEALTHY");

        if (activeEnv.equals("green")) {
            green.setStatus("ACTIVE");
            green.setTrafficWeight(100);
        } else {
            green.setStatus("INACTIVE");
            green.setTrafficWeight(0);
        }

        return repo.save(green);
    }

    public Map<String, Object> switchEnv(String target) {
        if (!target.equals("blue") && !target.equals("green")) {
            target = activeEnv.equals("blue") ? "green" : "blue";
        }

        Deployment blue = repo.findByEnvironment("blue").orElse(null);
        Deployment green = repo.findByEnvironment("green").orElse(null);

        if (blue == null || green == null) {
            return init();
        }

        if (target.equals("blue")) {
            blue.setStatus("ACTIVE");
            blue.setTrafficWeight(100);
            green.setStatus("INACTIVE");
            green.setTrafficWeight(0);
        } else {
            green.setStatus("ACTIVE");
            green.setTrafficWeight(100);
            blue.setStatus("INACTIVE");
            blue.setTrafficWeight(0);
        }

        repo.save(blue);
        repo.save(green);
        activeEnv = target;
        switchCount++;

        return Map.of(
            "message", "Traffic switched to " + target.toUpperCase() + " - Zero Downtime Success!",
            "activeEnv", activeEnv,
            "previousEnv", target.equals("blue") ? "green" : "blue",
            "traffic", Map.of("blue", blue.getTrafficWeight(), "green", green.getTrafficWeight()),
            "switchCount", switchCount,
            "downtime", "0s - Blue-Green ensures no downtime"
        );
    }

    public List<Deployment> getAll() {
        return repo.findAll();
    }

    public Map<String, Object> traffic() {
        Deployment blue = repo.findByEnvironment("blue").orElse(new Deployment("blue", "v1.0", "ACTIVE", 100));
        Deployment green = repo.findByEnvironment("green").orElse(new Deployment("green", "v2.0", "INACTIVE", 0));

        return Map.of(
            "activeEnv", activeEnv,
            "blue", blue.getTrafficWeight(),
            "green", green.getTrafficWeight(),
            "total", 100,
            "strategy", "Blue-Green 100-0"
        );
    }
}