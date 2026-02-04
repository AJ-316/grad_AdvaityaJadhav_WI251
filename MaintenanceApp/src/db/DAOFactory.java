package db;

import db.dao.MaintenanceDAO;
import db.dao.SiteDAO;
import db.dao.SiteUpdateRequestDAO;
import db.dao.UserDAO;
import db.daoimpl.MaintenanceDAOImpl;
import db.daoimpl.SiteDAOImpl;
import db.daoimpl.SiteUpdateRequestDAOImpl;
import db.daoimpl.UserDAOImpl;

public class DAOFactory {

    private static final UserDAO userDAO = new UserDAOImpl();
    private static final SiteDAO siteDAO = new SiteDAOImpl();
    private static final MaintenanceDAO maintenanceDAO = new MaintenanceDAOImpl();
    private static final SiteUpdateRequestDAO requestDAO = new SiteUpdateRequestDAOImpl();

    public static UserDAO userDAO() { return userDAO; }
    public static SiteDAO siteDAO() { return siteDAO; }
    public static MaintenanceDAO maintenanceDAO() { return maintenanceDAO; }
    public static SiteUpdateRequestDAO requestDAO() { return requestDAO; }
}
