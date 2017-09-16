package hxUIEngine;

public class HxUIClock {
	
	private boolean uiLoopRunning = true;
	private boolean paused = false;
	
	public HxUIClock(){
		initialize();
	}
	
	private void initialize(){
		runUILoop();
	}
	
	private void runUILoop() {
		new Thread() {
			public void run() {
				uiLoop();
			}
		}.start();
	}

	private void uiLoop() {
		final double GAME_HERTZ = 60;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		double now;
		double lastUpdateTime;
		while (uiLoopRunning) {
			if (!hxUIModifiersEmpty()) {
				now = System.nanoTime();
				lastUpdateTime = System.nanoTime();

				while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					now = System.nanoTime();
				}
				update(now - lastUpdateTime);
//				System.out.println("    Update. dT: " + (now - lastUpdateTime));
			}
		}
	}
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	private static void update(double dT){
		for(HxUIMover mover : HxUIMover.hxUIMovers){
			mover.update(dT);
		}
		for(HxUIFader fader : HxUIFader.hxUIFaders){
			fader.update(dT);
		}
		for(HxUIClipper clipper : HxUIClipper.hxUIClippers){
			clipper.update(dT);
		}
		for(HxUIResizer resizerr : HxUIResizer.hxUIResizers){
			resizerr.update(dT);
		}
	}
	
	private static boolean hxUIModifiersEmpty() {
		if (
				HxUIFader.hxUIFaders.size() != 0 || 
				HxUIMover.hxUIMovers.size() != 0 || 
				HxUIClipper.hxUIClippers.size() != 0 || 
				HxUIResizer.hxUIResizers.size() != 0
			) 
		{
			return false;
		} else {
			return true;
		}
	}
}
