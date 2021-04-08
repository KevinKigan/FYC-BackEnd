package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.dropbox.core.DbxException;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public interface IFicherosService {
    HashMap<Integer, String> getURLMarcaLogo(int idMarca, boolean varias);
    HashMap<Integer, String> getURLModelosLogo(List<Integer> idsModelos);
    HashMap<Integer, String> getURLUsuario(List<Integer> idsUsuarios);
    Path getPath(String nameImage);
    boolean delete(String nameImage);
    String copy(MultipartFile file) throws IOException;
    Resource loadModelo(String nameImage) throws MalformedURLException;
    void setURLUsuario(Usuario idUsuario);
    Resource loadPropietario() throws MalformedURLException;
//    Resource load(String nameImage) throws MalformedURLException;
    String uploadFile(String area, MultipartFile file, String filename);
    Path downloadFile(String area, String filename) throws FileNotFoundException, DbxException, IOException;
}
