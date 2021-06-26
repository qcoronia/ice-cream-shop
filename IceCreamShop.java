//Paul Albrick Coronia
//
//GUI Project

import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import java.util.Date.*;
import java.util.*;
import java.io.*;

public class IceCreamShop
{
	////////        Data/Variables      ////////
	private static double					theBalance;
	private static double					totalSellPrice;
	private static double					totalOrderPrice;
	
	private static Vector<Transaction>		transactionData;
	private static Vector<IceCreamDetail>	tDPlaceHolder;
	private static Vector<IceCreamDetail> 	iceCreamStocks;
	
	private static Vector<String> 			brandList;
	private static Vector<String> 			varietyList;
	private static Vector<String> 			flavorList;
	private static int 						icAvail;
	
	private static Vector<String>			sellCNames;
	private static Vector<String[]>			sellOrders;
	private static Vector<IceCreamDetail>	sellICD;
	
	private static Vector<String>			aICCNames;
	private static Vector<String[]>			aICOrders;
	private static Vector<IceCreamDetail>	aICICD;
	
	private static Vector<String>			stocksCNames;
	private static Vector<Vector<String>>	stocksData;
	
	private static Vector<Vector<String>>	transVector;
	
	private static Vector<Vector<String>>	transDetails;
	
	private static Date						date;
	private static SimpleDateFormat			dateFormat = new SimpleDateFormat("dd/MM/yyyy");//String d = dateFormat.format(date);
	
	////////        GUI Components      ////////

	private	JLayeredPane lp;
	///////	mainPanel
	private	JFrame 		w;
	private	JPanel		mainPanel,		cards1,			cards2;
	private	CardLayout 	cardLayout;
	private	JButton 	sellButton, 	stocksButton;
	///////	commonPanel
	private	JPanel		commonPanel;
	private	JLabel 		title;
	private	JLabel 		brand, 			variety, 		flavors, 		price,		qty;
	private	JComboBox 	brandCB, 		varietyCB, 		flavorsCB;
	private	JLabel 		iceAvail;
	private	JTextField	priceTF, 		qtyTF;
	///////	c1
	private	JPanel		c1;
	private	JLabel	 	totalPrice;
	private	JTable 		sellList;
	private	JScrollPane sp;
	private	JPanel 		cart;
	private	JButton 	addButton, 		removeButton;
	///////	c2
	private	JPanel		c2;
	private	JButton 	sell;
	private	JButton	 	addBrand, 		addVariety, 	addFlavor;
	private	JButton	 	remBrand, 		remVariety, 	remFlavor;
	private	JButton		addIceCream,	showTrans;
	private	JLabel 		balance;
	private	JTextField	balanceTF;
	///////	aIC
	private	JPanel		aIC;
	private	JTable 		aICList;
	private	JScrollPane aICSP;
	private	JButton 	add, 			remove, 		addToStocks,	remFromStocks;
	private	JLabel		stp;
	///////	stocksPanel
	private	JPanel		stocksPanel;
	private	JTable		stocksTable;
	private	JScrollPane	stocksView;
	///////	settingsPanel
	private	JPanel		sPanel;
	private	JLabel		bgColor,		bgImage;
	private	JComboBox	bgImageCB, 		bgColorCB;
	///////	miscButtonPanel
	private	JPanel		miscBPanel;
	private	JButton		about;			
	private	JToggleButton stocksB;
	private	JToggleButton	b;
	///////	Transactions view pane
	private JPanel		transPanel;
	private JTable		transTable;
	///////	Transaction Details pane
	private JPanel		transDPanel;
	private	JTable		transDTable;
	
	private Color backgroundColor = Color.cyan;
	private Color modifiableColor = new Color( 0, 0, 0, 0.0f);
	private String image;
	private String[] bgImageList;
	private String[] bgColorList;
	
	private CardButtonHandler 		cBH;
	private FormsButtonHandler 		fBH;
	private OperationButtonHandler 	oBH;
	private WindowClosingHandler 	wcH;
	private ComboBoxActionHandler 	cbAH;
	private SettingsButtonHandler	setBH;
	private	BackgroundChangeHandler	bcH;
	
	private int panelX = 400;
	private int panelY = 300;
	
	private String fontFace = "Papyrus";
	private JLabel bgc;
	private JLabel bg;
	
	////////        CONSTRUCTORS        ////////
	
	public IceCreamShop()
	{
		w = new JFrame();
		initializeShop();
		updateStocksList();
		
		w.addWindowListener( wcH);
		w.add( b);
		w.add( about);
		w.add( stocksB);
		w.add( mainPanel);
		w.setResizable(false);
		w.setTitle("Ice Cream Shop");
		w.setLocation( 100, 100);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.pack();
		w.setVisible(true);
	}
	
	private void setupCommonPanel()
	{
		commonPanel = new JPanel();
		commonPanel.setBackground(backgroundColor);
		commonPanel.setOpaque(false);
		
		commonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		Insets inset0 = new Insets( 5, 5, 5, 5);
		gbc.insets = inset0;
		gbc.ipadx = 15;
		gbc.ipady = 15;
		
		brand 	= new JLabel( "Brand:", SwingConstants.RIGHT);
			brand.setForeground( createColor());
			brand.setFont( new Font( fontFace, brand.getFont().getStyle(), 18));
		variety = new JLabel( "Variety:", SwingConstants.RIGHT);
			variety.setForeground( createColor());
			variety.setFont( new Font( fontFace, variety.getFont().getStyle(), 18));
		flavors = new JLabel( "Flavor:", SwingConstants.RIGHT);
			flavors.setForeground( createColor());
			flavors.setFont( new Font( fontFace, flavors.getFont().getStyle(), 18));
		price 	= new JLabel( "Price:", SwingConstants.RIGHT);
			price.setForeground( createColor());
			price.setFont( new Font( fontFace, price.getFont().getStyle(), 18));
		qty 	= new JLabel( "Quantity:", SwingConstants.RIGHT);
			qty.setForeground( createColor());
			qty.setFont( new Font( fontFace, qty.getFont().getStyle(), 18));
			
		brandCB 	= new JComboBox( brandList);
			brandCB.setForeground( createColor());
			brandCB.setBackground(backgroundColor);
			brandCB.setOpaque(false);
			brandCB.setFont( new Font( fontFace, brandCB.getFont().getStyle(), 18));
			brandCB.addActionListener(cbAH);
		varietyCB 	= new JComboBox( varietyList);
			varietyCB.setForeground( createColor());
			varietyCB.setBackground(backgroundColor);
			varietyCB.setOpaque(false);
			varietyCB.setFont( new Font( fontFace, varietyCB.getFont().getStyle(), 18));
			varietyCB.addActionListener(cbAH);
		flavorsCB 	= new JComboBox( flavorList);
			flavorsCB.setForeground( createColor());
			flavorsCB.setBackground(backgroundColor);
			flavorsCB.setOpaque(false);
			flavorsCB.setFont( new Font( fontFace, flavorsCB.getFont().getStyle(), 18));
			flavorsCB.addActionListener(cbAH);
			
		iceAvail 		= new JLabel( "" + icAvail);
			iceAvail.setForeground( createColor());
			iceAvail.setFont( new Font( fontFace, iceAvail.getFont().getStyle(), 18));
		
		priceTF			= new JTextField();					priceTF.setEditable(false);
			priceTF.setBackground(null);
		qtyTF 			= new JTextField();
			qtyTF.setBackground(null);
		
		gbc.gridy = 0;
			gbc.gridx = 0;
			commonPanel.add( brand, gbc);
			
			gbc.gridx = 1;
			gbc.gridwidth = 3;
			commonPanel.add( brandCB, gbc);
		
		gbc.gridy = 1;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			commonPanel.add( variety, gbc);
			
			gbc.gridx = 1;
			gbc.gridwidth = 3;
			commonPanel.add( varietyCB, gbc);
			
		gbc.gridy = 2;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			commonPanel.add( flavors, gbc);
			
			gbc.gridx = 1;
			gbc.gridwidth = 3;
			commonPanel.add( flavorsCB, gbc);
			
			gbc.gridx = 4;
			gbc.gridwidth = 1;
			commonPanel.add( iceAvail, gbc);
			
		gbc.gridy = 3;
			gbc.gridx = 0;
			commonPanel.add( price, gbc);
			
			gbc.gridx = 1;
			commonPanel.add( priceTF, gbc);
			
		gbc.gridy = 4;
			gbc.gridx = 0;
			commonPanel.add( qty, gbc);
			
			gbc.gridx = 1;
			commonPanel.add( qtyTF, gbc);
		
		commonPanel.setPreferredSize( new Dimension( panelX, panelY));
	}
	
