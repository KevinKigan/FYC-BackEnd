package com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces;

import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;


public interface IEmailService {
    boolean sendEmailVerificate(Usuario usuario, String tipo);

}
