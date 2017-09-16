package gui.components;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.lang.reflect.Field;
import java.net.URL;

import javax.swing.ImageIcon;

public class RetinaIcon extends ImageIcon {
	
	public RetinaIcon(URL filepath) {
		super(filepath);
	}
	
	public static boolean isRetina() {
		boolean isRetina = false;
		GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		try {
			Field field = graphicsDevice.getClass().getDeclaredField("scale");
			if (field != null) {
				field.setAccessible(true);
				Object scale = field.get(graphicsDevice);
				if (scale instanceof Integer && ((Integer) scale).intValue() == 2) {
					isRetina = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRetina;
	}
	
	@Override
	public int getIconWidth() {
	    if(isRetina()) {
	        return super.getIconWidth()/2;
	    }
	    return super.getIconWidth();
	}

	@Override
	public int getIconHeight() {
	    if(isRetina()) {
	        return super.getIconHeight()/2;
	    }
	    return super.getIconHeight();
	}
	
	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) 
	{
	    ImageObserver observer = getImageObserver();

	    if (observer == null) 
	    {
	        observer = c;
	    }

	    Image image = getImage();
	    int width = image.getWidth(observer);
	    int height = image.getHeight(observer);
	    final Graphics2D g2d = (Graphics2D)g.create(x, y, width, height);

	    if(isRetina()) {
	        g2d.scale(0.5, 0.5);
	    }

	    g2d.drawImage(image, 0, 0, observer);
	    g2d.scale(1, 1);
	    g2d.dispose();
	}

}
