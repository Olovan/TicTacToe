import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;


/**Tile handles the ActionListeners and visuals of each individual Tile in Tic Tac Toe
 *
 *
 * 							*/
public class Tile extends JPanel implements MouseListener
{
	public enum HIGHLIGHT {WIN, LOSE, NORMAL};
	public HIGHLIGHT overlay;
	Board board;
	Settings settings;
	String value = "";
	Color background;
	int index;
	Font font = new Font("ARIAL", Font.BOLD, 150);


	public Tile(int index, Board board)
	{
		this.settings = board.settings;
		setMinimumSize(settings.TILE_SIZE);
		setMaximumSize(settings.TILE_SIZE);
		setPreferredSize(settings.TILE_SIZE);
		setBackground(settings.TILE_COLOR);
		background = settings.TILE_COLOR;
		this.index = index;
		this.board = board;

		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//Draw Win or Lose highlight
		if(overlay == HIGHLIGHT.WIN)
		{
			setBackground(settings.WIN_HIGHLIGHT);
		}
		else if(overlay == HIGHLIGHT.LOSE)
		{
			setBackground(settings.LOSE_HIGHLIGHT);
		}

		//Draw Xs or Os
		if(settings.xImg != null && value.equals("X"))
		{
			g.drawImage(settings.xImg, 0, 0, getWidth(), getHeight(),  null);
		}
		else if(settings.oImg != null && value.equals("O"))
		{
			g.drawImage(settings.oImg, 0, 0, getWidth(), getHeight(), null);
		}
		else
		{
			drawString(value, g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		setBackground(settings.HIGHLIGHT_COLOR);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		setBackground(background);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(GameLogic.state != GameLogic.GAMESTATE.ACTIVE)
		{
			GameLogic.gameOver(GameLogic.state);
			return;
		}
		if(value == "" && GameLogic.turn == GameLogic.TURN.PLAYER)
		{
			GameLogic.playerTurn(this);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	//Backup Rendering using Text
	public void drawString(String str, Graphics g)
	{
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle2D rect = metrics.getStringBounds(str, g);
		int x = getWidth() /2 - (int)rect.getWidth() / 2;
		int y = getHeight()/2 + 50;
		g.drawString(str, x, y);
	}
}
