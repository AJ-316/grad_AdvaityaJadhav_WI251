package db.daoimpl;

import db.DatabaseManager;
import db.MaintenanceData;
import db.dao.MaintenanceDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAOImpl implements MaintenanceDAO {

    @Override
    public List<MaintenanceData> getByPaidStatus(boolean paid) {
        List<MaintenanceData> list = new ArrayList<>();
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("select_maintenance", paid).executeQuery()) {

            while (rs.next()) {
                list.add(new MaintenanceData(
                        rs.getInt("sid"),
                        rs.getInt("amount"),
                        rs.getBoolean("paid"),
                        rs.getInt("updated_uid")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insertBatch(List<MaintenanceData> maintenanceList) {
        try {
            PreparedStatement ps =
                    DatabaseManager.getPreparedStatement("insert_maintenance");

            for (MaintenanceData data : maintenanceList) {
                ps.setInt(1, data.sid());
                ps.setInt(2, data.amount());
                ps.setBoolean(3, data.paid());
                ps.setInt(4, data.update_uid());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBatch(List<MaintenanceData> maintenanceList) {
        try {
            PreparedStatement ps =
                    DatabaseManager.getPreparedStatement("update_maintenance");

            for (MaintenanceData data : maintenanceList) {
                ps.setBoolean(1, data.paid());
                ps.setInt(2, data.update_uid());
                ps.setInt(3, data.sid());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
