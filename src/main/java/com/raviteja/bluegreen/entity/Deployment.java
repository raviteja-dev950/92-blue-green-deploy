package com.raviteja.bluegreen.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deployments")
public class Deployment {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String environment; // blue / green
    private String version; // v1.0, v2.0
    private String status; // ACTIVE, INACTIVE
    private int trafficWeight; // 0-100
    private int instanceCount = 1;
    private LocalDateTime deployedAt = LocalDateTime.now();
    private String health = "HEALTHY";

    public Deployment() {}

    public Deployment(String env, String ver, String stat, int traffic) {
        this.environment = env;
        this.version = ver;
        this.status = stat;
        this.trafficWeight = traffic;
        this.deployedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String e) { this.environment = e; }

    public String getVersion() { return version; }
    public void setVersion(String v) { this.version = v; }

    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }

    public int getTrafficWeight() { return trafficWeight; }
    public void setTrafficWeight(int w) { this.trafficWeight = w; }

    public int getInstanceCount() { return instanceCount; }
    public void setInstanceCount(int c) { this.instanceCount = c; }

    public LocalDateTime getDeployedAt() { return deployedAt; }
    public void setDeployedAt(LocalDateTime d) { this.deployedAt = d; }

    public String getHealth() { return health; }
    public void setHealth(String h) { this.health = h; }
}