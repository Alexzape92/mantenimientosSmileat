package es.smileat.mantenimientos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class MantenimientoServices {
    private ICorrectivoRepository correctivoRepository;
    private IPreventivoRepository preventivoRepository;
    private IMaquinaRepository maquinaRepository;

    public MantenimientoServices() throws Exception {
        this.correctivoRepository = new SqlCorrectivoRepository();
        this.preventivoRepository = new SqlPreventivoRepository();
        this.maquinaRepository = new SqlMaquinaRepository();
    }

    public void registrarMantenimiento(Mantenimiento mantenimiento) throws Exception {
        if(mantenimiento instanceof Correctivo){
            correctivoRepository.save((Correctivo) mantenimiento);

            Maquina maquina = maquinaRepository.load(mantenimiento.getMaquina().getId());
            maquina.setIncidencias(maquina.getIncidencias() + 1);
            maquinaRepository.update(maquina);
        }

        else{
            preventivoRepository.save((Preventivo) mantenimiento);
        }
    }

    public Collection<Correctivo> getMantenimientosCorrectivos() throws Exception {
        return correctivoRepository.loadAll();
    }

    public Collection<Preventivo> getMantenimientosPreventivos() throws Exception {
        return preventivoRepository.loadAll();
    }

    public Collection<Correctivo> getCorrectivosMaquina(String id) throws Exception {
        Collection<Correctivo> mantenimientosMaquina = new ArrayList<Correctivo>();

        for (Correctivo mantenimiento : correctivoRepository.loadAll()) {
            if (mantenimiento.getMaquina().getId().equals(id)) {
                mantenimientosMaquina.add(mantenimiento);
            }
        }

        return mantenimientosMaquina;
    }

    public Collection<Preventivo> getPreventivosMaquina(String id) throws Exception {
        Collection<Preventivo> mantenimientosMaquina = new ArrayList<Preventivo>();

        for (Preventivo mantenimiento : preventivoRepository.loadAll()) {
            if (mantenimiento.getMaquina().getId().equals(id)) {
                mantenimientosMaquina.add(mantenimiento);
            }
        }

        return mantenimientosMaquina;
    }

    public Collection<Correctivo> getCorrectivosFecha(LocalDate ini, LocalDate fin) throws Exception {
        Collection<Correctivo> mantenimientosFecha = new ArrayList<Correctivo>();

        for (Correctivo mantenimiento : correctivoRepository.loadAll()) {
            if ((mantenimiento.getFecha().isAfter(ini) || mantenimiento.getFecha().equals(ini)) && (mantenimiento.getFecha().isBefore(fin) || mantenimiento.getFecha().equals(fin))) {
                mantenimientosFecha.add(mantenimiento);
            }
        }

        return mantenimientosFecha;
    }

    public Collection<Preventivo> getPreventivosFecha(LocalDate ini, LocalDate fin) throws Exception {
        Collection<Preventivo> mantenimientosFecha = new ArrayList<Preventivo>();

        for (Preventivo mantenimiento : preventivoRepository.loadAll()) {
            if ((mantenimiento.getFecha().isAfter(ini) || mantenimiento.getFecha().equals(ini)) && (mantenimiento.getFecha().isBefore(fin) || mantenimiento.getFecha().equals(fin))) {
                mantenimientosFecha.add(mantenimiento);
            }
        }

        return mantenimientosFecha;
    }
}
