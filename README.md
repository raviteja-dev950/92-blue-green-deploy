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
│   ├── demo4.png (GET /deploy/blue?version=v1.1 - Deploy blue v1.1 ACTIVE 100%)
│   ├── demo5.png (GET /deploy/green?version=v2.1 - Deploy green v2.1 INACTIVE 0%)
│   ├── demo6.png (GET /deploy/switch?target=green - Traffic switched to GREEN Success switchCount 1)
│   ├── demo7.png (GET /deploy/status after switch - activeEnv green blue 0 green 100 switchCount 1)
│   ├── demo8.png (GET /deploy/traffic - activeEnv green blue 0 green 100 total 100)
│   ├── demo9.png (GET /deploy/versions - 2 HEALTHY blue INACTIVE + green ACTIVE)
│   ├── demo10.png (GET /deploy/rollback - Rollback to BLUE Success switchCount 2)
│   ├── demo11.png (GET /deploy/status after rollback - activeEnv blue back)
│   └── demo12.png (Render Live - Deploy succeeded LIVE Tier 9 Complete)
│
├── Dockerfile
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# 📸 Screenshots - 12 Demos - Blue-Green Zero Downtime Verified

### Demo 1 – Browser - GET / - Home Live Blue-Green Zero Downtime activeEnv blue tier 9
[Demo1 Home](screenshots/demo1.png)

*GET http://localhost:9999/ - Browser JSON - 200 OK - Returns {"project":"92-blue-green-deploy","status":"Live - Blue-Green Zero Downtime","activeEnv":"blue","traffic":{"blue":0,"green":0},"totalDeployments":0,"owner":"Raviteja","tier":"9 - DevOps + Cloud - Blue-Green","endpoints":[...10 endpoints]} - Proof of Home Live - Portfolio ready - Fixed blue\n bug*

### Demo 2 – Browser - GET /deploy/init - Blue ACTIVE 100% Green INACTIVE 0% total 2 switchCount 0
[Demo2 Init](screenshots/demo2.png)

*GET http://localhost:9999/deploy/init - Browser JSON - 200 OK - Returns {"greenStatus":"INACTIVE","switchCount":0,"totalDeployments":2,"blueStatus":"ACTIVE","zeroDowntime":true,"blueVersion":"v1.0","greenVersion":"v2.0","strategy":"Blue-Green Deployment - 0 Downtime","activeEnv":"blue","traffic":{"green":0,"blue":100}} - Proof of Blue-Green Init - Creates 2 deployments - Blue v1.0 ACTIVE 100% + Green v2.0 INACTIVE 0% - Same as terraform apply blue + green*

### Demo 3 – Browser - GET /deploy/status - activeEnv blue traffic blue 100 green 0 zeroDowntime true
[Demo3 Status](screenshots/demo3.png)

*GET http://localhost:9999/deploy/status - Browser JSON - 200 OK - Returns {"greenStatus":"INACTIVE","switchCount":0,"totalDeployments":2,"blueStatus":"ACTIVE","zeroDowntime":true,"blueVersion":"v1.0","greenVersion":"v2.0","strategy":"Blue-Green Deployment - 0 Downtime","activeEnv":"blue","traffic":{"green":0,"blue":100}} - Proof of Status - activeEnv blue - traffic 100-0 - zeroDowntime true - blueVersion v1.0 greenVersion v2.0*

### Demo 4 – Browser - GET /deploy/blue?version=v1.1 - Deploy Blue v1.1 ACTIVE 100%
[Demo4 Deploy Blue](screenshots/demo4.png)

*GET http://localhost:9999/deploy/blue?version=v1.1 - Browser JSON - 200 OK - Returns {"id":1,"environment":"blue","version":"v1.1","status":"ACTIVE","trafficWeight":100,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"} - Proof of Deploy Blue - Updates blue to v1.1 - Keeps ACTIVE 100% if blue active - Simulates blue EC2 deploy*

