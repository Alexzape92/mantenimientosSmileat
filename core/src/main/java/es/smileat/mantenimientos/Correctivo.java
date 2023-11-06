package es.smileat.mantenimientos;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Correctivo implements Mantenimiento {
    private String id;
    private LocalDate fecha;
    private ResultadoType resultado;
    private String comentarios;
    private Usuario empleado;
    private Supervisor supervisor;
    private int tiempo;
    private Maquina maquina;
    

    //Default Constructor
    public Correctivo() {
    }

    //Constructor generando el id
    public Correctivo(LocalDate fecha, ResultadoType resultado, String comentarios, Usuario empleado, Supervisor supervisor, int tiempo, Maquina maquina) {
        this.id = UUID.randomUUID().toString();
        this.fecha = fecha;
        this.resultado = resultado;
        this.comentarios = comentarios;
        this.empleado = empleado;
        this.supervisor = supervisor;
        this.tiempo = tiempo;
        this.maquina = maquina;
    }

    //Constructor con id
    public Correctivo(String id, LocalDate fecha, ResultadoType resultado, String comentarios, Usuario empleado, Supervisor supervisor, int tiempo, Maquina maquina) {
        this.id = id;
        this.fecha = fecha;
        this.resultado = resultado;
        this.comentarios = comentarios;
        this.empleado = empleado;
        this.supervisor = supervisor;
        this.tiempo = tiempo;
        this.maquina = maquina;
    }
}
