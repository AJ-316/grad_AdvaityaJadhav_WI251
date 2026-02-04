package db.dao;

import db.MaintenanceData;

import java.util.List;

public interface MaintenanceDAO {
    List<MaintenanceData> getByPaidStatus(boolean paid);
    void insertBatch(List<MaintenanceData> maintenanceList);
    void updateBatch(List<MaintenanceData> maintenanceList);
}
