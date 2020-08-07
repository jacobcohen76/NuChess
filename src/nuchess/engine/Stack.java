package nuchess.engine;

public class Stack<T>
{
	private class Node
	{
		public T data;
		public Node next;
		
		public Node(T data, Node next)
		{
			this.data = data;
			this.next = next;
		}
	}
	
	private Node head;
	
	private Stack(Node head)
	{
		this.head = head;
	}
	
	public Stack()
	{
		this(null);
	}
	
	public boolean isEmpty()
	{
		return head == null;
	}
	
	public void push(T data)
	{
		head = new Node(data, head);
	}
	
	public T pop()
	{
		Node removed = head;
		head = head.next;
		return removed.data;
	}
}
