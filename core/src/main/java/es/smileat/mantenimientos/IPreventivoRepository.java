package es.smileat.mantenimientos;

import java.util.Collection;

public interface IPreventivoRepository {
    /**
     * Guarda un mantenimiento en el repositorio
     * @param mantenimiento Preventivo a guardar
     */
    public void save(Preventivo mantenimiento) throws Exception;

    /**
     * Devuelve un mantenimiento del repositorio
     * @param id  ID del mantenimiento a devolver
     * @return   Preventivo con el ID indicado
     */
    public Preventivo load(String id) throws Exception;

    /**
     * Actualiza un mantenimiento en el repositorio
     * @param mantenimiento Preventivo a actualizar. Se busca por ID y se actualiza el resto de campos
     */
    public void update(Preventivo mantenimiento) throws Exception;

    /**
     * Borra un mantenimiento del repositorio
     * @param id   ID del mantenimiento a borrar
     */
    public void delete(String id) throws Exception;

    /**
     * Devuelve todos los mantenimientos del repositorio
     * @return Colección con todos los mantenimientos
     */
    public Collection<Preventivo> loadAll() throws Exception;
}
