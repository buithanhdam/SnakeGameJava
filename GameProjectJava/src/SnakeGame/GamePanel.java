package SnakeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	final static int SCREEN_WITDH = 600 ;
	final static int SCREEN_HEIGHT = 600 ;
	final static int UNIT_SIZE = 20 ;
	final static int GAME_UNIT = (SCREEN_HEIGHT*SCREEN_WITDH)/UNIT_SIZE ;
	final static int DELAY = 75 ;
	
	final  int x[] = new int[GAME_UNIT]  ;
	final  int y[] = new int[GAME_UNIT]  ;
	int bodyPart = 6;
	int appleEaten = 0;
	int appleX;
	int appleY;
	
	char direction = 'R';
	boolean running = false;
	boolean isEnd = true;
	Timer timer;
	Random random;
	
	
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WITDH,SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
		
	}
public void startGame() {
	 newApple();
	 running  = true;
	 timer = new Timer(DELAY,this);
	 timer.start();
	
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WITDH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_WITDH/UNIT_SIZE))*UNIT_SIZE;
		for (int i = 0; i < bodyPart; i++) {
			if (appleX == x[i] && appleY == y[i]) {
				bodyPart++;
				appleEaten++;
				newApple();
			}
		}
		
		
	}
public void draw(Graphics g) {
if(running) {		
//		for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE;i++) {
//			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//			g.drawLine(0, i*UNIT_SIZE, SCREEN_WITDH, i*UNIT_SIZE);
//		}
		g.setColor(Color.GREEN);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	
		for(int i = 0 ; i < bodyPart ; i++) {
		
			if(i==0) {
				g.setColor(Color.RED);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}else {
				
//				if(i % 2 ==0) {
//					
//					g.setColor(new Color(random.nextInt(200),random.nextInt(200),random.nextInt(200)));
//			
//				}else {
					g.setColor(new Color(48,129,85));
				
//				}
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				
			}
		}
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("Ink Free",Font.BOLD,25));
		FontMetrics metric = getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEaten, (SCREEN_WITDH - metric.stringWidth("Game Over"))/2, g.getFont().getSize());
}	
else {
	startOver(g);
}
		
	}
