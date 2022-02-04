package hw04.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

/**
 * The View: GUI that displays the balls
 * @param <TDropListItem> the item in the drop list
 *
 */
public class BallGUI<TDropListItem> extends JFrame {

	/**
	 * Serial Version ID for BallGUI
	 */
	private static final long serialVersionUID = -5444648268560616744L;

	/**
	 * Adapter interface from view to model
	 */
	private IModelControlAdapter<TDropListItem> _ModelControlAdpt;
	//private IModelControlAdapter<TDropListItem> _ModelControlAdpt = IModelControlAdapter.NULL_OBJECT;

	/**
	 * Adapter interface from view to model
	 */
	private IModelUpdateAdapter _ModelUpdateAdpt = IModelUpdateAdapter.NULL_OBJECT;

	/**
	 * Content pane to display the balls
	 */
	private JPanel contentPane;

	/**
	 * Control panel for the buttons
	 */
	private final JPanel controlPanel = new JPanel();

	/**
	 * Canvas panel with an overridden paintComponent method.
	 */
	private JPanel displayPanel = new JPanel() {
		private static final long serialVersionUID = 2;

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // clear the panel and redo the background
			_ModelUpdateAdpt.paint(g); // call back to the model to paint the balls
		}
	};

	/**
	 * The textField to load strategy
	 */
	private final JTextField updateStratTF = new JTextField();
	/**
	 * the button to add strategy to the list
	 */
	private final JButton addStrategyButton = new JButton("Add to lists");
	/**
	 * The first box of drop list for strategies
	 */
	private final JComboBox<TDropListItem> strategyBox1 = new JComboBox<TDropListItem>();
	/**
	 * The second box of drop list for strategies
	 */
	private final JComboBox<TDropListItem> strategyBox2 = new JComboBox<TDropListItem>();

	/**
	 * The second box of drop list for strategies
	 */
	private final JComboBox<TDropListItem> paintBox = new JComboBox<TDropListItem>();
	/**
	 * The button to make a ball on canvas
	 */
	private final JButton makeBallButton = new JButton("Make Selected Ball");
	/**
	 * The button to combine two strategies
	 */
	private final JButton combineButton = new JButton("Combine!");
	/**
	 * The button to add switcher balls
	 */
	private final JButton makeSwitcherButton = new JButton("Add Switcher");
	/**
	 * The button to switch the strategies of the switcher balls
	 */
	private final JButton switchButton = new JButton("Switch!");
	/**
	 * The JPanel for the canvas
	 */
	private final JPanel updatePanel = new JPanel();
	/**
	 * The JPanel to combine the strategies
	 */
	private final JPanel combinePanel = new JPanel();
	/**
	 * The JPanel to make switcher balls
	 */
	private final JPanel switcherPanel = new JPanel();
	/**
	 * The JPanel to clear balls on canvas
	 */
	private final JPanel clearPanel = new JPanel();
	/**
	 * Label the switcher panel
	 */
	private final JLabel lblNewLabel = new JLabel("Switcher Controls");
	/**
	 * Label the canvas panel
	 */
	private final JLabel label = new JLabel("");
	/**
	 * Label the paint panel
	 */
	private final JPanel paintPanel = new JPanel();
	/**
	 * The button that add the entered paint strategy into the drop list
	 */
	private final JButton addPaintButton = new JButton("Add to lists");
	/**
	 * The button that clears the canvas
	 */
	private final JTextField paintStratTF = new JTextField();
	/**
	 * The button that clears the canvas
	 */
	private final JButton btnClear = new JButton("Clear");
	
	/**
	 * Constructor of BallGUI that calls the initialization of GUI
	 * @param ctrlAdpt control adapter interface from view to model
	 * @param updateAdpt update adapter interface from view to model
	 */
	public BallGUI(IModelControlAdapter<TDropListItem> ctrlAdpt, IModelUpdateAdapter updateAdpt) {
		this._ModelControlAdpt = ctrlAdpt;
		this._ModelUpdateAdpt = updateAdpt;
		initGUI();
	}

	/**
	 * Initialization of GUI
	 */
	private void initGUI() {
		//Initialize content pane and control panels
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(controlPanel, BorderLayout.NORTH);
		setContentPane(contentPane);


		controlPanel.setToolTipText("The Control Panel, where all the buttons and functionalities are contained.");
		controlPanel.setBackground(new Color(173, 216, 230));
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
		controlPanel.add(updatePanel);
		controlPanel.add(paintPanel);
		controlPanel.add(combinePanel);
		controlPanel.add(switcherPanel);
		btnClear.setToolTipText("Click to clear all balls.");
		
		clearPanel.add(btnClear);
		controlPanel.add(clearPanel);		
		
		updatePanel.setForeground(new Color(0, 0, 0));
		updatePanel.setBorder(new TitledBorder(null, "Update Strategy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		updatePanel.setLayout(new GridLayout(0, 1, 0, 0));
		updateStratTF.setText("Straight");
		updateStratTF.setToolTipText(
				"Enter the name of your desired update strategy here - either the full name (hw03.model.strategy.----Strategy, or the nickname such as Shrinking, Slowing, etc.)");
		updatePanel.add(updateStratTF);
		updateStratTF.setColumns(10);
		addStrategyButton.setToolTipText(
				"Type the strategy nickname (BounceColor, Shrinking, Slowing, Teleport, or ZigZag) and press the button to make it available in the dropdown menus to the right.");
		updatePanel.add(addStrategyButton);
		updatePanel.setBackground(new Color(173, 216, 230));
		updatePanel.setToolTipText(
				"The panel where the behavior of the balls are modified");

		
		paintPanel.setBackground(new Color(173, 216, 230));
		paintPanel.setToolTipText(
				"The panel where the appearences of the 'ball' are modified");
		paintPanel.setBorder(new TitledBorder(null, "Paint Strategies", TitledBorder.LEADING, TitledBorder.TOP, null, null));	
		paintPanel.setLayout(new GridLayout(0, 1, 0, 0));
		paintStratTF.setText("Ball");
		paintStratTF.setColumns(10);
		paintStratTF.setToolTipText(
				"The textfield to enter the desired paint strategy of the ball");
		paintPanel.add(paintStratTF);
		paintPanel.add(addPaintButton);
		paintBox.setToolTipText(
				"Select any of the strategies in this dropdown menu to combine with the strategy in the top dropdown menu.");
		paintPanel.add(paintBox);
		
		combinePanel.setBackground(new Color(173, 216, 230));
		combinePanel.setToolTipText(
				"The panel where the appearences of the 'ball' are modified");
		combinePanel.setLayout(new GridLayout(0, 1, 0, 0));
		makeBallButton.setToolTipText(
				"Press this button to create a new ball that has the strategy selected in the upper dropdown menu.");
		combinePanel.add(makeBallButton);
		strategyBox1.setToolTipText(
				"Select any of the strategies or strategy combinations in the dropdown. Pressing the Make Selected Ball button will generate a ball with this strategy.");
		combinePanel.add(strategyBox1);
		strategyBox2.setToolTipText(
				"Select any of the strategies in this dropdown menu to combine with the strategy in the top dropdown menu.");
		combinePanel.add(strategyBox2);
		combineButton.setToolTipText("Press this button to combine the strategies in the dropdown menu");
		combinePanel.add(combineButton);
		
		switcherPanel.setBackground(new Color(173, 216, 230));
		switcherPanel.setToolTipText(
				"The panel where the switcher balls are generated and modified");
		switcherPanel.setLayout(new GridLayout(0, 1, 0, 0));
		lblNewLabel.setToolTipText(
				"This panel allows us to generate switcher balls, which are ball instances that freely switch between different strategies (whichever strategy is selected in the top dropdown).");
		switcherPanel.add(lblNewLabel);
		switchButton.setToolTipText(
				"Press this button to switch all current switcher balls to whichever strategy is currently selected in the top dropdown.");
		switcherPanel.add(switchButton);
		makeSwitcherButton.setToolTipText(
				"Press this button to generate a switcher ball, which defaults to the straight strategy.");
		switcherPanel.add(makeSwitcherButton);
		switcherPanel.add(label);
		
		clearPanel.setBackground(new Color(173, 216, 230));
		clearPanel.setLayout(new GridLayout(0, 1, 0, 0));

		addStrategyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TDropListItem o = _ModelControlAdpt.addUpdateStrategy(updateStratTF.getText());
				if (null == o)
					return; // just in case

				strategyBox1.insertItemAt(o, 0);
				strategyBox2.insertItemAt(o, 0);
			}
		});

		addPaintButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TDropListItem o = _ModelControlAdpt.addPaintStrategy(paintStratTF.getText());
				if (null == o)
					return; // just in case

				paintBox.insertItemAt(o, 0);
			}
		});

		makeBallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_ModelControlAdpt.makeBall(_ModelControlAdpt.combineStrategies(strategyBox1.getItemAt(strategyBox1.getSelectedIndex()), paintBox.getItemAt(paintBox.getSelectedIndex())));
			}
		});

		combineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//call the model control adapter to combine strategies given
				TDropListItem combinedStrategy = _ModelControlAdpt.combineStrategies(
						strategyBox1.getItemAt(strategyBox1.getSelectedIndex()),
						strategyBox2.getItemAt(strategyBox2.getSelectedIndex()));
				//take the combined strategy from model and insert it into the drop list box
				strategyBox1.insertItemAt(combinedStrategy, 0);
				strategyBox2.insertItemAt(combinedStrategy, 0);
			}

		});

		makeSwitcherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Call the model control adapter to make a switcher ball
				_ModelControlAdpt.makeSwitcherBall();
			}
		});

		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//call the model control adapter to switcher the switcher ball strategy
				_ModelControlAdpt.switchStrategy(_ModelControlAdpt.combineStrategies(strategyBox1.getItemAt(strategyBox1.getSelectedIndex()), paintBox.getItemAt(paintBox.getSelectedIndex())));
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				_ModelControlAdpt.clearBall();
			}
		});

		contentPane.add(displayPanel, BorderLayout.CENTER);

		
		displayPanel.setToolTipText("The display panel, where the balls will have freedom to populate.");
	}

	/**
	 * @return the canvas
	 */
	public Container getCanvas() {
		return displayPanel;
	}

	/**
	 * Updates the view by repainting the canvas
	 */
	public void update() {
		displayPanel.repaint();
	}

	/**
	 * Starts the view
	 */
	public void start() {
		this.setVisible(true);
	}

}