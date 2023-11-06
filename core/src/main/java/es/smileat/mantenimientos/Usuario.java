package es.smileat.mantenimientos;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class Usuario {
    public abstract String getNombre();
    public abstract String getPassword();    //Cifrada
    public abstract void setPasswordCifrada(String password); //Cifrada
    public abstract void setNombre(String nombre);

    protected String cifrar(String toCifrar){
        return DigestUtils.sha256Hex(toCifrar);
    }

    public boolean check(String password){
        return getPassword().equals(cifrar(password));
    }
}
