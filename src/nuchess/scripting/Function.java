package nuchess.scripting;

public interface Function
{
	public String getName();
	public Type[] getArgs();
	public Type getReturnType();
	public Type getType();
	public void callVoid(int... registers);
	public int callNonVoid(int... registers);
}
