INSERT INTO roles (rol_name) VALUES('ROLE_ADMIN');
INSERT INTO roles (rol_name) VALUES('ROLE_USER');
INSERT INTO `usuarios` (`username`,`password`,`enabled`,`email`) VALUES ('nose','$2a$10$ZSXc0QXlhJ.fWzBjPcuWse8d29y4aeJyxDa.7m2ntquiDIBQCa54i',true,'kevingomez.a98asdf@gmail.com');
INSERT INTO `usuarios_roles` (`usuario_id`,`rol_id`) VALUES (1,1);
