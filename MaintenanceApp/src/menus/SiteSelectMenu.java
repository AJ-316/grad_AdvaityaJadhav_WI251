package menus;

import db.LMADBManager;
import db.SiteData;
import db.UserData;

import java.util.List;
import java.util.Scanner;

public class SiteSelectMenu extends Menu {

    private SiteData current;
    private OnSiteSelect onSelect;
    private UserData owner;

    public SiteSelectMenu(OnSiteSelect onSelect) {
        super("Confirm", "Cancel");
        this.onSelect = onSelect;
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
    }

    @Override
    public void ask() {
        if (owner == null) return;
        List<SiteData> sites = LMADBManager.instance().getSiteOfOwner(owner.uid());
        if (sites.isEmpty())
            System.out.println("No Site owned!");
        else {
            System.out.println("Owned Sites: ");
            sites.forEach(data -> {
                System.out.println("Owned Site ID: " + data.sid());
            });
        }

        System.out.print("Enter Site id: ");
        Scanner sc = new Scanner(System.in);

        current = LMADBManager.instance().getSite(sc.nextInt());

        super.ask();
    }

    @Override
    protected boolean onOptionIsExit(int choice) {
        if (choice == 1) onSelect.perform(current);
        return true;
    }
}
