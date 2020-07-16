package nuchess.scripting;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment
{
	public HashMap<String, HashMap<Type[], Function>> functionMap;
	public HashMap<String, Integer> variableMap;
	public ArrayList<String> memory;
	public ArrayList<Type> typeList;
	
	public void declare(String name, Type[] args, Function function)
	{
		HashMap<Type[], Function> overloadMap = functionMap.get(name);
		if(overloadMap == null)
		{
			overloadMap = new HashMap<Type[], Function>();
			functionMap.put(name, overloadMap);
		}
		
		if(!overloadMap.containsKey(args))
		{
			overloadMap.put(args, function);
		}
		else
		{
			throw new Error("Function \'" + name + "\' already declared with those parameters");
		}
	}
	
	public void declare(String name, Type type)
	{
		variableMap.put(name, typeList.size());
		typeList.add(type);
		memory.add(null);
	}
	
	public Type type(String name)
	{
		return variableMap.containsKey(name) ? typeList.get(variableMap.get(name)) : null;
	}
	
	
}
