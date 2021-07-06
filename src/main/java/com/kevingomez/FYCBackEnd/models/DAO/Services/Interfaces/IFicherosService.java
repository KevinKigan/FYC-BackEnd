package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.dropbox.core.DbxException;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Modelo;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public interface IFicherosService {
    HashMap<Integer, String> getURLMarcaLogo(int idMarca, boolean varias);
    HashMap<Integer, String> getURLModelosLogo(List<Integer> idsModelos);
    HashMap<Integer, String> getURLUsuario(List<Integer> idsUsuarios);
    void setURLUsuario(Usuario idUsuario);
    String uploadFile(String area, MultipartFile file, String filename, Modelo modelo);
    Path downloadFile(String area, String filename) throws DbxException, IOException;

    void setURLMarca(Marca marca);
    void setURLModelo(Modelo modelo, String filename);
}
