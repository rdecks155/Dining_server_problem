import java.util.concurrent.locks.*;

/**
 * Implementation of the Manager interface that
 * permits a number of licenses.
 */

public class LicenseManager implements Manager
{
    // the number of available permits
    private int permits;
    private Lock lock;
  
    public LicenseManager(int permits) {
        if (permits < 0)
            throw new IllegalArgumentException();
        this.lock = new ReentrantLock();
        this.permits = permits;
    }
  
    public boolean acquirePermit() {
        boolean rv = false;
		lock.lock();

        if (permits > 0) {
            permits--;
      
            rv = true;
        }
        lock.unlock();
		
    
    return rv;
    }
  
    public void releasePermit() {
    	lock.lock();
        permits++;

        lock.unlock();
    }
}
  
