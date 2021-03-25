package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IEmailService;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Verificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class EmailService implements IEmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private UsuariosService usuariosService;

    private final Properties propiedades = new Properties();


    @Override
    public boolean sendEmailVerificate(Usuario usuario, String tipo) {
        try {
            propiedades.load(new FileReader(String.valueOf(Paths.get("src/main/resources/").resolve("mail.properties").toAbsolutePath())));
            String header = propiedades.getProperty("mail.cabezeraVerificacion");
            String headerEnd = propiedades.getProperty("mail.cabezeraVerificacionCierre");
            String saludo = propiedades.getProperty("mail.saludo");
            String cierreSaludo = propiedades.getProperty("mail.cierreSaludo");
            String footer = propiedades.getProperty("mail.piePagina");
            String bodyEnd = propiedades.getProperty("mail.verificacion.finCuerpo");
            String body="";
            String title="";
            String subject="";
            String message = "";
            if (tipo.equals("verifyNewUser")) {
                body    = propiedades.getProperty("mail.verificacion.cuerpo");
                subject = "Verificación de usuario";
                title = "Verificación";
                }else if(tipo.equals("forgottenPassword")) {
                body    = propiedades.getProperty("mail.verificacionNewPassword.cuerpo");
                subject = "Solicitud de Nueva Contraseña";
                title = "Nueva Contraseña";
            }
            StringBuilder rand = new StringBuilder(String.valueOf((int) (Math.random() * 999998) + 1));
            while (rand.length() < 6) {
                rand.insert(0, "0");
            }
            message = header + title + headerEnd + saludo + usuario.getUsername() + "," + cierreSaludo + body + rand + bodyEnd + footer;
//            byte[] response = message.getBytes(StandardCharsets.ISO_8859_1);
//            message = new String(response, StandardCharsets.ISO_8859_1); // Cambiamos de codificacion para acentos y caracteres especiales
            log.info("Verificacion del usuario: " + usuario.getUsername());
            usuariosService.addVerificacionEnProceso(usuario.getId(), new Verificacion(usuario.getId(), rand.toString()));
            return sendEmailTool(message, usuario.getEmail(), subject, usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean sendEmailTool(String textMessage, String email, String subject, Usuario user) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;
            log.info("Mail enviado a " + user.getUsername());
        } catch (MessagingException e) {
            log.error("Hubo un error al enviar el mail: ", e);
        }
        return send;
    }


}
