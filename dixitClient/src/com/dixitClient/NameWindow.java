//package com.dixitClient;
//
//import com.dixitClient.dixit.Gamer;
//import com.dixitClient.dixit.ServerClientManager;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class NameWindow extends JFrame implements ActionListener {
//    private JButton button;
//    private JTextField nameTextField;
//    private PrintWriter printWriter;
//
//    private JTextField nameTextField;
//    private JTextField ipTextField;
//    private JTextField portTextField;
//    private JButton zatwierdźIPołączButton;
//
//    private Gamer gamer;
//    private ServerClientManager serverClientManager;
//
//    public NameWindow(){
//        JLabel panel = new JLabel();
//
//        nameTextField = new JTextField();
//        nameTextField.setEditable( true );
//        nameTextField.setHorizontalAlignment(JTextField.RIGHT);
//
//
//        button = new JButton("Potwierdź");
//        button.addActionListener( this );
//        button.setMinimumSize(new Dimension(25,10));
//        button.setMinimumSize(new Dimension(25,10));
//
//        panel.setLayout(new GridLayout(25,25));
//        panel.setBackground( new Color(0, 0, 0, 0) );
//
//            panel.add(new JLabel("Choose Your name"));
//            panel.add(nameTextField);
//            panel.add(button);
//
//        getContentPane().add(panel);
//        setResizable( true );
//    }
//
//    public NameWindow(Socket s) throws IOException {
//        printWriter=new PrintWriter(s.getOutputStream());
//        nameTextField = new JTextField();
//        nameTextField.setEditable( true );
//        nameTextField.setHorizontalAlignment(JTextField.RIGHT);
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout( new GridLayout(0, 1) );
//
//        JButton button = new JButton("Potwierdź");
//        button.addActionListener( this );
//        buttonPanel.add(button);
//
//        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//        getContentPane().add(nameTextField, BorderLayout.CENTER);
//        setResizable( false );
//
//
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        sendName();
//    }
//    private void sendName() {
//        printWriter.println(nameTextField.getText());
//        printWriter.flush();
//    }
//}
