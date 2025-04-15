package DAO;

import DTO.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sup_DAO {
    public List<Supplier> getAllSup() throws SQLException{
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "Select * FROM supplier";
        try (Connection con = DB_CONECTION.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             ){
            while (rs.next()){
                Supplier supplier = new Supplier();
                supplier.setID_Supp(rs.getString("Supplier_ID"));
                supplier.setName_Supp(rs.getString("Supplier_Name"));
                supplier.setPhone_Supp(rs.getString("Supplier_Phone"));
                supplier.setEmail_Supp(rs.getString("Supplier_Email"));
                suppliers.add(supplier);
                System.out.println(supplier);
            }
        }
        return suppliers;
    }
}