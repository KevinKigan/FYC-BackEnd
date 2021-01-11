package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.nio.file.Path;

public interface ISubirFicheroService {
    Resource load(String nameImage) throws MalformedURLException;
    Path getPath(String nameImage);
}
