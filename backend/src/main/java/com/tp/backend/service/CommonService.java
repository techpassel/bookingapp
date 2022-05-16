package com.tp.backend.service;

import exception.BackendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonService {
    public String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return "." + fileName.substring(i + 1);
        } else {
            throw new BackendException("File doesn't have any extension.");
        }
    }
}
