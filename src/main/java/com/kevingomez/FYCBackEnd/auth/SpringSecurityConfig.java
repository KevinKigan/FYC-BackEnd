package com.kevingomez.FYCBackEnd.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("usuariosService")
    @Autowired
    UserDetailsService usuarioService;

    /**
     * Metodo para registrar en el authentication manager
     * de spring security el servicio para autenticar
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception
     */
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
    }

    /**
     * Metodo para declarar el tipo de encriptado de la contraseña
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    //Mediante la anotacion bean, se registra el objeto que retorna el metodo passwordEncoder
    // de esta manera se puede inyectar con autowired y utilizar en cualquier clase
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Metodo para retornar el authentication maneger que se necesita en la
     * configuracion del OAuth2 que implementa el proceso de login
     *
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * Metodo para deshabilitar el csrf (falsificacion de peticion en sitios cruzados)
     * que protege nuestro formulario a traves de un token
     * ya que la seguridad en el login la hacemos en el lado de Angular
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Manejo de sesion deshabilitado por el lado de spring
    }
}
