package es.smileat.mantenimientos;

import java.util.ArrayList;
import java.util.Collection;

public class MaquinaServices {
    private IMaquinaRepository maquinaRepository;

    public MaquinaServices() throws Exception {
        maquinaRepository = new SqlMaquinaRepository();
    }

    public void registrarMaquina(Maquina maquina) throws Exception {
        maquinaRepository.save(maquina);
    }

    public void eliminarMaquina(String id) throws Exception {
        maquinaRepository.delete(id);
    }

    public void modificarMaquina(Maquina maquina) throws Exception {
        maquinaRepository.update(maquina);
    }

    public Maquina getMaquina(String id) throws Exception {
        return maquinaRepository.load(id);
    }

    public Collection<Maquina> getMaquinas() throws Exception {
        return maquinaRepository.loadAll();
    }

    public Collection<Maquina> getMaquinasSala(String sala) throws Exception {
        Collection<Maquina> maquinasSala = new ArrayList<Maquina>();


        for (Maquina maquina : maquinaRepository.loadAll()) {
            if (maquina.getSala().equals(sala)) {
                maquinasSala.add(maquina);
            }
        }

        return maquinasSala;
    }
}
