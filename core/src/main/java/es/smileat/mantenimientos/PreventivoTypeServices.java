package es.smileat.mantenimientos;

import java.util.Collection;

public class PreventivoTypeServices {
    private SqlPreventivosTypeRepository preventivosTypeRepository;

    public PreventivoTypeServices() throws Exception {
        preventivosTypeRepository = new SqlPreventivosTypeRepository();
    }

    public void registrarPreventivoType(PreventivoType preventivoType) throws Exception {
        preventivosTypeRepository.save(preventivoType);
    }

    public void eliminarPreventivoType(String id) throws Exception {
        preventivosTypeRepository.delete(id);
    }

    public void modificarPreventivoType(PreventivoType preventivoType) throws Exception {
        preventivosTypeRepository.update(preventivoType);
    }

    public PreventivoType getPreventivoType(String id) throws Exception {
        return preventivosTypeRepository.load(id);
    }

    public Collection<PreventivoType> getPreventivosType() throws Exception {
        return preventivosTypeRepository.loadAll();
    }
}
