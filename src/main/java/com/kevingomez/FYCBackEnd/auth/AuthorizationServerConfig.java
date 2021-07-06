package com.kevingomez.FYCBackEnd.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${security.auth.clientId}")
    private String CLIENT_ID;
    @Value("${security.auth.clientPass}")
    private String CLIENT_PASS;
    @Value("${jwt.privateKey}")
    private String PRIVATE_KEY;
    @Value("${jwt.publicKey}")
    private String PUBLIC_KEY;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdditionalInfoToken additionalInfoToken;

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
        clients.inMemory().withClient(CLIENT_ID)                     // Nombre del FrontEnd
                .secret(passwordEncoder.encode(CLIENT_PASS))         // Password del FrontEnd
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
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(PRIVATE_KEY);
        jwtAccessTokenConverter.setVerifierKey(PUBLIC_KEY);
        return jwtAccessTokenConverter;
    }
}
