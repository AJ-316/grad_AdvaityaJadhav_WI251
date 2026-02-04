package menus;

import java.util.InputMismatchException;
import java.util.Scanner;

/// //////////////////////////////////////////// Menus
public abstract class Menu {
    protected String askString;
    private int choices;

    public Menu(String... options) {
        askString = "\n======== Make choice ========\n";
        for (int i = 0; i < options.length; i++) {
            askString += (i + 1) + ") " + options[i] + "\n";
        }
        askString += "=>";
        choices = options.length;
    }

    public void ask() {
        if (askString == null) return;

        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(askString);
                int choice = sc.nextInt();
                if (choice > choices | choice <= 0) {
                    continue;
                }
                if (onOptionIsExit(choice)) break;
            } catch (InputMismatchException e) {
                System.err.println("Incorrect Choice");
                sc.nextLine();
            }
        }
    }

    protected abstract boolean onOptionIsExit(int choice);
}

