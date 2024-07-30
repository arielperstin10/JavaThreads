/**
 * Ariel Perstin
 * 324265164
 */

package utilities;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import game.arena.WinterArena;
import game.competition.ICompetitor;
import game.competition.SkiCompetition;
import game.competition.WinterCompetition;
import game.entities.sportsman.Skier;
import game.entities.sportsman.Snowboarder;
import game.entities.sportsman.WinterSportsman;
import game.enums.*;

public class CompetitionGUI extends JFrame {

    private WinterArena arena;
    private WinterCompetition competition;
    
    private JLayeredPane arenaPanel;
    private JPanel controlPanel;
    private JPanel competitorsIconsPanel;
    
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
    
    private JTable infoTable;
    private DefaultTableModel tableModel;
    
    private ArrayList<JLabel> icons;
    
    private boolean isStarted;
    private boolean isFinished;
    
    public CompetitionGUI() {
    	/*
    	 * GUI constructor
    	 */
        super("Competition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 730);
        setLayout(new BorderLayout());
        
        initComponents();
        createLayout();
        addActionListeners();
    }

    private void initComponents() {
    	/*
    	 * initializing components
    	 */
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
        
        infoTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Name", "Speed", "Max Speed", "Location", "Finished"}, 0);
        infoTable.setModel(tableModel);
        
        icons = new ArrayList<>();
    }

    private void createLayout() {
    	/*
    	 * Creating a layout for the GUI frame.
    	 */
        add(arenaPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        add(competitorsIconsPanel, BorderLayout.NORTH);
		
        controlPanel.add(createLabeledSection("Build Arena", 
            createComponent("Arena Length", arenaLength),
            createComponent("Snow Surface", snowSurface),
            createComponent("Weather Condition", weatherCondition),
            createButton("Build Arena")));
        
        controlPanel.add(createLabeledSection("Create Competition", 
            createComponent("Choose Competition", competitionChoice),
            createComponent("Max Competitors Number", maxCompetitors),
            createComponent("Discipline", disciplineChoice),
            createComponent("League", leagueChoice),
            createComponent("Gender", genderChoice),
            createButton("Create Competition")));
        
        controlPanel.add(createLabeledSection("Add Competitor", 
            createComponent("Name", name),
            createComponent("Age", age),
            createComponent("Max speed", maxSpeed),
            createComponent("Acceleration", acceleration),
            createButton("Add Competitor")));
        
        controlPanel.add(createButton("Start Competition"));
        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(createButton("Show Info"));
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
    }

    private void buildArena() {
    	/*
    	 * Build arena button action listener functionality
    	 */
        try {
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
            	arenaPanel.removeAll();
                String imagePath = "/icons/" + weatherCondition.getSelectedItem().toString() + ".jpg";
                ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
                if (length > 730)
                	resizeFrame(length);
                Image image = imageIcon.getImage().getScaledInstance(arenaPanel.getWidth(), arenaPanel.getHeight(), Image.SCALE_SMOOTH);
                arena = new WinterArena(length, surfaceStringToEnum(snowSurface.getSelectedItem().toString()), weatherConditionStringToEnum(weatherCondition.getSelectedItem().toString()));
                JLabel backgroundLabel = new JLabel(new ImageIcon(image));
                backgroundLabel.setBounds(0, 0, arenaPanel.getWidth(), arenaPanel.getHeight());
                arenaPanel.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
                JOptionPane.showMessageDialog(this, "Arena built successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                arenaPanel.revalidate();
                arenaPanel.repaint();
            }
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
                	String iconPath = "";
                	Class cls;
                	ClassLoader cl = ClassLoader.getSystemClassLoader();
                	Constructor ctor;
                    if (competition instanceof SkiCompetition) {
                    	try {
                    		cls = cl.loadClass("game.entities.sportsman.Skier");
                    		ctor = cls.getConstructor(String.class, double.class, Gender.class, double.class, double.class, Discipline.class);
                    		WinterSportsman competitor = (Skier) ctor.newInstance(name.getText(), ageValue, competition.getGender(), accelerationValue, maxSpeedValue, competition.getDiscipline());
                    		competition.addCompetitor(competitor);
                    		competitor.setArena(arena);
                        	iconPath = competition.getGender() == Gender.MALE ? "/icons/SkiMale.png" : "/icons/SkiFemale.png";
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
                    		competitor.setArena(arena);
                        	iconPath = competition.getGender() == Gender.MALE ? "/icons/SnowboardMale.png" : "/icons/SnowboardFemale.png";
                        	JOptionPane.showMessageDialog(this, "New snowboarder has been added successfully to the competition!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    	} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            JOptionPane.showMessageDialog(this, "Error creating competition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
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
        JFrame tableFrame = new JFrame("Competitors Information");
        tableFrame.setSize(600, 400);
        tableFrame.setLocationRelativeTo(this);
        
        JScrollPane scrollPane = new JScrollPane(infoTable);
        tableFrame.add(scrollPane);
        tableModel.setRowCount(0);
        
        if (competition != null) {
            for (int i = 0; i < competition.getActiveCompetitors().size(); i++) {
                WinterSportsman ws = (WinterSportsman) competition.getActiveCompetitors().get(i);
                tableModel.addRow(new Object[]{
                    ws.getName(),
                    ws.getSpeed(),
                    ws.getMaxSpeed(),
                    ws.getLocation().getX(),
                    "No"
                });
            }
            
            for (int i = 0; i < competition.getFinishedCompetitors().size(); i++) {
                WinterSportsman ws = (WinterSportsman) competition.getFinishedCompetitors().get(i);
                tableModel.addRow(new Object[]{
                    ws.getName(),
                    ws.getSpeed(),
                    ws.getMaxSpeed(),
                    ws.getLocation().getX(),
                    "Yes"
                });
            }
        }
        
        tableFrame.setVisible(true);
    }

    private JPanel createLabeledSection(String title, JComponent...components) {
    	/*
    	 * Create a section for given components.
    	 */
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("<html><u>" + title + "</u></html>");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        titleLabel.setForeground(Color.BLUE);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(1));

        for(JComponent component : components) {
            component.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(component);
            panel.add(Box.createVerticalStrut(1));
        }

        panel.add(Box.createHorizontalStrut(10));
        return panel;
	}
    
	private JPanel createComponent(String label, JComponent component) {
		/*
    	 * Create a single component.
    	 */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(label));
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (component instanceof JTextField) {
            ((JTextField) component).setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        } else if (component instanceof JComboBox) {
            ((JComboBox<?>) component).setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        }
        panel.add(component);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
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
    	setSize(1000, (int) arenaLength);
    	revalidate();
    	repaint();
    }
    
    private void finishedMessage() {
    	/*
    	 * A function to return a message when competition is finished.
    	 */
    	JOptionPane.showMessageDialog(this, "Competition has finished.", "Success", JOptionPane.INFORMATION_MESSAGE);
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