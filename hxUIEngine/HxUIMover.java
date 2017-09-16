package hxUIEngine;

import java.awt.Component;
import java.awt.Point;
import java.util.concurrent.CopyOnWriteArrayList;

public class HxUIMover {
	static CopyOnWriteArrayList<HxUIMover> hxUIMovers = new CopyOnWriteArrayList<HxUIMover>();
	
	private double dX;
	private double dY;
	private double duration;
	private double decimalPosX;
	private double decimalPosY;
	private Point toPoint;
	private Component component;
	/**
	 * Slides a JComponent by a change in X and Y values.
	 * @param component - Component to be moved.
	 * @param dX - Pixel amount to move component in x direction.
	 * @param dY - Pixel amount to move component in y direction.
	 * @param t - Amount of time, in seconds, over which to move the component.
	 */
	public HxUIMover(Component component, int dX, int dY, double t) {
		this.component = component;
	
		this.decimalPosX = component.getLocation().x;
		this.decimalPosY = component.getLocation().y;
		this.dX = dX;
		this.dY = dY;
		this.duration = t * 1000000000;
		this.toPoint = new Point((int) (decimalPosX + dX), (int) (decimalPosY + dY));
	
		hxUIMovers.add(this);
	}
	
	/**
	 * Slides a JComponent to a point.
	 * @param component - Component to be moved.
	 * @param p - Exact position to move top left corner of component to.
	 * @param t - Amount of time, in seconds, over which to move the component.
	 */
	public HxUIMover(Component c, Point p, double t) {
		this(c, p.x - c.getLocation().x, p.y - c.getLocation().y, t);
	}

	protected void update(double dT){
		double moveX = dT * dX / duration;
		double moveY = dT * dY / duration;
		
		this.decimalPosX += moveX;
		this.decimalPosY += moveY;
		
		component.setLocation((int) decimalPosX, (int) decimalPosY);
		
		if ((component.getLocation().x == toPoint.x && component.getLocation().y == toPoint.y) || (duration <= 0)) {
			component.setLocation(toPoint.x, toPoint.y);
			hxUIMovers.remove(this);
			return;
		}
		
		this.dX = toPoint.x - component.getLocation().x;
		this.dY = toPoint.y - component.getLocation().y;
		this.duration = duration - dT;
	}
}
