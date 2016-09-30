package kr.pe.swf.webframework.util;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;



public class StringUtils {

	public static String[] getToken(String src, String deli )
	{
		if (src==null) return null;
		
		StringTokenizer st =new StringTokenizer(src,deli);
		int nsize =st.countTokens();
		String[] tokens =new String[nsize];
		for (int i=0;i<nsize;i++){
			tokens[i] =st.nextToken();
		}
        
		return tokens ;
	}
	
	
	public static String getExceptionTrace (Throwable ex)
	{
		
		CharArrayWriter charOut = null ;
		PrintWriter printWriter = null ;
		String imsi = null ;
		try
		{
			charOut = new CharArrayWriter (200) ;
			printWriter = new PrintWriter (charOut) ;
			ex.printStackTrace (printWriter) ;
			imsi = charOut.toString() ;
			printWriter.close () ;
			charOut.close () ;
			printWriter = null ;
			charOut = null ;
		}
		catch (Exception e)
		{
			try { printWriter.close () ; } catch (Exception ignored) {}
			try { charOut.close () ; } catch (Exception ignored) {}
			//e.printStackTrace (System.out) ;
		}
		return imsi ;
	}
	public static String getDateFormat(String sformat) 
	{
		java.util.Date currentTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat (sformat);
		String sTime = formatter.format(currentTime) ;
		return sTime;
	}
	public static byte[] zipCompress (byte[] data) throws Exception
	{
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);
		compressor.setInput(data);
		compressor.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
		byte[] buf = new byte[1024];
		while (!compressor.finished()) 
		{
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		bos.close();
		
		return bos.toByteArray();
	}
	public static byte[] zipDeCompress (byte[] data) throws Exception
	{
		
		Inflater decompressor = new Inflater();
		decompressor.setInput(data);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
		
		byte[] buf = new byte[1024];
		int count = 0 ;
		while (!decompressor.finished()) 
		{
			count = decompressor.inflate(buf);
			bos.write(buf, 0, count);
		}
		bos.close();
		return bos.toByteArray();
	}
	public static String replace (String data, String before, String after) throws Exception
	{
		if(data.length()< before.length()) return data;
		int index = -1 ;
		int patternLength = before.length () ;
		StringBuffer buffer = new StringBuffer () ;

		index = data.indexOf (before) ;
		while (index > -1)
		{
			buffer.append (data.substring (0, index)) ;
			data = data.substring (index + patternLength) ;
			buffer.append (after) ;
			index = data.indexOf (before) ;
		}
		buffer.append(data);
		return buffer.toString();
	}
	public	static String	replaceAll	(String data, String before, String after)	throws Exception
	{
		
		if(data==null || data.length()<before.length()) return data;

		StringBuffer buf_data=new StringBuffer(data);
		StringBuffer buf_before=new StringBuffer(before);
		StringBuffer buffer=new StringBuffer();

		int buf_data_length=buf_data.length();
		for(int i=0;i<buf_data_length;i++){
			if(i>=buf_data_length-before.length()+1) ;
			else if(buf_data.charAt(i)==buf_before.charAt(0)){

				if(buf_data.substring(i,i+before.length()).equals(before))
				{
					buffer.append(after);
					i+=buf_before.length()-1;
					continue;
				}
			}
			buffer.append(buf_data.charAt(i));
		}

		return buffer.toString();
	}
	public static String changeApos(String str){
		if(str == null) return "";

		StringBuffer sb =new StringBuffer();
		int pos = 0;
		for(int i =0 ;i<str.length();i++){
			char c= str.charAt(i);
			if(c=='\'') {
				sb.append(str.substring(pos,i)+"'");
				pos=i;
			}
		}
		sb.append(str.substring(pos,str.length()));
		//System.out.println("###################->STRINGUTIL:changeAPos:"+sb.toString());
		return sb.toString();
	}
	
	public static String getDoubleQuoted(String str){
		String returnString = "";
		
		if(str != null) {
			returnString = "\"" + str + "\"";
		}
		
		return returnString;
	}
	
	public static String getQuoted(String str){
		String returnString = "";
		
		if(str != null) {
			returnString = "\'" + str + "\'";
		}
		
		return returnString;
	}


