package ru.vsu.sc.hmelevskoy_n_a.table;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.vsu.sc.hmelevskoy_n_a.table.panels.GamePanelHot;



public class Menu extends JFrame {

	JPanel menuPanel ;
	JButton play;
	JLabel name;
	public Menu() {
		super("Гули и Лисы");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font f =new Font(Font.DIALOG, Font.PLAIN, 24);
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = .25;
        c.insets = new Insets(15, 40, 15, 40);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
		this.add(menuPanel);
		
		name = new JLabel("Гуси и Лисы");
		play = new JButton("Начать игру");
		
		name.setFont(f);
		play.setFont(f);
		
		menuPanel.setBackground(new Color(0x889B6E));
		play.setBackground(new Color(0x695D5D));
		play.setOpaque(true);

		
		play.addActionListener(e -> {
			new GameFrame(new GamePanelHot());
			setVisible(false);
			dispose();
		});

		menuPanel.add(name ,c);
		menuPanel.add(play,c);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);


	}
}
