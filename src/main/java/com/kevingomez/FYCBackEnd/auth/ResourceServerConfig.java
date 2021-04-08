package com.kevingomez.FYCBackEnd.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${cors.url}")
    private String corsUrl;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET,
                "/api/modelos/**",
                "/api/coches/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/img/modeloslogo","/api/coches/precios").permitAll()
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }

    /**
     * Metodo para manejar las peticiones de origen
     * cruzado (CORS) sobre el cliente
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configurationSource = new CorsConfiguration();
        configurationSource.setAllowedOrigins(Arrays.asList(corsUrl));
        /* Se añade options ya que en algunos navegadores, al hacer la peticion para recibir el token
           se envia como options en ves de post */
        configurationSource.setAllowedMethods(Arrays.asList("GET","PUT","POST","DELETE","OPTIONS"));
        configurationSource.setAllowCredentials(true);
        configurationSource.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new  UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configurationSource);
        return source;
    }

    /**
     * Metodo para crear un filtro de cors y registrar la configuracion,
     * que se apliqua al servidor de autentificacion cuando se accede a
     * los endpoint para la autentificacion y generar el token y la
     * validacion de nuestro token
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
