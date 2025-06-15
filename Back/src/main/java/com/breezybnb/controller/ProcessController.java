package com.breezybnb.controller;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/start-reservation")
    public String startReservation() {
        // We haven’t got a process deployed yet, so this will log an error—but that’s OK
        runtimeService.startProcessInstanceByKey("reservation_process");
        return "Process start attempted";
    }
}
