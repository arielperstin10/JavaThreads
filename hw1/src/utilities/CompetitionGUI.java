package utilities;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.ICompetitor;
import game.competition.SkiCompetition;
import game.competition.WinterCompetition;
import game.entities.sportsman.ColoredSportsman;
import game.entities.sportsman.IWinterSportsman;
import game.entities.sportsman.Skier;
import game.entities.sportsman.Snowboarder;
import game.entities.sportsman.SpeedySportsman;
import game.entities.sportsman.WinterSportsman;
import game.enums.*;
import game.factory.ArenaFactory;
import game.factory.Engineer;
import game.factory.MySkiCompetitionBuilder;
import game.factory.SkiCompetitionBuilder;
import game.state.ActiveState;
import game.state.CompletedState;
import game.state.DisabledState;
import game.state.InjuredState;


public class CompetitionGUI extends JFrame {

	ArenaFactory AF;
    private IArena arena;
    private WinterCompetition competition;
    
    private JLayeredPane arenaPanel;
    private JPanel controlPanel;
    private JPanel competitorsIconsPanel;
    
    private JComboBox<String> arenaType;
    private JTextField arenaLength;
    private JComboBox<String> snowSurface;
    private JComboBox<String> weatherCondition;
    
    private JComboBox<String> competitionChoice;
    private JTextField maxCompetitors;
    private JComboBox<String> disciplineChoice;
    private JComboBox<String> leagueChoice;
    private JComboBox<String> genderChoice;
    
    private JTextField name;
    private JTextField age;
    private JTextField maxSpeed;
    private JTextField acceleration;
    
    JFrame tableFrame;
    private JTable infoTable;
    private DefaultTableModel tableModel;
    JScrollPane scrollPane;
    
    private ArrayList<JLabel> icons;
    
    private boolean isStarted;
    private boolean isFinished;
    
