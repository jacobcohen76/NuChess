package nuchess.scripting;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment
{
	public HashMap<String, Integer> variableMap;
	public ArrayList<String> memory;
		
	public void declare(String name)
	{
		if(variableMap.containsKey(name))
		{
			throw new Error(name + " is a duplicate variable");
		}
		else
		{
			variableMap.put(name, memory.size());
			memory.add(null);
		}
	}
	
	public void assign(String name, String value)
	{
		if(variableMap.containsKey(name))
		{
			memory.set(variableMap.get(name), value);
		}
		else
		{
			throw new Error(name + " cannot be resolved to a variable");
		}
	}
	
	
}
