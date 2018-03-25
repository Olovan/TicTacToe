import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

//Settings tracks all your appearance and difficulty settings and the current scoreboard data
//It exists to centralize all the mutable data so it can be easily saved and loaded
public class Settings implements Serializable{

	private static final String SAVE_FILE_DEFAULT_LOCATION = "./data/settings.db";

	public int aiScore;
	public int playerScore;
	public int ties;

	public Boolean aiMoveFirst = true; 
	public int AI_DIFFICULTY = 6; 
	
	public Color BACKGROUND_COLOR = new Color(150, 150, 150); 
	public Color TILE_COLOR = new Color(200, 200, 200); 
	public Color HIGHLIGHT_COLOR = new Color(255, 150, 80); 
	public Color WIN_HIGHLIGHT = new Color(255, 120, 120); 
	public Color LOSE_HIGHLIGHT = new Color(120, 120, 255); 
	public Dimension WINDOW_SIZE = new Dimension(600, 600); 
	public Dimension TILE_SIZE = new Dimension(200, 200); 

	public String xImgPath = "./images/X.png";
	public String oImgPath = "./images/O.png";

	//Because this image data cannot be serialized easily I'll just create new BufferedImages from
	//the String path variables whenever I import a Settings object
	transient public BufferedImage xImg; 
	transient public BufferedImage oImg; 

	//Export Settings to File
	public void exportSettingsToFile(String fileName) //TODO implement this
	{
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
			os.writeObject(this);
			os.close();
		} catch(Exception e){
			//e.printStackTrace();
			System.out.println("Settings Export Failed");
		}
	}

	//Import Settings from File
	public static Settings importFromFile(String fileName)
	{
		Settings importedSettings = null;
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File(fileName)));
			importedSettings = (Settings)is.readObject();
			importedSettings.loadImages();
			is.close();
		}catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println("Settings Import Failed");
			importedSettings = null;
		}
		return importedSettings; 
	}

	public static Settings defaultFileImport()
	{
		return importFromFile(SAVE_FILE_DEFAULT_LOCATION);
	}

	public void defaultFileExport()
	{
		exportSettingsToFile(SAVE_FILE_DEFAULT_LOCATION);
	}
	public void loadImages()
	{
		try {
			xImg = ImageIO.read(new File(xImgPath));
		} catch (Exception e) {
			System.out.println("Settings was unable to Load X Texture: " + xImgPath + "  ---Falling back to Text Rendering");
		}
		try {
			oImg = ImageIO.read(new File(oImgPath));
		} catch (Exception e) {
			System.out.println("Settings was unable to Load O Texture: " + oImgPath + "  ---Falling back to Text Rendering");
		}
	}


	//Load Default Settings
	Settings()
	{
		loadImages();
	}
}
