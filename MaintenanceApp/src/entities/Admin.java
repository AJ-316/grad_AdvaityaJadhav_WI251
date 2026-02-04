package entities;

import db.MaintenanceData;
import db.SiteData;
import db.UserData;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin(UserData data) {
        super(data);
    }

    public List<MaintenanceData> generateMaintenance(List<SiteData> siteDataList) {
        List<MaintenanceData> maintenanceDataList = new ArrayList<>();
        for (SiteData siteData : siteDataList) {
            maintenanceDataList.add(new MaintenanceData(siteData, false, data.uid()));
        }
        return maintenanceDataList;
    }

    public List<MaintenanceData> collectMaintenance(List<MaintenanceData> pending) {
        List<MaintenanceData> maintenanceDataList = new ArrayList<>();
        for (MaintenanceData old : pending) {
            maintenanceDataList.add(new MaintenanceData(old.sid(), old.amount(), true, data.uid()));
        }
        return maintenanceDataList;
    }
}
