package menus;

import db.*;
import entities.SiteOwner;

import java.util.Scanner;

public class SiteOwnerMenu extends Menu {

    private SiteOwner activeUser;
    private SiteSelectMenu displayOnSelectMenu;
    private SiteSelectMenu updateOnSelectMenu;

    public SiteOwnerMenu() {
        super("See Site", "Update Site Data", "Exit");
        this.displayOnSelectMenu = new SiteSelectMenu(data -> {
            if (data.uid() != activeUser.getData().uid() && LMADBManager.instance().isOwner(activeUser.getData())) {
                System.out.println("Access Denied to Site");
                return;
            }

            System.out.println(data);
        });

        this.updateOnSelectMenu = new SiteSelectMenu(data -> {
            if (data.uid() != activeUser.getData().uid() && LMADBManager.instance().isOwner(activeUser.getData())) {
                System.out.println("Access Denied to Site");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter location: ");
            String newLocation = sc.next();

            System.out.print("Enter type: ");
            String newType = sc.next();

            System.out.print("Enter status: ");
            String newOccupancy = sc.next();

            System.out.print("Enter size length: ");
            int length = sc.nextInt();

            System.out.print("Enter size breadth: ");
            int breadth = sc.nextInt();

            try {
                LMADBManager.instance().insertRequest(new SiteData(
                        data.sid(),
                        data.uid(),
                        newLocation,
                        SiteType.valueOf(newType),
                        OccupancyStatus.valueOf(newOccupancy), length, breadth
                ));

                System.out.println("Proposed Request for Update");
            } catch (Exception e) {
                System.out.println("Invalid Details");
            }
        });
    }

    @Override
    public void ask() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User id: ");
        int id = sc.nextInt();
        UserData user = LMADBManager.instance().getUser(id);

        if (!LMADBManager.instance().isOwner(user)) {
            System.out.println("Warning; User Not Owner (Admin)");
        }

        activeUser = new SiteOwner(user);
        displayOnSelectMenu.setOwner(activeUser.getData());
        updateOnSelectMenu.setOwner(activeUser.getData());
        super.ask();
    }

    @Override
    protected boolean onOptionIsExit(int choice) {
        switch (choice) {
            case 1 -> displayOnSelectMenu.ask();
            case 2 -> updateOnSelectMenu.ask();
            case 3 -> {
                return true;
            }
        }

        return false;
    }
}
