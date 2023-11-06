package es.smileat.mantenimientos;

import lombok.Getter;
import lombok.Setter;

public class Supervisor extends Usuario {
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String password;

    //Default Constructor
    public Supervisor() {
    }

    //Constructor
    public Supervisor(String nombre, String password) {
        this.nombre = nombre;
        this.password = cifrar(password);
    }

    //Password Setter
    public void setPasswordCifrada(String password) {
        this.password = cifrar(password);
    }
}
