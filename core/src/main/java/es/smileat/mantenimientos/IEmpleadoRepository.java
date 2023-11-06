package es.smileat.mantenimientos;

import java.util.Collection;

public interface IEmpleadoRepository {
    /**
     * Guarda un empleado en el repositorio
     * @param empleado  Empleado a guardar
     */
    void save(Empleado empleado) throws Exception;

    /**
     * Devuelve un empleado del repositorio
     * @param nombre   Nombre del empleado a devolver
     * @return         Empleado con el nombre indicado
     */
    Empleado load(String nombre) throws Exception;

    /**
     * Devuelve todos los empleados del repositorio
     * @return Colección con todos los empleados
     */
    Collection<Empleado> loadAll() throws Exception;

    /**
     * Actualiza un empleado en el repositorio
     * @param empleado Empleado a actualizar. Se busca por nombre (ID) y se actualiza el resto de campos
     */
    void update(Empleado empleado) throws Exception;

    /**
     * Borra un empleado del repositorio
     * @param nombre   Nombre del empleado a borrar
     */
    void delete(String nombre) throws Exception;
}
