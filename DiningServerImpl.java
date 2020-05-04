/**
 * DiningServer.java
 *
 * This class contains the methods called by the  philosophers.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.concurrent.locks.*;


public class DiningServerImpl  implements DiningServer
{  
	// different philosopher states
	enum State {THINKING, HUNGRY, EATING};
	private Lock lock;
	private Condition[] condVars;
	
	// number of philosophers
	public static final int NUMBER_OF_PHILOSOPHERS = 5;
	
	// array to record each philosopher's state
	private State[] state;
	
	public DiningServerImpl()
	{
		// array of philosopher's state
		state = new State[NUMBER_OF_PHILOSOPHERS];
		lock = new ReentrantLock();
		condVars = new Condition[NUMBER_OF_PHILOSOPHERS];
		
		for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
			condVars[i] = lock.newCondition();
			state[i] = State.THINKING;
		}
		
		// originally all philosopher's are thinking

	}
	
	// called by a philosopher when they wish to eat 
	public void takeForks(int pnum)  {
		lock.lock();
		state[pnum] = State.HUNGRY;
		this.test(pnum);
		state[pnum] = State.EATING;
		lock.unlock();
	}
	
	// called by a philosopher when they are finished eating 
	public void returnForks(int pnum) {
		lock.lock();
		state[pnum] = State.THINKING;
		this.test(leftNeighbor(pnum));
		this.test(rightNeighbor(pnum));
		lock.unlock();
	}
	
	private void test(int i)
	{
		// in other words ... if I'm hungry and my left and
		// right nieghbors aren't eating, then let me eat!
		if ((state[this.rightNeighbor(i)] != State.EATING) && (state[i] == State.HUNGRY) &&
				(state[this.leftNeighbor(i)] != State.EATING)) {
				condVars[i].signal();
		} else {
			try {
				condVars[i].await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	// return the index of the left neighbor of philosopher i
	private int leftNeighbor(int i)
	{
        return ((i + 1) % NUMBER_OF_PHILOSOPHERS);
    }
	
	// return the index of the right neighbor of philosopher i
	private int rightNeighbor(int i)
	{
        return ((i + 4) % NUMBER_OF_PHILOSOPHERS);
	}
}
