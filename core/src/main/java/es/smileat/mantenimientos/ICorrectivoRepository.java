package es.smileat.mantenimientos;

import java.util.Collection;

public interface ICorrectivoRepository {
    /**
     * Guarda un mantenimiento en el repositorio
     * @param mantenimiento Correctivo a guardar
     */
    public void save(Correctivo mantenimiento) throws Exception;

    /**
     * Devuelve un mantenimiento del repositorio
     * @param id  ID del mantenimiento a devolver
     * @return   Correctivo con el ID indicado
     */
    public Correctivo load(String id) throws Exception;

    /**
     * Actualiza un mantenimiento en el repositorio
     * @param mantenimiento Correctivo a actualizar. Se busca por ID y se actualiza el resto de campos
     */
    public void update(Correctivo mantenimiento) throws Exception;

    /**
     * Borra un mantenimiento del repositorio
     * @param id   ID del mantenimiento a borrar
     */
    public void delete(String id) throws Exception;

    /**
     * Devuelve todos los mantenimientos del repositorio
     * @return Colección con todos los mantenimientos
     */
    public Collection<Correctivo> loadAll() throws Exception;
}
