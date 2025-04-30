package DAO;

import DTO.Bill;
import DTO.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Bill_DAO {
    public List<Bill> getAllBill() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "Select * FROM employees";
        try (Connection con = DB_CONECTION.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
        ){
            while (rs.next()){
                Bill bill = new Bill();

                bills.add(bill);
                System.out.println(bill);
            }
        }
        return bills;
    }
}