### Demo 5 – Browser - GET /deploy/green?version=v2.1 - Deploy Green v2.1 INACTIVE 0%
[Demo5 Deploy Green](screenshots/demo5.png)

*GET http://localhost:9999/deploy/green?version=v2.1 - Browser JSON - 200 OK - Returns {"id":2,"environment":"green","version":"v2.1","status":"INACTIVE","trafficWeight":0,"instanceCount":1,"health":"HEALTHY"} - Proof of Deploy Green - Updates green to v2.1 - Keeps INACTIVE 0% if blue active - Test green internally before switch*

### Demo 6 – Browser - GET /deploy/switch?target=green - Traffic switched to GREEN Zero Downtime Success switchCount 1 downtime 0s
[Demo6 Switch Green](screenshots/demo6.png)

*GET http://localhost:9999/deploy/switch?target=green - Browser JSON - 200 OK - Returns {"activeEnv":"green","previousEnv":"blue","switchCount":1,"message":"Traffic switched to GREEN - Zero Downtime Success!","traffic":{"green":100,"blue":0},"downtime":"0s - Blue-Green ensures no downtime"} - Proof of Zero Downtime Switch - activeEnv green - previousEnv blue - switchCount 1 - downtime 0s - ALB listener rule weight switch simulation*

### Demo 7 – Browser - GET /deploy/status after switch - activeEnv green blue 0 green 100 switchCount 1
[Demo7 Status Green](screenshots/demo7.png)

*GET http://localhost:9999/deploy/status after switch - Browser JSON - 200 OK - Returns {"strategy":"Blue-Green Deployment - 0 Downtime","greenVersion":"v2.0","blueVersion":"v1.0","zeroDowntime":true,"blueStatus":"INACTIVE","totalDeployments":2,"switchCount":1,"greenStatus":"ACTIVE","traffic":{"blue":0,"green":100},"activeEnv":"green"} - Proof of Status after switch - activeEnv green - traffic blue 0 green 100 - switchCount 1 - Zero downtime maintained - Blue INACTIVE Green ACTIVE*

### Demo 8 – Browser - GET /deploy/traffic - activeEnv green blue 0 green 100 total 100 strategy 100-0
[Demo8 Traffic](screenshots/demo8.png)

*GET http://localhost:9999/deploy/traffic - Browser JSON - 200 OK - Returns {"strategy":"Blue-Green 100-0","green":100,"total":100,"activeEnv":"green","blue":0} - Proof of Traffic - After switch green 100% blue 0% - total 100 - strategy Blue-Green 100-0 - ALB weight simulation*

### Demo 9 – Browser - GET /deploy/versions - 2 deployments HEALTHY blue INACTIVE green ACTIVE
[Demo9 Versions](screenshots/demo9.png)

*GET http://localhost:9999/deploy/versions - Browser JSON - 200 OK - Returns [{"id":1,"environment":"blue","version":"v1.0","status":"INACTIVE","trafficWeight":0,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"},{"id":2,"environment":"green","version":"v2.0","status":"ACTIVE","trafficWeight":100,"instanceCount":1,"deployedAt":"2026-10-27T19:08:52.925906","health":"HEALTHY"}] - Proof of Versions - 2 deployments HEALTHY - Blue INACTIVE v1.0 traffic 0 + Green ACTIVE v2.0 traffic 100 - Instance count 1 each - Health HEALTHY*

### Demo 10 – Browser - GET /deploy/rollback - Rollback to BLUE Zero Downtime Success switchCount 2
[Demo10 Rollback](screenshots/demo10.png)

*GET http://localhost:9999/deploy/rollback - Browser JSON - 200 OK - Returns {"traffic":{"blue":100,"green":0},"message":"Traffic switched to BLUE - Zero Downtime Success!","switchCount":2,"previousEnv":"green","activeEnv":"blue","downtime":"0s - Blue-Green ensures no downtime"} - Proof of Rollback - If green has issue, rollback to blue - activeEnv blue again - switchCount 2 - Zero downtime rollback - Production safety - previousEnv green*

