package offline_3_1;

import static java.lang.Math.random;
import java.util.Scanner;

class Node
{
    int value; 
    int Height;
    Node next[];
    int distance[];
    
    Node(int value,int Height)
    {
        this.value=value;
        this.Height=Height;
        next=new Node[Height+1];
        distance=new int[Height+1];
    }
}

class skiplist
{
    int Height;
    int size;
    Node head;
    skiplist()
    {
        head = new Node(-9999, 0);
        Height = 0;
        size = 0;
    }
    
    int Random()
    {
        int level=0;
        double rand=random();
        while(rand < .5 )
        {
            level++;
            rand=random();
        }
        return level;
    }
    
    void Insert(int value) 
    {
        int newLevel = Random(); 
        Node position[];
        int rank[];
        if(newLevel > Height)
        {
            position = new Node[newLevel+1];
            rank = new int[newLevel+1];
        }
        else 
        {
            position = new Node[Height+1];
            rank = new int[Height+1];
        }
        if (newLevel > Height)         
        {
            Node temp = head;
            head = new Node(-9999, newLevel);
            for (int i=0; i<=Height; i++)
            {
                head.distance[i]=temp.distance[i];
                head.next[i] = temp.next[i];
            }
            for (int i=Height+1; i<=newLevel; i++)
            {
                position[i] = head;
                rank[i] = 0;
                position[i].distance[i] = size;
            }
            Height = newLevel;
        }
       
        Node x = head;       
        for (int i=Height; i>=0; i--) 
        { 
            if (i == Height) rank[i] = 0;
            else rank[i] = rank[i+1];
            while( x.next[i] != null && value > x.next[i].value)
            {
                rank[i] =rank[i] + x.distance[i];
                x = x.next[i];
            }
            position[i] = x; 
        }
        x = new Node(value, newLevel);
        for (int i=0; i<=newLevel; i++) 
        {      
            x.next[i] = position[i].next[i]; 
            position[i].next[i] = x;    
            x.distance[i] = position[i].distance[i] - (rank[0] - rank[i]);
            position[i].distance[i] = rank[0] - rank[i] + 1;
        }
        size++;   
        for (int i = newLevel+1; i <=Height; i++)
        {
            position[i].distance[i]++;
        }
    }
    
    int IndexOf(int Value)
    {
        int index=0;
	Node temp = head;
        for(int i = Height;i >= 0;i--)
        {
            while (temp.next[i] != null && Value>temp.next[i].value)
            {
                index= index + temp.distance[i];
                temp = temp.next[i];
            }
        }
        temp = temp.next[0];
        if(temp==null || temp.value != Value) return -1;
        return index;
    }
    
    void Print() 
    {
        for (int i=Height; i>=0; i--) 
        {
            Node x = head;
            while (x != null)
            {
                System.out.print(x.value);
                System.out.print(" --- ");
                x = x.next[i];
            }
            System.out.println("9999");
        }
        System.out.println("---------------------------");
    }
}

public class Offline_3_1 
{
    public static void main(String[] args)
    {
       Scanner in = new Scanner(System.in);  
       skiplist Sl=new skiplist();
       int n=0;
       while(n!=4)
       {
            System.out.println("Enter 1 to Insert");
            System.out.println("Enter 2 to Find Index");
            System.out.println("Enter 3 to Print");
            System.out.println("Enter 4 to Stop");
            n = in.nextInt();
       
            if(n==1)
            {
                System.out.println("Enter value to Insert");
                n = in.nextInt();
                Sl.Insert(n);
            }
            else if(n==2)
            {
                System.out.println("Enter value to Find Index");
                n = in.nextInt();
                int index=Sl.IndexOf(n);
                if(index==-1)System.out.println(n+" is not in the list");
                else System.out.println("Index of "+n+" is "+index);
            }
            else if(n==3)
            {
                System.out.println("Skip List");
                Sl.Print();
            }
       }
    }
    
}
