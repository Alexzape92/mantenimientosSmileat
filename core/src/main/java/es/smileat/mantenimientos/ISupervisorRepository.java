package es.smileat.mantenimientos;

import java.util.Collection;

public interface ISupervisorRepository {
    /**
     * Guarda un Supervisor en el repositorio
     * @param Supervisor  Supervisor a guardar
     */
    void save(Supervisor Supervisor) throws Exception;

    /**
     * Devuelve un Supervisor del repositorio
     * @param nombre   Nombre del Supervisor a devolver
     * @return         Supervisor con el nombre indicado
     */
    Supervisor load(String nombre) throws Exception;

    /**
     * Devuelve todos los Supervisors del repositorio
     * @return Colección con todos los Supervisors
     */
    Collection<Supervisor> loadAll() throws Exception;

    /**
     * Actualiza un Supervisor en el repositorio
     * @param Supervisor Supervisor a actualizar. Se busca por nombre (ID) y se actualiza el resto de campos
     */
    void update(Supervisor Supervisor) throws Exception;

    /**
     * Borra un Supervisor del repositorio
     * @param nombre   Nombre del Supervisor a borrar
     */
    void delete(String nombre) throws Exception;
}
