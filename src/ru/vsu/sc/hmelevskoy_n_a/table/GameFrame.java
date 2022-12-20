package ru.vsu.sc.hmelevskoy_n_a.table;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import ru.vsu.sc.hmelevskoy_n_a.table.panels.GamePanel;


public class GameFrame extends JFrame  {


	private GamePanel panel;
	public GameFrame(GamePanel gamePanel){
		super("Гуси и Лисы");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = gamePanel;
		this.add(panel);


		this.setJMenuBar(new JMenuBar());
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

}
