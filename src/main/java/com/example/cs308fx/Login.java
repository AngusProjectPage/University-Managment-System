package com.example.cs308fx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener {
    JFrame frame;
    JButton loginButton;
    JTextField userIdField;
    JPasswordField userPasswordField;
    JLabel userIdLabel;
    JLabel userPasswordLabel;
    JLabel displayMessage;

    Login() {
        // Instatiate frame variables
        frame = new JFrame();
        loginButton = new JButton("Login");
        userIdField = new JTextField();
        userPasswordField = new JPasswordField();
        userIdLabel = new JLabel("userId:");
        userPasswordLabel = new JLabel("password:");
        displayMessage = new JLabel("User Login");

        // Set widgets
        userIdLabel.setBounds(50,100,75,25);
        userPasswordLabel.setBounds(50,150,75,25);

        userIdField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        loginButton.setBounds(138,200,100,25);
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);

        displayMessage.setBounds(125, 50, 250, 35);
        displayMessage.setFont(new Font(null,Font.ITALIC,25));

        // Add widgets to frame
        frame.add(userIdLabel);
        frame.add(userPasswordLabel);
        frame.add(userIdField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(displayMessage);

        // Set frame settings and display
        frame.setTitle("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
