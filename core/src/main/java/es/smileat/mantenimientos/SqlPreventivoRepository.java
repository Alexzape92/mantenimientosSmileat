package es.smileat.mantenimientos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


public class SqlPreventivoRepository implements IPreventivoRepository {
    private Connection conn;
    private Statement stmt;

    public SqlPreventivoRepository() throws Exception{
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Smileat", "root", "adminSmileatJA");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido conectar con la base de datos. Error: " + e.getMessage());
        }
    }

    public void save(Preventivo mantenimiento) throws Exception {
        try{
            stmt = conn.createStatement();
            Date date = Date.valueOf(mantenimiento.getFecha());
            stmt.executeUpdate("INSERT INTO preventivos (ID, FECHA, RESULTADO, COMENTARIOS, EMPLEADO, SUPERVISOR, TIEMPO, MAQUINA, TIPO) VALUES ('" + mantenimiento.getId() + "', '" + date + "', '" + mantenimiento.getResultado().toString() + "', '" + mantenimiento.getComentarios() + "', '" + mantenimiento.getEmpleado().getNombre() + "', '" + mantenimiento.getSupervisor().getNombre() + "', " + mantenimiento.getTiempo() + ", '" + mantenimiento.getMaquina().getId() + "', '" + mantenimiento.getTipo().getId() + "')");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido guardar el mantenimiento. Error: " + e.getMessage());
        }
    }

    public Preventivo load(String id) throws Exception{
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM preventivos WHERE ID = '" + id + "'");

            while(rs.next()){
                String nombreUsuario = rs.getString("EMPLEADO");
                Usuario empleado = null;
                Supervisor supervisor = null;
                Maquina maquina = null;
                PreventivoType tipo = null;

                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                Statement stmt4 = conn.createStatement();
                Statement stmt5 = conn.createStatement();
                Statement stmt6 = conn.createStatement();
                ResultSet resultE = stmt2.executeQuery("SELECT * FROM empleados WHERE NOMBRE = '" + nombreUsuario + "'");
                ResultSet resultS = stmt3.executeQuery("SELECT * FROM supervisores WHERE NOMBRE = '" + nombreUsuario + "'");
                ResultSet resultM = stmt4.executeQuery("SELECT * FROM maquinas WHERE ID = '" + rs.getString("MAQUINA") + "'");
                ResultSet resultT = stmt6.executeQuery("SELECT * FROM preventivostype WHERE ID = '" + rs.getString("TIPO") + "'");

                while(resultE.next())
                    empleado = new Empleado(resultE.getString("NOMBRE"), resultE.getString("PASSWORD"));
                
                while(resultS.next())
                    empleado = new Supervisor(resultS.getString("NOMBRE"), resultS.getString("PASSWORD"));
                
                ResultSet resultSupervisor = stmt5.executeQuery("SELECT * FROM supervisores WHERE NOMBRE = '" + rs.getString("SUPERVISOR") + "'");

                while(resultSupervisor.next())
                    supervisor = new Supervisor(resultSupervisor.getString("NOMBRE"), resultSupervisor.getString("PASSWORD"));

                while(resultM.next())
                    maquina = new Maquina(resultM.getString("ID"), resultM.getString("MODELO"), resultM.getInt("INCIDENCIAS"), resultM.getString("SALA"));
                
                while(resultT.next())
                    tipo = new PreventivoType(resultT.getString("ID"), maquina, resultT.getInt("FRECUENCIA"), resultT.getInt("TIEMPO"), resultT.getString("DESCRIPCION"));

                return new Preventivo(rs.getString("ID"), rs.getDate("FECHA").toLocalDate(), ResultadoType.valueOf(rs.getString("RESULTADO")), rs.getString("COMENTARIOS"), empleado, supervisor, rs.getInt("TIEMPO"), maquina, tipo);
                
            }
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar el mantenimiento. Error: " + e.getMessage());
        }

        throw new IllegalArgumentException("No se ha encontrado el mantenimiento");
    }

    public void update(Preventivo mantenimiento) throws Exception{
        try{
            stmt = conn.createStatement();
            Date date = Date.valueOf(mantenimiento.getFecha());
            stmt.executeUpdate("UPDATE preventivos SET FECHA = '" + date + "', RESULTADO = '" + mantenimiento.getResultado().toString() + "', COMENTARIOS = '" + mantenimiento.getComentarios() + "', EMPLEADO = '" + mantenimiento.getEmpleado().getNombre() + "', SUPERVISOR = '" + mantenimiento.getSupervisor().getNombre() + "', TIEMPO = " + mantenimiento.getTiempo() + ", MAQUINA = '" + mantenimiento.getMaquina().getId() + "', TIPO = '" + mantenimiento.getTipo().getId() + "' WHERE ID = '" + mantenimiento.getId() + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido actualizar el mantenimiento. Error: " + e.getMessage());
        }
    }

    public void delete(String id) throws Exception{
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM preventivos WHERE ID = '" + id + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido borrar el mantenimiento. Error: " + e.getMessage());
        }
    }

    public Collection<Preventivo> loadAll() throws Exception{
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM preventivos");
            Collection<Preventivo> mantenimientos = new ArrayList<Preventivo>();

            while(rs.next()){
                String nombreUsuario = rs.getString("EMPLEADO");
                Usuario empleado = null;
                Supervisor supervisor = null;
                Maquina maquina = null;
                PreventivoType tipo = null;

                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                Statement stmt4 = conn.createStatement();
                Statement stmt5 = conn.createStatement();
                Statement stmt6 = conn.createStatement();
                ResultSet resultE = stmt2.executeQuery("SELECT * FROM empleados WHERE NOMBRE = '" + nombreUsuario + "'");
                ResultSet resultS = stmt3.executeQuery("SELECT * FROM supervisores WHERE NOMBRE = '" + nombreUsuario + "'");
                ResultSet resultM = stmt4.executeQuery("SELECT * FROM maquinas WHERE ID = '" + rs.getString("MAQUINA") + "'");
                ResultSet resultT = stmt6.executeQuery("SELECT * FROM preventivostype WHERE ID = '" + rs.getString("TIPO") + "'");

                while(resultE.next())
                    empleado = new Empleado(resultE.getString("NOMBRE"), resultE.getString("PASSWORD"));
                
                while(resultS.next())
                    empleado = new Supervisor(resultS.getString("NOMBRE"), resultS.getString("PASSWORD"));
                
                ResultSet resultSupervisor = stmt5.executeQuery("SELECT * FROM supervisores WHERE NOMBRE = '" + rs.getString("SUPERVISOR") + "'");

                while(resultSupervisor.next())
                    supervisor = new Supervisor(resultSupervisor.getString("NOMBRE"), resultSupervisor.getString("PASSWORD"));

                while(resultM.next())
                    maquina = new Maquina(resultM.getString("ID"), resultM.getString("MODELO"), resultM.getInt("INCIDENCIAS"), resultM.getString("SALA"));
                
                while(resultT.next())
                    tipo = new PreventivoType(resultT.getString("ID"), maquina, resultT.getInt("FRECUENCIA"), resultT.getInt("TIEMPO"), resultT.getString("DESCRIPCION"));

                mantenimientos.add(new Preventivo(rs.getString("ID"), rs.getDate("FECHA").toLocalDate(), ResultadoType.valueOf(rs.getString("RESULTADO")), rs.getString("COMENTARIOS"), empleado, supervisor, rs.getInt("TIEMPO"), maquina, tipo));
            }

            return mantenimientos;
        } catch(Exception e){
            throw new RuntimeException("No se han podido cargar los mantenimientos. Error: " + e.getMessage());
        }
    }
}
