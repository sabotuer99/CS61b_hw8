package hw8;
/* UserList.java */

import queue.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserList {

    protected CatenableQueue<User> userQueue;
    private int size;

    /**
    * Creates empty UserList containing no users
    **/
    public UserList(){
        userQueue = new CatenableQueue<User>();
        size = 0;
    }

    /**
    *  addUser() adds a defensive copy of the specified user.
    **/
    public void add(User u){
        if (u.getPagesPrinted() < 0) {
            System.out.println("A user cannot have a negative number of pages printed.");
            return;
        }
        User uCopy = new User(u.getId(), u.getPagesPrinted());
        userQueue.enqueue(uCopy);
        size++;
    }

    /**
    *  getSize() returns the number of users in the UserList
    **/
    public int getSize(){
        return size;
    }

    /**
    * getUsers() returns the CatenableQueue<User> of Users.
    **/
    public CatenableQueue<User> getUsers(){
        return userQueue;
    }

    /**
    *  toString() prints out the id and pages printed of all users in the UserList.
    **/
    public String toString(){
        return userQueue.toString();
    }

    /**
    *  partition() partitions qUnsorted using the pivot integer.  On completion of
    *  this method, qUnsorted is empty, and its items have been moved to qLess,
    *  qEqual, and qGreater, according to their relationship to the pivot.
    *
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    *   @param qUnsorted is a CatenableQueue<User> of User objects.
    *   @param pivot is an integer used for partitioning.
    *   @param qLess is a CatenableQueue<User>, in which all Users with sortFeature less than pivot
    *       will be enqueued.
    *   @param qEqual is a CatenableQueue<User>, in which all Users with sortFeature equal to the pivot
    *       will be enqueued.
    *   @param qGreater is a CatenableQueue<User>, in which all Users with sortFeature greater than pivot
    *       will be enqueued.  
    **/ 
    public static void partition(String sortFeature, CatenableQueue<User> qUnsorted, int pivot, 
        CatenableQueue<User> qLess, CatenableQueue<User> qEqual, CatenableQueue<User> qGreater){
 
    	//null check
    	if(qUnsorted == null)
    		return;
    	
    	// put user in right partition
    	while(qUnsorted.size() > 0){
        	// get value to pivot on
        	User user = qUnsorted.dequeue();
        	int comparitor = getPivot(sortFeature, user);
        	
    		if( comparitor < pivot){
    			//qLess = checkInit(qLess);
    			qLess.enqueue(user);
    		} else if( comparitor > pivot ){
    			//qGreater = checkInit(qGreater);
    			qGreater.enqueue(user);
    		} else {
    			//qEqual = checkInit(qEqual);
    			qEqual.enqueue(user);
    		}
    		
    	}

        	
    }

	private static int getPivot(String sortFeature, User user) {
		int comparitor = 0;
    	if(sortFeature.equals("id"))
    		comparitor = user.getId();
    	else 
    		comparitor = user.getPagesPrinted();
		return comparitor;
	}

/*    private static CatenableQueue<User> checkInit(CatenableQueue<User> queue){
    	if(queue == null)
    		return new CatenableQueue<User>();
    	else
    		return queue;
    }*/
    
    /**
    *   quickSort() sorts q from smallest to largest according to sortFeature using quicksort.
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    *   @param q is an unsorted CatenableQueue containing User items.
    **/

	public static void quickSort(String sortFeature, CatenableQueue<User> q){ 
    	CatenableQueue<User> left = new CatenableQueue<User>(), 
    			             stuck = new CatenableQueue<User>(), 
    			             right = new CatenableQueue<User>();
    	int pivotIndex = (int)(Math.random() * (double)q.size());
    	User user = q.nth(pivotIndex);
    	int pivot = getPivot(sortFeature, user);
    	
    	partition(sortFeature, q, pivot, left, stuck, right);
    	
    	if(left.size() > 0){
    		quickSort(sortFeature, left);
    	}
    	
    	if(right.size() > 0){
    		quickSort(sortFeature, right);   		
    	}
    	
    	q.append(left);
    	q.append(stuck);
    	q.append(right);
    	
    }

    /**
    *  quickSort() sorts userQueue from smallest to largest according to sortFeature, using quicksort.
    *  @param sortFeature is a string that equals "id" if we are sorting users by their IDs, or equals
    *  "pages" if we are sorting users by the number of pages they have printed.
    **/
    public void quickSort(String sortFeature){
        quickSort(sortFeature, userQueue);
    }


    /**
    *  makeQueueOfQueues() makes a queue of queues, each containing one User
    *  of userQueue.  Upon completion of this method, userQueue is empty.
    *  @return a CatenableQueue<CatenableQueue<User>>, where each CatenableQueue
    *    contains one User from userQueue.
    **/
    public CatenableQueue<CatenableQueue<User>> makeQueueOfQueues(){
        //Replace with solution.
    	CatenableQueue<CatenableQueue<User>> qq = new CatenableQueue<CatenableQueue<User>>();
    	while(userQueue.size() > 0){
    		User user = userQueue.dequeue();
    		CatenableQueue<User> userq = new CatenableQueue<User>();
    		userq.enqueue(user);
    		qq.enqueue(userq);
    	}
        return qq;
    }

    /**
    *  mergeTwoQueues() merges two sorted queues into one sorted queue.  On completion
    *  of this method, q1 and q2 are empty, and their Users have been merged
    *  into the returned queue. Assume q1 and q2 contain only User objects.
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    *  @param q1 is CatenableQueue<User> of User objects, sorted from smallest to largest by their sortFeature.
    *  @param q2 is CatenableQueue<User> of User objects, sorted from smallest to largest by their sortFeature.
    *  @return a CatenableQueue<User> containing all the Users from q1 and q2 (and nothing else),
    *       sorted from smallest to largest by their sortFeature.
    **/
    public static CatenableQueue<User> mergeTwoQueues(String sortFeature, CatenableQueue<User> q1, CatenableQueue<User> q2){
        // if one queue is null, return the other
    	// if both are null, will return null
    	if(q1 == null || q1.size() == 0)
    		return q2;
    	if(q2 == null || q2.size() == 0)
    		return q1;
    	
    	CatenableQueue<User> sortedq = new CatenableQueue<User>();
    	
    	while(q1.size() != 0 && q2.size() != 0){
    		
    		int q1val = getPivot(sortFeature, q1.nth(0));
    		int q2val = getPivot(sortFeature, q2.nth(0));
    		
    		if(q1val <= q2val){
    			sortedq.enqueue(q1.dequeue());
    		} else {
    			sortedq.enqueue(q2.dequeue());
    		}
    			  		
    		// if one queue is empty and the other not, 
    		// append the non empty queue
    		if(q1.size() == 0 && q2.size() != 0){
    			sortedq.append(q2);
    		}
    		
    		if(q1.size() != 0 && q2.size() == 0){
    			sortedq.append(q1);
    		}
    	}
    	
    	
        return sortedq;
    }

    /**
    *   mergeSort() sorts this UserList from smallest to largest according to sortFeature using mergesort.
    *   You should complete this method without writing any helper methods.
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    **/
    public void mergeSort(String sortFeature){
        if(size < 2)
        	return;
        
        CatenableQueue<CatenableQueue<User>> qq = makeQueueOfQueues();
        
        while(qq.size() > 1){
        	CatenableQueue<User> q1 = qq.dequeue();
        	CatenableQueue<User> q2 = qq.dequeue();
        	qq.enqueue(mergeTwoQueues(sortFeature, q1, q2)); 	
        }
        
        userQueue = qq.dequeue();
        
        //recursive version
        //divide into two queues
/*        CatenableQueue<User> q1 = new CatenableQueue<User>();
        CatenableQueue<User> q2 = new CatenableQueue<User>();
        for(int i = 0; userQueue.size() > 0; i += 1){
        	if(i%2 == 0){
        		q1.enqueue(userQueue.dequeue());
        	} else {
        		q2.enqueue(userQueue.dequeue());
        	}
        }
        	
        q1.mergeSort(sortFeature);
        q2.mergeSort(sortFeature);              
        userQueue = mergeTwoQueues(sortFeature, q1, q2);*/  
    }

    /**
    *   sortByBothFeatures() sorts this UserList's userQueue from smallest to largest pages printed.
    *   If two Users have printed the same number of pages, the User with the smaller user ID is first.
    **/
    public void sortByBothFeatures(){
        //Replace with solution. Don't overthink this one!
    	mergeSort("id");
    	mergeSort("print");
    }


    

    public static void main(String [] args) {
        // Naive right-idea tests. Just because these tests pass does NOT mean
        // your code is bug-free!

        // Uncomment the following line when ready
        // jh61b.junit.textui.runClasses(UserList.class);
    }

}