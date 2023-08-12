package SnakeGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	
	GameFrame(){
		
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.add(new GamePanel());
	this.isResizable();
	this.pack();
	this.setVisible(true);
	this.setLocationRelativeTo(null);
	
	
	
	
	}
	

}
