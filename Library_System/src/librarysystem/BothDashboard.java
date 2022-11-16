package librarysystem;

import business.*;
import librarysystem.guiElements.*;
import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.CheckOutGui;
import librarysystem.guiElements.checkOut.OverDuePanel;
import librarysystem.guiElements.checkOut.PrintMemberCheckOut;
import librarysystem.guiElements.member.EditOrDeleteMember;
import librarysystem.guiElements.book.AddBookCopyPanel;
import librarysystem.guiElements.book.BookGui;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.member.MemberUI;
import librarysystem.guiElements.member.SearchMemberPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BothDashboard extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;

	public static final BothDashboard INSTANCE = new BothDashboard();
	ControllerInterface ci = new SystemController();

	private boolean isInitialized = false;

	JList<ListItem> linkList;
	JPanel cards;

	List<ListItem> itemList = new ArrayList<>();

	private JPanel bothDashobardPanel;
	public JTable memberListJTable, bookListJTable, checkOutList;

	// Singleton class
	private BothDashboard() {
		setSize(Config.APP_WIDTH, Config.APP_HEIGHT);
		UIController.INSTANCE.bothDashboard = this;
		memberListJTable = MemberUI.INSTANCE.getMemberList();
		bookListJTable = BookGui.INSTANCE.getBookList();
		checkOutList = CheckOutGui.INSTANCE.getCheckOutList();
	}

	public void constructSideBarMenu() {

		for (String item : Config.ALL_MENU) {
			itemList.add(new ListItem(item, true));
		}
	}

	public void init() {

		// Construct sideBarMenu ListItems
		constructSideBarMenu();

		// Create sidebar
		createLinkLabels();

		// create main panels
		createMainPanels();

		// link my sidebar
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().getItemName();
			boolean allowed = linkList.getSelectedValue().highlight();
			CardLayout cl = (CardLayout) (cards.getLayout());

			if (!allowed) {
				value = itemList.get(0).getItemName();
				linkList.setSelectedIndex(0);
			}
			cl.show(cards, value);
		});
		linkList.setBackground(new java.awt.Color(53,49,49));
		linkList.setVisibleRowCount(4);
		linkList.setFixedCellHeight(40);
		linkList.setSelectionForeground(Color.BLACK);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
		splitPane.setDividerLocation(Config.DIVIDER);

		JPanel mainPanel = new JPanel(new FlowLayout());
		mainPanel.add(splitPane);

		add(splitPane);
		isInitialized = true;
		// this.setResizable(false);
		centerFrameOnDesktop(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}

	public void createMainPanels() {

		// create admin panel
		setBothDashboardPanel();

		// Assign crossponding panels to crsossponding Cards
		setCards();

	}

	public void setCards() {

		// book related panels
		JPanel searchBookPanel = SearchBookPanel.INSTANCE.getSearchBookPanel();
		JPanel addBookPanel = BookGui.INSTANCE.getAddBookPanel();
		JPanel addBookCopyPanel = AddBookCopyPanel.INSTANCE.getAddBookCopyPanel();

		// member related panels
		JPanel searchMemberPanel = SearchMemberPanel.INSTANCE.getsearchMemberPanel();
		JPanel addMemberPanel = MemberUI.INSTANCE.getAddMemberPanel();
		JPanel editOrDeletePanel = EditOrDeleteMember.INSTANCE.getAddMemberPanel();

		// logout panel
		JPanel logoutPanel = Logout.INSTANCE.getLoginPanel();

		// checkoutRelated Panels
		JPanel checkOutBookPanel = CheckOutBookPanel.INSTANCE.getCheckOutPanel();
		JPanel checkOutStatusPanel = OverDuePanel.INSTANCE.getSearchOverDuePanel();
		JPanel searchMemberCheckOutPanel = PrintMemberCheckOut.INSTANCE.getPrintMemberCheckOutPanel();

		// Dashboard panel
		cards = new JPanel(new CardLayout());
		cards.add(bothDashobardPanel, itemList.get(0).getItemName());
		cards.add(addMemberPanel, itemList.get(1).getItemName());
		cards.add(addBookPanel, itemList.get(2).getItemName());
		cards.add(addBookCopyPanel, itemList.get(3).getItemName());
		cards.add(checkOutBookPanel, itemList.get(4).getItemName());
		cards.add(checkOutStatusPanel, itemList.get(5).getItemName());
		cards.add(searchBookPanel, itemList.get(6).getItemName());
		cards.add(searchMemberPanel, itemList.get(7).getItemName());
		cards.add(searchMemberCheckOutPanel, itemList.get(8).getItemName());
		cards.add(editOrDeletePanel, itemList.get(9).getItemName());
		cards.add(logoutPanel, itemList.get(10).getItemName());

	}

	public void setBothDashboardPanel() {

		// create panel
		bothDashobardPanel = new JPanel(new BorderLayout());
		bothDashobardPanel.add(new JLabel("Administrator Dashboard"), BorderLayout.NORTH);

		JTabbedPane tp = new JTabbedPane();
		tp.setPreferredSize(new Dimension(Config.APP_WIDTH - Config.DIVIDER, Config.APP_HEIGHT));
		tp.add("Checkouts", new JScrollPane(checkOutList));

		tp.add("Books", new JScrollPane(bookListJTable));
		tp.add("Members", new JScrollPane(memberListJTable));
		tp.setFont(Config.DEFUALT_FONT);
		tp.setForeground(Color.black);
		tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		bothDashobardPanel.add(tp, BorderLayout.CENTER);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

	@SuppressWarnings("serial")
	public void createLinkLabels() {

		DefaultListModel<ListItem> model = new DefaultListModel<>();

		for (ListItem item : itemList) {
			model.addElement(item);
		}

		linkList = new JList<ListItem>(model);
		linkList.setCellRenderer(new DefaultListCellRenderer() {

			@SuppressWarnings("rawtypes")
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {

				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof ListItem) {
					ListItem nextItem = (ListItem) value;
					setText(nextItem.getItemName());
					if (nextItem.highlight()) {
						setForeground(Util.LINK_AVAILABLE);
					} else {
						setForeground(Util.LINK_NOT_AVAILABLE);
					}
					if (isSelected) {
						setForeground(Color.BLACK);
						setBackground(new Color(236, 243, 245));
					}
					setFont(Config.DEFUALT_FONT);
				} else {
					setText("illegal item");
				}
				return c;
			}

		});
	}

}
