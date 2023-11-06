package es.smileat.mantenimientos;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreventivoType {
    private String id;
    private Maquina maquina;
    private int frecuencia;
    private int tiempo;
    private String descripcion;

    public PreventivoType() {
    }

    public PreventivoType(String id, Maquina maquina, int frecuencia, int tiempo, String descripcion) {
        this.id = id;
        this.maquina = maquina;
        this.frecuencia = frecuencia;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
    }

    public PreventivoType(Maquina maquina, int frecuencia, int tiempo, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.maquina = maquina;
        this.frecuencia = frecuencia;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PreventivoType)) {
            return false;
        }
        PreventivoType preventivoType = (PreventivoType) o;
        return id.equals(preventivoType.id);
    }
}
