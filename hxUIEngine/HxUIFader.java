package hxUIEngine;

import java.awt.Color;
import java.awt.Component;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class HxUIFader {
	static CopyOnWriteArrayList<HxUIFader> hxUIFaders = new CopyOnWriteArrayList<HxUIFader>();
	
	private double duration;
	private double toAlpha;
	private double decimalAlphaValue;
	private Component component;
	
	/**
	 * Changes the given components alpha value to the given value over time.
	 * @param component - Component to be moved.
	 * @param a - Desired alpha value to change component to..
	 * @param t - Amount of time, in seconds, over which to move the component.
	 */
	public HxUIFader(Component component, double a, double t) {
		this.component = component;
		
		this.toAlpha = a;
		this.duration = t * 1000000000;
			
		if (component instanceof JPanel) {
			this.decimalAlphaValue = component.getBackground().getAlpha();
		} else {
			this.decimalAlphaValue = component.getForeground().getAlpha();
		}

		hxUIFaders.add(this);
	}

	protected void update(double dT){
		Color fg = component.getForeground();
		if (component instanceof JPanel) {
			fg = component.getBackground();
		}
		int currentAlpha = fg.getAlpha();
		
		double dA = dT * (toAlpha * 255 - currentAlpha) / duration;

		if ((currentAlpha == (int) toAlpha * 255) || (duration <= 0)) {
			if (component instanceof JPanel) {
				component.setBackground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) (toAlpha * 255)));
			} else {
				component.setForeground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) (toAlpha * 255)));
			}
			hxUIFaders.remove(this);
			return;
		}
		
		decimalAlphaValue += dA;
		
		if (decimalAlphaValue > 255) 
			decimalAlphaValue = 255;
		else if (decimalAlphaValue < 0)
			decimalAlphaValue = 0;
		
		if (component instanceof JPanel) {
			component.setBackground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) decimalAlphaValue));
		} else {
			component.setForeground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) decimalAlphaValue));
		}
		component.repaint();
		
		this.duration = duration - dT;
	}
}
