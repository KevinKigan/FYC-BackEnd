package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ISubirFicheroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SubirFicheroService implements ISubirFicheroService {
    private static Logger log = LoggerFactory.getLogger(SubirFicheroService.class);

    @Value("${images.logo}")
    private String LOGO_DIR;

    @Override
    public Resource load(String nameImage) throws MalformedURLException {
        Resource resource;
        HttpHeaders headers = new HttpHeaders();
        Path filePath = Paths.get("src/main/resources/static/images").resolve("fyclogo.png").toAbsolutePath();
        resource = new UrlResource(filePath.toUri());

        log.info(filePath.toString());
        return resource;
    }

    @Override
    public Path getPath(String nameImage) {
        return Paths.get(LOGO_DIR).resolve(nameImage).toAbsolutePath();
    }
}
