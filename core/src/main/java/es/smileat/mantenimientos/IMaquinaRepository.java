package es.smileat.mantenimientos;

import java.util.Collection;

public interface IMaquinaRepository {
    /**
     * Guarda una maquina en el repositorio
     * @param maquina Maquina a guardar
     */
    public void save(Maquina maquina) throws Exception;

    /**
     * Actualiza una maquina en el repositorio
     * @param maquina Maquina a actualizar. Se busca por nombre (ID) y se actualiza el resto de campos
     */
    public void update(Maquina maquina) throws Exception;

    /**
     * Borra una maquina del repositorio
     * @param id   ID de la maquina a borrar
     */
    public void delete(String id) throws Exception;

    /**
     * Devuelve una maquina del repositorio
     * @param id   ID de la maquina a devolver
     * @return     Maquina con el ID indicado
     */
    public Maquina load(String id) throws Exception;

    /**
     * Devuelve todas las maquinas del repositorio
     * @return Colección con todas las maquinas
     */
    public Collection<Maquina> loadAll() throws Exception;
}
