import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Main 
{
	public static void main(String[] args) 
	{
		final int listSize = 30000;
		final int maxSetSize = 20;
		final int maxSetValue = 100;
		
		//Generate list of HashSets
		LinkedList<ExtendedHashSet<Integer>> list = new LinkedList<ExtendedHashSet<Integer>>();
		Random rand = new Random(System.currentTimeMillis());		
		for(int i = 0; i < listSize; i++)
		{
			ExtendedHashSet<Integer> set = new ExtendedHashSet<Integer>();				
			int maxIndex = rand.nextInt(maxSetSize - 3) + 3;
			for(int j = 0; j < maxIndex; j++)
			{
				set.add(rand.nextInt(maxSetValue));
			}			
			list.add(set);
		}		
		
		//run sample for ExtendedHashSet
		long start = System.currentTimeMillis();
		long subsetCount = 0;		
		for(ExtendedHashSet<Integer> setA : list)
		{
			for(ExtendedHashSet<Integer> setB : list)
			{
				if(setB.isSubsetOf(setA))
				{
					subsetCount++;
				}
			}
		}		
		System.out.println("Subset search completed in " + (System.currentTimeMillis() - start) + " milliseconds with " + subsetCount + " Subsets Found\n");
		
		//run sample for Normal HashSet
		start = System.currentTimeMillis();
		subsetCount = 0;		
		for(HashSet<Integer> setA : list)
		{
			for(HashSet<Integer> setB : list)
			{
				if(setB.size() <= setA.size())
				{
					if(setA.containsAll(setB))
					{
						subsetCount++;
					}
				}
			}
		}		
		System.out.println("Subset search completed in " + (System.currentTimeMillis() - start) + " milliseconds with " + subsetCount + " Subsets Found\n");
	} 
	
	public static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}
