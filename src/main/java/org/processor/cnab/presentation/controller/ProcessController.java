package org.processor.cnab.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.processor.cnab.application.service.ProcessorServicer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("v1/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessorServicer processorServicer;

    @PostMapping("cnab")
    public ResponseEntity<String> processCNAB(@RequestParam("file") MultipartFile file) throws Exception {
        processorServicer.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("File processed successfully.");
    }



}
