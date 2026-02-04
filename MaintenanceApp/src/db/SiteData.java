package db;

public record SiteData(int sid, int uid, String location, SiteType type, OccupancyStatus status, int length,
                       int breadth) {
    @Override
    public String toString() {
        return "\u001b[34mSiteData{\u001b[0m" +
                "sid=" + sid +
                ", owner_id=" + uid +
                ", location=" + location +
                ", type=" + type +
                ", status=" + status +
                ", length=" + length +
                ", breadth=" + breadth +
                "\u001b[34m}\u001b[0m";
    }
}
