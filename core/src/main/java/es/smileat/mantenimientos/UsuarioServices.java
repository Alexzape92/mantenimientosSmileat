package es.smileat.mantenimientos;

import java.util.Collection;

//En esta clase se utiliza mucho el instanceof y el downcasting, lo cual podría ser un problema, sin embargo, se supone que las clases Empleado y Supervisor no van a cambiar, por lo que no debería haber problemas
public class UsuarioServices {
    private IEmpleadoRepository empleadoRepository;
    private ISupervisorRepository supervisorRepository;

    public UsuarioServices() throws Exception {
        this.empleadoRepository = new SqlEmpleadoRepository();
        this.supervisorRepository = new SqlSupervisorRepository();
    }

    public void registro(Usuario usuario) throws Exception {
        if (usuario instanceof Empleado) {
            Empleado empleado = (Empleado) usuario;
            empleadoRepository.save(empleado);
        } else if (usuario instanceof Supervisor) {
            Supervisor supervisor = (Supervisor) usuario;
            supervisorRepository.save(supervisor);
        }
    }

    public Usuario login(String nombre, String password) throws Exception {
        Usuario usuario = null;

        try{
            Empleado empleado = empleadoRepository.load(nombre);
            usuario = empleado;
        } catch (Exception e) {}

        try{
            Supervisor supervisor = supervisorRepository.load(nombre);
            usuario = supervisor;
        } catch (Exception e) {}
       
        if(usuario != null && usuario.check(password))
            return usuario;
        
        throw new IllegalArgumentException("Usuario o contraseña incorrectos");
    }

    public void eliminar(Usuario usuario) throws Exception {
        if (usuario instanceof Empleado) {
            empleadoRepository.delete(usuario.getNombre());
        } else if (usuario instanceof Supervisor) {
            supervisorRepository.delete(usuario.getNombre());
        }
    }

    public void actPassword(Usuario usuario, String password) throws Exception {
        usuario.setPasswordCifrada(password);
        if (usuario instanceof Empleado) {
            Empleado empleado = (Empleado) usuario;
            empleadoRepository.update(empleado);
        } else if (usuario instanceof Supervisor) {
            Supervisor supervisor = (Supervisor) usuario;
            supervisorRepository.update(supervisor);
        }
    }

    public Usuario load(String nombre) throws Exception {
        Usuario usuario = null;

        try{
            Empleado empleado = empleadoRepository.load(nombre);
            usuario = empleado;
        } catch (Exception e) {}

        try{
            Supervisor supervisor = supervisorRepository.load(nombre);
            usuario = supervisor;
        } catch (Exception e) {}

        return usuario;
    }

    public Collection<Empleado> loadAllEmpleados() throws Exception {
        return empleadoRepository.loadAll();
    }

    public Collection<Supervisor> loadAllSupervisores() throws Exception {
        return supervisorRepository.loadAll();
    }

    public void upgrade(Empleado empleado) throws Exception {
        Supervisor supervisor = new Supervisor(empleado.getNombre(), "");
        supervisor.setPasswordCifrada(empleado.getPassword());
        supervisorRepository.save(supervisor);
        empleadoRepository.delete(empleado.getNombre());
    }
}
