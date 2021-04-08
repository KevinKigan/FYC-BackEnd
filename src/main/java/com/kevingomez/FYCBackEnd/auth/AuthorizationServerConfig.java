package com.kevingomez.FYCBackEnd.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdditionalInfoToken additionalInfoToken;

    private final Properties propiedades = new Properties();

    /**
     * Metodo para restringir las llamadas dependiendo de los permisos que tenga el usuario
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()"); // Verifica el token y la firma: /oauth/check_token
    }

    /**
     * Metodo para registrar el cliente (FrontEnd)
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        propiedades.load(new FileReader(String.valueOf(Paths.get("src/main/resources/")
                .resolve("security.properties").toAbsolutePath())));
        String clientId = propiedades.getProperty("security.auth.clientId");
        String clientPass = propiedades.getProperty("security.auth.clientPass");
        clients.inMemory().withClient(clientId)                     // Nombre del FrontEnd
                .secret(passwordEncoder.encode(clientPass))         // Password del FrontEnd
                .scopes("read","write")                             // Tipo de accesos permitidos
                .authorizedGrantTypes("password", "refresh_token")  // Como se va a autorizar
                .accessTokenValiditySeconds(14400)                  // Tiempo de validez del token
                .refreshTokenValiditySeconds(14400);                // Tiempo en el que se tiene que
                                                                    // refrescar el token

    }

    /**
     * Metodo para configurar el endpoint del authorization server
     * y manejar el proceso de autentificacion y validación del token
     * Registra el authentication manager y el accesTokenConverter
     * que almacena los datos de autentificacion del usuario
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(additionalInfoToken, accessTokenConverter()));
        endpoints.authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    /**
     * Metodo para crear el accessTokenConverter
     * espeficicamente de tipo JWT
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws IOException {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        propiedades.load(new FileReader(String.valueOf(Paths.get("src/main/resources/")
                .resolve("security.properties").toAbsolutePath())));
        String privateKey = propiedades.getProperty("jwt.privateKey");
        String publicKey  = propiedades.getProperty("jwt.publicKey");
        jwtAccessTokenConverter.setSigningKey(privateKey);
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }
}
