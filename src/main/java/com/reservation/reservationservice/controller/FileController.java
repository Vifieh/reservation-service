package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.FileInfo;
import com.reservation.reservationservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin()
@RestController
public class FileController {

    @Autowired
    FileStorageService fileStorageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "protected/upload/{directory}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> uploadFiles(@PathVariable String directory, @RequestPart("files")MultipartFile[] files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                fileStorageService.save(directory, file);
                fileNames.add(file.getOriginalFilename());
            });
            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("public/files")
    public ResponseEntity<List<FileInfo>> getListFiles(@RequestParam String directory) {
        List<FileInfo> fileInfos = fileStorageService.loadAll(directory).map(path -> {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
                .fromMethodName(FileController.class, "getFile", directory, path.getFileName().toString()).build().toString();
        return new FileInfo(filename, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("public/files/{directory}/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String directory, @PathVariable String filename) {
        Resource file = fileStorageService.load(directory, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
