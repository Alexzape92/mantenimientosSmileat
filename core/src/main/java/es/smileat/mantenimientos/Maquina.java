package es.smileat.mantenimientos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Maquina {
    //Atributos
    private String id;
    private String modelo;
    private int incidencias;
    private String sala;

    //Default Constructor
    public Maquina() {
    }

    //Constructor
    public Maquina(String id, String modelo, int incidencias, String sala) {
        this.id = id;
        this.modelo = modelo;
        this.incidencias = incidencias;
        this.sala = sala;
    }
}
