package kr.pe.sdh.common.util;

public class Namespace {

	private String prefix;
    private String uri;
    
	public Namespace(String prefix, String uri)
    {
        this.prefix = prefix == null ? "" : prefix;
        this.uri = uri == null ? "" : uri;
    }
	
	public String getPrefix()
    {
        return prefix;
    }

    public String getURI()
    {
        return uri;
    }
	
}
