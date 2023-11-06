package es.smileat.mantenimientos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CalendarioServices {
    private MantenimientoServices mantenimientoServices;
    private PreventivoTypeServices preventivoTypeServices;
    private List<PreventivoType> mantenimientos[];

    @SuppressWarnings("unchecked")
    public CalendarioServices() throws Exception {
        mantenimientoServices = new MantenimientoServices();
        preventivoTypeServices = new PreventivoTypeServices();
        mantenimientos = (ArrayList<PreventivoType>[]) new ArrayList[30];
        for(int i = 0; i < 30; i++){
            mantenimientos[i] = new ArrayList<PreventivoType>();
        }
    }

    public void rellenarCalendario() throws Exception {
        int i = 0;
        List<PreventivoType> tipos = new ArrayList<PreventivoType>(preventivoTypeServices.getPreventivosType());
        Collections.sort(tipos, (t1, t2) -> t2.getTiempo() - t1.getTiempo());  //Ordenar por tiempo de mantenimiento descendiente
        for(PreventivoType tipo: tipos){
            Maquina maquina = tipo.getMaquina();
            Collection<Preventivo> preventivosMaquina = mantenimientoServices.getPreventivosMaquina(maquina.getId()).stream().filter(m -> m.getTipo().equals(tipo)).toList();
            LocalDate last = preventivosMaquina.stream().map(Preventivo::getFecha).max(LocalDate::compareTo).orElse(null);
            if(last == null || last.until(LocalDate.now(), ChronoUnit.DAYS) >= tipo.getFrecuencia()){
                int firsti = i;
                while(mantenimientos[i].stream().map(PreventivoType::getTiempo).reduce(0, Integer::sum) + tipo.getTiempo() > 480){
                    i = (i + 1) % 30;
                    if(i == firsti){
                        throw new RuntimeException("No caben todos los mantenimientos en el calendario");
                    }
                }
                mantenimientos[i].add(tipo);
                i = (i + 1) % 30;
            }
        }
    }

    public List<PreventivoType>[] getCalendario() {
        return mantenimientos;
    }
}
