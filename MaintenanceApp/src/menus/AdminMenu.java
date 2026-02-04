package menus;

import db.LMADBManager;
import db.MaintenanceData;
import db.SiteData;
import db.UserData;
import entities.Admin;

import java.util.List;
import java.util.Scanner;

public class AdminMenu extends Menu {

    private Admin activeAdmin;
    private ApprovalMenu approvalMenu;

    public AdminMenu() {
        super("Create/Update Owner", "Show Users", "Show Sites", "Check Approvals", "Generate Maintenance", "Show Pending Maintenance", "Collect Pending Maintenance", "Exit");
        this.approvalMenu = new ApprovalMenu();
    }

    @Override
    public void ask() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User id: ");
        int id = sc.nextInt();
        UserData user = LMADBManager.instance().getUser(id);
        if (!LMADBManager.instance().isAdmin(user)) {
            System.out.println("Access Denied; User Not Admin");
            return;
        }

        activeAdmin = new Admin(user);
        super.ask();
    }

    @Override
    protected boolean onOptionIsExit(int choice) {
        switch (choice) {
            case 1: {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter Owner Name: ");
                String name = sc.next();

                LMADBManager.instance().insertUser(new UserData(0, name, "SITE_OWNER"));
                System.out.println("Created Site Owner");

                return false;
            }
            case 2: {
                List<UserData> userData = LMADBManager.instance().selectAllUsers("SITE_OWNER");
                userData.forEach(System.out::println);
                return false;
            }
            case 3: {
                List<SiteData> sList = LMADBManager.instance().selectAllSites();
                System.out.println("Total Sites: " + sList.size());
                sList.forEach(site -> System.out.println(site + " Owner -> " + LMADBManager.instance().getUser(site.uid())));
                return false;
            }

            case 4: {
                List<SiteData> rList = LMADBManager.instance().selectAllRequests(false);
                System.out.println("Pending Approvals: " + rList.size());
                rList.forEach(req -> System.out.println("REQUESTED RECORD : " + req + "\nORIGINAL RECORD  : " + LMADBManager.instance().getSite(req.sid())));

                if(!rList.isEmpty()) approvalMenu.ask();
                return false;
            }

            case 5: {
                List<MaintenanceData> newData = activeAdmin.generateMaintenance(LMADBManager.instance().selectAllSites());
                System.out.println("Generated Maintenance for all sites: " + newData.size());
                LMADBManager.instance().insertMaintenance(newData);
                return false;
            }

            case 6: {
                List<MaintenanceData> mList = LMADBManager.instance().selectAllMaintenance(false);
                System.out.println("Pending Maintenance: " + mList.size());
                mList.forEach(System.out::println);
                return false;
            }

            case 7: {
                List<MaintenanceData> mList = LMADBManager.instance().selectAllMaintenance(false);
                System.out.println("Pending Maintenance: " + mList.size());
                if (mList.isEmpty()) {
                    System.out.println("Collected None");
                    return false;
                }

                mList = activeAdmin.collectMaintenance(mList);
                LMADBManager.instance().updateMaintenance(mList);
                System.out.println("Collected!");
                return false;
            }

            case 8: {
                return true;
            }
        }
        return false;
    }

    private static class ApprovalMenu extends Menu {

        public ApprovalMenu() {
            super("Approve Request", "Exit");
        }

        @Override
        protected boolean onOptionIsExit(int choice) {
            if (choice == 1) {
                System.out.print("Requested Site ID: ");

                Scanner sc = new Scanner(System.in);
                int sid = sc.nextInt();

                if (!LMADBManager.instance().isRequestPresent(sid)) {
                    System.out.println("Invalid ID; No Request Present");
                    return false;
                }

                LMADBManager.instance().updateRequest(sid, true);
                System.out.println("Request Approved");
            }
            return choice == 2;
        }
    }
}
