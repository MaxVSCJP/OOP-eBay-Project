import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class MessagingPageGUI {

    private JFrame frame;
    private JPanel leftPanel;
    private JPanel rightPanel;

    public MessagingPageGUI(ArrayList <User> people) {
        frame = new JFrame("Messaging Page");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on screen

        // Set system look and feel for a native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left panel for list of people
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Example data (replace with actual data handling logic)
        for (User person : people) {
            JPanel personPanel = createPersonPanel(person);
            leftPanel.add(personPanel);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical spacing
        }

        JScrollPane leftScrollPane = new JScrollPane(leftPanel);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScrollPane.setPreferredSize(new Dimension(200, frame.getHeight())); // Fixed width

        mainPanel.add(leftScrollPane, BorderLayout.WEST);

        // Right panel (initially empty, will be populated when person is clicked)
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Method to create a panel for each person in the left panel
    private JPanel createPersonPanel(User personName) {
        JPanel personPanel = new JPanel(new BorderLayout());
        personPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Light gray border

        // Center panel for person details
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE); // White background for details area

        JLabel nameLabel = new JLabel(personName.getUsername());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        centerPanel.add(nameLabel, BorderLayout.NORTH);



        // Mouse listener to open detailed panel when clicked
        personPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor
        personPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openDetailedPanel(personName.getUsername());
            }
        });
        personPanel.add(centerPanel);

        return personPanel;
    }

    // Method to open detailed messaging panel for a person
    private void openDetailedPanel(String personName) {
        rightPanel.removeAll(); // Clear previous content

        // Top panel with person's image and name
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE); // White background

        JLabel nameLabel = new JLabel(personName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font
        topPanel.add(nameLabel);

        rightPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for messages
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE); // White background

        JTextArea receivedMessages = new JTextArea();
        receivedMessages.setEditable(false);
        receivedMessages.setLineWrap(true); // Wrap text
        ArrayList<Massage> mess = MesssageSQL.retrivebyuser(personName);
        for (Massage m : mess) {
            receivedMessages.append(m.getSender() + " : " +m.getContent() + "\n");
        }

        JScrollPane messagesScrollPane = new JScrollPane(receivedMessages);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagesScrollPane.setPreferredSize(new Dimension(400, 250));

        centerPanel.add(messagesScrollPane, BorderLayout.CENTER);

        rightPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with text input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE); // White background

        PlaceholderTextField messageField = new PlaceholderTextField();
        bottomPanel.add(messageField, BorderLayout.CENTER);
        messageField.setPlaceholder("Message...");

        JButton sendButton = new JButton("Send"); // Replace with actual image
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                receivedMessages.append(LoginManager.getActiveUser() + " : " +message + "\n"); // Display sent message
                User uu = MesssageSQL.getuser(personName);
                Massage mess = new Massage(LoginManager.getActiveUser(), uu, message);
                MesssageSQL.ADD(mess);
                messageField.setText(""); // Clear input field
            }
        });
        bottomPanel.add(sendButton, BorderLayout.EAST);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Refresh the right panel
        rightPanel.revalidate();
        rightPanel.repaint();
    }

     public class PlaceholderTextField extends JTextField {
        private String placeholder;

        public PlaceholderTextField() {
        }

        public PlaceholderTextField(String pText) {
            super(pText);
        }

        @Override
        protected void paintComponent(Graphics pG) {
            super.paintComponent(pG);
            if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
                return;
            }
            Graphics2D g = (Graphics2D) pG;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(getDisabledTextColor());
            g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
        }

        public void setPlaceholder(String s) {
            placeholder = s;
        }
    }



}
