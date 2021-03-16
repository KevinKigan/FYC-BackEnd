package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Verificacion;

import java.util.ArrayList;

public interface IEmailService {
    boolean sendEmailVerificate(Usuario usuario, String tipo);

}
