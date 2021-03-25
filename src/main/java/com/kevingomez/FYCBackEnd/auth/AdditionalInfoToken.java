package com.kevingomez.FYCBackEnd.auth;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdditionalInfoToken implements TokenEnhancer {

    @Autowired
    IUsuariosService usuariosService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        Usuario usuario = usuariosService.findByUsername(oAuth2Authentication.getName());
        info.put("username",usuario.getUsername());
        info.put("email",usuario.getEmail());
        info.put("enabled",usuario.getEnabled());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
