package hxUIEngine;

import java.awt.Component;
import java.awt.Point;
import java.util.concurrent.CopyOnWriteArrayList;

public class HxUIClipper {
	static CopyOnWriteArrayList<HxUIClipper> hxUIClippers = new CopyOnWriteArrayList<HxUIClipper>();
	
	private double dX;
	private double dY;
	private double duration;
	private double decimalPosX;
	private double decimalPosY;
	private Point toPoint;
	private Component component;
	/**
	 * Clipes a side of a JComponent by the given pixels.
	 * @param component - Component to be moved.
	 * @param dTop - Pixel amount to shrink from the top of component frame.
	 * @param dRight - Pixel amount to shrink from the right of component frame.
	 * @param dBot - Pixel amount to shrink from the bottom of component frame.
	 * @param dLeft - Pixel amount to shrink from the left of component frame.
	 * @param t - Amount of time, in seconds, over which to clip the component.
	 */
	public HxUIClipper(Component component, int dTop, int dRight, int dBot, int dLeft, double t) {
		this.component = component;
	
		this.decimalPosX = component.getLocation().x;
		this.decimalPosY = component.getLocation().y;
		
		this.duration = t * 1000000000;
	
		hxUIClippers.add(this);
	}

	protected void update(double dT){
		double moveX = dT * dX / duration;
		double moveY = dT * dY / duration;
		
		this.decimalPosX += moveX;
		this.decimalPosY += moveY;
		
		component.setLocation((int) decimalPosX, (int) decimalPosY);
		
		if ((component.getLocation().x == toPoint.x && component.getLocation().y == toPoint.y) || (duration <= 0)) {
			component.setLocation(toPoint.x, toPoint.y);
			hxUIClippers.remove(this);
			return;
		}
		
		this.dX = toPoint.x - component.getLocation().x;
		this.dY = toPoint.y - component.getLocation().y;
		this.duration = duration - dT;
	}
}
