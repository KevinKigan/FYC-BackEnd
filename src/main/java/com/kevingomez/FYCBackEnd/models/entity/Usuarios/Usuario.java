package com.kevingomez.FYCBackEnd.models.entity.Usuarios;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 4,max = 20)
    @NotEmpty(message = "El campo Nombre de Usuario no puede estar vacio.")
    @Column(unique = true, length = 20)
    private String username;

    @NotEmpty(message = "El campo Contraseña no puede estar vacio.")
    @Size(min = 6,max = 60)
    @Column(length = 60)
    private String password;
    private String image;
    private Boolean enabled = false;
    private Boolean verified = false;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo Email no puede estar vacio.")
    @Email
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date")
    private Date registrationDate;

    /*
        Si quisieramos cambiar el nombre de la tabla o el de los campos, seria:
        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinTable(name = "user_auhorities", joinColumns=@JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // Si se elimina al usuario, se eliminan sus roles e igual para la creacion
    //@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"),uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
    private List<Rol> roles;

    public Usuario() {
    }

    @PrePersist
    public void prePersist(){
        registrationDate = new Date();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
