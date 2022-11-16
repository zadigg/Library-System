package librarysystem;

import java.awt.*;

public class Config {

    public static final String APP_NAME = "FREEDOM LIBRARY";
    public static final int APP_WIDTH = 900;
    public static final int APP_HEIGHT = 600;
    public static final int DIVIDER = 200;
    public static  final Font DEFUALT_FONT = new java.awt.Font("Tahoma", 1, 13);


    public static final String[] ALL_MENU = {

            "DashBoard",
            "Add member",
            "Add book",
            "Add book copy",
            "Checkout book",
            "OverDue",
            "Search book",
            "Search member",
            "Print Member Checkout",
            "Update/Delete member",
            "Logout",
    };
    public static final String[] LIBRARIAN_MENU = {
            "Dashboard",
            "Search member",
            "Search book",
            "Checkout book",
            "OverDue",
            "Search member checkouts",
            "Logout",
    };

    public static final String[] ADMIN_MENU = {
            "Dashboard",
            "Add member",
            "Add book",
            "Add book copy",
            "Search member",
            "Search book",
            "Update/Delete member",
            "Logout",
    };





}