	private void setupC1()
	{
		c1 = new JPanel();
		c1.setBackground(backgroundColor);
		c1.setOpaque(false);
		
		c1.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		Insets inset0 = new Insets( 5, 5, 5, 5);
		gbc.insets = inset0;
		
		sellCNames = new Vector<String>(5);
			sellCNames.addElement("Brand");
			sellCNames.addElement("Variety");
			sellCNames.addElement("Flavor");
			sellCNames.addElement("Quantity");
			sellCNames.addElement("SubTotal");
		
		sellOrders = new Vector<String[]>(2);
		sellICD = new Vector<IceCreamDetail>();
		
		sellList = new JTable( new DefaultTableModel( sellOrders, sellCNames)
		/*DefaultTableModel*/		{
										public boolean isCellEditable( int row, int col)
										{
											return false;
										}
									});
		sp = new JScrollPane( sellList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			sellList.setBackground(backgroundColor.brighter());
			sellList.setOpaque(false);
			sellList.getTableHeader().setBackground(backgroundColor);
			sellList.getTableHeader().setForeground( createColor());
			sellList.getTableHeader().setFont( new Font( "Papyrus", Font.PLAIN, 12));
			sp.getViewport().setBackground(backgroundColor);
			
		cart = new JPanel();
			cart.setLayout( new GridBagLayout());
			GridBagConstraints gbcC = new GridBagConstraints();
			gbcC.fill = GridBagConstraints.BOTH;
			
			gbcC.weightx = 1;
			gbcC.weighty = 1;
			gbcC.gridy = 0;
			gbcC.gridwidth = 2;
			cart.add( sp, gbcC);
		
		addButton 		= new JButton("Add ");
			addButton	.setActionCommand("sell>Add");
			addButton	.addActionListener(oBH);
		removeButton 	= new JButton("Remove ");
			removeButton.setActionCommand("sell>Rem");
			removeButton.addActionListener(oBH);
		sell		 	= new JButton("Sell ");
			sell		.setActionCommand("sell>Sell");
			sell		.addActionListener(oBH);
		
		Vector<JButton> btns = new Vector<JButton>();
			btns.add(addButton);
			btns.add(removeButton);
			btns.add(sell);
		dressButtons( btns, "Papyrus", 18, Font.BOLD + Font.ITALIC, SwingConstants.RIGHT, SwingConstants.BOTTOM, 180, 0);
		
		totalPrice = new JLabel("Total Price: 0.0", SwingConstants.LEFT);
			totalPrice	.setForeground( createColor());
			totalPrice	.setFont( new Font( "Papyrus", Font.BOLD + Font.ITALIC, 16));
		
		gbc.gridy = 0;
			gbc.gridwidth = 3;
			gbc.gridheight = 3;
			gbc.ipady = 200;
			c1.add( cart, gbc);
		
		gbc.gridy = 4;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.ipady = 10;
			c1.add( addButton, gbc);
			
			gbc.gridx = 1;
			c1.add( removeButton, gbc);
		
		//Sell
		gbc.gridy = 5;
			gbc.gridx = 0;
			gbc.ipadx = 60;
			gbc.ipady = 0;
			c1.add( sell, gbc);
			
			gbc.gridx = 1;
			gbc.ipadx = 0;
			c1.add( totalPrice, gbc);
		
		c1.setPreferredSize( new Dimension( panelX, panelY));
	}
	
	private void setupC2()
	{
		c2 = new JPanel();
		c2.setBackground(backgroundColor);
		c2.setOpaque(false);
		
		c2.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets( 5, 5, 5, 5);
		
		balance 	= new JLabel( "Balance:");
			balance.setFont( new Font( "Papyrus", Font.BOLD + Font.ITALIC, 12));
		
		addBrand 		= new JButton("Add a brand ");
			addBrand		.setActionCommand("addNewBrand");
			addBrand		.addActionListener(fBH);
		remBrand 		= new JButton("Remove a brand ");
			remBrand		.setActionCommand("remNewBrand");
			remBrand		.addActionListener(fBH);
			
		addVariety 		= new JButton("Add a variety ");
			addVariety		.setActionCommand("addNewVariety");
			addVariety		.addActionListener(fBH);
		remVariety 		= new JButton("Remove a variety ");
			remVariety		.setActionCommand("remNewVariety");
			remVariety		.addActionListener(fBH);
			
		addFlavor 		= new JButton("Add a flavor ");
			addFlavor		.setActionCommand("addNewFlavor");
			addFlavor		.addActionListener(fBH);
		remFlavor 		= new JButton("Remove a flavor ");
			remFlavor		.setActionCommand("remNewFlavor");
			remFlavor		.addActionListener(fBH);
		
		showTrans 		= new JButton("View Transactions ");
			showTrans		.setActionCommand("showTrans");
			showTrans		.addActionListener(cBH);
			
		addIceCream 	= new JButton("More Ice Cream ");
			addIceCream		.setActionCommand("addIceCream");
			addIceCream		.addActionListener(cBH);
		balanceTF 	= new JTextField( "" + theBalance);			balanceTF.setEditable(false);
			balanceTF.setBackground(null);
		
		Vector<JButton> btns = new Vector<JButton>();
			btns.add(addBrand);
			btns.add(remBrand);
			btns.add(addVariety);
			btns.add(remVariety);
			btns.add(addFlavor);
			btns.add(remFlavor);
			btns.add(addIceCream);
			btns.add(showTrans);
		dressButtons( btns, "Papyrus", 18, Font.BOLD + Font.ITALIC, SwingConstants.RIGHT, SwingConstants.BOTTOM, 180, 0);
		
		gbc.ipady = 40;
		gbc.gridy = 0;
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			c2.add( addBrand, gbc);
			gbc.gridx = 2;
			gbc.gridwidth = 1;
			c2.add( remBrand, gbc);
			
		gbc.gridy = 1;
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			c2.add( addVariety, gbc);
			gbc.gridx = 2;
			gbc.gridwidth = 1;
			c2.add( remVariety, gbc);
			
		gbc.gridy = 2;
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			c2.add( addFlavor, gbc);
			gbc.gridx = 2;
			gbc.gridwidth = 1;
			c2.add( remFlavor, gbc);
			
		gbc.insets = new Insets( 25, 5, 5, 5);
		gbc.gridy = 3;
			gbc.gridx = 2;
			c2.add( showTrans, gbc);
			
		gbc.ipady = 20;
		gbc.gridy = 4;
			gbc.gridx = 0;
			c2.add( balance, gbc);
			
			gbc.gridx = 1;
			c2.add( balanceTF, gbc);
		
			gbc.gridx = 2;
			c2.add( addIceCream, gbc);
		
		c2.setPreferredSize( new Dimension( panelX, panelY));
	}
	
	private void setupAIC()
	{
		aIC = new JPanel();
		aIC.setBackground(backgroundColor);
		aIC.setOpaque(false);
		
		aIC.setLayout( new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets( 5, 5, 5, 5);
		gbc.weightx = 1;
		
		Dimension addRemoveButtons = new Dimension( 180, 0);
		
		aICCNames = new Vector<String>(2);
			aICCNames.addElement("Brand");
			aICCNames.addElement("Variety");
			aICCNames.addElement("Flavor");
			aICCNames.addElement("Price");
			aICCNames.addElement("Quantity");
			aICCNames.addElement("SubTotal");
		
		aICICD = new Vector<IceCreamDetail>();
		aICOrders 	= new Vector<String[]>(2);
		
		aICList 	= new JTable( new DefaultTableModel(  aICOrders, aICCNames)
		/*DefaultTableModel*/	{
									public boolean isCellEditable( int row, int col)
									{
										if(col == 3 || col == 4)
										{
											return true;
										}
										else
										{
											return false;
										}
									}
								});
		aICSP 		= new JScrollPane( aICList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			aICList.setBackground( backgroundColor.brighter());
			aICList.getTableHeader().setBackground(backgroundColor);
			aICList.getTableHeader().setFont( new Font( "Papyrus", Font.PLAIN, 12));
			aICList.getTableHeader().setForeground( createColor());
			aICSP.getViewport().setBackground(backgroundColor);
			aICSP.setOpaque(false);
			
		add 		= new JButton("Add ");
			add			.setActionCommand("aIC>Add");
			add			.addActionListener(oBH);
		remove 		= new JButton("Remove ");
			remove		.setActionCommand("aIC>Rem");
			remove		.addActionListener(oBH);
		addToStocks = new JButton("Add To Stocks ");
			addToStocks	.setActionCommand("aIC>ats");
			addToStocks	.addActionListener(oBH);
		remFromStocks = new JButton("Remove ");
			remFromStocks	.setActionCommand("aIC>rfs");
			remFromStocks	.addActionListener(oBH);
		
		Vector<JButton> btns = new Vector<JButton>();
			btns.add(add);
			btns.add(remove);
		dressButtons( btns, "Papyrus", 18, Font.BOLD + Font.ITALIC, SwingConstants.RIGHT, SwingConstants.BOTTOM, 90, 0);
		Vector<JButton> btns1 = new Vector<JButton>();
			btns1.add(addToStocks);
		dressButtons( btns1, "Papyrus", 12, Font.BOLD + Font.ITALIC, SwingConstants.RIGHT, SwingConstants.BOTTOM, 150, 0);
		
		stp = new JLabel( "Total Price: 0.0", SwingConstants.LEFT);
			stp	.setFont(new Font( "Papyrus", Font.BOLD + Font.ITALIC, 16));
			stp	.setForeground( createColor());
		
		gbc.gridy = 0;
			gbc.gridx = 0;
			gbc.ipady = 200;
			gbc.ipadx = 0;
			gbc.gridwidth = 2;
			aIC.add( aICSP, gbc);
			gbc.ipady = 10;
			
		gbc.gridy = 1;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			aIC.add( add, gbc);
			
			gbc.gridx = 1;
			aIC.add( remove, gbc);
			
		gbc.gridy = 2;
			gbc.gridx = 0;
			aIC.add( addToStocks, gbc);
			
			gbc.gridx = 1;
			aIC.add( stp, gbc);
		
		aIC.setPreferredSize( new Dimension( panelX, panelY));
	}
	
	private void setupShowStocks()
	{
		stocksPanel = new JPanel();
			stocksPanel.setBackground(backgroundColor);
			stocksPanel.setOpaque(false);
		
		stocksPanel.setLayout( new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets( 5, 5, 5, 5);
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		stocksCNames = new Vector<String>();
			stocksCNames.addElement("Brand");
			stocksCNames.addElement("Variety");
			stocksCNames.addElement("Flavor");
			stocksCNames.addElement("Price");
			stocksCNames.addElement("Quantity");
		
		stocksData = new Vector<Vector<String>>();
		
		fillUpStocksData( stocksData);
		
		stocksTable = new JTable( new DefaultTableModel(  stocksData, stocksCNames)
		/*DefaultTableModel*/{
								public boolean isCellEditable( int row, int col)
								{
									return false;
								}
							});
							
		stocksView = new JScrollPane( stocksTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			stocksTable.setBackground( backgroundColor.brighter());
			stocksTable.getTableHeader().setBackground(backgroundColor);
			stocksTable.getTableHeader().setFont(new Font( "Papyrus", Font.PLAIN, 12));
			stocksTable.getTableHeader().setForeground( createColor());
			stocksView.getViewport().setBackground(backgroundColor);
			stocksView.setOpaque(false);
			
			showStocksElements(stocksTable);
		
		stocksPanel.add( stocksView, gbc);
		
		stocksPanel.setPreferredSize( new Dimension( panelX, panelY));
	}
	
	private void setupSettingsPanel()
	{
		sPanel = new JPanel();
			sPanel.setBackground(new Color( 0, 0, 0));
			sPanel.setOpaque(false);
		
		sPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.insets = new Insets( 5, 5, 5, 5);
		gbc.ipadx = 15;
		gbc.ipady = 15;
		
		bgImage 	= new JLabel( "Bg Image:", SwingConstants.RIGHT);
			bgImage.setForeground( createColor());
			bgImage.setFont( new Font( fontFace, Font.PLAIN, 18));
		bgColor		= new JLabel( "Bg Color:", SwingConstants.RIGHT);
			bgColor.setForeground( createColor());
			bgColor.setFont( new Font( fontFace, Font.PLAIN, 18));
			
		bgImageCB 	= new JComboBox( bgImageList);
			bgImageCB.setForeground( createColor());
			bgImageCB.setBackground(backgroundColor);
			bgImageCB.setOpaque(false);
			bgImageCB.setFont( new Font( fontFace, Font.PLAIN, 18));
			bgImageCB.addActionListener(bcH);
		
		bgColorCB	= new JComboBox( bgColorList);
			bgColorCB.setForeground( createColor());
			bgColorCB.setBackground(backgroundColor);
			bgColorCB.setOpaque(false);
			bgColorCB.setFont( new Font( fontFace, Font.PLAIN, 18));
			bgColorCB.addActionListener(bcH);
		
		JLabel m = new JLabel("-changing the background color requires restart.", SwingConstants.RIGHT);
			m.setVerticalAlignment(SwingConstants.TOP);
		
		gbc.gridy = 0;
			gbc.gridx = 0;
			sPanel.add( bgImage, gbc);
			
			gbc.gridx = 1;
			sPanel.add( bgImageCB, gbc);
		
		gbc.gridy = 1;
			gbc.gridx = 0;
			sPanel.add( bgColor, gbc);
			
			gbc.gridx = 1;
			sPanel.add( bgColorCB, gbc);
		
		gbc.gridy = 2;
			gbc.gridx = 1;
			gbc.ipady = 50;
			sPanel.add( m, gbc);
			
		sPanel.setPreferredSize( new Dimension( panelX, panelY));
	}
	
	private void setupTransView()
	{
		transPanel = new JPanel();
			transPanel.setBackground(backgroundColor);
			transPanel.setOpaque(false);
			
			transPanel.setLayout( new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets( 5, 5, 5, 5);
			gbc.weightx = 1;
			gbc.weighty = 1;
		
		JLabel filters = new JLabel("Filters");
			filters.setForeground( createColor());
			filters.setFont( new Font( fontFace, Font.BOLD, 16));
		
		JButton in = new JButton("\"IN\"");
		JButton out = new JButton("\"OUT\"");
		
		Vector<JButton> bt = new Vector<JButton>();
			bt.add(in);
			bt.add(out);
		dressButtons( bt, fontFace, 12, Font.BOLD, SwingConstants.RIGHT, SwingConstants.BOTTOM, 60, 25);
		
		Vector<String> transCNames = new Vector<String>(2);
			transCNames.addElement("Trans. Number");
			transCNames.addElement("Trans.Type");
			transCNames.addElement("Date");
			transCNames.addElement("Time");
			transCNames.addElement("No.of Items");
		
		//transVector = new Vector<String[]>(2);
		
		transTable 	= new JTable( new DefaultTableModel(  transVector, transCNames)
		/*DefaultTableModel*/	{
									public boolean isCellEditable( int row, int col)
									{
										return false;
									}
								});
		JScrollPane transSP = new JScrollPane( transTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			transTable.setBackground( backgroundColor.brighter());
			transTable.getTableHeader().setBackground(backgroundColor);
			transTable.getTableHeader().setFont( new Font( "Papyrus", Font.PLAIN, 12));
			transTable.getTableHeader().setForeground( createColor());
			transSP.setPreferredSize( new Dimension( 300, 200));
			transSP.getViewport().setBackground(backgroundColor);
			transSP.setOpaque(false);
		
		gbc.gridy = 0;
			gbc.gridx = 0;
			transPanel.add( filters, gbc);
			gbc.gridx = 1;
			transPanel.add( in);
			gbc.gridx = 2;
			transPanel.add( out);
		gbc.gridy = 1;
			gbc.gridx = 0;
			gbc.gridwidth = 3;
			transPanel.add( transSP, gbc);

	}
	
	private void setupTransDetails()
	{
		transDPanel = new JPanel();
			transDPanel.setBackground(backgroundColor);
			transDPanel.setOpaque(false);
		
		transDPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		Vector<String> transDCNames = new Vector<String>();
			transDCNames.addElement("Brand");
			transDCNames.addElement("Variety");
			transDCNames.addElement("Flavor");
			transDCNames.addElement("Price");
			transDCNames.addElement("Quantity");
			transDCNames.addElement("SubTotal");
		
		transDTable 	= new JTable( new DefaultTableModel(  transVector, transDCNames)
		/*DefaultTableModel*/	{
									public boolean isCellEditable( int row, int col)
									{
										return false;
									}
								});
		JScrollPane transDSP = new JScrollPane( transTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			transDTable.setBackground( backgroundColor.brighter());
			transDTable.getTableHeader().setBackground(backgroundColor);
			transDTable.getTableHeader().setFont( new Font( "Papyrus", Font.PLAIN, 12));
			transDTable.getTableHeader().setForeground( createColor());
			transDSP.setPreferredSize( new Dimension( 300, 200));
			transDSP.getViewport().setBackground(backgroundColor);
			transDSP.setOpaque(false);
		
		transDPanel.add( transDTable);
	}
	
	////////      ACTION LISTENERS      ////////
	
	private class CardButtonHandler implements ActionListener
	{//handles	Sell,	Stocks,	addIceCream,	showSettings,	StocksTablePanel, showTrans
		public void actionPerformed(ActionEvent e)
		{
			//get the cardLayout objects for each panel that uses cardLayout
			CardLayout cl1 = (CardLayout)cards1.getLayout();
			CardLayout cl2 = (CardLayout)cards2.getLayout();
			
			if(e.getActionCommand().equals("Sell"))
			{
				cl2.show( cards2, "Sell");
				priceTF.setEditable(false);
			}
			else if(e.getActionCommand().equals("Stocks"))
			{
				cl2.show( cards2, "Stocks");
				priceTF.setEditable(false);
			}
			else if(e.getActionCommand().equals("addIceCream"))
			{
				cl2.show( cards2, "AddIC");
				priceTF.setEditable(true);
			}
			else if(e.getActionCommand().equals("showSettings"))
			{
				JToggleButton tb = (JToggleButton)e.getSource();
				if(tb.isSelected())
				{
					cl1.show( cards1, "showSettings");
				}
				else
				{
					cl1.show( cards1, "commonPanel");
				}
			}
			else if(e.getActionCommand().equals("aIC>sST"))
			{
				JToggleButton tb = (JToggleButton)e.getSource();
				if(tb.isSelected())
				{
					cl1.show( cards1, "showStocks");
				}
				else
				{
					cl1.show( cards1, "commonPanel");
				}
			}
			else if(e.getActionCommand().equals("showTrans"))
			{
				cl2.show( cards2, "transView");
			}
		}
	}
	
	private class FormsButtonHandler implements ActionListener
	{//handles	add and remove buttons from Stocks Panel,	ABOUT dialog
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("addNewBrand"))
			{//adds a brand to the brand selection
				String input = JOptionPane.showInputDialog("Add a new ice cream brand").trim();
				if( input.equals(null))
				{
					JOptionPane.showMessageDialog( null, "Enter the name of the new brand");
				}
				else
				{
					brandList.addElement( input);
				}
			}
			else if(e.getActionCommand().equals("addNewVariety"))
			{//adds a variety to the selection
				String input = JOptionPane.showInputDialog("Add a new ice cream variety").trim();
				if( input.equals(null))
				{
					JOptionPane.showMessageDialog( null, "Enter a name for a variety");
				}
				else
				{
					varietyList.addElement( input);
				}
			}
			else if(e.getActionCommand().equals("addNewFlavor"))
			{//adds a flavor to the selection
				String input = JOptionPane.showInputDialog("Add a new ice cream flavor").trim();
				if( input.equals(null))
				{
					JOptionPane.showMessageDialog( null, "Enter a name for a flavor");
				}
				else
				{
					flavorList.addElement( input);
				}
			}
			else if(e.getActionCommand().equals("remNewBrand"))
			{//removes a brand from the selection
				String input = brandList.elementAt( brandList.indexOf(JOptionPane.showInputDialog( new JComboBox()
																									, "Select a brand to remove"
																									, "Input"
																									, JOptionPane.QUESTION_MESSAGE
																									, null
																									, brandList.toArray(new String[0])
																									, brandList.elementAt(0)))			);
				if( !input.equals(null))
				{
					if( !input.equals("select brand..."))
					{
						brandList.removeElementAt( brandList.indexOf(input));
					}
				}
			}
			else if(e.getActionCommand().equals("remNewVariety"))
			{//removes a variety from the selection
				String input = varietyList.elementAt( varietyList.indexOf(JOptionPane.showInputDialog( new JComboBox()
																									, "Select a variety to remove"
																									, "Input"
																									, JOptionPane.QUESTION_MESSAGE
																									, null
																									, varietyList.toArray(new String[0])
																									, varietyList.elementAt(0)))			);
				if( !input.equals(null))
				{
					if( !input.equals("select variety..."))
					{
						varietyList.removeElementAt( varietyList.indexOf(input));
					}
				}
			}
			else if(e.getActionCommand().equals("remNewFlavor"))
			{//removes a flavor from the selection
				String input = flavorList.elementAt( flavorList.indexOf(JOptionPane.showInputDialog( new JComboBox()
																									, "Select a flavor to remove"
																									, "Input"
																									, JOptionPane.QUESTION_MESSAGE
																									, null
																									, flavorList.toArray(new String[0])
																									, flavorList.elementAt(0)))			);
				if( !input.equals(null))
				{
					if( !input.equals("select flavor..."))
					{
						flavorList.removeElementAt( flavorList.indexOf(input));
					}
				}
			}
			else if(e.getActionCommand().equals("About"))
			{//displays "ABOUT"
				String message = "T H E   I C E   C R E A M   S H O P\n"
								+"- Paul Albrick Coronia";
				JOptionPane.showMessageDialog( null, message, "About", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
	}
	
	private class OperationButtonHandler implements ActionListener
	{//handles	sell's add, rem, sell button && aIC's add, rem, ats
		public void actionPerformed(ActionEvent e)
		{
			DefaultTableModel sellModel = (DefaultTableModel)sellList.getModel();
			DefaultTableModel aICModel = (DefaultTableModel)aICList.getModel();
			
			String	iB = "";
			String 	iV = "";
			String 	iF = "";
			Double 	iP = 0.0;
			int		iQ = 0;
			int		avail = 0;
			
			try
			{
				iB = (String)brandCB.getSelectedItem();
				iV = (String)varietyCB.getSelectedItem();
				iF = (String)flavorsCB.getSelectedItem();
				iP = Double.parseDouble(priceTF.getText());
				iQ = Integer.parseInt(qtyTF.getText());
				avail = Integer.parseInt( iceAvail.getText() );
			}
			catch(NumberFormatException nfe)
			{
				if(e.getActionCommand().equals("sell>Add") || e.getActionCommand().equals("aIC>Add"))
				{
					JOptionPane.showMessageDialog( null, "The quantity input is invalid.");
				}
			}
			
			IceCreamDetail ICDTemp = new IceCreamDetail( new IceCream( iB, iV, iF, iP), iQ);
			
			if( iB.equals("select brand...") || iV.equals("select variety...") || iF.equals("select flavor..."))
			{
				if(e.getActionCommand().equals("sell>Add") || e.getActionCommand().equals("aIC>Add"))
				{
					JOptionPane.showMessageDialog( null, "Please select at least 1 brand, 1 variety, and 1 flavor.");
				}
			}
			else
			{
				////Sell's operations
				if(e.getActionCommand().equals("sell>Add"))
				{
					if(iQ > avail)
					{
						JOptionPane.showMessageDialog( null, "The selected ice cream's availability\nis less than the quantity.", "Insufficient stocks", JOptionPane.WARNING_MESSAGE);
					}
					else if( iQ <= 0)
					{
						JOptionPane.showMessageDialog( null, "Please fill the quantity up with valid numbers.");
					}
					else if(avail <= 0)
					{
						JOptionPane.showMessageDialog( null, "The selected ice cream is not available.");
					}
					else
					{
						boolean added = false;
						for( IceCreamDetail icd : sellICD)
						{
							if(icd.getIceCream().getBrand().equals(ICDTemp.getIceCream().getBrand())
							&& icd.getIceCream().getVariety().equals(ICDTemp.getIceCream().getVariety())
							&& icd.getIceCream().getFlavor().equals(ICDTemp.getIceCream().getFlavor()))
							{
								icd.increaseQuantityBy(ICDTemp.getQuantity());
								added = true;
								break;
							}
						}
						if(added == false)
						{
							sellICD.addElement(ICDTemp);
						}
						icAvail = Integer.parseInt(iceAvail.getText());
						icAvail -= ICDTemp.getQuantity();
						iceAvail.setText("" + icAvail);
						updateSellList();
					}
				}
				
				////AIC's operations
				else if(e.getActionCommand().equals("aIC>Add"))
				{
					if( iQ <= 0)
					{
						JOptionPane.showMessageDialog( null, "Please fill the quantity up with valid numbers.");
					}
					else
					{
						boolean added = false;
						for( IceCreamDetail icd : aICICD)
						{
							if(icd.getIceCream().getBrand().equals(ICDTemp.getIceCream().getBrand())
							&& icd.getIceCream().getVariety().equals(ICDTemp.getIceCream().getVariety())
							&& icd.getIceCream().getFlavor().equals(ICDTemp.getIceCream().getFlavor()))
							{
								if(icd.getIceCream().getPrice() != ICDTemp.getIceCream().getPrice())
								{
									String message = "Do you want to change the price\n"
													+" From: " + icd.getIceCream().getPrice()
													+" To: " + ICDTemp.getIceCream().getPrice() + "?";
									if(JOptionPane.showConfirmDialog( null, message, "Change Price?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
									{
										icd.getIceCream().setPrice(ICDTemp.getIceCream().getPrice());
									}
								}
								icd.increaseQuantityBy(ICDTemp.getQuantity());
								added = true;
								break;
							}
						}
						if(added == false)
						{
							aICICD.addElement(ICDTemp);
						}
						updateAICList();
					}
				}
			}
			if(e.getActionCommand().equals("sell>Rem"))
			{
				int[] r = sellList.getSelectedRows();
				if( r.length <= 0)
				{
					JOptionPane.showMessageDialog( null, "Please select at least 1 row to remove.");
				}
				else
				{
					for( int i = r.length-1; i >= 0; i--)
					{
						sellICD.removeElementAt(i);
						icAvail = Integer.parseInt(iceAvail.getText());
						icAvail += ICDTemp.getQuantity();
						iceAvail.setText("" + icAvail);
					}
				}
				updateSellList();
			}
			else if(e.getActionCommand().equals("aIC>Rem"))
			{
				int[] r = aICList.getSelectedRows();
				if( r.length <= 0)
				{
					JOptionPane.showMessageDialog( null, "Please select at least 1 row to remove.");
				}
				else
				{
					for( int i = r.length-1; i >= 0; i--)
					{
						aICICD.removeElementAt(i);
					}
				}
				updateAICList();
			}
			
			if(e.getActionCommand().equals("sell>Sell"))
			{
				for( IceCreamDetail icd : sellICD)
				{
					for( IceCreamDetail icds : iceCreamStocks)
					{
						if(icd.getIceCream().getBrand().equals(icds.getIceCream().getBrand())
						&& icd.getIceCream().getVariety().equals(icds.getIceCream().getVariety())
						&& icd.getIceCream().getFlavor().equals(icds.getIceCream().getFlavor()))
						{
							icds.decreaseQuantityBy(icd.getQuantity());
							theBalance += icd.getSubTotal();
							balanceTF.setText("" + theBalance);
						}
					}
				}
				for( IceCreamDetail d : iceCreamStocks)
				{
					if(d.getQuantity() <= 0)
					{
						iceCreamStocks.removeElementAt(iceCreamStocks.indexOf(d));
					}
				}
				if(sellICD.size() > 0)
				{
					date = new Date();//               0123456789012345678
					dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String fDate = dateFormat.format(date);
					dateFormat = new SimpleDateFormat("HH:mm:ss");
					String fTime = dateFormat.format(date);
						
						tDPlaceHolder = new Vector<IceCreamDetail>();
						tDPlaceHolder.setSize(sellICD.size());
						Collections.copy( tDPlaceHolder, sellICD);
						
					transactionData.addElement(new Transaction(transactionData.size(), "OUT", fDate, fTime, tDPlaceHolder));
					
					Vector<String> transRow = new Vector<String>();
						transRow.add("" + transactionData.elementAt( transactionData.size()-1).getTransactionNumber());
						transRow.add( transactionData.elementAt( transactionData.size()-1).getTransactionType());
						transRow.add( transactionData.elementAt( transactionData.size()-1).getDate());
						transRow.add( transactionData.elementAt( transactionData.size()-1).getTime());
					transVector.add( transRow);
					
					sellICD.removeAllElements();
					updateSellList();
					updateStocksList();
				}
			}
			else if(e.getActionCommand().equals("aIC>ats"))
			{
				boolean added = false;
				for( IceCreamDetail icd : aICICD)
				{
					for( IceCreamDetail icds : iceCreamStocks)
					{
						if(icd.getIceCream().getBrand().equals(icds.getIceCream().getBrand()) && 
							icd.getIceCream().getVariety().equals(icds.getIceCream().getVariety()) &&
							icd.getIceCream().getFlavor().equals(icds.getIceCream().getFlavor()))
						{
							icds.increaseQuantityBy(icd.getQuantity());
							icds.getIceCream().setPrice(icd.getIceCream().getPrice());
							added = true;
							theBalance -= icd.getSubTotal();
							balanceTF.setText("" + theBalance);
						}
					}
					if(!added)
					{
						iceCreamStocks.addElement(icd);
						added = true;
						theBalance -= icd.getSubTotal();
						balanceTF.setText("" + theBalance);
					}
				}
				if(aICICD.size() > 0)
				{
					date = new Date();//               0123456789012345678
					dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String fDate = dateFormat.format(date);
					dateFormat = new SimpleDateFormat("HH:mm:ss");
					String fTime = dateFormat.format(date);
					
						tDPlaceHolder = new Vector<IceCreamDetail>();
						tDPlaceHolder.setSize(aICICD.size());
						Collections.copy( tDPlaceHolder, aICICD);
					
					transactionData.addElement(new Transaction(transactionData.size(), "IN", fDate, fTime, tDPlaceHolder));
					
					icAvail = Integer.parseInt(iceAvail.getText());
					icAvail += ICDTemp.getQuantity();
					iceAvail.setText("" + icAvail);
					
					aICICD.removeAllElements();
					updateAICList();
					updateStocksList();
				}
			}
		}
	}
	
	private class ComboBoxActionHandler implements ActionListener
	{//handles the changes in the three comboBoxes in the commonPanel
		public void actionPerformed(ActionEvent e)
		{//gets the input...
			String b = (String)brandCB.getSelectedItem();
			String v = (String)varietyCB.getSelectedItem();
			String f = (String)flavorsCB.getSelectedItem();
			
			//then, updates the availability notification
			checkAvailability( b, v, f);
		}
	}
	
	private class WindowClosingHandler extends WindowAdapter
	{//handles file saving before closing
		@Override
		public void windowClosing(WindowEvent e)
		{
			saveIceCreamStocksFile();
			saveSettingsFile();
			saveTransactions();
		}
	}
	
	private class SettingsButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("applyColor"))
			{
				String item = (String)bgColorCB.getSelectedItem();
				
				if(item == "White")
				{
					backgroundColor = Color.white;
				}
				else if(item == "Gray")
				{
					backgroundColor = Color.gray;
				}
				else if(item == "Cyan")
				{
					backgroundColor = Color.cyan;
				}
				else if(item == "Magenta")
				{
					backgroundColor = Color.magenta;
				}
				mainPanel.setBackground(backgroundColor);
			}	
		}
	}
	
	private class BackgroundChangeHandler implements ActionListener
	{//handles JLabel-bg
		public void actionPerformed(ActionEvent e)
		{
			image = (String)bgImageCB.getSelectedItem();
			image += ".png";
			bg.setIcon( new ImageIcon(getClass().getResource(image)));
		}
	}
	
	////////      MISCELLANEOUS	METHODS      ////////
	
	private void initializeShop()
	{//a start of everything
		theBalance = 50000.00;
		transactionData = new Vector<Transaction>();
		
		bgImageList = new String[3];
			bgImageList[2] = "IceCreamCones";
			bgImageList[1] = "Bubbles";
			bgImageList[0] = "Bubbles2";
		bgColorList = new String[4];
			bgColorList[0] = "White";
			bgColorList[1] = "Cyan";
			bgColorList[2] = "Magenta";
			bgColorList[3] = "Gray";
		
		loadICStocks();
		loadSettingsFile();
		loadTransactions();
		initializeListeners();
		setupJOptionPane();
		setupMainPanel();
	}
	
	private void loadICStocks()
	{//loads iceCreamShop from a file
		try
		{
			Scanner inFile = new Scanner( new FileReader("iceCreamStocks.file"));//the Scanner
			
			iceCreamStocks = new Vector<IceCreamDetail>(); //initialize the iceCreamStocks variable
			if(inFile.hasNext())
			{
				Vector<IceCreamDetail> icdT = new Vector<IceCreamDetail>();//initialize an IceCreamDetail place holders
				theBalance = inFile.nextDouble();//get the balance
				int c = inFile.nextInt();//get the number of ice creams to gather
				
				for(int i = 0; i < c; i++)
				{//gets an ice cream per line...
					String 	b 	= inFile.next();
					String 	v 	= inFile.next();
					String 	f 	= inFile.next();
					double 	p 	= inFile.nextDouble();
					int 	q	= inFile.nextInt();inFile.nextDouble();
					//then, adds it to the place holders
					iceCreamStocks.addElement( new IceCreamDetail( new IceCream( b, v, f, p), q));
				}
			}
			else
			{
				//Empty File
			}
			updateLists();//arrange the list then, sets the selection lists
		}
		catch(IOException ioE)
		{
			//System.out.println(ioE.getMessage());
		}
	}
	
	private void loadSettingsFile()
	{//loads bakcgroundColor && bgImage
		try
		{
			Scanner inFile = new Scanner( new FileReader("icsSettings.file"));
			
			String img = "IceCreamCones";
			String clr = "White";
			
			if(inFile.hasNext())
			{
				img = inFile.next();
				clr = inFile.next();
			}
			
			bg =  new JLabel(new ImageIcon(getClass().getResource(img + ".png")));
			
			if(clr.equals("White"))
			{
				backgroundColor = Color.white;
			}
			else if(clr.equals("Cyan"))
			{
				backgroundColor = Color.cyan;
			}
			else if(clr.equals("Magenta"))
			{
				backgroundColor = Color.magenta;
			}
			else if(clr.equals("Gray"))
			{
				backgroundColor = Color.gray;
			}
		}
		catch(IOException ioE)
		{
			//nothing to do here...
		}
	}
	
	private void loadTransactions()
	{//loads transaction history from a file
		try
		{
			Scanner inFile = new Scanner( new FileReader("transactionsHistory.file"));
			
			transactionData = new Vector<Transaction>();
			transVector = new Vector<Vector<String>>();
			
			if(inFile.hasNext())
			{
				int transCount = inFile.nextInt();
				Vector<String> transRow = new Vector<String>(4);
				
				for( int i = 0; i < transCount; i++)
				{
					int transNumber = inFile.nextInt();
					String transType = inFile.next();
					String date = inFile.next();
					String time = inFile.next();
					int icdCount = inFile.nextInt();
					
					transRow.addElement("" + transNumber);
					transRow.addElement(transType);
					transRow.addElement(date);
					transRow.addElement(time);
					transRow.addElement("" + icdCount);
					
					transVector.addElement( transRow);
					
					Vector<IceCreamDetail> icdTemp = new Vector<IceCreamDetail>();
					
					for( int j = 0; j < icdCount; j++)
					{
						String bnd = inFile.next();
						String var = inFile.next();
						String fla = inFile.next();
						Double pri = inFile.nextDouble();
						int qty = inFile.nextInt();
						Double sTotal = inFile.nextDouble();
						
						icdTemp.addElement( new IceCreamDetail( new IceCream( bnd, var, fla, pri), qty));
					}
					transactionData.addElement( new Transaction( transNumber, transType, date, time, icdTemp));
				}
			}
		}
		catch(IOException ioE)
		{
			//nothing to do here
		}
	}
	
	private void saveIceCreamStocksFile()
	{//saves iceCreamStocks to a file
		try
		{
			PrintWriter outFile = new PrintWriter("iceCreamStocks.file");//the printWriter...
			
			outFile.print("" + theBalance + " ");//saves the balance
			outFile.print(iceCreamStocks.size());//writes the number of ice creams in the stocks
			
			for( IceCreamDetail icd : iceCreamStocks)
			{//write the ice creams in the file
				outFile.print("\n");
				outFile.print(icd.toString());
			}
			outFile.close();//don't forget to close the file after use
		}
		catch(IOException ioE)
		{
			//nothing to do here
		}
	}
	
	private void saveSettingsFile()
	{//saves backgroundColor && bgImage to a file	
		String img = (String)bgImageCB.getSelectedItem();
		String clr = (String)bgColorCB.getSelectedItem();
		
		try
		{
			PrintWriter outFile = new PrintWriter("icsSettings.file");
			
			outFile.print(img + " " + clr);
			
			outFile.close();
		}
		catch(IOException ioE)
		{
			//nothing to do here...
		}
	}
	
	private void saveTransactions()
	{//saves transaction history to a file
		try
		{
			PrintWriter outFile = new PrintWriter("transactionsHistory.file");
			
			outFile.print(transactionData.size() + "\n");
			for( Transaction t : transactionData)
			{
				outFile.print( t.toString());
			}
			outFile.close();
		}
		catch(IOException ioE)
		{
			//nothing to do here
		}
	}
	
	private void initializeListeners()
	{//initializes listeners for use
		cBH 	= new CardButtonHandler();
		fBH 	= new FormsButtonHandler();
		oBH 	= new OperationButtonHandler();
		wcH 	= new WindowClosingHandler();
		cbAH 	= new ComboBoxActionHandler();
		bcH		= new BackgroundChangeHandler();
		setBH	= new SettingsButtonHandler();
	}
	
	private void showStocksElements( JTable t)
	{//adds all stocksData elements to the stocks table
		DefaultTableModel model = (DefaultTableModel)t.getModel();
		
		for( Vector<String> s : stocksData)
		{
			model.addRow(s);
		}
	}
	
	private void setLists( Vector<IceCreamDetail> icList)
	{//sets list for combo boxes
		Vector<String> b = new Vector<String>();
		Vector<String> v = new Vector<String>();
		Vector<String> f = new Vector<String>();
		
		Vector<String> stocksDataTemp = new Vector<String>();
		stocksData = new Vector<Vector<String>>();
		
		for( IceCreamDetail icd : icList)
		{
			b.addElement(icd.getIceCream().getBrand());
			v.addElement(icd.getIceCream().getVariety());
			f.addElement(icd.getIceCream().getFlavor());
			
			stocksDataTemp.addElement(icd.getIceCream().getBrand());
			stocksDataTemp.addElement(icd.getIceCream().getVariety());
			stocksDataTemp.addElement(icd.getIceCream().getFlavor());
			stocksDataTemp.addElement("" + icd.getIceCream().getPrice());
			stocksDataTemp.addElement("" + icd.getQuantity());
			
			stocksData.addElement(stocksDataTemp);
		}
		
		brandList = new Vector<String>();
		brandList.setSize(b.size());
		Collections.copy( brandList, b);
		brandList.insertElementAt("select brand...", 0);
		
		varietyList = new Vector<String>();
		varietyList.setSize(v.size());
		Collections.copy( varietyList, v);
		varietyList.insertElementAt("select variety...", 0);
		
		flavorList = new Vector<String>();
		flavorList.setSize(f.size());
		Collections.copy( flavorList, f);
		flavorList.insertElementAt("select flavor...", 0);
	}
	
	private void updateLists()
	{//gets the iceCreamStocks and passes it to setList()
		Vector<IceCreamDetail> 	icL = new Vector<IceCreamDetail>();
		
		Vector<IceCreamDetail> 	icT = new Vector<IceCreamDetail>();
		
		if(iceCreamStocks.size() <= 0)
		{
			System.out.println("iceCreamStocks is null");
		}
		else
		{
			icL.addElement(iceCreamStocks.elementAt(0));
			
			icT.setSize(iceCreamStocks.size());
			Collections.copy( icT, iceCreamStocks);
			
			for( IceCreamDetail t : icT)
			{
				if(!icL.contains(t))
				{
					icL.addElement(t);
				}
			}
		}
		
		setLists( icL);
	}
	
	private void checkAvailability( String b, String v, String f)
	{//updates the availability counter and sets the price TextField
		int c = 0;
		double p = 0.00;
		
		for( IceCreamDetail icd : iceCreamStocks)
		{
			if( b.equals(icd.getIceCream().getBrand()) && v.equals(icd.getIceCream().getVariety()) && f.equals(icd.getIceCream().getFlavor()))
			{
			//	c++;
				c = icd.getQuantity();
				p = icd.getIceCream().getPrice();
			}
		}
		
		iceAvail.setText("" + c);
		priceTF.setText("" + p);
	}
	
	private void fillUpStocksData( Vector<Vector<String>> d)
	{//updates the stocks table
		if(d == null)
		{
			for( IceCreamDetail icd : iceCreamStocks)
			{
				Vector<String> r = new Vector<String>();
				
				r.addElement(icd.getIceCream().getBrand());
				r.addElement(icd.getIceCream().getVariety());
				r.addElement(icd.getIceCream().getFlavor());
				r.addElement("" + icd.getIceCream().getPrice());
				r.addElement("" + icd.getQuantity());
				
				d.add(r);
			}
		}
		else
		{
			d = new Vector<Vector<String>>();
		}
	}
	
	private void dressButtons( Vector<JButton> buttons, String fontName, int fontSize, int fStyle, int alignmentX, int alignmentY, int dimX, int dimY)
	{//for setting up buttons
		
		for( JButton c : buttons)
		{
			c.setBorder(createBorder());
			c.setBackground(new Color( 0, 0, 0, 0.0f));
			c.setOpaque(false);
			c.setForeground( createColor());
			c.setMargin(new Insets( 0, 0, 0, 0));
			c.setFont( new Font( fontName, fStyle, fontSize));
			c.setHorizontalAlignment(alignmentX);
			c.setVerticalAlignment(alignmentY);
			c.setPreferredSize( new Dimension( dimX, dimY));
		}
	}
	
	private Color createColor()
	{//creates color based from the background color for better contrast
		int r = backgroundColor.getRed();
		int g = backgroundColor.getGreen();
		int b = backgroundColor.getBlue();
		
		if(r > (225/2))
		{
			r -= (225/2);
		}
		else
		{
			r += (225/2);
		}
		if(g > (225/2))
		{
			g -= (225/2);
		}
		else
		{
			g += (225/2);
		}
		if(b > (225/2))
		{
			b -= (225/2);
		}
		else
		{
			b += (225/2);
		}
		return (new Color( r, g, b));
	}
	
	private Border createBorder()
	{//for custom button border
		int r = backgroundColor.getRed();
		int g = backgroundColor.getGreen();
		int b = backgroundColor.getBlue();
		Border b1, b2, b3, b4, b5;									//   r,   g,   b
			b1 = BorderFactory.createMatteBorder( 0, 0, 1, 3, new Color( r, g, b));
			b2 = BorderFactory.createMatteBorder( 0, 0, 2, 2, new Color( r-(r/3), g-(g/3), b-(b/3)));
			b3 = BorderFactory.createCompoundBorder( b1, b2);
			b4 = BorderFactory.createMatteBorder( 0, 0, 3, 1, new Color( r-(r/2), g-(g/2), b-(b/2)));
			b5 = BorderFactory.createCompoundBorder( b3, b4);
		return b5;
	}
	
	private void setupJOptionPane()
	{//for custom background for JOptionPane
		UIManager uim = new UIManager();
			uim.put("OptionPane.background", backgroundColor);
			uim.put("Panel.background", backgroundColor);
			uim.put("ScrollBar.background", backgroundColor);
			uim.put("ScrollBar.highlight", backgroundColor);
	}
	
	private void setupMainPanel()
	{//setup the main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(backgroundColor);
		GridBagConstraints gbcMain = new GridBagConstraints();
		gbcMain.fill = GridBagConstraints.BOTH;
		gbcMain.insets = new Insets( 1, 1, 1, 1);
		gbcMain.ipady = 10;
		
		//Create Buttons			
			sellButton = new JButton("SELL");
			sellButton.setActionCommand("Sell");
			sellButton.addActionListener(cBH);
			
			stocksButton = new JButton("STOCKS");
			stocksButton.setActionCommand("Stocks");
			stocksButton.addActionListener(cBH);
			
			Vector<JButton> btns = new Vector<JButton>();
				btns.add(sellButton);
				btns.add(stocksButton);
			dressButtons( btns, "Verdana", 26, Font.BOLD + Font.ITALIC, SwingConstants.RIGHT, SwingConstants.BOTTOM, 100, 40);
			
			//group and format the buttons
			JPanel tabButtonGroup = new JPanel();
			tabButtonGroup.setBackground(backgroundColor);
			tabButtonGroup.setOpaque(false);
			
			tabButtonGroup.setLayout(new GridBagLayout());
			GridBagConstraints gbctabs = new GridBagConstraints();
			gbctabs.fill = GridBagConstraints.BOTH;
			gbctabs.insets = new Insets( 5, 5, 5, 5);
			gbctabs.weightx = 1;
			gbctabs.weighty = 1;
			tabButtonGroup.add(sellButton, gbctabs);
			tabButtonGroup.add(stocksButton, gbctabs);
			tabButtonGroup.setPreferredSize( new Dimension( panelX, 50));
			
			title = new JLabel("The Ice Cream Shop", SwingConstants.RIGHT);
				title.setVerticalAlignment(SwingConstants.BOTTOM);
				title.setForeground( createColor());
				title.setFont(new Font( fontFace, Font.BOLD + Font.ITALIC, 25));
		
	//	bg = new JLabel(new ImageIcon(getClass().getResource(image)));
			bg.setBorder(null);
			bg.setBounds( 0, 0, w.getContentPane().getWidth(), w.getContentPane().getHeight());
			bg.setBackground(null);
			bg.setOpaque(false);
		
		b = new JToggleButton(new ImageIcon(getClass().getResource("gear.png")));
			b.setBackground(backgroundColor);
			b.setOpaque(false);
			b.setBounds( 5, 5, 25, 25);
			b.setActionCommand("showSettings");
			b.addActionListener( cBH);
			
		about = new JButton(new ImageIcon(getClass().getResource("exclamationPoint.png")));
			about.setBackground(backgroundColor);
			about.setOpaque(false);
			about.setBounds( 35, 5, 25, 25);
			about.setActionCommand("About");
			about.addActionListener( fBH);
			
		stocksB = new JToggleButton("Stocks");
			stocksB.setBorder( createBorder());
			stocksB.setBackground(backgroundColor);
			stocksB.setMargin(new Insets( 0, 0, 0, 0));
			stocksB.setOpaque(false);
			stocksB.setBounds( 5, 35, 60, 25);
			stocksB.setActionCommand("aIC>sST");
			stocksB.addActionListener( cBH);
			
		miscBPanel = new JPanel();
			miscBPanel.setLayout( new GridBagLayout());
			GridBagConstraints gbcMisc = new GridBagConstraints();
			gbcMisc.fill = GridBagConstraints.BOTH;
			gbcMisc.insets = new Insets( 0, 0, 0, 0);
			
			gbcMisc.gridy = 0;
			gbcMisc.gridx = 0;
			gbcMisc.anchor = GridBagConstraints.LINE_END;
			//miscBPanel.add( title, gbcMisc);
			
			gbcMisc.gridx = 1;
			
		gbcMain.gridx = 1;
		gbcMain.gridwidth = 1;
		mainPanel.add(tabButtonGroup, gbcMain);
		
		setupCommonPanel();
		setupShowStocks();
		setupC1();
		setupC2();
		setupAIC();
		setupSettingsPanel();
		setupTransView();
		setupTransDetails();
		
		cards1 = new JPanel();
			cards1.setBackground(backgroundColor);
			cards1.setOpaque(false);
		cards1.setLayout( new CardLayout());
		cards1.add( commonPanel, 	"commonPanel");
		cards1.add( stocksPanel, 	"showStocks");
		cards1.add( sPanel,			"showSettings");
		cards1.add( transDPanel,	"transDetails");
		
		cards2 = new JPanel();
			cards2.setBackground(backgroundColor);
			cards2.setOpaque(false);
		cards2.setLayout( new CardLayout());
		cards2.add( c1, 		"Sell");
		cards2.add( c2, 		"Stocks");
		cards2.add( aIC, 		"AddIC");
		cards2.add( transPanel, "transView");
		
		gbcMain.gridy = 0;
		gbcMain.gridx = 0;
			mainPanel.add( title, gbcMain);
		gbcMain.gridheight = 2;
		gbcMain.gridwidth = 2;
			mainPanel.add( bg, gbcMain);
		
		gbcMain.gridx = 0;
		gbcMain.gridheight = 1;
		gbcMain.gridwidth = 1;
		gbcMain.anchor = GridBagConstraints.LINE_START;
			mainPanel.add( miscBPanel, gbcMain);
		gbcMain.anchor = GridBagConstraints.CENTER;
			//mainPanel.add( title, gbcMain);
		
		gbcMain.gridwidth = 1;
		gbcMain.gridy = 1;
			gbcMain.gridx = 0;
			mainPanel.add( cards1, gbcMain);
		
			gbcMain.gridx = 1;
			mainPanel.add( cards2, gbcMain);
		
	}
	
	private void updateSellList()
	{//updates the sellList Table, and so with the totalPrice display on Sell Panel
		DefaultTableModel model = (DefaultTableModel)sellList.getModel();
		double tPrice = 0.0;
		
		for( int i = sellList.getRowCount()-1; i >= 0; i--)//clearList
		{
			model.removeRow(i);
		}
		
		for( IceCreamDetail icd : sellICD)//supply list with updated Data
		{
			Vector<String> row = new Vector<String>();
				row.addElement(icd.getIceCream().getBrand());
				row.addElement(icd.getIceCream().getVariety());
				row.addElement(icd.getIceCream().getFlavor());
				row.addElement("" + icd.getQuantity());
				row.addElement("" + icd.getSubTotal());
			model.addRow(row);
			
			tPrice += icd.getSubTotal();
		}
		
		totalPrice.setText("Total Price: " + tPrice);
	}
	
	private void updateAICList()
	{//updates the aICList Table, and so with the totalPrice display on aIC Panel
		DefaultTableModel model = (DefaultTableModel)aICList.getModel();
		double tPrice = 0.0;
		
		for( int i = aICList.getRowCount()-1; i >= 0; i--)//clearList
		{
			model.removeRow(i);
		}
		
		for( IceCreamDetail icd : aICICD)//supply list with updated Data
		{
			Vector<String> row = new Vector<String>();
				row.addElement(icd.getIceCream().getBrand());
				row.addElement(icd.getIceCream().getVariety());
				row.addElement(icd.getIceCream().getFlavor());
				row.addElement("" + icd.getIceCream().getPrice());
				row.addElement("" + icd.getQuantity());
				row.addElement("" + icd.getSubTotal());
			model.addRow(row);
			
			tPrice += icd.getSubTotal();
		}
		
		stp.setText("Total Price: " + tPrice);
	}
	
	private void updateStocksList()
	{//updates the stocksTable, and so the availability
		DefaultTableModel model = (DefaultTableModel)stocksTable.getModel();
		
		for( int i = stocksTable.getRowCount()-1; i >= 0; i--)//clearList
		{
			model.removeRow(i);
		}
		
		for( IceCreamDetail icd : iceCreamStocks)//supply list with updated Data
		{
			Vector<String> row = new Vector<String>();
				row.addElement(icd.getIceCream().getBrand());
				row.addElement(icd.getIceCream().getVariety());
				row.addElement(icd.getIceCream().getFlavor());
				row.addElement("" + icd.getIceCream().getPrice());
				row.addElement("" + icd.getQuantity());
			model.addRow(row);
		}
	}
	
}