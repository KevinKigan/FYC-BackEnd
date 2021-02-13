package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.UploadFileServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService implements UploadFileServiceInterface {

    private static Logger log = LoggerFactory.getLogger(UploadFileService.class);

    @Value("${uploadPath.Image}")
    private String UPLOAD_DIR;

    @Value("${propietario.Image}")
    private String PROPIETARIO_NAME;

    private static String tipo;

    @Override
    public Resource load(String nameImage) throws MalformedURLException {
        Resource resource;
        HttpHeaders headers = new HttpHeaders();
        Path filePath = this.getPath(nameImage);
        resource = new UrlResource(filePath.toUri());
        if (!resource.exists() && !resource.isReadable()) {
            filePath = Paths.get("src/main/resources/static/images").resolve("defaultImage.jpg").toAbsolutePath();
            resource = new UrlResource(filePath.toUri());
        }
        log.info(filePath.toString());
        return resource;
    }
    @Override
    public Resource loadPropietario() throws MalformedURLException {
        tipo = "usuarios";
        return load(PROPIETARIO_NAME);
    }
    @Override
    public Resource loadModelo(String nameImage) throws MalformedURLException {
        tipo = "modelos";
        return load(nameImage);
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(" ", "");
        Path filePath = getPath(fileName);
        log.info(filePath.toString());
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    @Override
    public boolean delete(String nameImage) {
        if(nameImage != null && nameImage.length() > 0){
            Path lastfilePath = getPath(nameImage);
            File lastFile = lastfilePath.toFile();
            if(lastFile.exists() && lastFile.canRead()){
                lastFile.delete();
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nameImage) {
        if(tipo.equals("usuarios")){
            return Paths.get(UPLOAD_DIR+"usuarios").resolve(nameImage).toAbsolutePath();
        }else if(tipo.equals("modelos")){
            return Paths.get(UPLOAD_DIR+"modelos").resolve(nameImage).toAbsolutePath();
        }else{
            return null;
        }
    }




}
