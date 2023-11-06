package es.smileat.mantenimientos;

import java.util.Collection;

public interface IPreventivosRepository {
    public void save(PreventivoType preventivoType) throws Exception;

    public void update(PreventivoType preventivoType) throws Exception;

    public void delete(String id) throws Exception;

    public PreventivoType load(String id) throws Exception;

    public Collection<PreventivoType> loadByMaquina(Maquina maquina) throws Exception;
}
