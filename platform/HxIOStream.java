package platform;

public class HxIOStream {
	String buffer;
	Object lock = new Object();

	public HxIOStream(){
		buffer = new String();
	}
	
	public void write(String s){
		buffer += s + "\n";
        synchronized (lock) {
		    lock.notifyAll();
		}
	}
	
	public void flush(){
		buffer = "";
	}
	
	public String read(){
		String temp = buffer;
		flush();
		return temp;
	}
	public String readLine() {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
			}
		}
		if (buffer.equals(""))
			return null;
		String temp = buffer.substring(0, buffer.indexOf('\n'));
		buffer = buffer.substring(buffer.indexOf('\n') + 1);
		return temp;
	}
	
	public String waitForLine(){
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return read();
	}
	
	public void close() {
		flush();
		synchronized (lock) {
		    lock.notifyAll();
		}
		lock = null;
	}
}