package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Exam;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Interfaces.IService;

import java.sql.*;
import java.util.ArrayList;

public class ServiceModule implements IService <Module> {
    private Connection cnx ;
    public ServiceModule(){
        cnx =DataBaseConnection.getInstance().getCnx();
    }
    @Override
    public void add(Module module) {
        String qry ="INSERT INTO `modules`( `nom`, `description`, `imgPath`, `tempsT`) VALUES (?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,module.getNom());
            stm.setString(2,module.getDescription());
            stm.setString(3,module.getModuleImgPath());
            stm.setInt(4,0);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    @Override
    public ArrayList<Module> getAll() {
        ArrayList<Module> modules = new ArrayList();
        String qry ="SELECT * FROM `modules`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Module m = new Module();
                m.setIdModule(rs.getInt(1));
                m.setNom(rs.getString("nom"));
                m.setDescription(rs.getString(3));
                m.setModuleImgPath(rs.getString("imgPath"));
                m.setTempsEstimeM(rs.getInt("tempsT"));
                modules.add(m);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modules;
    }

    @Override
    public void update(Module module) {
        String qry ="UPDATE `modules` SET `nom`=?, `description`=?, `imgPath`=?, `tempsT`=? WHERE `idModule`=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, module.getNom());
            stm.setString(2, module.getDescription());
            stm.setString(3, module.getModuleImgPath());
            stm.setInt(4, module.getTempsEstimeM());
            stm.setInt(5, module.getIdModule());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean delete(Module module) {
        String qry = "DELETE FROM modules WHERE idModule = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, module.getIdModule());

            stm.executeUpdate();
            return true ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false ;
        }
    }

    public Integer getIdModule(Module module) {
        String query = "SELECT idModule FROM modules WHERE nom = ? AND description = ?";

        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, module.getNom());
            statement.setString(2, module.getDescription());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("idModule");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID du module", e);
        }
    }
    public Integer getIdModulebyNom(String nom) {
        String query = "SELECT idModule FROM modules WHERE nom = ? ";

        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, nom);


            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("idModule");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID du module", e);
        }
    }
    public Module getModulebyNom(String nom) {
        String query = "SELECT * FROM modules WHERE nom = ? ";

        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, nom);


            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Module module = new Module();
                module.setIdModule(resultSet.getInt("idModule"));
                module.setNom(resultSet.getString("nom"));
                module.setDescription(resultSet.getString("description"));
                module.setModuleImgPath(resultSet.getString("imgPath"));
                module.setTempsEstimeM(resultSet.getInt("tempsT"));
                return module;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID du module", e);
        }
    }
    public Module getModulebyId(int id) {
        String query = "SELECT * FROM modules WHERE idModule = ? ";

        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Module m = new Module();
                m.setIdModule(rs.getInt(1));
                m.setNom(rs.getString("nom"));
                m.setDescription(rs.getString(3));
                m.setModuleImgPath(rs.getString("imgPath"));
                return m;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID du module", e);
        }
        return null;
    }


    public ArrayList<Module> triByTempsEstimeM (String ascDesc) {
        ArrayList<Module> modules = new ArrayList();
        String qry = "SELECT * FROM `modules` ORDER BY tempsT " + ascDesc;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Module m = new Module();
                m.setIdModule(rs.getInt(1));
                m.setNom(rs.getString("nom"));
                m.setDescription(rs.getString(3));
                m.setModuleImgPath(rs.getString("imgPath"));
                m.setTempsEstimeM(rs.getInt("tempsT"));

                modules.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modules;
    }

}
