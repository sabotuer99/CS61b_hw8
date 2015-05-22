package hw8;

import static org.junit.Assert.*;
import hw8.Stopwatch;

import org.junit.Test;

import queue.CatenableQueue;

public class UserListTests {

	@Test
    public void naivePartitionTest() {
        UserList list = new UserList();

        list.add(new User(0, 20));
        list.add(new User(1, 0));
        list.add(new User(2, 10));

        CatenableQueue<User> less = new CatenableQueue<User>();
        CatenableQueue<User> equal = new CatenableQueue<User>();
        CatenableQueue<User> greater = new CatenableQueue<User>();

        /* pivot on user 1 by id */
        UserList.partition("id", list.userQueue, 1, less, equal, greater);
        assertEquals(1, less.size());
        assertEquals(1, equal.size());
        assertEquals(1, greater.size());
        assertEquals(new User(0, 20), less.front());
        assertEquals(new User(1, 0), equal.front());
        assertEquals(new User(2, 10), greater.front());
    }

    @Test
    public void naiveQuickSortTest() {
        UserList list = new UserList();
        list.add(new User(2, 12));
        list.add(new User(0, 10));
        list.add(new User(1, 11));

        list.quickSort("id");

        String sorted =
         "[ User ID: 0, Pages Printed: 10,\n  User ID: 1, Pages Printed: 11,\n  User ID: 2, Pages Printed: 12 ]";

        assertEquals(sorted, list.toString());

        list.quickSort("pages");
        assertEquals(sorted, list.toString()); 
    }

    @Test
    public void naiveMakeQueuesTest(){
        UserList list = new UserList();

        list.add(new User(0, 20));
        list.add(new User(1, 0));
        list.add(new User(2, 10));

        CatenableQueue<CatenableQueue<User>> queues = list.makeQueueOfQueues();
        String queueOfQueues = 
        "[ [ User ID: 0, Pages Printed: 20 ],\n  [ User ID: 1, Pages Printed: 0 ],\n  [ User ID: 2, Pages Printed: 10 ] ]";

        assertEquals(queueOfQueues, queues.toString());        
    }

    @Test
    public void naiveMergeQueuesTest(){
        CatenableQueue<User> q1 = new CatenableQueue<User>();
        CatenableQueue<User> q2 = new CatenableQueue<User>();
        q1.enqueue(new User(0, 20));
        q2.enqueue(new User(1, 10));

        CatenableQueue<User> merged = UserList.mergeTwoQueues("pages", q1, q2);
        String mergeByPages = 
        "[ User ID: 1, Pages Printed: 10,\n  User ID: 0, Pages Printed: 20 ]";
        assertEquals(mergeByPages, merged.toString());        

        q1 = new CatenableQueue<User>();
        q2 = new CatenableQueue<User>();
        q1.enqueue(new User(0, 20));
        q2.enqueue(new User(1, 10));

        merged = UserList.mergeTwoQueues("id", q1, q2);
        String mergeById = 
        "[ User ID: 0, Pages Printed: 20,\n  User ID: 1, Pages Printed: 10 ]";
        assertEquals(mergeById, merged.toString());        
    }

    @Test
    public void naiveMergeSortTest() {
        UserList list = new UserList();
        list.add(new User(2, 12));
        list.add(new User(0, 10));
        list.add(new User(1, 11));

        list.mergeSort("id");

        String sorted =
         "[ User ID: 0, Pages Printed: 10,\n  User ID: 1, Pages Printed: 11,\n  User ID: 2, Pages Printed: 12 ]";

        assertEquals(sorted, list.toString());

        list.mergeSort("pages");
        assertEquals(sorted, list.toString()); 
    }

    @Test
    public void naiveSortByBothTest() {
        UserList list = new UserList();
        list.add(new User(2, 12));
        list.add(new User(1, 10));
        list.add(new User(0, 10));

        list.sortByBothFeatures();

        String sorted =
         "[ User ID: 0, Pages Printed: 10,\n  User ID: 1, Pages Printed: 10,\n  User ID: 2, Pages Printed: 12 ]";

        assertEquals(sorted, list.toString());
    }
    
	public static Integer[] initArray(Integer length){
		Integer[] arr = new Integer[length];
		for(int i = 0; i < length; i += 1){
			//Fisher-Yates "inside-out"
			int target = (int)(Math.random() * (i + 1));
			arr[i] = arr[target];
			arr[target] = i;
		}
		//System.out.println("Arrays Initiallized!");
		return arr;
	}
	
    @Test
    public void timeTrial() {
    	
    	System.out.println("Warmup, this doesn't count...");
    	timetrial(1);
    	System.out.println("Ready, set.... GO!");
    	
    	for (int j = 1; j <= 5; j++) {		
	        timetrial(j);
	        timetrial(j);
    	}
    }

	private void timetrial(int j) {
		UserList list1 = new UserList();
		UserList list2 = new UserList();
		int uidCount = 1000000 * j;
		
		Integer[] uids = initArray(uidCount);
		
		for (int i = 0; i < uidCount; i++) {   	
			list1.add(new User(uids[i], (int)(Math.random() * uidCount) ));
			list2.add(new User(uids[i], (int)(Math.random() * uidCount) ));
		}
		

		Stopwatch qs = new Stopwatch();
			list1.quickSort("id");
		long qstime = (long)(qs.elapsedTime() * 1000);
		
		System.out.println("Quicksort " + uidCount + ": " + qstime);
		
		Stopwatch ms = new Stopwatch();
			list2.mergeSort("id");
		long mstime = (long)(ms.elapsedTime() * 1000);  	
		
		System.out.println("Mergesort " + uidCount + ": " + mstime + "\n");
	}

}
