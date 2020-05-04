/**
 * DiningPhilosophers.java
 *
 * This program starts the dining philosophers problem.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */


public class DiningPhilosophers
{  
   public static void main(String args[])
   {
      DiningServer server = new DiningServerImpl();

      Philosopher[] philosopherArray = new Philosopher[DiningServerImpl.NUMBER_OF_PHILOSOPHERS];
     
     // create the philosopher threads
     for (int i = 0; i < DiningServerImpl.NUMBER_OF_PHILOSOPHERS; i++)
         philosopherArray[i] = new Philosopher(server,i);
     
     for (int i = 0; i < DiningServerImpl.NUMBER_OF_PHILOSOPHERS; i++)
         new Thread(philosopherArray[i]).start();
   }
}
