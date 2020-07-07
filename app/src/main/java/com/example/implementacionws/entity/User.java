package com.example.implementacionws.entity;

public class User {

    private Integer documento;
    private String nombreUser;
    private String profesion;

    public User(Integer documento, String nombreUser, String profesion) {
        this.documento = documento;
        this.nombreUser = nombreUser;
        this.profesion = profesion;
    }

    public User() {
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
}
