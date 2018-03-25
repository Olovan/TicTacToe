import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

//The Appearance Settings Window for changing the visual look and feel of the Application
public class AppearanceWindow extends JFrame {
	private final Dimension SIZE = new Dimension(400, 200);
	Board board;
	Settings settings;
	
	JTextField bgColorRedField;
	JTextField bgColorGreenField;
	JTextField bgColorBlueField;
	
	JTextField tileColorRedField;
	JTextField tileColorGreenField;
	JTextField tileColorBlueField;
	
	JTextField highlightFieldRed;
	JTextField highlightFieldGreen;
	JTextField highlightFieldBlue;
	
	JTextField winHighlightFieldRed;
	JTextField winHighlightFieldGreen;
	JTextField winHighlightFieldBlue;

	JTextField loseHighlightFieldRed;
	JTextField loseHighlightFieldGreen;
	JTextField loseHighlightFieldBlue;
	
	JButton accept;
	JButton cancel;

	public AppearanceWindow(Board board)
	{
		//Assign Settings and Board references
		this.board = board;
		settings = board.settings;

		//Window Settings
		setTitle("Appearance Settings");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 20));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setLocation(board.getLocation().x + 100, board.getLocation().y + 100);
		setMinimumSize(SIZE);
		
		mainPanel.add(new TextureSelectionPanel());
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(new JLabel("Background Color"));
		mainPanel.add(new BackgroundColorPanel());
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(new JLabel("Tile Color"));
		mainPanel.add(new TileColorPanel());
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(new JLabel("Highlight Color"));
		mainPanel.add(new HighlightColorPanel());
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(new JLabel("X Win Color"));
		mainPanel.add(new WinHighlightPanel());
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(new JLabel("O Win Color"));
		mainPanel.add(new LoseHighlightPanel());
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(new ButtonPanel());
		add(mainPanel);
		
		loadSettings();
		
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	
	//Load the current settings into the Text Fields when the Window Opens
	private void loadSettings()
	{
		bgColorRedField.setText(Integer.toString(settings.BACKGROUND_COLOR.getRed()));
		bgColorGreenField.setText(Integer.toString(settings.BACKGROUND_COLOR.getGreen()));
		bgColorBlueField.setText(Integer.toString(settings.BACKGROUND_COLOR.getBlue()));

		tileColorRedField.setText(Integer.toString(settings.TILE_COLOR.getRed()));
		tileColorGreenField.setText(Integer.toString(settings.TILE_COLOR.getGreen()));
		tileColorBlueField.setText(Integer.toString(settings.TILE_COLOR.getBlue()));

		highlightFieldRed.setText(Integer.toString(settings.HIGHLIGHT_COLOR.getRed()));
		highlightFieldGreen.setText(Integer.toString(settings.HIGHLIGHT_COLOR.getGreen()));
		highlightFieldBlue.setText(Integer.toString(settings.HIGHLIGHT_COLOR.getBlue()));

		winHighlightFieldRed.setText(Integer.toString(settings.WIN_HIGHLIGHT.getRed()));
		winHighlightFieldGreen.setText(Integer.toString(settings.WIN_HIGHLIGHT.getGreen()));
		winHighlightFieldBlue.setText(Integer.toString(settings.WIN_HIGHLIGHT.getBlue()));

		loseHighlightFieldRed.setText(Integer.toString(settings.LOSE_HIGHLIGHT.getRed()));
		loseHighlightFieldGreen.setText(Integer.toString(settings.LOSE_HIGHLIGHT.getGreen()));
		loseHighlightFieldBlue.setText(Integer.toString(settings.LOSE_HIGHLIGHT.getBlue()));
	}
	
	//Save the current TextField data into the settings object and serialize it
	private void saveSettings()
	{
		int r, g, b;
		//Make sure settings are valid
		if(validateSettings())
		{
			r = Integer.parseInt(bgColorRedField.getText());
			g = Integer.parseInt(bgColorGreenField.getText());
			b = Integer.parseInt(bgColorBlueField.getText());
			settings.BACKGROUND_COLOR = new Color(r, g, b);

			r = Integer.parseInt(tileColorRedField.getText());
			g = Integer.parseInt(tileColorGreenField.getText());
			b = Integer.parseInt(tileColorBlueField.getText());
			settings.TILE_COLOR = new Color(r, g, b);
			
			r = Integer.parseInt(highlightFieldRed.getText());
			g = Integer.parseInt(highlightFieldGreen.getText());
			b = Integer.parseInt(highlightFieldBlue.getText());
			settings.HIGHLIGHT_COLOR = new Color(r, g, b);
			
			r = Integer.parseInt(winHighlightFieldRed.getText());
			g = Integer.parseInt(winHighlightFieldGreen.getText());
			b = Integer.parseInt(winHighlightFieldBlue.getText());
			settings.WIN_HIGHLIGHT = new Color(r, g, b);
			
			r = Integer.parseInt(loseHighlightFieldRed.getText());
			g = Integer.parseInt(loseHighlightFieldGreen.getText());
			b = Integer.parseInt(loseHighlightFieldBlue.getText());
			settings.LOSE_HIGHLIGHT = new Color(r, g, b);
			
			//Now load the new colors into the board and save the settings
			board.reloadColors();
			settings.defaultFileExport();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Invalid Color Input. \nColors must be numbers between 0 - 255", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Make sure the color values are valid
	Boolean validateSettings()
	{
		if(!validateColorField(bgColorRedField))
			return false;
		if(!validateColorField(bgColorGreenField))
			return false;
		if(!validateColorField(bgColorBlueField))
			return false;
		if(!validateColorField(tileColorRedField))
			return false;
		if(!validateColorField(tileColorGreenField))
			return false;
		if(!validateColorField(tileColorBlueField))
			return false;
		if(!validateColorField(highlightFieldRed))
			return false;
		if(!validateColorField(highlightFieldGreen))
			return false;
		if(!validateColorField(highlightFieldBlue))
			return false;
		if(!validateColorField(winHighlightFieldRed))
			return false;
		if(!validateColorField(winHighlightFieldGreen))
			return false;
		if(!validateColorField(winHighlightFieldBlue))
			return false;
		if(!validateColorField(loseHighlightFieldRed))
			return false;
		if(!validateColorField(loseHighlightFieldGreen))
			return false;
		if(!validateColorField(loseHighlightFieldBlue))
			return false;
		return true;
	}
	
	//Make sure the field contains an int between 0 and 255
	Boolean validateColorField(JTextField field)
	{
		int value;
		try {
			value = Integer.parseInt(field.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(value > 255 || value < 0)
			return false;
		
		return true;
	}
	
	//Private classes to hold the various Sub-Panels that make up the Appearance Window
	//TextureSelectionPanel holds the Panel allowing you to select a new file to serve as your
	//X or O texture. The new Texture is automatically loaded into settings when you accept it rather
	//than waiting for you to hit the "Save" button
	private class TextureSelectionPanel extends JPanel
	{
		JLabel xImgPathLabel;
		JLabel oImgPathLabel;
		JButton xPathButton;
		JButton oPathButton;

		public TextureSelectionPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel xSelectionPanel = new JPanel();
			JPanel oSelectionPanel = new JPanel();

			xSelectionPanel.setLayout(new BoxLayout(xSelectionPanel, BoxLayout.X_AXIS));
			oSelectionPanel.setLayout(new BoxLayout(oSelectionPanel, BoxLayout.X_AXIS));

			xPathButton = new JButton("Change");
			xImgPathLabel = new JLabel("X Texture: " + settings.xImgPath.substring(settings.xImgPath.lastIndexOf("/") + 1, settings.xImgPath.length()));
			oPathButton = new JButton("Change");
			oImgPathLabel = new JLabel("O Texture: " + settings.oImgPath.substring(settings.oImgPath.lastIndexOf("/") + 1, settings.oImgPath.length()));
			
			xPathButton.addActionListener(new TextureSelectorListener("X"));
			oPathButton.addActionListener(new TextureSelectorListener("O"));

			xSelectionPanel.add(xImgPathLabel);
			xSelectionPanel.add(Box.createHorizontalGlue());
			xSelectionPanel.add(xPathButton);

			oSelectionPanel.add(oImgPathLabel);
			oSelectionPanel.add(Box.createHorizontalGlue());
			oSelectionPanel.add(oPathButton);
			
			add(xSelectionPanel);
			add(oSelectionPanel);
		}
		
		private class TextureSelectorListener implements ActionListener
		{
			String target;
			public TextureSelectorListener(String target)
			{
				this.target = target;
			}
			
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./images"));
				int result = chooser.showOpenDialog(AppearanceWindow.this);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					if(target.equals("X"))
					{
						settings.xImgPath = chooser.getSelectedFile().getPath();
						xImgPathLabel.setText("X Texture: " + chooser.getSelectedFile().getName());
						settings.loadImages();
					}
					else if(target.equals("O"))
					{
						settings.oImgPath = chooser.getSelectedFile().getPath();
						oImgPathLabel.setText("O Texture: " + chooser.getSelectedFile().getName());
						settings.loadImages();
					}
					
				}
			}
		}
	}
	
	private class BackgroundColorPanel extends JPanel
	{
		public BackgroundColorPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			bgColorRedField = new JTextField(3);
			bgColorGreenField = new JTextField(3);
			bgColorBlueField = new JTextField(3);
			
			add(new JLabel("R:"));
			add(bgColorRedField);
			add(Box.createHorizontalGlue());
			add(new JLabel("G:"));
			add(bgColorGreenField);
			add(Box.createHorizontalGlue());
			add(new JLabel("B:"));
			add(bgColorBlueField);
		}
	}
	
	private class TileColorPanel extends JPanel
	{
		public TileColorPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			tileColorRedField = new JTextField(3);
			tileColorGreenField = new JTextField(3);
			tileColorBlueField = new JTextField(3);
			
			add(new JLabel("R:"));
			add(tileColorRedField);
			add(Box.createHorizontalGlue());
			add(new JLabel("G:"));
			add(tileColorGreenField);
			add(Box.createHorizontalGlue());
			add(new JLabel("B:"));
			add(tileColorBlueField);
		}
	}

	private class HighlightColorPanel extends JPanel
	{
		public HighlightColorPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			highlightFieldRed = new JTextField(3);
			highlightFieldGreen = new JTextField(3);
			highlightFieldBlue = new JTextField(3);
			
			add(new JLabel("R:"));
			add(highlightFieldRed);
			add(Box.createHorizontalGlue());
			add(new JLabel("G:"));
			add(highlightFieldGreen);
			add(Box.createHorizontalGlue());
			add(new JLabel("B:"));
			add(highlightFieldBlue);
		}
	}

	private class WinHighlightPanel extends JPanel
	{
		public WinHighlightPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			winHighlightFieldRed = new JTextField(3);
			winHighlightFieldGreen = new JTextField(3);
			winHighlightFieldBlue = new JTextField(3);
			
			add(new JLabel("R:"));
			add(winHighlightFieldRed);
			add(Box.createHorizontalGlue());
			add(new JLabel("G:"));
			add(winHighlightFieldGreen);
			add(Box.createHorizontalGlue());
			add(new JLabel("B:"));
			add(winHighlightFieldBlue);
		}
	}

	private class LoseHighlightPanel extends JPanel
	{
		public LoseHighlightPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			loseHighlightFieldRed = new JTextField(3);
			loseHighlightFieldGreen = new JTextField(3);
			loseHighlightFieldBlue = new JTextField(3);
			
			add(new JLabel("R:"));
			add(loseHighlightFieldRed);
			add(Box.createHorizontalGlue());
			add(new JLabel("G:"));
			add(loseHighlightFieldGreen);
			add(Box.createHorizontalGlue());
			add(new JLabel("B:"));
			add(loseHighlightFieldBlue);
		}
	}
	
	private class ButtonPanel extends JPanel implements ActionListener
	{
		public ButtonPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			accept = new JButton("Save");
			cancel = new JButton("Cancel");
			
			accept.addActionListener(this);
			cancel.addActionListener(this);
			
			add(Box.createHorizontalGlue());
			add(accept);
			add(cancel);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			switch(e.getActionCommand())
			{
			case "Save":
				saveSettings();
				break;
			case "Cancel":
				AppearanceWindow.this.setVisible(false);
				break;
			}
		}
		
	}
}
