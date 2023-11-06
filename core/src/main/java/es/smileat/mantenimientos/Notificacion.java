package es.smileat.mantenimientos;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacion {
    private String id;
    private LocalDate fecha;
    private String descripcion;

    //Default Constructor
    public Notificacion() {
    }

    //Constructor
    public Notificacion(LocalDate fecha, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
}
