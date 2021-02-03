package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface UploadFileServiceInterface {

    Resource load(String nameImage) throws MalformedURLException;
    Resource loadModelo(String nameImage) throws MalformedURLException;
    Resource loadPropietario() throws MalformedURLException;
    String copy(MultipartFile file) throws IOException;
    boolean delete(String nameImage);
    Path getPath(String nameImage);
}
