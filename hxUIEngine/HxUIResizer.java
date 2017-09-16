package hxUIEngine;

import java.awt.Component;
import java.awt.Dimension;
import java.util.concurrent.CopyOnWriteArrayList;

public class HxUIResizer {
	static CopyOnWriteArrayList<HxUIResizer> hxUIResizers = new CopyOnWriteArrayList<HxUIResizer>();
	
	private double duration;
	private Component component;
	private double widthPrecise;
	private double heightPrecise;
	private double dWidth;
	private double dHeight;
	private Dimension toSize;
	
	/**
	 * Changes the given components width and/or height by the given values over time.
	 * @param component - Component to be moved.
	 * @param w - Desired width to change component's size by.
	 * @param h - Desired height to change component's size by.
	 * @param t - Amount of time, in seconds, over which to move the component.
	 */
	public HxUIResizer(Component component, int dWidth, int dHeight, double t) {
		this.component = component;
		
		this.dWidth = dWidth;
		this.dHeight = dHeight;
		this.duration = t * 1000000000;
		this.widthPrecise = component.getSize().getWidth();
		this.heightPrecise = component.getSize().getHeight();
		this.toSize = new Dimension(component.getSize().width + dWidth, component.getSize().height + dHeight);

		hxUIResizers.add(this);
	}
	
	/**
	 * Changes the given components width and/or height by the given values over time.
	 * @param component - Component to be moved.
	 * @param size - The new size of the component.
	 * @param t - Amount of time, in seconds, over which to move the component.
	 */
	public HxUIResizer(Component component, Dimension size, double t) {
		this(component, size.width - component.getSize().width, size.height - component.getSize().height, t);
	}

	protected void update(double dT){
		double interpWidth = dT * dWidth / duration;
		double interpHeight = dT * dHeight / duration;
		
		this.widthPrecise += interpWidth;
		this.heightPrecise += interpHeight;

		component.setSize((int) widthPrecise, (int) heightPrecise);

		if (duration <= 0) {
			component.setSize(toSize);
			hxUIResizers.remove(this);
			return;
		}

		this.dWidth = toSize.width - component.getSize().width;
		this.dHeight = toSize.height - component.getSize().height;
		this.duration = duration - dT;
	}
}
