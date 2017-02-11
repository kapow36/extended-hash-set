import java.util.Collection;
import java.util.HashSet;

public class ExtendedHashSet<T> extends HashSet<T>
{
	private static final long serialVersionUID = 1L;
	public static int modulus = 30;
	private long product = 1;
	
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
		int x = (element.hashCode() % modulus + modulus) % modulus + 1;		
		if(product > 0)
		{
			if(this.product <= Long.MAX_VALUE / x)
			{
				this.product *= x;
			}
			else
			{
				this.product = 0;
			}
		}				
		return super.add(element);
	}
	
	public void clear()
	{
		this.product = 1;
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
		boolean removed = super.remove(obj);
		if(removed == true)
		{
			int x = (obj.hashCode() % modulus + modulus) % modulus + 1;
			if(this.product > 0)
			{
				this.product /= x;
			}
		}		
		return removed;
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
			if(this.product == set.product)
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
	
	public boolean isSubsetOf(ExtendedHashSet<T> set)
	{
		if(this.size() > set.size())
		{
			return false;
		}
		if(this.product <= 0 || set.product <= 0 || set.product % this.product == 0)
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
