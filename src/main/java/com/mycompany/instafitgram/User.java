package com.mycompany.instafitgram;

/**
 *
 * @author Manu
 */
public class User {

    private int id;
    private String nom;
    private String email;
    private String passwordHash;
    private byte[] foto;
    private boolean isIntructor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isIsIntructor() {
        return isIntructor;
    }

    public void setIsIntructor(boolean isIntructor) {
        this.isIntructor = isIntructor;
    }
    
    @Override
    public String toString() {
        return ""+nom+" // "+email;
    }

}
