package com.raviteja.bluegreen.controller;

import com.raviteja.bluegreen.entity.Deployment;
import com.raviteja.bluegreen.service.BlueGreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/deploy")
public class DeploymentController {

    @Autowired
    private BlueGreenService service;

    @GetMapping("/init")
    public Map<String, Object> init() {
        return service.init();
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        return service.status();
    }

    @RequestMapping(value = "/blue", method = {RequestMethod.GET, RequestMethod.POST})
    public Deployment deployBlue(@RequestParam(defaultValue = "v1.1") String version) {
        return service.deployBlue(version);
    }

    @RequestMapping(value = "/green", method = {RequestMethod.GET, RequestMethod.POST})
    public Deployment deployGreen(@RequestParam(defaultValue = "v2.1") String version) {
        return service.deployGreen(version);
    }

    @RequestMapping(value = "/switch", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> switchEnv(@RequestParam(defaultValue = "green") String target) {
        return service.switchEnv(target);
    }

    @GetMapping("/traffic")
    public Map<String, Object> traffic() {
        return service.traffic();
    }

    @GetMapping("/versions")
    public List<Deployment> versions() {
        return service.getAll();
    }

    @GetMapping("/rollback")
    public Map<String, Object> rollback() {
        String prev = service.status().get("activeEnv").toString().equals("blue") ? "green" : "blue";
        return service.switchEnv(prev);
    }
}