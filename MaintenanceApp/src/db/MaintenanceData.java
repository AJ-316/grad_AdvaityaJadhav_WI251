package db;

public record MaintenanceData(int sid, int amount, boolean paid, int update_uid) {

    public MaintenanceData(SiteData data, boolean paid, int update_uid) {
        this(data.sid(), data.length() * data.breadth() * getRate(data.status()), paid, update_uid);
    }

    public static int getRate(OccupancyStatus status) {
        return status.equals(OccupancyStatus.OPEN) ? 6 : 9;
    }

    @Override
    public String toString() {
        return "MaintenanceData{" +
                "sid=" + sid +
                ", amount=" + amount +
                ", paid=" + paid +
                ", update_uid=" + update_uid +
                '}';
    }
}
