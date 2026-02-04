package menus;

public class LMAMenu extends Menu {

    private final SubMenuSelectUser subMenuSelectUser;

    public LMAMenu() {
        super("Select User", "Exit");
        subMenuSelectUser = new SubMenuSelectUser();
    }

    @Override
    protected boolean onOptionIsExit(int choice) {
        if (choice == 2) return true;
        subMenuSelectUser.ask();
        return false;
    }

    private static class SubMenuSelectUser extends Menu {

        private final AdminMenu adminMenu;
        private final SiteOwnerMenu siteOwnerMenu;

        public SubMenuSelectUser() {
            super("Admin", "Site Owner", "Exit");
            adminMenu = new AdminMenu();
            siteOwnerMenu = new SiteOwnerMenu();
        }

        @Override
        protected boolean onOptionIsExit(int choice) {
            if (choice == 1) {
                adminMenu.ask();
                return true;
            }

            if (choice == 2) {
                siteOwnerMenu.ask();
                return true;
            }

            return choice == 3;
        }
    }
}
