import java.util.Collection;
import java.util.HashSet;

public class ExtendedHashSet<T> extends HashSet<T>
{	
	private static final long serialVersionUID = 1L;
	private long productA = 1;
	private long productB = 1;
	private long productC = 1;
	
	public ExtendedHashSet()
	{
		super();
	}
	
	public ExtendedHashSet(Collection<? extends T> collection)
	{
		super(collection.size());
		this.addAll(collection);
	}
	
	public ExtendedHashSet(int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}
	
	public ExtendedHashSet(int initialCapacity)
	{
		super(initialCapacity);
	}
	
	public boolean add(T element)
	{
		if(super.add(element) == true)
		{			
			if(this.productA > 0)
			{
				int hashCode = element.hashCode();
				int a = (hashCode & 7) + 1;
				int b = ((hashCode >> 3) & 7) + 1;
				int c = ((hashCode >> 6) & 7) + 1;
					
				this.productA *= a;
				this.productB *= b;
				this.productC *= c;
				
				//check if overflow happened
				if(this.productA < 0 || this.productB < 0 || this.productC < 0)
				{
					this.productA = 0;
					this.productB = 0;
					this.productC = 0;
				}
			}
			return true;
		}
		return false;
	}
	
	public void clear()
	{
		this.productA = 1;
		this.productB = 1;
		super.clear();
	}
	
	public Object clone()
	{
		ExtendedHashSet<T> newSet = new ExtendedHashSet<T>();
		for(T item : this)
		{
			newSet.add(item);
		}
		return newSet;
	}
	
	public boolean remove(Object obj)
	{
		if(super.remove(obj) == true)
		{		
			if(this.productA > 0)
			{
				int hashCode = obj.hashCode();
				int a = (hashCode & 7) + 1;					
				int b = ((hashCode >> 3) & 7) + 1;
				int c = ((hashCode >> 6) & 7) + 1;
				
				this.productA /= a;
				this.productB /= b;
				this.productC /= c;
			}
			return true;
		}		
		return false;
	}
	
	public boolean removeAll(Collection<?> collection)
	{
		boolean removed = true;
		for(Object item : collection)
		{
			removed = removed && remove(item);
		}
		return removed;
	}
	
	public boolean equals(Object obj)
	{
		if(obj == this) 
		{
			return true;
		}		
		if(obj instanceof ExtendedHashSet<?> == true)
		{
			ExtendedHashSet<?> set = (ExtendedHashSet<?>)obj;
			if(this.productA == set.productA && this.productB == set.productB && this.productC == set.productC)
			{
				return super.equals(obj);
			}
		}		
		return false;
	}
	
	public boolean addAll(Collection<? extends T> collection)
	{
		boolean added = true;
		for(T item : collection)
		{
			added = added && add(item);
		}
		return added;
	}
	
	public boolean retainAll(Collection<?> collection)
	{
		return super.retainAll(collection);
	}
	
	//check if "this" is a subset of "set"
	public boolean isSubsetOf(ExtendedHashSet<T> set)
	{		
		//if "this" has a greater size than "set" or "this" overflows and "set" does not, can't be a subset
		if(this.size() > set.size() || (this.productA == 0 && set.productA != 0))
		{
			return false;
		}
		
		//if "set" overflows, have to do a full check
		//check if "this" products divide "set" products, if they do, we need to do a full check
		if(set.productA == 0 || (set.productA % this.productA == 0 && set.productB % this.productB == 0 && set.productC % this.productC == 0))
		{
			return set.containsAll(this);
		}
		
		return false;
	}
	
	public boolean isSupersetOf(ExtendedHashSet<T> set)
	{
		return set.isSubsetOf(this);
	}
	
	public boolean isProperSubsetOf(ExtendedHashSet<T> set)
	{
		if(this.size() == set.size())
		{
			return false;
		}
		return this.isSubsetOf(set);
	}
	
	public boolean isProperSupersetOf(ExtendedHashSet<T> set)
	{
		return set.isProperSubsetOf(this);
	}
}
