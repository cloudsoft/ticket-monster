package org.jboss.seam.example.ticketmonster.dto;

import java.util.List;

import org.jboss.seam.example.ticketmonster.action.BookingManager;
import org.jboss.seam.example.ticketmonster.model.Allocation;

/**
 * Represents the ticket allocation state for a single row at a show
 * 
 * @author Shane Bryzak
 *
 */
public class RowAllocation
{      
   /**
    * Tracks the allocated seats
    */
   private boolean[] allocatedSeats;      
   
   public RowAllocation(int capacity, List<Allocation> allocations)
   {
      this.allocatedSeats = new boolean[capacity];
      
      // Populate the seat allocations
      for (Allocation allocation : allocations)
      {
         int startSeat = Integer.valueOf(allocation.getStartSeat());
         int endSeat = Integer.valueOf(allocation.getEndSeat());         
         
         for (int i = startSeat; i <= endSeat; i++)
         {
            allocatedSeats[i - 1] = true;
         }
      }
   }

   public int getMaxAvailable()
   {
      int max = BookingManager.MAX_AVAILABLE_SEATS_LIMIT;
      
      int count = 0;
      for (int i = 0; i < allocatedSeats.length; i++)
      {
         if (!allocatedSeats[i]) 
         {
            count++;
         }
         else
         {
            if (count < max) max = count;
            count = 0;
         }
      }
      
      return max;
   }
   
   public boolean allocateSeats(int quantity)
   {
      return false;
   }
}