    public CompetitionGUI() {
    	/*
    	 * GUI constructor
    	 */
        super("Competition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        initComponents();
        createLayout();
        addActionListeners();
        
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	/*
    	 * initializing components
    	 */
    	AF = new ArenaFactory();
        arenaPanel = new JLayeredPane();
        arenaPanel.setLayout(null);
        arenaPanel.setBackground(Color.WHITE);
        arenaPanel.setOpaque(true);
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setPreferredSize(new Dimension(200, getHeight()));
        competitorsIconsPanel = new JPanel();
        competitorsIconsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        arenaType = new JComboBox<>(new String[]{"Winter", "Summer"});
        arenaLength = new JTextField(10);
        snowSurface = new JComboBox<>(new String[]{"Powder", "Crud", "Ice"});
        weatherCondition = new JComboBox<>(new String[]{"Sunny", "Cloudy", "Stormy"});
        
        competitionChoice = new JComboBox<>(new String[]{"Ski", "Snowboard"});
        maxCompetitors = new JTextField(10);
        disciplineChoice = new JComboBox<>(new String[]{"Slalom", "Giant Slalom", "Downhill", "Freestyle"});
        leagueChoice = new JComboBox<>(new String[]{"Junior", "Adult", "Senior"});
        genderChoice = new JComboBox<>(new String[]{"Male", "Female"});
        
        name = new JTextField(10);
        age = new JTextField(10);
        maxSpeed = new JTextField(10);
        acceleration = new JTextField(10);
        
        tableFrame = new JFrame("Competitors Information");
        infoTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Name", "Speed", "Max Speed", "Location", "Status", "Injured Time"}, 0);
        infoTable.setModel(tableModel);
        scrollPane = new JScrollPane(infoTable);
        tableFrame.setSize(600, 400);
        tableFrame.setLocationRelativeTo(this);
        tableFrame.add(scrollPane);
        icons = new ArrayList<>();
        
        Dimension fieldSize = new Dimension(150, 25);
        arenaLength.setPreferredSize(fieldSize);
        maxCompetitors.setPreferredSize(fieldSize);
        name.setPreferredSize(fieldSize);
        age.setPreferredSize(fieldSize);
        maxSpeed.setPreferredSize(fieldSize);
        acceleration.setPreferredSize(fieldSize);
        
        Dimension comboSize = new Dimension(150, 25);
        arenaType.setPreferredSize(comboSize);
        snowSurface.setPreferredSize(comboSize);
        weatherCondition.setPreferredSize(comboSize);
        competitionChoice.setPreferredSize(comboSize);
        disciplineChoice.setPreferredSize(comboSize);
        leagueChoice.setPreferredSize(comboSize);
        genderChoice.setPreferredSize(comboSize);
    }

    private void createLayout() {
    	/*
    	 * Creating a layout for the GUI frame.
    	 */
    	JPanel mainPanel = new JPanel(new BorderLayout());
        
        arenaPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.add(arenaPanel, BorderLayout.CENTER);
        
        JScrollPane controlScrollPane = new JScrollPane(controlPanel);
        controlScrollPane.setPreferredSize(new Dimension(300, 600));
        controlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(controlScrollPane, BorderLayout.EAST);
        
        competitorsIconsPanel.setPreferredSize(new Dimension(getWidth(), 50));
        mainPanel.add(competitorsIconsPanel, BorderLayout.NORTH);
        
        add(mainPanel, BorderLayout.CENTER);
        
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        add(arenaPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        add(competitorsIconsPanel, BorderLayout.NORTH);
		
        controlPanel.add(createLabeledSection("Build Arena",
        	createComponent("Arena Type", arenaType),
            createComponent("Arena Length", arenaLength),
            createComponent("Snow Surface", snowSurface),
            createComponent("Weather Condition", weatherCondition),
            createButton("Build Arena")));
        
        controlPanel.add(createLabeledSection("Create Competition", 
            createComponent("Choose Competition", competitionChoice),
            createComponent("Max Competitors", maxCompetitors),
            createComponent("Discipline", disciplineChoice),
            createComponent("League", leagueChoice),
            createComponent("Gender", genderChoice),
            createButton("Create Competition"),
            createButton("Default Ski Competition")));
        
        controlPanel.add(createLabeledSection("Add Competitor", 
            createComponent("Name", name),
            createComponent("Age", age),
            createComponent("Max speed", maxSpeed),
            createComponent("Acceleration", acceleration),
            createButton("Add Competitor"),
            createButton("Customize Competitor")));
        
        controlPanel.add(createButton("Copy Competitor"));
        controlPanel.add(createButton("Start Competition"));
        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(createButton("Show Info"));
        
        controlPanel.add(Box.createVerticalGlue());
    }

    private void addActionListeners() {
    	/*
    	 * Action listeners for the GUI buttons.
    	 */
        ((JButton)getComponentByName(controlPanel, "Build Arena")).addActionListener(e -> buildArena());
        ((JButton)getComponentByName(controlPanel, "Create Competition")).addActionListener(e -> createCompetition());
        ((JButton)getComponentByName(controlPanel, "Add Competitor")).addActionListener(e -> addCompetitor());
        ((JButton)getComponentByName(controlPanel, "Start Competition")).addActionListener(e -> startCompetition());
        ((JButton)getComponentByName(controlPanel, "Show Info")).addActionListener(e -> showInfo());
        ((JButton)getComponentByName(controlPanel, "Copy Competitor")).addActionListener(e -> copyCompetitor());
        ((JButton)getComponentByName(controlPanel, "Default Ski Competition")).addActionListener(e -> {
			try {
				defaultCompetition();
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        ((JButton)getComponentByName(controlPanel, "Customize Competitor")).addActionListener(e -> customizeCompetitor());
    }

    private void buildArena() {
    	/*
    	 * Build arena button action listener functionality
    	 * (Factory Method)
    	 */
        try {
        	String arenaTypeValue = arenaType.getSelectedItem().toString();
            double length = Double.parseDouble(arenaLength.getText());
            if (isStarted)
            	JOptionPane.showMessageDialog(this, "Competition is ongoing.", "Error", JOptionPane.ERROR_MESSAGE);
            else if (competition != null && competition.hasActiveCompetitors())
            	JOptionPane.showMessageDialog(this, "Competition with competitors is already exist, please start it.", "Error", JOptionPane.ERROR_MESSAGE);
            else if (length < 700 || length > 900) {
                JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else {
            	isFinished = false;
            	competition = null;
                String imagePath = "/icons/" + weatherCondition.getSelectedItem().toString() + ".jpg";
                //resizeFrame(length);
                arenaPanel.setPreferredSize(new Dimension((int) length, arenaPanel.getHeight()));
                arenaPanel.setMaximumSize(arenaPanel.getPreferredSize());
                repaint();
                revalidate();
                arena = AF.createArena(arenaTypeValue, length, surfaceStringToEnum(snowSurface.getSelectedItem().toString()), weatherConditionStringToEnum(weatherCondition.getSelectedItem().toString()));
                paintArena(imagePath);

                JOptionPane.showMessageDialog(this, "Arena built successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedOperationException | InvocationTargetException | IllegalArgumentException | IllegalAccessException | InstantiationException | SecurityException
        			| NoSuchMethodException | ClassNotFoundException err) {
        	JOptionPane.showMessageDialog(this, err, "Error", JOptionPane.ERROR_MESSAGE);
        }	
    }

    private void defaultCompetition() throws NumberFormatException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException {
    	/*
    	 * Create a default competition (Builder design pattern)
    	 */
    	isFinished = false;
    	String numCompetitors = JOptionPane.showInputDialog(this,
                "Enter number of competitors:", "Default Competition",
                JOptionPane.QUESTION_MESSAGE);
    	int numCompetitorsChose = Integer.parseInt(numCompetitors);
    	if (numCompetitorsChose > 20 || numCompetitorsChose < 1) {
    		JOptionPane.showMessageDialog(this, "Enter valid number of competitors (1-20)", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	SkiCompetitionBuilder skiCompBuilder = new MySkiCompetitionBuilder();
    	Engineer engineer = new Engineer(skiCompBuilder);
    	engineer.constructSkiCompetition(numCompetitorsChose);
    	competition = engineer.getCompetition();
    	
    	arena = competition.getArena();
    	arenaPanel.removeAll();
        String imagePath = "/icons/Sunny.jpg";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
        resizeFrame(850);
        Image image = imageIcon.getImage().getScaledInstance(arenaPanel.getWidth(), arenaPanel.getHeight(), Image.SCALE_SMOOTH);
    	JLabel backgroundLabel = new JLabel(new ImageIcon(image));
        backgroundLabel.setBounds(0, 0, arenaPanel.getWidth(), arenaPanel.getHeight());
        arenaPanel.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        arenaPanel.revalidate();
        arenaPanel.repaint();
    	
        for(int i = 0; i < Integer.parseInt(numCompetitors); i++) {
        	ICompetitor tempCompetitor = competition.getActiveCompetitors().get(i);
        	((WinterSportsman) tempCompetitor).setArena((WinterArena)arena);
        	((WinterSportsman) tempCompetitor).addState();
        	((WinterSportsman) tempCompetitor).setColor("Black");
        	ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icons/SkiBlack.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            icons.add(iconLabel);
            int iconCount = arenaPanel.getComponentCount() - 1;
            int x = 10 + (iconCount * 35);
            int y = 10;
            
            iconLabel.setBounds(x, y, 30, 30);
            arenaPanel.add(iconLabel, JLayeredPane.PALETTE_LAYER);
            arenaPanel.revalidate();
            arenaPanel.repaint();
        }
        
    }
    
    private void createCompetition() {
    	/*
    	 * Create competition button action listener functionality
    	 */
        try {
            int maxCompetitorsValue = Integer.parseInt(maxCompetitors.getText());
            if (isStarted)
            	JOptionPane.showMessageDialog(this, "Competition is ongoing.", "Error", JOptionPane.ERROR_MESSAGE);
            else if(isFinished)
            	JOptionPane.showMessageDialog(this, "Competition is finished, please build new arena to start new competition.", "Error", JOptionPane.ERROR_MESSAGE);
            else if (competition != null && competition.hasActiveCompetitors())
            	JOptionPane.showMessageDialog(this, "Competition with competitors is already exist, please start it.", "Error", JOptionPane.ERROR_MESSAGE);
            else if (maxCompetitorsValue < 0 || maxCompetitorsValue > 20) {
                JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (arena == null) {
                JOptionPane.showMessageDialog(this, "Please build arena before building a competition.", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else {
            	Class cls;
            	ClassLoader cl = ClassLoader.getSystemClassLoader();
            	Constructor ctor;
                Discipline discipline = disciplineStringToEnum(disciplineChoice.getSelectedItem().toString());
                League league = leagueStringToEnum(leagueChoice.getSelectedItem().toString());
                Gender gender = genderStringToEnum(genderChoice.getSelectedItem().toString());
                if (competitionChoice.getSelectedItem().toString().equals("Ski")) {
                	try {
                		cls = cl.loadClass("game.competition.SkiCompetition");
                		ctor = cls.getConstructor(WinterArena.class, int.class, Discipline.class, League.class, Gender.class);
                		competition = (WinterCompetition) ctor.newInstance(arena, maxCompetitorsValue, discipline, league, gender);
                		JOptionPane.showMessageDialog(this, "New ski competition has been added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                	} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        JOptionPane.showMessageDialog(this, "Error creating competition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                	try {
                		cls = cl.loadClass("game.competition.SnowboardCompetition");
                    	ctor = cls.getConstructor(WinterArena.class, int.class, Discipline.class, League.class, Gender.class);
                    	competition = (WinterCompetition) ctor.newInstance(arena, maxCompetitorsValue, discipline, league, gender);	
                    	JOptionPane.showMessageDialog(this, "New snowboard competition has been added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                	}catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        JOptionPane.showMessageDialog(this, "Error creating competition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch(NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        
    }

    private void addCompetitor() {
    	/*
    	 * Add competitor button action listener functionality
    	 */
        try {
            double ageValue = Double.parseDouble(age.getText());
            double maxSpeedValue = Double.parseDouble(maxSpeed.getText());
            double accelerationValue = Double.parseDouble(acceleration.getText());
            
            if (isStarted)
            	JOptionPane.showMessageDialog(this, "Competition is ongoing.", "Error", JOptionPane.ERROR_MESSAGE);
            else if(isFinished)
            	JOptionPane.showMessageDialog(this, "Competition is finished, please build new arena to start new competition.", "Error", JOptionPane.ERROR_MESSAGE);
            else if (competition == null) {
                JOptionPane.showMessageDialog(this, "Please build arena and create a competition before adding a competitor.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!competition.getLeague().isInLeague(ageValue)) {
                JOptionPane.showMessageDialog(this, "Competitor does not fit to competition! Choose another competitor.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                	String color = "";
                	Class cls;
                	ClassLoader cl = ClassLoader.getSystemClassLoader();
                	Constructor ctor;
                    if (competition instanceof SkiCompetition) {
                    	try {
                    		cls = cl.loadClass("game.entities.sportsman.Skier");
                    		ctor = cls.getConstructor(String.class, double.class, Gender.class, double.class, double.class, Discipline.class);
                    		WinterSportsman competitor = (Skier) ctor.newInstance(name.getText(), ageValue, competition.getGender(), accelerationValue, maxSpeedValue, competition.getDiscipline());
                    		competition.addCompetitor(competitor);
                    		competitor.setArena((WinterArena)arena);
                    		competitor.addState();
                        	color = competition.getGender() == Gender.MALE ? "Blue" : "Pink";
                        	competitor.setColor(color);
                        	addIcon(color);
                        	JOptionPane.showMessageDialog(this, "New skier has been added successfully to the competition!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    	} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            JOptionPane.showMessageDialog(this, "Error creating competition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                    	try {
                    		cls = cl.loadClass("game.entities.sportsman.Snowboarder");
                    		ctor = cls.getConstructor(String.class, double.class, Gender.class, double.class, double.class, Discipline.class);
                    		WinterSportsman competitor = (Snowboarder) ctor.newInstance(name.getText(), ageValue, competition.getGender(), accelerationValue, maxSpeedValue, competition.getDiscipline());
                    		competition.addCompetitor(competitor);
                    		competitor.setArena((WinterArena)arena);
                    		competitor.addState();
                        	color = competition.getGender() == Gender.MALE ? "Blue" : "Pink";
                        	competitor.setColor(color);
                        	addIcon(color);
                        	JOptionPane.showMessageDialog(this, "New snowboarder has been added successfully to the competition!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    	} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            JOptionPane.showMessageDialog(this, "Error creating competition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IllegalStateException err) {
                    JOptionPane.showMessageDialog(this, "Competition is full.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch(NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void startCompetition() {
    	/*
    	 * Start competition button action listener functionality
    	 */
    	
    	if (isStarted)
    		JOptionPane.showMessageDialog(this, "Competition is ongoing.", "Error", JOptionPane.ERROR_MESSAGE);
    	else if (isFinished)
    		JOptionPane.showMessageDialog(this, "Competition is finished, please build new arena to start new competition.", "Error", JOptionPane.ERROR_MESSAGE);
    	else if (arena == null)
    		JOptionPane.showMessageDialog(this, "Please initialize arena.", "Error", JOptionPane.ERROR_MESSAGE);
    	else if (competition == null)
    		JOptionPane.showMessageDialog(this, "Please initialize competition.", "Error", JOptionPane.ERROR_MESSAGE);
    	else if (!competition.hasActiveCompetitors())
    		JOptionPane.showMessageDialog(this, "Please add at least 1 competitor before starting.", "Error", JOptionPane.ERROR_MESSAGE);
    	else {
	    	for (int i = 0; i < competition.getActiveCompetitors().size(); i++)
	    		((WinterSportsman)competition.getActiveCompetitors().get(i)).setIcon(icons.get(i));
	    	
	    	isStarted = true;
			(new Thread() {
					public void run() {
						while (competition.hasActiveCompetitors()) {
							updateCompetitors();
							updateInfoTable();
							try {
								Thread.sleep(30);
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}
						isFinished = true;
						isStarted = false;
						icons.clear();
						finishedMessage();
						for (int i = 0; i < competition.getFinishedCompetitors().size(); i++) {
				    		WinterSportsman ws = (WinterSportsman) competition.getFinishedCompetitors().get(i);
				    		System.out.println(ws.getState().getCurrentStatus().getClass());
				    	}
					}
				}).start();
			
				for (ICompetitor comp: competition.getActiveCompetitors()) {
					new Thread(((WinterSportsman) comp)).start();
				}
    	}
    }
    
    private void showInfo() {
    	/*
    	 * Show info button action listener functionality
    	 */       
    	tableModel.setRowCount(0);
        updateInfoTable();
        tableFrame.setVisible(true);
    }
    
    private void updateInfoTable() {
    	/*
    	 * function to update the competition table in real time which have 2 cases
    	 * 1. checking the stats of the finished competitors.
    	 * 2. checking the stats of the active competitors.
    	 * 3. Combining the competitors' state (State design pattern)
    	 */
    	if (tableModel != null && competition != null) {
            SwingUtilities.invokeLater(() -> {
                tableModel.setRowCount(0);

                for (int i = 0; i < competition.getActiveCompetitors().size(); i++) {
                	WinterSportsman ws = (WinterSportsman) competition.getActiveCompetitors().get(i);
                	if (ws.getState().getCurrentStatus() instanceof ActiveState) {
                        tableModel.addRow(new Object[]{
                            ws.getName(),
                            ws.getSpeed(),
                            ws.getMaxSpeed(),
                            ws.getLocation().getX(),
                            "Active"
                        });
                    } else if (ws.getState().getCurrentStatus() instanceof InjuredState) {
                        tableModel.addRow(new Object[]{
                            ws.getName(),
                            ws.getSpeed(),
                            ws.getMaxSpeed(),
                            ws.getLocation().getX(),
                            "Injured",
                            ws.getInjuredTime()
                        });
                    } else if (ws.getState().getCurrentStatus() instanceof DisabledState) {
                        tableModel.addRow(new Object[]{
                            ws.getName(),
                            ws.getSpeed(),
                            ws.getMaxSpeed(),
                            ws.getExitedLoc(),
                            "Failed",
                            ws.getInjuredTime()
                        });
                    } else if (ws.getState().getCurrentStatus() instanceof CompletedState) {
                        tableModel.addRow(new Object[]{
                            ws.getName(),
                            ws.getSpeed(),
                            ws.getMaxSpeed(),
                            ws.getLocation().getX(),
                            "Finished"
                        });
                    }
                }
                int j = 0, k = 0;
                String status = "";
                while (j < 2) {
                	while (k < competition.getFinishedCompetitors().size()) {
                		WinterSportsman ws = (WinterSportsman) competition.getFinishedCompetitors().get(k);
	                	if ((ws.getState().getCurrentStatus() instanceof CompletedState || ws.getState().getCurrentStatus() instanceof InjuredState) && j == 0) {
	                		if (ws.getInjuredTime() == 0)
	                			status = "Finished";
	                		else
	                			status = "Injured";
	                		tableModel.addRow(new Object[]{
	                                ws.getName(),
	                                ws.getSpeed(),
	                                ws.getMaxSpeed(),
	                                ws.getLocation().getX(),
	                                status,
	                                ws.getInjuredTime() > 0 ? ws.getInjuredTime() : ""
	                            });
	                		k++;
	                	}
	                	else if (ws.getState().getCurrentStatus() instanceof DisabledState && j == 1) {
	                		tableModel.addRow(new Object[]{
	                                ws.getName(),
	                                ws.getSpeed(),
	                                ws.getMaxSpeed(),
	                                ws.getExitedLoc(),
	                                "Failed",
	                                ws.getInjuredTime() > 0 ? ws.getInjuredTime() : ""
	                            });
	                		k++;
	                	}
	                	else
	                		k++;
                	}
                	k = 0;
                	j++;
                }
 
            });
       
    	}
    }

    private JPanel createLabeledSection(String title, JComponent...components) {
    	/*
    	 * Create a section for given components.
    	 */
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        for (JComponent component : components) {
            component.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(component);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
	}
    
	private JPanel createComponent(String label, JComponent component) {
		/*
    	 * Create a single component.
    	 */
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(120, 25));
        panel.add(jLabel);
        
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        panel.add(component);
        
        return panel;
    }
	
	private JButton createButton(String text) {
		/*
    	 * Create a button.
    	 */
		JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        return button;
		
    }

	private Component getComponentByName(Container container, String name) {
    	/*
    	 * Function to return a component by its name.
    	 */
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals(name)) {
                return component;
            }
            if (component instanceof Container) {
                Component found = getComponentByName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
	
    
    private void updateCompetitors() {
    	/*
    	 * Update competitors location in the GUI.
    	 */
    	
    	SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < competition.getActiveCompetitors().size(); i++) {
                WinterSportsman competitor = (WinterSportsman)competition.getActiveCompetitors().get(i);
                JLabel iconLabel = competitor.getIcon();
                
                int x = iconLabel.getBounds().x;
                
                int competitorLocation = (int)competition.getActiveCompetitors().get(i).getLocation().getX();
                
                int y = Math.min(competitorLocation, arenaPanel.getHeight() - 30);
                
                iconLabel.setLocation(x, y);
            }
            arenaPanel.revalidate();
            arenaPanel.repaint();
        });
        
    }
    
    
    private void resizeFrame(double arenaLength) {
    	/*
    	 * Resize frame by the height of the user in build arena.
    	 */
    	setSize(this.getWidth(), (int) arenaLength);
    	revalidate();
    	repaint();
    }
    
    
    private void finishedMessage() {
    	/*
    	 * A function to return a message when competition is finished.
    	 */
    	JOptionPane.showMessageDialog(this, "Competition has finished.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void copyCompetitor() {
    	/*
    	 * Copy an existing competitor and selecting a color for him (Prototype design pattern)
    	 */
    	if (isStarted) {
            JOptionPane.showMessageDialog(this, "Competition is ongoing.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isFinished) {
            JOptionPane.showMessageDialog(this, "Competition is finished, please build new arena to start new competition.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (competition == null || !competition.hasActiveCompetitors()) {
            JOptionPane.showMessageDialog(this, "No competitors to copy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] competitorNames = competition.getActiveCompetitors().stream()
                                    .map(c -> ((WinterSportsman)c).getName())
                                    .toArray(String[]::new);

        String selectedName = (String) JOptionPane.showInputDialog(this,
                "Select a competitor to copy:", "Copy Competitor",
                JOptionPane.QUESTION_MESSAGE, null, competitorNames, competitorNames[0]);
        
        String[] colors = {"Red", "Black", "Blue", "Pink", "Orange", "Green", "Light Green"};
        String selectedColor = (String) JOptionPane.showInputDialog(this,
                "Select a color:", "Copy Competitor",
                JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);
        
        WinterSportsman selectedCompetitor = (WinterSportsman) competition.getActiveCompetitors().stream()
                                                .filter(c -> ((WinterSportsman)c).getName().equals(selectedName))
                                                .findFirst().orElse(null);

        if (selectedCompetitor == null) return;

        try {
            WinterSportsman copiedCompetitor = (WinterSportsman) selectedCompetitor.clone();
            
            String newName = JOptionPane.showInputDialog(this, "Enter a new name for the copied competitor:", 
                                                         selectedName + " (Copy)");
            if (newName == null || newName.trim().isEmpty()) return;
            copiedCompetitor.setName(newName);
            copiedCompetitor.setColor(selectedColor);
            competition.addCompetitor(copiedCompetitor);
            copiedCompetitor.setArena((WinterArena)arena);
            copiedCompetitor.addState();
            addIcon(selectedColor);
            JOptionPane.showMessageDialog(this, "Competitor copied successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (CloneNotSupportedException e) {
            JOptionPane.showMessageDialog(this, "Error copying competitor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, "Competition is full.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void customizeCompetitor() {
    	/*
    	 * customize competitor button functionality (Decorator)
    	 */
    	if (competition == null || competition.getActiveCompetitors().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No competitors to customize.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] competitorNames = competition.getActiveCompetitors().stream()
                .map(c -> ((IWinterSportsman)c).getName())
                .toArray(String[]::new);

        String selectedName = (String) JOptionPane.showInputDialog(this,
                "Select a competitor to customize:", "Customize Competitor",
                JOptionPane.QUESTION_MESSAGE, null, competitorNames, competitorNames[0]);

        if (selectedName == null) return;

        IWinterSportsman selectedCompetitor = (IWinterSportsman) competition.getActiveCompetitors().stream()
                .filter(c -> ((IWinterSportsman)c).getName().equals(selectedName))
                .findFirst().orElse(null);

        if (selectedCompetitor == null) return;

        String[] customizationOptions = {"Change Color", "Increase Speed"};
        String selectedOption = (String) JOptionPane.showInputDialog(this,
                "Choose customization:", "Customize Competitor",
                JOptionPane.QUESTION_MESSAGE, null, customizationOptions, customizationOptions[0]);

        if (selectedOption == null) return;

        if (selectedOption.equals("Change Color")) {
            customizeColor(selectedCompetitor);
        } else if (selectedOption.equals("Increase Speed")) {
            customizeSpeed(selectedCompetitor);
        }
    }
    
    private void customizeColor(IWinterSportsman sportsman) {
    	/*
    	 * customize color of a sportsman in the decorator
    	 */
        String[] colors = {"Red", "Black", "Blue", "Pink", "Orange", "Green", "Light Green"};
        String selectedColor = (String) JOptionPane.showInputDialog(this,
                "Select a new color:", "Customize Color",
                JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);

        if (selectedColor == null) return;

        IWinterSportsman coloredSportsman = new ColoredSportsman(sportsman, selectedColor);
        updateCompetitorInCompetition(sportsman, coloredSportsman);
        updateGUI();
    }
    

    private void customizeSpeed(IWinterSportsman sportsman) {
    	/*
    	 * customize speed of a sportsman in the decorator
    	 */
        String input = JOptionPane.showInputDialog(this, "Enter acceleration increase:", "Customize Speed", JOptionPane.QUESTION_MESSAGE);
        if (input == null) return;

        try {
            double accelerationIncrease = Double.parseDouble(input);
            IWinterSportsman speedySportsman = new SpeedySportsman(sportsman, accelerationIncrease);
            updateCompetitorInCompetition(sportsman, speedySportsman);
            updateGUI();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCompetitorInCompetition(IWinterSportsman oldCompetitor, IWinterSportsman newCompetitor) {
    	/*
    	 * Update decorated competitor in the competition
    	 */
        int index = competition.getActiveCompetitors().indexOf((ICompetitor)oldCompetitor);
        if (index != -1) {
        	if (newCompetitor instanceof ColoredSportsman)
        		newCompetitor = ((ColoredSportsman) newCompetitor).getOriginalSportsman();
        	else if (newCompetitor instanceof SpeedySportsman)
        		newCompetitor = ((SpeedySportsman) newCompetitor).getOriginalSportsman();
        	competition.getActiveCompetitors().set(index, (ICompetitor)newCompetitor);
        }

    }
    
    
	private void updateGUI() {
		/*
		 * function to update GUI when changes in the graphics happens
		 */
	    arenaPanel.removeAll();
	    icons.clear();
	    paintArena("/icons/" + ((WinterArena)arena).getWeatherCondition() + ".jpg");
	    for (ICompetitor competitor : competition.getActiveCompetitors()) {
	        IWinterSportsman sportsman = (IWinterSportsman) competitor;
	        addIcon(sportsman.getColor());
	    }
	}
	
    
    private void addIcon(String color) {
    	/*
    	 * function to add a competitor icon when a competitor is added
    	 */
    	String iconPath = null;
        if (competition instanceof SkiCompetition) {
            if (color == "Black")
            	iconPath = "/icons/SkiBlack.png";
            else if(color == "Light Green")
            	iconPath = "/icons/SkiLightGreen.png";
            else if(color == "Green")
            	iconPath = "/icons/SkiGreen.png";
            else if(color == "Orange")
            	iconPath = "/icons/SkiOrange.png";
            else if(color == "Red")
            	iconPath = "/icons/SkiRed.png";
            else if(color == "Pink")
            	iconPath = "/icons/SkiFemale.png";
            else if(color == "Blue")
            	iconPath = "/icons/SkiMale.png";
        }
        else {
        	if (color == "Black")
            	iconPath = "/icons/SnowboardBlack.png";
            else if(color == "Light Green")
            	iconPath = "/icons/SnowboardLightGreen.png";
            else if(color == "Green")
            	iconPath = "/icons/SnowboardGreen.png";
            else if(color == "Orange")
            	iconPath = "/icons/SnowboardOrange.png";
            else if(color == "Red")
            	iconPath = "/icons/SnowboardRed.png";
            else if(color == "Pink")
            	iconPath = "/icons/SnowboardFemale.png";
            else if(color == "Blue")
            	iconPath = "/icons/SnowboardMale.png";
        }
        
        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        icons.add(iconLabel);
        int iconCount = arenaPanel.getComponentCount() - 1;
        int x = 10 + (iconCount * 35);
        int y = 10;
        
        iconLabel.setBounds(x, y, 30, 30);
        arenaPanel.add(iconLabel, JLayeredPane.PALETTE_LAYER);
        arenaPanel.revalidate();
        arenaPanel.repaint();
    }
    
    
    private void paintArena(String imagePath) {
    	/*
    	 * function to customize the arena
    	 */
    	arenaPanel.removeAll();
    	ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
    	Image image = imageIcon.getImage().getScaledInstance(arenaPanel.getWidth(), arenaPanel.getHeight(), Image.SCALE_SMOOTH);
    	JLabel backgroundLabel = new JLabel(new ImageIcon(image));
        backgroundLabel.setBounds(0, 0, arenaPanel.getWidth(), arenaPanel.getHeight());
        arenaPanel.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        arenaPanel.revalidate();
        arenaPanel.repaint();
    }
    
    
    private static SnowSurface surfaceStringToEnum(String surface) {
    	/*
    	 * Function to return the E-num of the surface to initialize the arena.
    	 */
		switch (surface) {
		case "Powder":
			return SnowSurface.POWDER;
		case "Crud":
			return SnowSurface.CRUD;
		case "Ice":
			return SnowSurface.ICE;
		}
		return null;
	}
    

	private static WeatherCondition weatherConditionStringToEnum(String condition) {
		/*
    	 * Function to return the E-num of the weather condition to initialize the arena.
    	 */
		switch (condition) {
		case "Sunny":
			return WeatherCondition.SUNNY;
		case "Cloudy":
			return WeatherCondition.CLOUDY;
		case "Stormy":
			return WeatherCondition.STORMY;
		}
		return null;
	}
	
	private static League leagueStringToEnum(String league) {
		/*
    	 * Function to return the E-num of the league to initialize the competition.
    	 */
		switch (league) {
		case "Junior":
			return League.JUNIOR;
		case "Adult":
			return League.ADULT;
		case "Senior":
			return League.SENIOR;
		}
		return null;
	}
	
	private static Discipline disciplineStringToEnum(String discipline) {
		/*
    	 * Function to return the E-num of the surface to initialize the competition.
    	 */
		switch (discipline) {
		case "Slalom":
			return Discipline.SLALOM;
		case "Giant Slalom":
			return Discipline.GIANT_SLALOM;
		case "Downhill":
			return Discipline.DOWNHILL;
		case "Freestyle":
			return Discipline.FREESTYLE; 
		}
		return null;
	}
	
	private static Gender genderStringToEnum(String gender) {
		/*
    	 * Function to return the E-num of the surface to initialize the competition.
    	 */
		switch (gender) {
		case "Male":
			return Gender.MALE;
		case "Female":
			return Gender.FEMALE; 
		}
		return null;
	}
	
}