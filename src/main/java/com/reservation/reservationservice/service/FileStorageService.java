package com.reservation.reservationservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    void init();

    void save(String directoryName, MultipartFile file);

    Resource load(String directoryName, String filename);

    void deleteAll();

    Stream<Path> loadAll(String directoryName);

}
