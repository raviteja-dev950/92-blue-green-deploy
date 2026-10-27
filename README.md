# 🚀 Project 92 – Blue-Green Deployment Zero Downtime | Full Stack DevOps | AWS ALB Ready

[Java 21](https://img.shields.io/badge/Java-21-E76F00?logo=openjdk&logoColor=white)
[Spring Boot 3.2.5](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F?logo=springboot&logoColor=white)
[Docker Ready](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker&logoColor=white)
[Blue-Green](https://img.shields.io/badge/Blue--Green-Deployment-00BFFF?logo=blueprint&logoColor=white)
[Zero Downtime](https://img.shields.io/badge/Zero%20Downtime-Enabled-00FF7F?logo=awselasticloadbalancing&logoColor=white)
[AWS EC2](https://img.shields.io/badge/AWS-EC2-FF9900?logo=amazonec2&logoColor=white)
[AWS ALB](https://img.shields.io/badge/AWS-ALB-FF9900?logo=awselasticloadbalancing&logoColor=white)
[AWS RDS](https://img.shields.io/badge/AWS-RDS-527FFF?logo=amazonrds&logoColor=white)
[AWS S3](https://img.shields.io/badge/AWS-S3-569A31?logo=amazons3&logoColor=white)
[MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
[H2 Database](https://img.shields.io/badge/H2-In--Memory-1024D6?logo=h2&logoColor=white)
[Ubuntu](https://img.shields.io/badge/Ubuntu-22.04-E95420?logo=ubuntu&logoColor=white)
[Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)
[Tomcat](https://img.shields.io/badge/Apache%20Tomcat-10.1.20-D4AF00?logo=apachetomcat&logoColor=white)
[Port](https://img.shields.io/badge/Port-9999-00BFFF)
[Status](https://img.shields.io/badge/Status-Blue--Green%20Live-00BFFF)

**Live URL:** https://nine2-blue-green-deploy.onrender.com/

[[Live](https://img.shields.io/badge/Live-Render-brightgreen?style=for-the-badge&logo=render)](https://nine2-blue-green-deploy.onrender.com)

Project 92 - Part of Java 100 Projects Challenge - DevOps + Cloud Tier - **Tier 9 COMPLETE 83-92**
---

# 📖 Project Overview

**Blue-Green Deployment Zero Downtime** is Project 92 of **Tier 9 – DevOps + Cloud Deployment – FINAL PROJECT OF TIER 9**, developed using **Java 21**, **Spring Boot 3.2.5**, **Spring Data JPA**, **Blue-Green Deployment Simulation**, **H2 In-Memory (RDS-Ready Fallback)**, **MySQL / AWS RDS MySQL**, **Docker**, **AWS EC2 + ALB + S3 + RDS (Simulated)**, **Ubuntu 22.04**, **Maven**, and **Apache Tomcat 10.1.20** in a **production Blue-Green ready repo**.

Spring Boot REST API running on port 9999 inside Docker container with 10 Blue-Green endpoints `GET /`, `GET /health`, `GET /deploy/init`, `GET /deploy/status`, `GET /deploy/blue?version=`, `GET /deploy/green?version=`, `GET /deploy/switch?target=`, `GET /deploy/traffic`, `GET /deploy/versions`, `GET /deploy/rollback` + home `/` returns live JSON `{"project":"92-blue-green-deploy","status":"Live - Blue-Green Zero Downtime","activeEnv":"blue","traffic":{"blue":100,"green":0},"totalDeployments":2,"owner":"Raviteja"}`. This repo contains **Dockerfile (multi-stage)**, **Deployment Entity (JPA)**, **DeploymentRepository (JPA)**, **BlueGreenService (init + deployBlue + deployGreen + switch + traffic + rollback)**, **DeploymentController (init + status + blue + green + switch + traffic + versions + rollback)**, **HomeController (Root + Health)**, **Spring Boot source** - eliminating manual local run only. One `git push` gives full Blue-Green Zero Downtime service live production URL. Local uses `jdbc:h2:mem:raviteja92;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE` fallback, on AWS it auto switches to real RDS MySQL `raviteja92` via env.

This is Project 92 of Tier 9 - FINAL - After Terraform Script (91), now real Blue-Green base for Tier 10 Rate Limited API (93).

**Bug Fixed:** `blue\n` with newline - `bluegreen.active-env=${ACTIVE_ENV:blue}` reads env var `ACTIVE_ENV=blue\n` with hidden newline from Eclipse Run Configurations Environment Tab. Fixed by hardcoding `bluegreen.active-env=blue` in `application.properties` and removing `ACTIVE_ENV` from Run Configurations Environment - Same pattern as Project 90/91 `RDS_URL` jdbc bug fix.

**Bug Fixed:** `URL must start with 'jdbc'` - `RDS_URL=raviteja92` without `jdbc:` prefix from old Eclipse Run Configuration env overrides properties - Same bug as Project 90/91. Fixed by **Run -> Run Configurations -> Environment -> Remove `RDS_URL`, `RDS_USER`, `RDS_PASSWORD`, `ACTIVE_ENV`** + Use hardcoded local `spring.datasource.url=jdbc:h2:mem:raviteja92` for local Eclipse.

**Bug Fixed:** `src/main/java/META-INF/MANIFEST.MF` causing Render Docker build fail - Eclipse creates `META-INF` folder inside `src/main/java` which conflicts with Maven build. Fixed by adding `**/META-INF/` to `.gitignore` + `Remove-Item -Recurse -Force src/main/java/META-INF` before push - Same as Project 90/91.

---

# ✨ Features

- Blue-Green Ready status API with JSON response - `{"project":"92-blue-green-deploy","status":"Live - Blue-Green Zero Downtime","activeEnv":"blue","traffic":{"blue":100,"green":0},"totalDeployments":2}`
- Fully production Blue-Green service - 10 endpoints + home + health + init + status + blue deploy + green deploy + switch + traffic + versions + rollback - Docker on Render
- Blue-Green Deployment Simulation - Zero Downtime design - Local `blue` active 100% `green` inactive 0% for demo, real AWS ALB listener rule weight switching via env `ACTIVE_ENV` - `init -> deployBlue v1.1 -> deployGreen v2.0 -> switch green -> traffic 0-100 -> rollback` workflow
- Deployment Entity - JPA `@Entity @Table(name="deployments")` -> Fields `id (auto), environment blue/green, version v1.0/v2.0, status ACTIVE/INACTIVE, trafficWeight 0-100, instanceCount 1, deployedAt now, health HEALTHY` -> `ddl-auto=update` auto creates table
- Init - `GET /deploy/init` -> Deletes all + Creates blue v1.0 ACTIVE 100% + green v2.0 INACTIVE 0% -> Returns `{"activeEnv":"blue","traffic":{"blue":100,"green":0},"blueVersion":"v1.0","greenVersion":"v2.0","switchCount":0,"zeroDowntime":true,"strategy":"Blue-Green Deployment - 0 Downtime"}` 200 OK - Simulates `terraform apply` blue + green env
- Deploy Blue - `GET /deploy/blue?version=v1.1` -> Updates blue version v1.1 + Keeps traffic 100% if blue active -> Returns `{"id":1,"environment":"blue","version":"v1.1","status":"ACTIVE","trafficWeight":100,"health":"HEALTHY"}` 200 OK
- Deploy Green - `GET /deploy/green?version=v2.1` -> Updates green version v2.1 + Keeps traffic 0% if blue active -> Returns `{"id":2,"environment":"green","version":"v2.1","status":"INACTIVE","trafficWeight":0,"health":"HEALTHY"}` 200 OK
- Switch - `GET /deploy/switch?target=green` -> Switches activeEnv blue -> green + blue 100->0 + green 0->100 + switchCount++ -> Returns `{"message":"Traffic switched to GREEN - Zero Downtime Success!","activeEnv":"green","previousEnv":"blue","traffic":{"blue":0,"green":100},"switchCount":1,"downtime":"0s - Blue-Green ensures no downtime"}` 200 OK - Simulates ALB listener rule weight switch
- Traffic - `GET /deploy/traffic` -> Returns `{"activeEnv":"green","blue":0,"green":100,"total":100,"strategy":"Blue-Green 100-0"}` - Simulates `aws_lb_listener_rule` weight
- Versions - `GET /deploy/versions` -> Returns `[{"id":1,"environment":"blue","version":"v1.0","status":"INACTIVE","trafficWeight":0},{"id":2,"environment":"green","version":"v2.0","status":"ACTIVE","trafficWeight":100}]` - Simulates `aws_instance` blue + green versions
- Rollback - `GET /deploy/rollback` -> Switches back to previous env -> `{"activeEnv":"blue","previousEnv":"green","message":"Traffic switched to BLUE - Zero Downtime Success!"}` - Zero downtime rollback
- Home Info - `GET /` -> Returns `project`, `status`, `activeEnv`, `traffic`, `totalDeployments`, `owner`, `tier`, `endpoints` (10 endpoints) - Portfolio proof of Blue-Green
- BlueGreenService - Central Blue-Green tracking - `activeEnv blue` + `switchCount 0` + `init()` + `status()` + `deployBlue()` + `deployGreen()` + `switchEnv()` + `traffic()` + `getAll()` logic
- Dockerfile Multi-Stage - `maven:3.9-eclipse-temurin-21` builder + `eclipse-temurin:21-jdk` runtime - Image ~195MB
- One-Command Deploy - Same `deploy.sh` pattern as Project 90/91 with `docker build`, `docker run -d -p 9999:9999 --restart always`
- Auto-Restart on Reboot - `--restart always` ensures Blue-Green service up after restart
- GitHub Actions CD Ready - Same `ec2-deploy.yml` can be reused
- Spring Boot banner v3.2.5 inside logs
- Tomcat initialized with port 9999 (http) inside Docker container - Same as 90 on 9797, 91 on 9898, now 9999 - Tier 9 Final Port
- Started Application in 11.275 seconds (Local Eclipse Java 21) - Includes JPA EntityManagerFactory + HikariPool-1 Start completed + BlueGreenService - FASTER than 91 (11.952s)
- Verified with Browser - `GET /` + `GET /deploy/init` + `GET /deploy/status` + `GET /deploy/switch?target=green` + `GET /deploy/traffic` + `GET /deploy/versions` 200 OK
- Public Production URL - `https://nine2-blue-green-deploy.onrender.com` accessible anywhere - Portfolio ready Blue-Green project - Tier 9 COMPLETE

---

# 🛠 Technologies Used

- Java 21.0.10
- Spring Boot 3.2.5 (Banner v3.2.5)
- Spring Data JPA - `JpaRepository<Deployment, Long>` - Hibernate 6.4.4.Final
- Spring Web - REST API `@RestController` + `@RequestMapping("/deploy")` + `RequestMethod.GET, POST`
- Spring Boot Starter Validation
- Blue-Green Deployment 100-0 Strategy - Zero Downtime Simulation - `BlueGreenService` + `DeploymentController` + `deployments` table + ALB listener rule weight simulation via `/deploy/traffic` + `switchCount` + `zeroDowntime true`
- Blue-Green Tracking - `BlueGreenService.java` - `activeEnv blue` + `switchCount int` + `init()` + `status()` + `deployBlue(version)` + `deployGreen(version)` + `switchEnv(target)` + `traffic()` + `getAll()` + `rollback()` logic
- H2 Database 2.2.224 - In-Memory `jdbc:h2:mem:raviteja92` - `DB_CLOSE_DELAY=-1` - Local fallback without AWS card
- MySQL Connector/J 8.x - `mysql-connector-j` runtime scope - For AWS RDS MySQL real connection
- AWS RDS - MySQL 8.0 - Instance `raviteja-92-rds` - Region ap-south-1 (Mumbai) - RDS-Ready pattern (Same as 90/91) - For storing deployment state when real
- Docker - Multi-stage build - `maven:3.9` builder + `eclipse-temurin:21-jdk`
- AWS EC2 - t2.micro Free Tier - For future real Blue-Green `aws_instance` blue + green
- AWS ALB - Application Load Balancer - `raviteja-92-alb` - Listener rule weight 100% blue / 0% green -> Switch to 0% blue / 100% green - Zero downtime switch
- AWS S3 - `raviteja-blue-green-92` bucket - For storing deployment artifacts `v1.0` + `v2.0`
- AWS VPC - `vpc-0a1b2c3d` - For future real Blue-Green VPC + 2 AZ + ALB
- Ubuntu 22.04 LTS - EC2 OS
- GitHub Actions - Ready for `appleboy/ssh-action@v1.0.3` CD
- GitHub Secrets - `EC2_HOST`, `EC2_USERNAME`, `EC2_SSH_KEY`, `RDS_URL`, `RDS_USER`, `RDS_PASSWORD`, `ACTIVE_ENV`, `AWS_REGION`
- HikariCP - `HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:raviteja92 user=SA` - Connection pooling
- Hibernate - `create table deployments (id bigint generated by default as identity, deployed_at timestamp(6), environment varchar(255), health varchar(255), instance_count integer not null, status varchar(255), traffic_weight integer not null, version varchar(255), primary key (id))` - Auto DDL
- Maven 3.9+
- Apache Tomcat 10.1.20 (Embedded) - TomcatWebServer port 9999
- Browser - Chrome - For `GET /deploy/init` + `GET /deploy/status` + `GET /deploy/switch?target=green` test
- Windows 11 - PowerShell + Eclipse IDE + Browser
- AWS Console - EC2 + ALB + S3 + RDS + Target Group + Listener Rule Dashboard

---

# 📂 Project Structure - Blue-Green Repo

```text
92-blue-green-deploy
│
├── .github
│   └── workflows
│       └── ec2-deploy.yml (Optional - Same as 90/91)
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── raviteja
│   │   │           └── bluegreen
│   │   │               ├── BlueGreenApplication.java (Home / + /health)
│   │   │               ├── controller
│   │   │               │   └── DeploymentController.java (GET /init + /status + /blue + /green + /switch + /traffic + /versions + /rollback)
│   │   │               ├── entity
│   │   │               │   └── Deployment.java
│   │   │               ├── repository
│   │   │               │   └── DeploymentRepository.java
│   │   │               └── service
│   │   │                   └── BlueGreenService.java (init + deployBlue + deployGreen + switch + traffic + rollback logic)
│   │   │
│   │   └── resources
│   │       └── application.properties (active-env + region)
│   │
│   └── test
│       └── java
│           └── com
│               └── raviteja
│                   └── bluegreen
│                       └── ApplicationTests.java
│
├── screenshots
│   ├── demo1.png (GET / - Home Live Blue-Green Zero Downtime)
│   ├── demo2.png (GET /deploy/init - blue ACTIVE 100% green INACTIVE 0% total 2)
│   ├── demo3.png (GET /deploy/status - activeEnv blue traffic 100-0 zeroDowntime true)
│   ├── demo4.png (GET /deploy/switch?target=green - Traffic switched to GREEN Success switchCount 1 downtime 0s)
│   ├── demo5.png (GET /deploy/traffic - activeEnv green blue 0 green 100 total 100 strategy 100-0)
│   ├── demo6.png (GET /deploy/versions - 2 deployments HEALTHY v1.0 blue INACTIVE + v2.0 green ACTIVE)
│   ├── demo7.png (GET /deploy/status after switch - activeEnv green)
│   ├── demo8.png (GET /deploy/rollback - Rollback to blue)
│   └── demo9.png (Render Live - Deploy succeeded LIVE)
│
├── Dockerfile
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# ▶ How to Run - Blue-Green Repo

## 1⃣ Clone the Repository

```bash
git clone https://github.com/raviteja-dev950/92-blue-green-deploy.git
cd 92-blue-green-deploy
```

---

## 2⃣ Verify application.properties & Controllers

- Verify `src/main/resources/application.properties` - Blue-Green-Ready - NO driver-class-name! - HARDCODED to avoid Render bug!

```properties
spring.application.name=92-blue-green-deploy
server.port=${PORT:9999}
spring.datasource.url=jdbc:h2:mem:raviteja92;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
bluegreen.active-env=blue
app.name=92-blue-green-deploy
app.region=${AWS_REGION:ap-south-1}
```

- Verify `BlueGreenService.java` - NEW FOR 92 - Core Zero Downtime Logic

```java
@Service
public class BlueGreenService {
  private String activeEnv = "blue";
  private int switchCount = 0;

  public Map<String, Object> init() {
    repo.deleteAll();
    Deployment blue = new Deployment("blue", "v1.0", "ACTIVE", 100);
    Deployment green = new Deployment("green", "v2.0", "INACTIVE", 0);
    repo.save(blue); repo.save(green);
    activeEnv = "blue";
    return status();
  }
  public Map<String, Object> status() {
    Deployment blue = repo.findByEnvironment("blue").orElse(new Deployment("blue","v1.0","ACTIVE",100));
    Deployment green = repo.findByEnvironment("green").orElse(new Deployment("green","v2.0","INACTIVE",0));
    return Map.of("activeEnv",activeEnv,"traffic",Map.of("blue",blue.getTrafficWeight(),"green",green.getTrafficWeight()),"blueVersion",blue.getVersion(),"greenVersion",green.getVersion(),"switchCount",switchCount,"zeroDowntime",true,"strategy","Blue-Green Deployment - 0 Downtime");
  }
  public Map<String, Object> switchEnv(String target) {
    Deployment blue = repo.findByEnvironment("blue").orElse(null);
    Deployment green = repo.findByEnvironment("green").orElse(null);
    if(target.equals("blue")) { blue.setStatus("ACTIVE"); blue.setTrafficWeight(100); green.setStatus("INACTIVE"); green.setTrafficWeight(0); }
    else { green.setStatus("ACTIVE"); green.setTrafficWeight(100); blue.setStatus("INACTIVE"); blue.setTrafficWeight(0); }
    repo.save(blue); repo.save(green); activeEnv=target; switchCount++;
    return Map.of("message","Traffic switched to "+target.toUpperCase()+" - Zero Downtime Success!","activeEnv",activeEnv,"traffic",Map.of("blue",blue.getTrafficWeight(),"green",green.getTrafficWeight()),"switchCount",switchCount,"downtime","0s - Blue-Green ensures no downtime");
  }
}
```

- Verify `DeploymentController.java` - NEW FOR 92 - 8 endpoints + rollback

```java
@RestController
@RequestMapping("/deploy")
public class DeploymentController {
  @GetMapping("/init")
  public Map<String, Object> init() { return service.init(); }

  @GetMapping("/status")
  public Map<String, Object> status() { return service.status(); }

  @RequestMapping(value="/blue", method={GET,POST})
  public Deployment deployBlue(@RequestParam(defaultValue="v1.1") String version) { return service.deployBlue(version); }

  @RequestMapping(value="/green", method={GET,POST})
  public Deployment deployGreen(@RequestParam(defaultValue="v2.1") String version) { return service.deployGreen(version); }

  @RequestMapping(value="/switch", method={GET,POST})
  public Map<String, Object> switchEnv(@RequestParam(defaultValue="green") String target) { return service.switchEnv(target); }

  @GetMapping("/traffic")
  public Map<String, Object> traffic() { return service.traffic(); }

  @GetMapping("/versions")
  public List<Deployment> versions() { return service.getAll(); }

  @GetMapping("/rollback")
  public Map<String, Object> rollback() { String prev = service.status().get("activeEnv").toString().equals("blue") ? "green" : "blue"; return service.switchEnv(prev); }
}
```

- Verify `Dockerfile`

```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","app.jar"]
```

---

## 3⃣ Run in Eclipse IDE (No Docker Needed)

Eclipse Steps:

```text
File -> New -> Spring Starter Project
  Name: 92-blue-green-deploy
  Group: com.raviteja
  Artifact: 92-blue-green-deploy
  Package: com.raviteja.bluegreen
  Java: 17
  Dependencies: Spring Web, Spring Data JPA, Validation, H2, MySQL Driver
Finish

Replace src + pom.xml + application.properties + Dockerfile from this repo
Project -> Clean -> Clean all
Right Click Project -> Maven -> Update Project -> Force Update -> OK
Run -> Run Configurations -> Environment -> REMOVE RDS_URL, RDS_USER, RDS_PASSWORD, ACTIVE_ENV if present
Right Click Project -> Run As -> Spring Boot App
```

Expected Console:

```text
 :: Spring Boot ::                (v3.2.5)
 Tomcat initialized with port 9999 (http)
 HikariPool-1 - Starting...
 HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:raviteja92 user=SA
 HikariPool-1 - Start completed.
 Hibernate: create table deployments (id bigint generated by default as identity, deployed_at timestamp(6), environment varchar(255), health varchar(255), instance_count integer not null, status varchar(255), traffic_weight integer not null, version varchar(255), primary key (id))
 Initialized JPA EntityManagerFactory for persistence unit 'default'
 Tomcat started on port 9999 (http) with context path ''
 Started BlueGreenApplication in 11.275 seconds (process running for 12.007)
```

---

## 4⃣ Test with Browser - 8 Endpoints - Real Blue-Green Flow

Open Browser:

**Test 1: Home - Live Blue-Green**

```text
GET http://localhost:9999/
Expected 200 OK:
{"project":"92-blue-green-deploy","status":"Live - Blue-Green Zero Downtime","activeEnv":"blue","traffic":{"blue":0,"green":0},"totalDeployments":0,"owner":"Raviteja","tier":"9 - DevOps + Cloud - Blue-Green"}
```

**Test 2: Init - Blue ACTIVE 100% Green INACTIVE 0%**

```text
GET http://localhost:9999/deploy/init
Expected 200 OK:
{"greenStatus":"INACTIVE","switchCount":0,"totalDeployments":2,"blueStatus":"ACTIVE","zeroDowntime":true,"blueVersion":"v1.0","greenVersion":"v2.0","strategy":"Blue-Green Deployment - 0 Downtime","activeEnv":"blue","traffic":{"green":0,"blue":100}}
```

**Test 3: Status - activeEnv blue traffic 100-0**

```text
GET http://localhost:9999/deploy/status
Expected 200 OK:
{"greenStatus":"INACTIVE","switchCount":0,"totalDeployments":2,"blueStatus":"ACTIVE","zeroDowntime":true,"blueVersion":"v1.0","greenVersion":"v2.0","strategy":"Blue-Green Deployment - 0 Downtime","activeEnv":"blue","traffic":{"green":0,"blue":100}}
```

**Test 4: Switch to Green - Zero Downtime Success**

```text
GET http://localhost:9999/deploy/switch?target=green
Expected 200 OK:
{"activeEnv":"green","previousEnv":"blue","switchCount":1,"message":"Traffic switched to GREEN - Zero Downtime Success!","traffic":{"green":100,"blue":0},"downtime":"0s - Blue-Green ensures no downtime"}
```

**Test 5: Traffic - green 100 blue 0 after switch**

```text
GET http://localhost:9999/deploy/traffic
Expected 200 OK:
{"strategy":"Blue-Green 100-0","green":100,"total":100,"activeEnv":"green","blue":0}
```

**Test 6: Versions - 2 deployments HEALTHY**

```text
GET http://localhost:9999/deploy/versions
Expected 200 OK:
[{"id":1,"environment":"blue","version":"v1.0","status":"INACTIVE","trafficWeight":0,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"},{"id":2,"environment":"green","version":"v2.0","status":"ACTIVE","trafficWeight":100,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"}]
```

**Test 7: Deploy Blue v1.1**

```text
GET http://localhost:9999/deploy/blue?version=v1.1
Expected 200 OK: {"id":1,"environment":"blue","version":"v1.1","status":"INACTIVE","trafficWeight":0,"health":"HEALTHY"}
```

**Test 8: Rollback - Switch back to Blue**

```text
GET http://localhost:9999/deploy/rollback
Expected 200 OK: {"activeEnv":"blue","previousEnv":"green","switchCount":2,"message":"Traffic switched to BLUE - Zero Downtime Success!","traffic":{"green":0,"blue":100}}
```

---

## 5⃣ Docker Build & Run (Local)

```bash
docker build -t 92-blue-green-deploy .
docker run -d -p 9999:9999 --name bg-app 92-blue-green-deploy
docker ps
docker logs bg-app
curl http://localhost:9999/
curl http://localhost:9999/deploy/init
curl http://localhost:9999/deploy/status
curl http://localhost:9999/deploy/switch?target=green
curl http://localhost:9999/deploy/traffic
curl http://localhost:9999/deploy/versions
```

---

## 6⃣ Deploy to Render (Docker) - Live URL - TIER 9 COMPLETE

Go to `Render.com -> New Web Service -> Connect GitHub Repo: 92-blue-green-deploy`

```text
Runtime: Docker
Dockerfile Path: ./Dockerfile
Port: 9999
Branch: main

Env Vars (Local H2 + Blue-Green Simulation - No AWS card needed - SAME AS 90/91):
  PORT = 9999
  ACTIVE_ENV = blue
  AWS_REGION = ap-south-1
  No RDS_URL! No DATABASE_URL! Hardcoded H2 in properties!
```

Click `Deploy`

Expected Logs on Render:

```text
#12 exporting to image
#13 DONE
Deploy succeeded
HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:raviteja92 user=SA
Tomcat started on port 9999
Started BlueGreenApplication in 11.275 seconds
Your service is live
Available at: https://nine2-blue-green-deploy.onrender.com
```

Test Live:

```bash
curl https://nine2-blue-green-deploy.onrender.com/
curl https://nine2-blue-green-deploy.onrender.com/deploy/init
curl https://nine2-blue-green-deploy.onrender.com/deploy/status
curl https://nine2-blue-green-deploy.onrender.com/deploy/switch?target=green
curl https://nine2-blue-green-deploy.onrender.com/deploy/traffic
curl https://nine2-blue-green-deploy.onrender.com/deploy/versions
curl https://nine2-blue-green-deploy.onrender.com/deploy/rollback
```

---

# 🔄 Flow Diagram - How Blue-Green Zero Downtime Works

```text
    User Laptop (Eclipse + Browser)
        |
        +-- E:\Java-100-Projects\92-blue-green-deploy
        |       +-- src -> BlueGreenApplication.java (Home / + /health)
        |       +-- controller -> DeploymentController.java (init + status + blue + green + switch + traffic + versions + rollback)
        |       +-- service -> BlueGreenService.java (activeEnv + switchCount + init + deploy + switch + traffic logic)
        |       +-- entity -> Deployment.java (@Entity deployments) + repository
        |       +-- resources -> application.properties (active-env + region hardcoded blue)
        |       +-- Dockerfile (multi-stage builder + runtime)
        |       +-- screenshots (demo1-9)
        |
        v
    git push origin main
        |
        +---> GitHub Repository: raviteja-dev950/92-blue-green-deploy
        |           |
        |           v
        |       Render Auto Deploy (Docker)
        |           +-- Dockerfile build mvn clean package -Dmaven.test.skip=true
        |           +-- ENTRYPOINT java -jar app.jar (PORT=9999, ACTIVE_ENV=blue)
        |           +-- Live: https://nine2-blue-green-deploy.onrender.com
        |
        v
    Local Eclipse Run (No AWS card needed - Blue-Green Simulation + H2)
        |
        +-- Spring Boot App -> Tomcat 9999
        |     +-- DataSource Auto-Detect from URL jdbc:h2:mem:raviteja92
        |     |     +-- HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:raviteja92 user=SA
        |     +-- JPA / Hibernate
        |     |     +-- create table deployments
        |     +-- BlueGreenService
        |     |     +-- init() -> deleteAll() + blue v1.0 ACTIVE 100% + green v2.0 INACTIVE 0% + save() + activeEnv=blue + switchCount=0
        |     |     +-- status() -> findByEnvironment blue + green + Map activeEnv + traffic + versions + switchCount + zeroDowntime true
        |     |     +-- deployBlue(version) -> find blue + set version + set deployedAt now + if activeEnv blue set ACTIVE 100% else INACTIVE 0% + save()
        |     |     +-- deployGreen(version) -> find green + set version + same traffic logic
        |     |     +-- switchEnv(target green) -> find blue + green + blue ACTIVE 100% -> INACTIVE 0% + green INACTIVE 0% -> ACTIVE 100% + activeEnv=green + switchCount++ + return message Zero Downtime Success!
        |     |     +-- traffic() -> return activeEnv + blue weight + green weight + total 100 + strategy Blue-Green 100-0
        |     |     +-- getAll() -> findAll() deployments
        |     |     +-- rollback() -> switch to previous env -> same as switchEnv but prev
        |     +-- Controllers
        |           +-- GET / -> {project:92-blue-green-deploy, status:Live - Blue-Green Zero Downtime, activeEnv:blue, traffic:100-0, totalDeployments:2}
        |           +-- GET /deploy/init -> blue ACTIVE 100% green INACTIVE 0% total 2 switchCount 0
        |           +-- GET /deploy/status -> activeEnv blue blueVersion v1.0 greenVersion v2.0 zeroDowntime true traffic blue 100 green 0
        |           +-- GET /deploy/blue?version=v1.1 -> Updates blue to v1.1 ACTIVE if blue active
        |           +-- GET /deploy/green?version=v2.0 -> Updates green to v2.0 INACTIVE if blue active
        |           +-- GET /deploy/switch?target=green -> Traffic switched to GREEN - Zero Downtime Success! switchCount 1 downtime 0s
        |           +-- GET /deploy/traffic -> activeEnv green blue 0 green 100 strategy 100-0
        |           +-- GET /deploy/versions -> 2 deployments HEALTHY
        |           +-- GET /deploy/rollback -> Switch back to blue - Zero Downtime Success!
        |
        v
    Future AWS Production (With AWS Card - Real Blue-Green + ALB)
        |
        +-- AWS Architecture - Blue-Green Zero Downtime
        |     +-- VPC vpc-0a1b2c3d with 2 AZ ap-south-1a + ap-south-1b + 2 Public Subnets + 2 Private Subnets + NAT Gateway
        |     +-- ALB raviteja-92-alb - Listener Port 80 + Target Group Blue TG (blue-ec2-1) + Target Group Green TG (green-ec2-1) + Listener Rule weight
        |     +-- Blue Environment - EC2 t2.micro ap-south-1a - AMI Ubuntu 22.04 + Docker 92-blue-green-deploy:v1.0 + Security Group Port 9999 + ACTIVE + Weight 100%
        |     +-- Green Environment - EC2 t2.micro ap-south-1b - AMI Ubuntu 22.04 + Docker 92-blue-green-deploy:v2.0 + Security Group Port 9999 + INACTIVE + Weight 0%
        |     +-- Deployment Flow - 1. Deploy Blue v1.0 -> ALB 100% Blue 0% Green -> Test Blue -> 2. Deploy Green v2.0 -> ALB still 100% Blue -> Test Green internally -> 3. Switch -> ALB Listener Rule Weight 0% Blue 100% Green -> Zero Downtime Switch -> 4. Monitor Green -> If issue -> Rollback -> ALB 100% Blue
        |     +-- RDS raviteja-92-rds MySQL 8.0 db.t3.micro - Stores deployments table - Both blue + green connect to same RDS
        |     +-- S3 raviteja-blue-green-92 bucket - Stores artifacts v1.0.jar + v2.0.jar
        |     +-- CloudWatch Alarms - UnhealthyHostCount >0 in Blue TG + Green TG + ALB 5xx >0 + CPU >80% - SNS email alert
        |     +-- Route 53 - Domain blue-green.raviteja.dev -> ALB DNS
        |     +-- Real Commands: terraform apply -var="environment=blue" -> creates blue infra -> terraform apply -var="environment=green" -> creates green infra -> aws elbv2 modify-listener --listener-arn arn:aws:elbv2:... --default-actions Type=forward,ForwardConfig={TargetGroups=[{TargetGroupArn=blue-tg-arn,Weight=0},{TargetGroupArn=green-tg-arn,Weight=100}]} -> Switch
        |
        v
    Browser / Portfolio / AWS Console
        |
        +-- localhost:9999/ -> Live Blue-Green Zero Downtime
        +-- localhost:9999/deploy/init -> blue ACTIVE 100% green INACTIVE 0%
        +-- localhost:9999/deploy/status -> activeEnv blue
        +-- localhost:9999/deploy/switch?target=green -> Traffic switched to GREEN Success!
        +-- localhost:9999/deploy/traffic -> green 100 blue 0
        +-- localhost:9999/deploy/versions -> 2 deployments HEALTHY
        +-- localhost:9999/deploy/rollback -> Rollback to blue
        +-- Live: https://nine2-blue-green-deploy.onrender.com/ -> Same JSON on Internet
              |
              +-- Real ALB Target Group Dashboard (Future): Blue TG - 1 healthy host - Green TG - 1 healthy host - Listener Rule - Weight Blue 100 Green 0 -> After Switch - Weight Blue 0 Green 100 - Zero Downtime
              v
    Portfolio: Live Blue-Green URL + GitHub + 9 Demo Screenshots + ALB Flow -> Resume Ready - DevOps + Cloud Tier 9 COMPLETE 83-92 - 92/100
```

---

# 📸 Screenshots

### Demo 1 – Browser - GET / - Home Live Blue-Green Zero Downtime activeEnv blue tier 9
[Demo1 Home](screenshots/demo1.png)

*GET http://localhost:9999/ - Browser JSON - 200 OK - Returns {"project":"92-blue-green-deploy","status":"Live - Blue-Green Zero Downtime","activeEnv":"blue","traffic":{"blue":0,"green":0},"totalDeployments":0,"owner":"Raviteja","tier":"9 - DevOps + Cloud - Blue-Green","endpoints":[...10 endpoints]} - Proof of Home Live - Portfolio ready*

### Demo 2 – Browser - GET /deploy/init - Blue ACTIVE 100% Green INACTIVE 0% total 2 switchCount 0
[Demo2 Init](screenshots/demo2.png)

*GET http://localhost:9999/deploy/init - Browser JSON - 200 OK - Returns {"greenStatus":"INACTIVE","switchCount":0,"totalDeployments":2,"blueStatus":"ACTIVE","zeroDowntime":true,"blueVersion":"v1.0","greenVersion":"v2.0","strategy":"Blue-Green Deployment - 0 Downtime","activeEnv":"blue","traffic":{"green":0,"blue":100}} - Proof of Blue-Green Init - Creates 2 deployments - Blue v1.0 ACTIVE 100% + Green v2.0 INACTIVE 0% - Same as terraform apply blue + green*

### Demo 3 – Browser - GET /deploy/status - activeEnv blue traffic blue 100 green 0 zeroDowntime true
[Demo3 Status](screenshots/demo3.png)

*GET http://localhost:9999/deploy/status - Browser JSON - 200 OK - Returns {"greenStatus":"INACTIVE","switchCount":0,"totalDeployments":2,"blueStatus":"ACTIVE","zeroDowntime":true,"blueVersion":"v1.0","greenVersion":"v2.0","strategy":"Blue-Green Deployment - 0 Downtime","activeEnv":"blue","traffic":{"green":0,"blue":100}} - Proof of Status - activeEnv blue - traffic 100-0 - zeroDowntime true - blueVersion v1.0 greenVersion v2.0*

### Demo 4 – Browser - GET /deploy/switch?target=green - Traffic switched to GREEN Zero Downtime Success switchCount 1 downtime 0s
[Demo4 Switch](screenshots/demo4.png)

*GET http://localhost:9999/deploy/switch?target=green - Browser JSON - 200 OK - Returns {"activeEnv":"green","previousEnv":"blue","switchCount":1,"message":"Traffic switched to GREEN - Zero Downtime Success!","traffic":{"green":100,"blue":0},"downtime":"0s - Blue-Green ensures no downtime"} - Proof of Zero Downtime Switch - activeEnv green - previousEnv blue - switchCount 1 - downtime 0s - ALB listener rule weight switch simulation*

### Demo 5 – Browser - GET /deploy/traffic - activeEnv green blue 0 green 100 total 100 strategy 100-0
[Demo5 Traffic](screenshots/demo5.png)

*GET http://localhost:9999/deploy/traffic - Browser JSON - 200 OK - Returns {"strategy":"Blue-Green 100-0","green":100,"total":100,"activeEnv":"green","blue":0} - Proof of Traffic - After switch green 100% blue 0% - total 100 - strategy Blue-Green 100-0 - ALB weight*

### Demo 6 – Browser - GET /deploy/versions - 2 deployments HEALTHY blue INACTIVE green ACTIVE
[Demo6 Versions](screenshots/demo6.png)

*GET http://localhost:9999/deploy/versions - Browser JSON - 200 OK - Returns [{"id":1,"environment":"blue","version":"v1.0","status":"INACTIVE","trafficWeight":0,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"},{"id":2,"environment":"green","version":"v2.0","status":"ACTIVE","trafficWeight":100,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"}] - Proof of Versions - 2 deployments HEALTHY - Blue INACTIVE v1.0 traffic 0 + Green ACTIVE v2.0 traffic 100 - Instance count 1 each*

### Demo 7 – Browser - GET /deploy/status after switch - activeEnv green blue 0 green 100
[Demo7 Status Green](screenshots/demo7.png)

*GET http://localhost:9999/deploy/status after switch - Browser JSON - 200 OK - Returns {"activeEnv":"green","traffic":{"blue":0,"green":100},"blueVersion":"v1.0","greenVersion":"v2.0","switchCount":1,"zeroDowntime":true} - Proof of Status after switch - activeEnv green - traffic blue 0 green 100 - switchCount 1 - Zero downtime maintained*

### Demo 8 – Browser - GET /deploy/rollback - Rollback to blue Zero Downtime
[Demo8 Rollback](screenshots/demo8.png)

*GET http://localhost:9999/deploy/rollback - Browser JSON - 200 OK - Returns {"activeEnv":"blue","previousEnv":"green","switchCount":2,"message":"Traffic switched to BLUE - Zero Downtime Success!","traffic":{"green":0,"blue":100},"downtime":"0s"} - Proof of Rollback - If green has issue, rollback to blue - activeEnv blue again - switchCount 2 - Zero downtime rollback - Production safety*

### Demo 9 – Render Live - Deploy Succeeded - LIVE - TIER 9 COMPLETE
[Demo9 Render](screenshots/demo9.png)

*Render Dashboard - 92-blue-green-deploy - STATUS: Deploy succeeded | Live - DURATION: 2m15s - DEPLOYED: Oct 27, 2026 at 7:15:05 PM GMT+5:30 - TRIGGER: Manual Deploy - SOURCE: commit xxxxxxx - Logs: Tomcat started on port 9999 - Started BlueGreenApplication in 11.275 seconds (process running for 12.007) - Your service is live - Available at your primary URL https://nine2-blue-green-deploy.onrender.com - Proof of Live Deployment on Internet - Docker Multi-Stage + Blue-Green Simulation working on Render - TIER 9 COMPLETE 83-92 92/100*

---

# 🧪 API Testing Examples

```bash
# GET Home - Local after Eclipse Run
curl http://localhost:9999/

# GET Home - LIVE on Render
curl https://nine2-blue-green-deploy.onrender.com/

# Init - Blue ACTIVE 100% Green INACTIVE 0%
curl http://localhost:9999/deploy/init
curl https://nine2-blue-green-deploy.onrender.com/deploy/init

# Status - activeEnv blue traffic 100-0
curl http://localhost:9999/deploy/status
curl https://nine2-blue-green-deploy.onrender.com/deploy/status

# Deploy Blue v1.1
curl http://localhost:9999/deploy/blue?version=v1.1

# Deploy Green v2.1
curl http://localhost:9999/deploy/green?version=v2.1

# Switch to Green - Zero Downtime Success
curl http://localhost:9999/deploy/switch?target=green
curl https://nine2-blue-green-deploy.onrender.com/deploy/switch?target=green

# Traffic - green 100 blue 0 after switch
curl http://localhost:9999/deploy/traffic
curl https://nine2-blue-green-deploy.onrender.com/deploy/traffic

# Versions - 2 deployments HEALTHY
curl http://localhost:9999/deploy/versions
curl https://nine2-blue-green-deploy.onrender.com/deploy/versions

# Rollback - Switch back to Blue
curl http://localhost:9999/deploy/rollback

# Switch to Blue
curl http://localhost:9999/deploy/switch?target=blue

# Docker Test
docker build -t 92-blue-green-deploy .
docker run -d -p 9999:9999 --name bg-app 92-blue-green-deploy
docker logs bg-app
curl http://localhost:9999/deploy/init
curl http://localhost:9999/deploy/status
curl http://localhost:9999/deploy/switch?target=green
curl http://localhost:9999/deploy/traffic
```

---

# 🎯 Learning Outcomes

- Understanding Blue-Green vs Rolling vs Canary Deployment - Rolling: Update 1 instance at a time - 1 old + 2 new + 1 old + 2 new (downtime risk if new version fails during rollout) vs Canary: 10% traffic to new version -> 50% -> 100% (gradual but complex) vs Blue-Green: 100% Blue ACTIVE + 0% Green INACTIVE -> Test Green internally -> Switch 100% Green 0% Blue in 1 sec (zero downtime, instant rollback, but needs double infra)
- Understanding ALB Listener Rule Weight - AWS ALB has Listener Port 80 + Rule `IF Path /* THEN Forward to Target Group` + Target Group Blue TG (blue-ec2:9999) Weight 100 + Target Group Green TG (green-ec2:9999) Weight 0 = 100% traffic to Blue - Switch: `aws elbv2 modify-listener --listener-arn arn --default-actions Type=forward,ForwardConfig={TargetGroups=[{TargetGroupArn=blue-arn,Weight=0},{TargetGroupArn=green-arn,Weight=100}]}` = 0% Blue 100% Green - Zero downtime switch in milliseconds
- Implementing BlueGreenService - activeEnv String blue/green + switchCount int + init() deletes all + creates blue v1.0 ACTIVE 100% + green v2.0 INACTIVE 0% + saves + activeEnv=blue -> status() finds blue + green + returns activeEnv + traffic map blue 100 green 0 + versions + switchCount + zeroDowntime true + strategy -> deployBlue(version) finds blue + sets version + deployedAt now + if activeEnv blue sets ACTIVE 100% else INACTIVE 0% + save() -> deployGreen same -> switchEnv(target green) finds blue + green + blue ACTIVE 100% -> INACTIVE 0% + green INACTIVE 0% -> ACTIVE 100% + activeEnv=green + switchCount++ + returns message Zero Downtime Success + traffic + downtime 0s
- Understanding Zero Downtime - Blue env serves 100% live traffic -> Deploy Green env with v2.0 but 0% traffic -> Test Green internally via `GET /deploy/green?version=v2.1` + health check -> Switch traffic via ALB rule weight 0% blue 100% green -> Green serves 100% live traffic -> If Green fails -> Rollback via `GET /deploy/rollback` -> ALB weight back 100% blue 0% green in 1 sec -> No user sees downtime because ALB keeps serving from healthy TG during switch
- Creating 10 Blue-Green Endpoints - GET / (home with activeEnv + traffic + totalDeployments), GET /health (UP + Blue-Green Ready + zeroDowntime Enabled), GET /deploy/init (blue ACTIVE 100% green INACTIVE 0% total 2), GET /deploy/status (activeEnv + traffic + versions + switchCount + zeroDowntime), GET /deploy/blue?version=v1.1 (deploy blue), GET /deploy/green?version=v2.0 (deploy green), GET /deploy/switch?target=green (Traffic switched to GREEN Success downtime 0s), GET /deploy/traffic (activeEnv + blue 0 green 100 total 100), GET /deploy/versions (2 deployments HEALTHY), GET /deploy/rollback (rollback to blue)
- Using Spring Data JPA + Blue-Green - JpaRepository gives save(), findAll(), findByEnvironment(), count(), deleteAll() - H2 in-memory for local simulation, MySQL RDS for real when storing deployment state - Deployment entity mirrors `aws_instance` blue + green + `aws_lb_target_group` blue TG + green TG + `aws_lb_listener_rule` weight
- Fixing blue\n newline bug - `bluegreen.active-env=${ACTIVE_ENV:blue}` reads env var with hidden \n - Fixed by hardcoding `bluegreen.active-env=blue` + removing ACTIVE_ENV from Run Configurations Environment Tab - Same pattern as Project 90/91 RDS_URL jdbc bug - Render env vars only PORT + ACTIVE_ENV + AWS_REGION no RDS_URL
- Fixing META-INF build fail - Eclipse creates `src/main/java/META-INF/MANIFEST.MF` which conflicts with Maven `target/classes/META-INF` - Fixed by `**/META-INF/` in .gitignore + `Remove-Item -Recurse -Force src/main/java/META-INF` + `git rm -r --cached src/main/java/META-INF` before push - Same as Project 90/91
- Preparing for Tier 10 Rate Limited API (93) - Blue-Green switchCount + trafficWeight + activeEnv tracking will be used for rate limiting per environment - Blue env 100 req/sec + Green env 100 req/sec + ALB distributes + Rate limiter per env
- Understanding Tier 9 Complete - Projects 83-92 = DevOps + Cloud Tier - 83 Docker + 84 EC2 + 85 GitHub Actions CI/CD + 86 S3 + 87 RDS + 88 Secrets + 89 CloudWatch + 90 Load Balanced App + 91 Terraform Script + 92 Blue-Green Deploy = Full DevOps + Cloud Production Ready - 92/100 overall

---

# 🚀 Future Enhancements

- 🏗 Add Real main.tf for Blue-Green - Project 92 Enhancement - Create actual `main.tf`: `terraform { required_providers { aws={source="hashicorp/aws" version="~>5.0"} } } provider "aws" { region="ap-south-1" } resource "aws_vpc" "main" { cidr_block="10.0.0.0/16" } resource "aws_subnet" "public_a" { vpc_id=aws_vpc.main.id cidr_block="10.0.1.0/24" availability_zone="ap-south-1a" } resource "aws_subnet" "public_b" { vpc_id=aws_vpc.main.id cidr_block="10.0.2.0/24" availability_zone="ap-south-1b" } resource "aws_instance" "blue" { ami="ami-ubuntu" instance_type="t2.micro" subnet_id=aws_subnet.public_a.id tags={Name="blue-v1.0", Environment="blue"} } resource "aws_instance" "green" { ami="ami-ubuntu" instance_type="t2.micro" subnet_id=aws_subnet.public_b.id tags={Name="green-v2.0", Environment="green"} } resource "aws_lb" "main" { name="raviteja-92-alb" load_balancer_type="application" subnets=[aws_subnet.public_a.id, aws_subnet.public_b.id] } resource "aws_lb_target_group" "blue" { name="blue-tg" port=9999 protocol="HTTP" vpc_id=aws_vpc.main.id health_check={path="/health"} } resource "aws_lb_target_group" "green" { name="green-tg" port=9999 protocol="HTTP" vpc_id=aws_vpc.main.id health_check={path="/health"} } resource "aws_lb_listener" "main" { load_balancer_arn=aws_lb.main.arn port=80 protocol="HTTP" default_action={type="forward" forward={target_group=[{arn=aws_lb_target_group.blue.arn weight=100},{arn=aws_lb_target_group.green.arn weight=0}]}} }`
- 🔵🟢 Add Auto Switch via Lambda - Add `aws_lambda_function` triggered by `aws_cloudwatch_metric_alarm` green healthy + `aws_lambda` code `modify-listener weight blue 0 green 100` - Auto switch when green passes health checks - No manual `aws elbv2 modify-listener`
- 🔒 Add Blue-Green + Terraform Remote Backend - Store tfstate in S3 `raviteja-blue-green-state` with versioning + DynamoDB `terraform-locks-blue-green` lock - `terraform { backend "s3" { bucket="raviteja-blue-green-state" key="92/terraform.tfstate" region="ap-south-1" dynamodb_table="terraform-locks-blue-green" } }` - Prevents concurrent blue-green deploy
- 🌐 Add Route 53 Weighted Routing - Add `aws_route53_record` with `weighted_routing_policy` weight 100 blue + 0 green -> Switch to 0 blue 100 green - DNS level blue-green instead of ALB level - For global blue-green
- 📦 Add ECR + ECS Blue-Green via CodeDeploy - Add `aws_ecr_repository` + `aws_ecs_cluster` + `aws_ecs_task_definition` blue v1.0 + green v2.0 + `aws_codedeploy_app` + `aws_codedeploy_deployment_group` with `blue_green_deployment_config` + `deployment_style` `BLUE_GREEN` + `load_balancer_info` - ECS managed blue-green
- 🔐 Add Blue-Green + Feature Flags - Add `app-config` or `launchdarkly` feature flag `new-ui-enabled` false in blue + true in green - Deploy green with flag off -> Switch traffic 100% green but flag off so users see old UI -> Gradually enable flag 10% -> 100% -> Canary + Blue-Green together
- 📈 Add CloudWatch + Blue-Green Metrics - Add `aws_cloudwatch_metric_alarm` for Blue TG `UnhealthyHostCount >0` + Green TG `UnhealthyHostCount >0` + ALB `HTTPCode_Target_5XX_Count >0` + `TargetResponseTime >1s` - SNS alert + Lambda auto rollback to blue if green fails
- 🧪 Add Smoke Tests After Switch - Add `aws_codebuild_project` smoke tests `curl https://blue-green.raviteja.dev/health` + `curl https://blue-green.raviteja.dev/deploy/status` + Check activeEnv green + traffic green 100 - If fail -> Auto rollback
- 🏆 Final 100 - Add Blue-Green + All AWS Together - Project 100 - Use Terraform to create VPC + 2 AZ + ALB + Blue EC2 + Green EC2 + RDS + S3 + CloudWatch + Route 53 + One `terraform apply` creates full blue-green production + `aws elbv2 modify-listener` weight switch = Ultimate DevOps portfolio - 100/100

---

# 👨💻 Author

Ravi Teja

Java Full Stack Developer

100 Java Full Stack Projects Challenge

Project 92 / 100

Tier 9 – DevOps + Cloud Deployment – FINAL – TIER 9 COMPLETE 83-92

---

## ⭐ Support

If you found this project helpful, consider giving it a ⭐ Star on GitHub.

**Single Repo:** [92-blue-green-deploy](https://github.com/raviteja-dev950/92-blue-green-deploy)

**Live URL:** https://nine2-blue-green-deploy.onrender.com/

**Init URL:** https://nine2-blue-green-deploy.onrender.com/deploy/init -> Blue ACTIVE 100% Green INACTIVE 0% total 2

**Status URL:** https://nine2-blue-green-deploy.onrender.com/deploy/status -> activeEnv blue traffic blue 100 green 0 zeroDowntime true

**Switch URL:** https://nine2-blue-green-deploy.onrender.com/deploy/switch?target=green -> Traffic switched to GREEN - Zero Downtime Success! switchCount 1 downtime 0s

**Traffic URL:** https://nine2-blue-green-deploy.onrender.com/deploy/traffic -> activeEnv green blue 0 green 100 total 100 strategy Blue-Green 100-0

**Versions URL:** https://nine2-blue-green-deploy.onrender.com/deploy/versions -> 2 deployments HEALTHY blue + green

**Rollback URL:** https://nine2-blue-green-deploy.onrender.com/deploy/rollback -> Rollback to blue - Zero downtime safety

**Docker Image:** `92-blue-green-deploy:latest` on Render (or `ravitejadev950/92-blue-green-deploy:latest` on Docker Hub optional)

**GitHub Actions:** Ready for `Deploy via Blue-Green` - On Push to `main` via `aws elbv2 modify-listener` weight switch - Same as Project 90/91 but Blue-Green

**Secrets:** `EC2_HOST` + `EC2_USERNAME` + `EC2_SSH_KEY` + `ACTIVE_ENV` + `AWS_REGION` + `RDS_URL` (for real AWS)

**Blue-Green Strategy:** Blue 100% Green 0% -> Deploy Green 0% -> Test Green -> Switch Green 100% Blue 0% -> Monitor -> Rollback if needed -> Zero Downtime

**Trigger:** `git push origin main` -> Render auto deploy -> `https://nine2-blue-green-deploy.onrender.com/deploy/init` LIVE -> `https://nine2-blue-green-deploy.onrender.com/deploy/switch?target=green` Zero Downtime!

**Browser:** `GET /deploy/init` -> blue ACTIVE 100% -> `GET /deploy/status` -> activeEnv blue -> `GET /deploy/switch?target=green` -> GREEN Success! -> `GET /deploy/traffic` -> green 100 -> All 200 OK + Blue-Green flow

**Pull:** `git clone https://github.com/raviteja-dev950/92-blue-green-deploy.git`

**Tier 9:** 83 Docker (Dockerfile) + 84 EC2 (Deploy) + 85 GitHub Actions (CI/CD) + 86 S3 (Storage) + 87 RDS (Database) + 88 Secrets (Env) + 89 CloudWatch (Monitoring) + 90 Load Balanced App (ALB) + 91 Terraform Script (IaC) + 92 Blue-Green Deploy (Zero Downtime) = **TIER 9 COMPLETE 10/10 - 92/100 OVERALL**

**Next Tier 10:** 93 Rate Limited API + 94 URL Shortener Scale + 95 Chat WebSockets + 96 Notification Service + 97 Analytics Dashboard + 98 Search Elastic + 99 Payment Stripe + 100 Final Deploy - Scaling + Microservices Tier
