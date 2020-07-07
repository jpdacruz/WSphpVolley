package com.example.implementacionws.entity;

public class User {

    private Integer documento;
    private String nombreUser;
    private String profesion;
    private String password;

    public User(Integer documento, String nombreUser, String profesion, String password) {
        this.documento = documento;
        this.nombreUser = nombreUser;
        this.profesion = profesion;
        this.password = password;

    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "documento=" + documento +
                ", nombreUser='" + nombreUser + '\'' +
                ", profesion='" + profesion + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