	public static void makeInsertQry(String sfield, String val,
			StringBuffer fsb, StringBuffer vsb) {
		// TODO Auto-generated method stub
		if (val !=null && val.length()>0) {
			fsb.append(sfield+",");
			if (val.equals("sysdate")) vsb.append(val+",");
			else vsb.append("'"+StringUtils.changeApos(val)+"',");
		}
	}
	public static String makeInsertQry(String tableName,Map info) {
		// TODO Auto-generated method stub
		Iterator ite =info.keySet().iterator();
		StringBuffer fsb=new StringBuffer();
		StringBuffer vsb =new StringBuffer();
		String sfield =null;
		String val =null;
		while(ite.hasNext()){
			sfield =(String)ite.next();
			val =(String)info.get(sfield);
			if (val !=null && val.length()>0) {
				fsb.append(sfield+",");
				if (val.equals("sysdate")) vsb.append(val+",");
				else vsb.append("'"+StringUtils.changeApos(val)+"',");
			}
		}
		String query ="INSERT INTO "+tableName+" ("+fsb.toString()+") VALUES ("+vsb.toString()+")";
		return query;
	}
	public static String makeUpdateQry(String tableName,Map info,String swhere) {
		// TODO Auto-generated method stub
		Iterator ite =info.keySet().iterator();
		StringBuffer fsb=new StringBuffer();
		String sfield =null;
		String val =null;
		fsb.append("UPDATE ").append(tableName).append(" SET ");
		boolean isStart =false;
		while(ite.hasNext()){
			sfield =(String)ite.next();
			val =(String)info.get(sfield);
			if (val !=null && val.length()>0) {
				if (isStart) fsb.append(",");
				fsb.append(sfield).append("=");
				fsb.append(sfield+",");
				if (val.equals("sysdate")) fsb.append(val);
				else fsb.append("'"+StringUtils.changeApos(val)+"'");
				isStart =true;
			}
		}
		if (!isStart) return null;
		fsb.append(" ").append(swhere);
		return fsb.toString();
	}


	public static String rtrim(String str) {
		if (str == null || str.length() == 0) 
			return str;
		if (str.charAt(0) != ' ') {
			return str.trim();
		}
		else {
			int c = str.length()-1;
			
			for(int i=c; i>=0; i--) {
				if (str.charAt(i) != ' ') {
					return str.substring(0,i+1);
				}
			}
			return "";
		}
	}

	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}

	public static boolean isNotEmpty(Object str) {
		return !(str == null || "".equals(str));
	}

	public static String trimStr(Object str){
		return (str == null ? "" : String.valueOf(str).trim());
	}

	public static String blankStr(Object str){
		return (str == null ? "" : String.valueOf(str));
	}

	public static boolean isNum(String val)
	{
		String nreg ="[.0-9]+";
		if (val==null) return false;
		if (val.matches(nreg)) return true;
		return false;
	}
	public static boolean isAlpha(String val)
	{
		String nreg ="[A-Za-z]+";
		if (val==null) return false;
		if (val.matches(nreg)) return true;
		return false;
	}


	public static boolean isNumeric(Object val) {
		// TODO Auto-generated method stub
		if (val ==null) return false;
		if (val instanceof String) {
			String tmp  =(String)val;
			for (int i=0;i<tmp.length();i++)
				if (!Character.isDigit(tmp.charAt(i))) return false;
			return true;
		}else if (val instanceof Integer) return true;
		else if (val instanceof BigDecimal) return true;
		
		return false;
	}

    public static String arrayToString(Object arr) {
    	StringBuffer buffer = new StringBuffer();
		buffer.append("[");

		if(arr instanceof List){
			for(Object o : (List)arr){
				buffer.append("\"").append((String)o).append("\",");
			}
		}else if(arr instanceof String[]){
			for(Object s : (String[])arr){
				buffer.append("\"").append(s).append("\",");
			}
		}

		if(buffer.length() > 1){
			buffer.deleteCharAt(buffer.length()-1);
		}

		buffer.append("]");

		return buffer.toString();
    }

    public static String listMapToString(List<Map<String, String>> list){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");

		for(Map<String, String> data : list){
			buffer.append("{");
			Iterator<String> iterator = data.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				buffer.append(key).append(": \"").append(data.get(key)).append("\",");
			}
			buffer.deleteCharAt(buffer.length()-1);
			buffer.append("},");
		}

		if(buffer.length() > 1){
			buffer.deleteCharAt(buffer.length()-1);
		}

		buffer.append("]");

		return buffer.toString();

	}
}