### Demo 11 – Browser - GET /deploy/status after rollback - activeEnv blue blue 100 green 0 switchCount 2
[Demo11 Status Blue After Rollback](screenshots/demo11.png)

*GET http://localhost:9999/deploy/status after rollback - Browser JSON - 200 OK - Returns {"activeEnv":"blue","traffic":{"blue":100,"green":0},"blueVersion":"v1.0","greenVersion":"v2.0","switchCount":2,"zeroDowntime":true,"strategy":"Blue-Green Deployment - 0 Downtime"} - Proof of Status after rollback - activeEnv blue back - traffic blue 100 green 0 - switchCount 2 - Zero downtime rollback verified*

### Demo 12 – Render Live - Deploy Succeeded - LIVE - TIER 9 COMPLETE 92/100
[Demo12 Render](screenshots/demo12.png)

*Render Dashboard - 92-blue-green-deploy - STATUS: Deploy succeeded | Live - DURATION: 2m15s - DEPLOYED: Oct 27, 2026 at 7:15:05 PM GMT+5:30 - TRIGGER: Manual Deploy - SOURCE: commit xxxxxxx - Logs: Tomcat started on port 9999 - Started BlueGreenApplication in 11.275 seconds (process running for 12.007) - Your service is live - Available at your primary URL https://nine2-blue-green-deploy.onrender.com - Proof of Live Deployment on Internet - Docker Multi-Stage + Blue-Green Simulation working on Render - TIER 9 COMPLETE 83-92 92/100 - Final Project of Tier 9*

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

## 3⃣ Run in Eclipse IDE (No Docker Needed)

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

## 4⃣ Test with Browser - 12 Endpoints

- GET / -> Home Live
- GET /deploy/init -> Blue ACTIVE 100%
- GET /deploy/status -> Blue 100
- GET /deploy/blue?version=v1.1 -> Deploy Blue
- GET /deploy/green?version=v2.1 -> Deploy Green
- GET /deploy/switch?target=green -> GREEN Success!
- GET /deploy/status -> Green ACTIVE
- GET /deploy/traffic -> Green 100
- GET /deploy/versions -> 2 HEALTHY
- GET /deploy/rollback -> BLUE Success switchCount 2
- GET /deploy/status -> Blue back
- Render Live

## 5⃣ Docker Build & Run

```bash
docker build -t 92-blue-green-deploy .
docker run -d -p 9999:9999 --name bg-app 92-blue-green-deploy
docker ps
docker logs bg-app
curl http://localhost:9999/deploy/init
curl http://localhost:9999/deploy/switch?target=green
```

## 6⃣ Deploy to Render (Docker) - Live URL - TIER 9 COMPLETE

```text
Runtime: Docker
Port: 9999
Env Vars:
  PORT = 9999
  ACTIVE_ENV = blue
  AWS_REGION = ap-south-1
```

---

# 🧪 API Testing Examples

```bash
curl http://localhost:9999/
curl http://localhost:9999/deploy/init
curl http://localhost:9999/deploy/status
curl http://localhost:9999/deploy/switch?target=green
curl http://localhost:9999/deploy/traffic
curl http://localhost:9999/deploy/versions
curl http://localhost:9999/deploy/rollback
curl https://nine2-blue-green-deploy.onrender.com/deploy/init
```

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

**Tier 9:** 83 Docker + 84 EC2 + 85 GitHub Actions + 86 S3 + 87 RDS + 88 Secrets + 89 CloudWatch + 90 Load Balanced App + 91 Terraform Script + 92 Blue-Green Deploy = **TIER 9 COMPLETE 10/10 - 92/100 OVERALL**

**Next Tier 10:** 93 Rate Limited API + 94 URL Shortener Scale + 95 Chat WebSockets + 96 Notification Service + 97 Analytics Dashboard + 98 Search Elastic + 99 Payment Stripe + 100 Final Deploy
