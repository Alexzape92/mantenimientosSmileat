package es.smileat.mantenimientos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class SqlPreventivosTypeRepository implements IPreventivosRepository {
    private Connection conn;
    private Statement stmt;

    public SqlPreventivosTypeRepository(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Smileat", "root", "adminSmileatJA");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido conectar con la base de datos. Error: " + e.getMessage());
        }
    }

    public void save(PreventivoType preventivoType) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO preventivostype (ID, MAQUINA, FRECUENCIA, TIEMPO, DESCRIPCION) VALUES ('" + preventivoType.getId() + "', '" + preventivoType.getMaquina().getId() + "', " + preventivoType.getFrecuencia() + ", " + preventivoType.getTiempo() + ", '" + preventivoType.getDescripcion() +  "')");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido guardar el preventivo. Error: " + e.getMessage());
        }
    }

    public void update(PreventivoType preventivoType) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE preventivostype SET MAQUINA = '" + preventivoType.getMaquina().getId() + "', FRECUENCIA = " + preventivoType.getFrecuencia() + ", TIEMPO = " + preventivoType.getTiempo() + ", DESCRIPCION = '" + preventivoType.getDescripcion() + "' WHERE ID = '" + preventivoType.getId() + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido actualizar el preventivo. Error: " + e.getMessage());
        }
    }

    public void delete(String id) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM preventivostype WHERE ID = '" + id + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido eliminar el preventivo. Error: " + e.getMessage());
        }
    }

    public PreventivoType load(String id) throws Exception {
        try{
            stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM preventivostype WHERE ID = '" + id + "'");
            ResultSet resultM = stmt2.executeQuery("SELECT * FROM maquinas WHERE ID = '" + rs.getString("MAQUINA") + "'");

            while(rs.next()){
                while(resultM.next()){
                    Maquina maquina = new Maquina(resultM.getString("ID"), resultM.getString("MODELO"), resultM.getInt("INCIDENCIAS"), resultM.getString("SALA"));
                    return new PreventivoType(rs.getString("ID"), maquina, rs.getInt("FRECUENCIA"), rs.getInt("TIEMPO") , rs.getString("DESCRIPCION"));
                }
            }
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar el preventivo. Error: " + e.getMessage());
        }

        throw new IllegalArgumentException("No se ha encontrado el preventivo");
    }

    public Collection<PreventivoType> loadByMaquina(Maquina maquina) throws Exception {
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM preventivostype WHERE MAQUINA = '" + maquina.getId() + "'");
            Collection<PreventivoType> preventivos = new ArrayList<PreventivoType>();

            while(rs.next()){
                preventivos.add(new PreventivoType(rs.getString("ID"), maquina, rs.getInt("FRECUENCIA"), rs.getInt("TIEMPO") , rs.getString("DESCRIPCION")));
            }
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar el preventivo. Error: " + e.getMessage());
        }

        throw new IllegalArgumentException("No se ha encontrado el preventivo");
    }

    public Collection<PreventivoType> loadAll() throws Exception{
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM preventivostype");
            Collection<PreventivoType> preventivos = new ArrayList<PreventivoType>();

            while(rs.next()){
                Statement stmt2 = conn.createStatement();
                ResultSet resultM = stmt2.executeQuery("SELECT * FROM maquinas WHERE ID = '" + rs.getString("MAQUINA") + "'");

                while(resultM.next()){
                    Maquina maquina = new Maquina(resultM.getString("ID"), resultM.getString("MODELO"), resultM.getInt("INCIDENCIAS"), resultM.getString("SALA"));
                    preventivos.add(new PreventivoType(rs.getString("ID"), maquina, rs.getInt("FRECUENCIA"), rs.getInt("TIEMPO") , rs.getString("DESCRIPCION")));
                }
            }

            return preventivos;
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar el preventivo. Error: " + e.getMessage());
        }
    }
}
