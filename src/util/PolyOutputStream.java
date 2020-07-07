package util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class PolyOutputStream extends OutputStream
{
	private OutputStream[] streams;
	
	public PolyOutputStream(OutputStream... outputStreams)
	{
		streams = outputStreams;
	}
	
	public PolyOutputStream(Collection<OutputStream> outputStreams)
	{
		this(outputStreams.toArray(new OutputStream[outputStreams.size()]));
	}
	
	@Override
	public void write(int b) throws IOException
	{
		for(OutputStream os : streams)
		{
			os.write(b);
		}
	}
}
