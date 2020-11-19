package com.dixitClient.dixit.login;

import com.dixitClient.dixit.DixitGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow implements Runnable {

	private JFrame frame;
	private JTextField nameTextBox;
	private JTextField ipTextBox;
	private JTextField portTextBox;

	public LoginWindow() {
		run();
	}

	public JFrame getFrame() {
		return frame;
	}

	@Override
	public void run() {
		frame = new JFrame();
		frame.setBounds(10, 10, 300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Twoje imię:");
		lblName.setBounds(10, 31, 100, 14);
		frame.getContentPane().add(lblName);

		JLabel lblPhone = new JLabel("Podaj adres IP");
		lblPhone.setBounds(10, 68, 100, 14);
		frame.getContentPane().add(lblPhone);

		JLabel lblEmailId = new JLabel("Port");
		lblEmailId.setBounds(10, 105, 100, 14);
		frame.getContentPane().add(lblEmailId);

		nameTextBox = new JTextField();
		nameTextBox.setBounds(128, 28, 86, 20);
		frame.getContentPane().add(nameTextBox);
		nameTextBox.setColumns(10);

		ipTextBox = new JTextField("localhost");
		ipTextBox.setBounds(128, 65, 120, 20);
		frame.getContentPane().add(ipTextBox);
		ipTextBox.setColumns(16);


		portTextBox = new JTextField("9090");
		portTextBox.setBounds(128, 102, 86, 17);
		frame.getContentPane().add(portTextBox);
		portTextBox.setColumns(5);


		JButton btnSubmit = new JButton("Potwierdż");

		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(65, 135, 89, 23);
		frame.getContentPane().add(btnSubmit);
		frame.setMinimumSize(new Dimension(300,200));
		frame.setMaximumSize(new Dimension(300,200));
		frame.setResizable(false);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nameTextBox.getText().isEmpty() || (ipTextBox.getText().isEmpty()) || (portTextBox.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Uzupełnij wszystkie pola");
				} else {
					LoginManager.name = PolishLettersParser.parse(nameTextBox.getText());
					LoginManager.ip = ipTextBox.getText();
					LoginManager.port = Integer.parseInt(portTextBox.getText());
					LoginManager.finish = true;
					frame.dispose();
				}
			}
		});
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
}
