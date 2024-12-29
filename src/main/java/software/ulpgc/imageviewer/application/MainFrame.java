package software.ulpgc.imageviewer.application;

import software.ulpgc.imageviewer.control.Command;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainFrame extends JFrame {

    private final Map<String, Command> commands;

    public MainFrame(Component display) {
        this.commands = new HashMap<>();
        initializeFrame(display);
        add(createButtonPanel("next"), BorderLayout.EAST);
        add(createButtonPanel("previous"), BorderLayout.WEST);
    }

    private void initializeFrame(Component display) {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 675);
        this.setLocationRelativeTo(null);
        this.add(display, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel(String commandName) {
        JPanel panel = new JPanel();
        panel.add(ButtonFactory.createButton(commandName, commands, this.getWidth(), this.getHeight()));
        return panel;
    }

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    static class ButtonFactory {

        public static JButton createButton(String name, Map<String, Command> commands, int frameWidth, int frameHeight) {
            JButton button = new JButton(name);
            button.setPreferredSize(new Dimension((int) (frameWidth * 0.1), frameHeight));
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.addActionListener(e -> executeCommand(name, commands));
            return button;
        }

        private static void executeCommand(String name, Map<String, Command> commands) {
            Command command = commands.get(name);
            if (command != null) {
                command.execute();
            }
        }
    }
}

