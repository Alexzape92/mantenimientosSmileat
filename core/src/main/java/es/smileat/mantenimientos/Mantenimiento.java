package es.smileat.mantenimientos;

import java.time.LocalDate;

public interface Mantenimiento {
    public String getId();
    public void setId(String id);

    public Maquina getMaquina();
    public void setMaquina(Maquina maquina);

    public int getTiempo();
    public void setTiempo(int tiempo);

    public String getComentarios();
    public void setComentarios(String comentarios);

    public Usuario getEmpleado();
    public void setEmpleado(Usuario empleado);

    public Supervisor getSupervisor();
    public void setSupervisor(Supervisor supervisor);

    public ResultadoType getResultado();
    public void setResultado(ResultadoType resultado);
    
    public LocalDate getFecha();
    public void setFecha(LocalDate fecha);
}
