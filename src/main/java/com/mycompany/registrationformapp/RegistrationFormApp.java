/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationformapp;

/**
 *
 * @author Anisha.Amondi
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationFormApp extends JFrame {

    // Declare GUI components
    private final JTextField txtID;
    private final JTextField txtName;
    private final JTextField txtAddress;
    private final JTextField txtContact;
    private final JRadioButton maleButton;
    private final JRadioButton femaleButton;
    private final JTable table;
    private final DefaultTableModel model;
    private final ButtonGroup genderGroup;

    public RegistrationFormApp() {
        // Set up JFrame properties
        setTitle("Registration Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set background color
        getContentPane().setBackground(Color.PINK);

        // Layout manager for better control of layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // ID field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        txtID = new JTextField(10);
        gbc.gridx = 1;
        add(txtID, gbc);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);
        txtName = new JTextField(10);
        gbc.gridx = 1;
        add(txtName, gbc);

        // Gender selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Gender:"), gbc);
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        add(genderPanel, gbc);

        // Address field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Address:"), gbc);
        txtAddress = new JTextField(10);
        gbc.gridx = 1;
        add(txtAddress, gbc);

        // Contact field
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Contact:"), gbc);
        txtContact = new JTextField(10);
        gbc.gridx = 1;
        add(txtContact, gbc);

        // Register and Exit buttons
        JButton btnRegister = new JButton("Register");
        JButton btnExit = new JButton("Exit");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(btnRegister, gbc);
        gbc.gridx = 1;
        add(btnExit, gbc);

        // Table to display registered data with scroll pane
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Gender");
        model.addColumn("Address");
        model.addColumn("Contact");
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(550, 150));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        // Action listener for Register button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        // Action listener for Exit button
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void registerUser() {
        // Collect data from form
        String id = txtID.getText();
        String name = txtName.getText();
        String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        // Check if required fields are filled
        if (id.isEmpty() || name.isEmpty() || gender.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Insert data into database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_db", "root", "Daniels@2024")) {
            String query = "INSERT INTO users (ID, Name, Gender, Address, Contact) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setString(5, contact);
            stmt.executeUpdate();

            // Add data to JTable
            model.addRow(new Object[]{id, name, gender, address, contact});

            // Clear form fields
            txtID.setText("");
            txtName.setText("");
            txtAddress.setText("");
            txtContact.setText("");
            genderGroup.clearSelection();
            
            JOptionPane.showMessageDialog(this, "User registered successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationFormApp().setVisible(true));
    }
}
