import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.Random;

class SnakeGame extends JPanel implements KeyListener,ActionListener
{
	private ImageIcon titleimage;
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon snakeimage;
	private ImageIcon enemyimage;

	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean pause = true;

	private int length = 3;
	private int delay = 100;
	private int move = 0;
	private int score = 0;
	private int x[] = new int[750];
	private int y[] = new int[750];
	private int fx[] = {14,7,20};
	private int fy[] = {4,5,9};
	private int fxpos[] = { 25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850 };
	private int fypos[] = { 75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625 };
	
	private Random random = new Random();
	private Timer timer;

	public SnakeGame()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		timer = new Timer(delay,this);
		timer.start();
	}

	public void paint(Graphics g)
	{
		if(move == 0)
		{
			x[0]=100;
			x[1]=75;
			x[2]=50;

			y[0]=100;
			y[1]=100;
			y[2]=100;
		}

		// draw title image border

		g.setColor(Color.RED);
		g.drawRect(24,10,851,55);

		// draw the title image 

		titleimage = new ImageIcon("icons/snaketitle.jpg");
		titleimage.paintIcon(this,g,25,11);

		//draw border for gameplay
		g.setColor(Color.WHITE);
		g.drawRect(24,74,851,577);

		//draw background for the gameplay
		g.setColor(Color.BLACK);
		g.fillRect(25,75,850,575);

		//draw score and length bar
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.drawString("Scores: "+score,780,30);

		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.drawString("Length: "+length,780,50);

		rightmouth = new ImageIcon("icons/rightmouth.png");
		leftmouth = new ImageIcon("icons/leftmouth.png");
		upmouth = new ImageIcon("icons/upmouth.png");
		downmouth = new ImageIcon("icons/downmouth.png");

		rightmouth = new ImageIcon("icons/rightmouth.png");
		rightmouth.paintIcon(this,g,x[0],y[0]);

		for(int i=0; i<length; i++)
		{
			if(i==0 && right)
			{
				rightmouth = new ImageIcon("icons/rightmouth.png");
				rightmouth.paintIcon(this,g,x[i],y[i]);
			}
			else if(i==0 && left)
			{
				leftmouth = new ImageIcon("icons/leftmouth.png");
				leftmouth.paintIcon(this,g,x[i],y[i]);
			}
			else if(i==0 && up)
			{
				upmouth = new ImageIcon("icons/upmouth.png");
				upmouth.paintIcon(this,g,x[i],y[i]);
			}
			else if(i==0 && down)
			{
				downmouth = new ImageIcon("dicons/ownmouth.png");
				downmouth.paintIcon(this,g,x[i],y[i]);
			}
			if(i!=0)
			{
				snakeimage = new ImageIcon("icons/snakeimage.png");
				snakeimage.paintIcon(this,g,x[i],y[i]);
			}
		}

		for(int i=0; i<3; i++)
		{
			enemyimage = new ImageIcon("icons/enemy.png");
			enemyimage.paintIcon(this,g,fxpos[fx[i]],fypos[fy[i]]);
		}

		for(int i=0; i<3; i++)
		if(fxpos[fx[i]] == x[0] && fypos[fy[i]] == y[0])
		{
			length++;
			score+=5;
			fx[i] = random.nextInt(34);
			fy[i] = random.nextInt(23);
		}

		for(int i=1; i<length; i++)
		{
			if(x[0]==x[i] && y[0]==y[i] || move == 0)
			{
				if(move!=0)
				{
					move = 0;
					score = 0;
					length = 3;

					g.setColor(Color.YELLOW);
					g.setFont(new Font("arial",Font.PLAIN,20));
					g.drawString("<<<<< GAME OVER >>>>>",300,300);
					g.drawString("PRESS ENTER KEY TO RESTART",300,350);

					pause = true;
					timer.stop();
				}
				else
				{
					g.setColor(Color.YELLOW);
					g.setFont(new Font("arial",Font.PLAIN,20));
					g.drawString("LET'S PLAY THE GAME",300,300);
					g.drawString("PRESS SPACE KEY TO PAUSE AND RESUME GAME ",300,350);
				}
			}
		}

		g.dispose();
	}

	public void actionPerformed(ActionEvent e)
	{
		timer.start();

		if(right)
		{
			for(int i=length-1; i>=0; i--)
			{
				y[i+1]=y[i];
			}
			for(int i=length; i>=0; i--)
			{
				if(i==0)
					x[i]=x[i]+25;
				else
					x[i]=x[i-1];
				if(x[i]>850)
					x[i]=25;
			}

			repaint();
		}
		else if(left)
		{
			for(int i=length-1; i>=0; i--)
			{
				y[i+1]=y[i];
			}
			for(int i=length; i>=0; i--)
			{
				if(i==0)
					x[i]=x[i]-25;
				else
					x[i]=x[i-1];
				if(x[i]<25)
					x[i]=850;
			}

			repaint();
		}
		else if(down)
		{
			for(int i=length-1; i>=0; i--)
			{
				x[i+1]=x[i];
			}
			for(int i=length; i>=0; i--)
			{
				if(i==0)
					y[i]=y[i]+25;
				else
					y[i]=y[i-1];
				if(y[i]>625)
					y[i]=75;
			}

			repaint();
		}
		else if(up)
		{
			for(int i=length-1; i>=0; i--)
			{
				x[i+1]=x[i];
			}
			for(int i=length; i>=0; i--)
			{
				if(i==0)
					y[i]=y[i]-25;
				else
					y[i]=y[i-1];
				if(y[i]<75)
					y[i]=625;
			}

			repaint();
		}
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			move = 0;
			score = 0;
			length = 3;

			right = false;
			left = false;
			up = false;
			down = false;

			timer.start();
			repaint();
		}
	    else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(pause)
				timer.stop();
			else
				timer.start();

			pause = !pause;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			move++;

			if(!right)
				left = true;

			up = false;
			down = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			move++;

			if(!left)
				right = true;

			up = false;
			down = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			move++;

			if(!down)
				up = true;

			right = false;
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			move++;

			if(!up)
				down = true;

			right = false;
			left = false;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		
	}

	public static void main(String [] str) {

		JFrame obj = new JFrame();

		SnakeGame snakeGame = new SnakeGame();

		obj.setBounds(10,10,905,700);
		obj.setBackground(Color.GREEN);
		obj.setResizable(true);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(snakeGame);

	}
}