public void Move() {
	for(int i = bodyPart ; i>0; i--) {
		x[i] = x [i-1];
		y[i] = y [i-1];
		
	}
	int checkY = appleY - y[0];
	int checkX = appleX - x[0];
	if (checkY> 0) {
		
		if (checkX ==0) {
			if(direction != 'U') {
				direction = 'D';
			}else {
				checkOtherSide();
			}
		}else if (checkX>0) {
			if (appleY == y[0]) {
				if(direction != 'L') {
					direction = 'R';
				}else{
					checkOtherSide();
				}
			}
			if(direction != 'U') {
				direction = 'D';
			}else {
				checkOtherSide();
			}
		}else if(checkX <0) {
			if (appleY == y[0]) {
				if(direction != 'R') {
					direction = 'L';
				}else {
					checkOtherSide();
				}
				
			}
			if(direction != 'U') {
				direction = 'D';
			}else {
				checkOtherSide();
			}
		}
	}else if(checkY == 0) {
		if (checkX ==0) {
			return;
		}else if (checkX>0) {
			if(direction != 'L') {
				direction = 'R';
			}else {
				checkOtherSide();
			}
		}else if(checkX <0) {
			if(direction != 'R') {
				direction = 'L';
			}else {
				checkOtherSide();
			}
		}
	} else if (checkY < 0) {
		if (checkX ==0) {
			if(direction != 'D') {
				direction = 'U';
			}else {
				checkOtherSide();
			}
		}else if (checkX>0) {
			if (appleY == y[0]) {
				if(direction != 'L') {
					direction = 'R';
				}else {
					checkOtherSide();
				}
			}
			if(direction != 'D') {
				direction = 'U';
			}else {
				checkOtherSide();
			}
		}else if(checkX <0) {
			if (appleY == y[0]) {
				if(direction != 'R') {
					direction = 'L';
				}else {
					checkOtherSide();
				}
				
			}
			if(direction != 'D') {
				direction = 'U';
			}else {
				checkOtherSide();
			}
		}
	}
	
	switch(direction) {
	case 'U':
		y[0] = y[0] - UNIT_SIZE;
		break;
		
	case 'D':
		y[0] = y[0] + UNIT_SIZE;
		break;
	
	case 'L':
		x[0] = x[0] - UNIT_SIZE;
		break;
		
	case 'R':
		x[0] = x[0] + UNIT_SIZE;
		break;
	}
	
}
public void snakeHeadVision() {
	for(int i = bodyPart; i>0 ; i--) {
		if (direction =='U') {
			if (x[i] == x[0]&& y[i]==y[0]-UNIT_SIZE) {
				if (x[i] == x[0]-UNIT_SIZE && y[i]==y[0]) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0]+UNIT_SIZE && y[i]==y[0]) {
					isEnd = false;
					checkOtherSide();
					return;
				}
			}else {
				if (x[i] == x[0]-UNIT_SIZE && y[i]==y[0]) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0]+UNIT_SIZE && y[i]==y[0]) {
					isEnd = false;
					checkOtherSide();
					return;
				}
			}
		
			
		}else if (direction == 'D') {
			if (x[i] == x[0]&& y[i]==y[0]+UNIT_SIZE) {
				if (x[i] == x[0]+UNIT_SIZE && y[i]==y[0]) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0]-UNIT_SIZE && y[i]==y[0]) {
					isEnd = true;
					checkOtherSide();
					return;
				}
			}else {
				if (x[i] == x[0]+UNIT_SIZE && y[i]==y[0]) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0]-UNIT_SIZE && y[i]==y[0]) {
					isEnd = true;
					checkOtherSide();
					return;
				}
			}
			
		}else if (direction == 'L') {
			if (x[i] == x[0]-UNIT_SIZE && y[i]==y[0]) {
				if (x[i] == x[0] && y[i]==y[0]-UNIT_SIZE) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0] && y[i]==y[0]+UNIT_SIZE){
					isEnd = false;
					checkOtherSide();
					return;
				}
			}else {
				if (x[i] == x[0] && y[i]==y[0]-UNIT_SIZE) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0] && y[i]==y[0]+UNIT_SIZE){
					isEnd = false;
					checkOtherSide();
					return;
				}
				
			}
			
		}else if (direction == 'R') {
			if (x[i] == x[0]+UNIT_SIZE&& y[i]==y[0]) {
				if (x[i] == x[0] && y[i]==y[0]+UNIT_SIZE) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0] && y[i]==y[0]-UNIT_SIZE){
					isEnd = false;
					checkOtherSide();
					return;
				}
			}else {
				if (x[i] == x[0] && y[i]==y[0]+UNIT_SIZE) {
					isEnd = true;
					checkOtherSide();
					return;
				}else if (x[i] == x[0] && y[i]==y[0]-UNIT_SIZE){
					isEnd = false;
					checkOtherSide();
					return;
				}
			}
			
		}
	}
}

public void checkApple() {
	if(x[0] == appleX && y[0] == appleY) {
		bodyPart++;
		appleEaten++;
		newApple();
	}

	
}
public void checkOtherSide() {
	if (direction == 'U') {
		direction = 'R';
//		checkCollision();
		if (isEnd == false) {
			direction = 'L';
		}
	}else if (direction == 'R') {
		direction = 'U';
//		checkCollision();
		if (isEnd == false) {
			direction = 'D';
		}
		
	}else if (direction == 'L') {
		direction = 'D';
//		checkCollision();
		if (isEnd == false) {
			direction = 'U';
		}
		
	}else if (direction == 'D') {
		direction = 'L';
//		checkCollision();
		if (isEnd == false) {
			direction = 'R';
		}
	}
	
}
public void checkCollision() {
	// check when head touches body
	snakeHeadVision();
	isEnd = true;
	//head touches left border
	if(x[0] <0) {
		running = false;
	}
	//head touches R border
	if(x[0] > SCREEN_WITDH) {
		running = false;
	}
	//head touches top border
	if(y[0] <0) {
		running = false;
	}
	//head touches bot border
	if(y[0] >SCREEN_HEIGHT) {
		running = false;
	}
	
	if(!running) {
		timer.stop();
	}
	return;
	
}
public void startOver(Graphics g) {
	//score
	g.setColor(Color.GREEN);
	g.setFont(new Font("Ink Free",Font.BOLD,50));
	FontMetrics metric1 = getFontMetrics(g.getFont());
	g.drawString("Score: "+appleEaten, (SCREEN_WITDH - metric1.stringWidth("Game Over"))/2, g.getFont().getSize());
	
	//game over
	g.setColor(Color.RED);
	g.setFont(new Font("Ink Fee",Font.BOLD,75));
	FontMetrics metric = getFontMetrics(g.getFont());
	g.drawString("Game Over", (SCREEN_WITDH - metric.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	

	
}
	
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(running) {
			Move();
			checkApple();
			checkCollision();
			
		}
		repaint();
		
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
	}

}
