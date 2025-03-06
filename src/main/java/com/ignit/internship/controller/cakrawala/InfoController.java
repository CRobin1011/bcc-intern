package com.ignit.internship.controller.cakrawala;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.cakrawala.InfoRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.cakrawala.Info;
import com.ignit.internship.service.cakrawala.InfoService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<Info>> createInfo(@RequestBody InfoRequest request) throws IdNotFoundException, IOException {
        return ResponseEntity.ok().body(DefaultResponse.success(infoService.createInfo(request)));
    }
    
    @GetMapping("/{name}")
    public ResponseEntity<DefaultResponse<Info>> getInfo(@PathVariable String name) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(infoService.getInfo(name)));
    }
    
}
