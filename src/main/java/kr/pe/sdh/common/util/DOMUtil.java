/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.pe.sdh.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * A collection of W3C DOM helper methods
 *
 * @version $Revision: 564607 $
 */
public final class DOMUtil {
    
    //private static final Log LOG = LogFactory.getLog(DOMUtil.class);
    private static DocumentBuilderFactory dbf;
    private static Queue builders = new ConcurrentLinkedQueue();

    
    private DOMUtil() {
    }

    /**
     * Returns the text of the element
     */
    public static String getElementText(Element element) {
        
        if (element ==null) return null;
        StringBuffer buffer = new StringBuffer();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.CDATA_SECTION_NODE) {
                buffer.append(node.getNodeValue());
            }
        }
        return buffer.toString();
    }
    public static String getElementText(Element element,String childNod) {
        return getElementText(getElement(element,childNod));
    }
    public static String getElementTextByPath(Element element,String childNod) {
		if(childNod.indexOf("/@") != -1) {
			String[] temp = childNod.split("/@");
			Element currEle = getElementByPath(element, temp[0]);
			if(currEle != null)
				return currEle.getAttribute(temp[1]);
			else return "";
		}  else 
			return getElementText(getElementByPath(element, childNod));    	
//        return getElementText(getElementByPath(element,childNod));
    }
    
    public static String getElementText(Element element, Namespace ns,
			String childNod) {
		// TODO Auto-generated method stub
		return getElementText(getElement(element,ns,childNod));
	}
    
    public static Element getElement(Element element,String childNod) {
    	if (element ==null) return null;
    	NodeList nodeList = element.getChildNodes();
    	String nodName =childNod;
    	if (childNod.startsWith("//")){
    		return getDescendentElement(element,childNod.substring(2));
    	}
    	int ipos =childNod.indexOf("[");
    	String condiStr =null;
    	if(ipos>0){
    		nodName=childNod.substring(0, ipos);
    		condiStr=childNod.substring(ipos+1,childNod.lastIndexOf("]"));
    	}
    	for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() ==Node.ELEMENT_NODE) {
            	if(node.getNodeName().equals(nodName) && checkCondition((Element)node,condiStr,i+1)){
                	return (Element)node;
                	
                }
            }
        }
        return null;
    }
    private static Element getDescendentElement(Element element,
			String childName) {
		// TODO Auto-generated method stub
    	List eal =DOMUtil.getChildren(element);
    	int nsize =eal.size();
    	Element el=null;
    	for (int i=0;i<nsize;i++){
    		el =getElement((Element)eal.get(i),childName);
    		if (el !=null) return el;
    	}
    	for (int i=0;i<nsize;i++){
    		el =getDescendentElement((Element)eal.get(i),childName);
    		if (el !=null) return el;
    	}
    	
		return null;
	}

	protected static boolean checkCondition(Element em,String condiStr,int posIdx)
    {
    	if (condiStr ==null) return true;
    	int ipos =condiStr.indexOf("=");
    	if (ipos <0){
    		try{
    			ipos =Integer.parseInt(condiStr);
    			if (ipos==posIdx) return true;
    			return false;
    		}catch(Exception ex){
    			return true;
    		}
    	}
    	String nod =condiStr.substring(0,ipos).trim();
    	String val =condiStr.substring(ipos+1).trim();
    	String tmVal =null;
    	if (nod.startsWith("@")){
    		tmVal =em.getAttribute(nod.substring(1));
    		
    	}else{
    		tmVal=getElementTextByPath(em,nod);
    		
    	}
    	if (val.equals(tmVal)){
			return true;
		}
    	return false;
    }
    public static Element getElementByPath(Element element,String childNod) {
    	if (element ==null) return null;
    	NodeList nodeList = element.getChildNodes();
    	String[] tokens=getElementToken(childNod);//StringUtils.getToken(childNod, "/");
    	Element etmp =element;
    	for(int i=0;i<tokens.length;i++){
    		if (tokens[i].trim().length()==0) continue;
    		etmp=getElement(etmp,tokens[i]);
    		if (etmp==null )break;
    	}
    	return etmp;
        
    }
    private static String[] getElementToken(String nodname)
    {
    	if (nodname ==null) return null;
    	ArrayList al =new ArrayList();
    	String tmpstr =nodname;
    	int ipos =nodname.indexOf("/");
    	int spos =0;
    	while(ipos >=0){
    		tmpstr =nodname.substring(spos,ipos);
    		
    		int mpos =tmpstr.indexOf("[");
	    	if (mpos>0){
	    		mpos =nodname.indexOf("]",spos);
	    		tmpstr =nodname.substring(spos, mpos+1);
	    		spos =mpos+2;
	    	}else spos =ipos+1;
	    	
	    	al.add(tmpstr);
	    	ipos =nodname.indexOf("/",spos);
    	}
    	if (spos <nodname.length()) al.add(nodname.substring(spos));
    	String[] retarr =new String[al.size()];
    	//System.out.println("element token :"+retarr);
    	for (int i=0;i<al.size();i++) retarr[i]=(String)al.get(i);
    	
    	return retarr;
    }
    public static Element getElement(Element element,Namespace ns,String childNod) {
    	NodeList nodeList = element.getChildNodes();
    	if (element ==null) return null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() ==Node.ELEMENT_NODE) {
                if(node.getLocalName().equals(childNod)&& node.getNamespaceURI().equals(ns.getURI()))
                	return (Element)node;
            }
        }
        return null;
    }
    /**
     * Moves the content of the given element to the given element
     */
    public static void moveContent(Element from, Element to) {
        // lets move the child nodes across
        NodeList childNodes = from.getChildNodes();
        while (childNodes.getLength() > 0) {
            Node node = childNodes.item(0);
            from.removeChild(node);
            to.appendChild(node);
        }
    }

    /**
     * Copy the attribues on one element to the other
     */
    public static void copyAttributes(Element from, Element to) {
        // lets copy across all the remainingattributes
        NamedNodeMap attributes = from.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr node = (Attr) attributes.item(i);
            to.setAttributeNS(node.getNamespaceURI(), node.getName(), node.getValue());
        }
    }

    /**
     * A helper method useful for debugging and logging which will convert the given DOM node into XML text
     */
    public static String asXML(Node node) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter buffer = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(buffer));
        return buffer.toString();
    }

    /**
     * A helper method useful for debugging and logging which will convert the given DOM node into XML text
     */
    public static String asIndentedXML(Node node) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter buffer = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(buffer));
        return buffer.toString();
    }

    /**
     * Adds the child element with the given text
     */
    public static void addChildElement(Element element, String name, Object textValue) {
        Document document = element.getOwnerDocument();
        Element child = document.createElement(name);
        element.appendChild(child);
        if (textValue != null) {
            String text = textValue.toString();
            child.appendChild(document.createTextNode(text));
        }
    }
    public static void addChildElement(Element element, String name,Namespace ns, String textValue)  {
       // Document document = element.getOwnerDocument();
        Element child = newElement(element,name,ns);
        element.appendChild(child);
        addElementText(child,textValue);
        
    }
    /**
     * Creates a QName instance from the given namespace context for the given qualifiedName
     *
     * @param element       the element to use as the namespace context
     * @param qualifiedName the fully qualified name
     * @return the QName which matches the qualifiedName
     */
    public static QName createQName(Element element, String qualifiedName) {
        int index = qualifiedName.indexOf(':');
        if (index >= 0) {
            String prefix = qualifiedName.substring(0, index);
            String localName = qualifiedName.substring(index + 1);
            String uri = recursiveGetAttributeValue(element, "xmlns:" + prefix);
            return new QName(uri, localName, prefix);
        } else {
            String uri = recursiveGetAttributeValue(element, "xmlns");
            if (uri != null) {
                return new QName(uri, qualifiedName);
            }
            return new QName(qualifiedName);
        }
    }

    /**
     * Recursive method to find a given attribute value
     */
    public static String recursiveGetAttributeValue(Element element, String attributeName) {
        String answer = null;
        try {
            answer = element.getAttribute(attributeName);
        } catch (Exception e) {
            //if (LOG.isTraceEnabled()) {
              //  LOG.trace("Caught exception looking up attribute: " + attributeName + " on element: " + element + ". Cause: " + e, e);
           // }
        }
        if (answer == null || answer.length() == 0) {
            Node parentNode = element.getParentNode();
            if (parentNode instanceof Element) {
                return recursiveGetAttributeValue((Element) parentNode, attributeName);
            }
        }
        return answer;
    }

    /**
     * Get the first child element
     * @param parent
     * @return
     */
    public static Element getFirstChildElement(Node parent) {
        NodeList childs = parent.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node child = childs.item(i);
            if (child instanceof Element) {
                return (Element) child;
            }
        }
        return null;
    }
    
    /**
     * Get the next sibling element
     * @param el
     * @return
     */
    public static Element getNextSiblingElement(Element el) {
        for (Node n = el.getNextSibling(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element) {
                return (Element) n;
            }
        }
        return null;
    }
    
    /**
     * Build a QName from the element name
     * @param el
     * @return
     */
    public static QName getQName(Element el) {
        if (el == null) {
            return null;
        } else if (el.getPrefix() != null) {
            return new QName(el.getNamespaceURI(), el.getLocalName(), el.getPrefix());
        } else {
            return new QName(el.getNamespaceURI(), el.getLocalName());
        }
    }

    public static DocumentBuilder getBuilder() throws ParserConfigurationException {
        DocumentBuilder builder = (DocumentBuilder) builders.poll();
        if (builder == null) {
            if (dbf == null) {
                dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
            }
            builder = dbf.newDocumentBuilder();
        }
        
        return builder;
    }

    public static void releaseBuilder(DocumentBuilder builder) {
        builders.add(builder);
    }

    /**
     * Return a new document, ready to populate.
     * @return
     * @throws ParserConfigurationException
     */
    public static Document newDocument() throws ParserConfigurationException {
        DocumentBuilder builder = getBuilder();
        Document doc = builder.newDocument();
        releaseBuilder(builder);
        return doc;
    }
    public static Element newElement(Document doc,String localPart,Namespace ns) 
    {
    	Element e =doc.createElementNS(ns.getURI(), localPart);
    	e.setPrefix(ns.getPrefix());
    	return e;
    }
    public static Element newElement(Element srcEle,String localPart,Namespace ns) 
    {
    	Document doc =srcEle.getOwnerDocument();
    	Element e =doc.createElementNS(ns.getURI(), localPart);
    	e.setPrefix(ns.getPrefix());
    	return e;
    }
    public static Element newElement(Element srcEle,String localPart) 
    {
    	Document doc =srcEle.getOwnerDocument();
    	Element e =doc.createElement(localPart);
    	//e.setPrefix(ns.getPrefix());
    	return e;
    }
    public static Document parse(File f) throws Exception
    {
    	return getBuilder().parse(f);
    }

    public static Document parse(InputStream is) throws Exception
    {
    	return getBuilder().parse(is);
    }
    public static Document parse(String uri) throws Exception
    {
    	return getBuilder().parse(uri);
    }

	public static List getChildren(Element documentElement) {
		// TODO Auto-generated method stub
		if (documentElement==null) return null;
		
		NodeList nodeList = documentElement.getChildNodes();
		ArrayList al =new ArrayList();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                al.add(node);
            }
        }
		return al;
	}
	public static List getChildren(Element documentElement,String childNod) {
		// TODO Auto-generated method stub
		NodeList nodeList = documentElement.getChildNodes();
		ArrayList al =new ArrayList();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	if(node.getNodeName().equals(childNod))
            		al.add(node);
            }
        }
		return al;
	}
	public static List getChildrenByPath(Element documentElement,String childNod) {
		// TODO Auto-generated method stub
		NodeList nodeList = documentElement.getChildNodes();
		List al =null;
		String[] tokens=StringUtils.getToken(childNod, "/");
    	Element etmp =documentElement;
    	al=getChildren(etmp,tokens[0]);
    	for(int i=1;i<tokens.length;i++){
    		if (tokens[i].trim().length()==0) continue;
    		al =getChildren(al,tokens[i]);
    		
    	}
    	
		return al;
	}
	private static List getChildren(List al, String childNod) {
		// TODO Auto-generated method stub
		Element em =null;
		List etmp =null;
		ArrayList retAl =new ArrayList();
		for(int i=0;i<al.size();i++){
			em=(Element)al.get(i);
			etmp=getChildren(em,childNod);
			
			if (etmp !=null) retAl.addAll(etmp);
		}
		return retAl;
	}

	public static List getChildren(Element documentElement,Namespace ns,String childNod) {
		// TODO Auto-generated method stub
		NodeList nodeList = documentElement.getChildNodes();
		ArrayList al =new ArrayList();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	if(node.getLocalName().equals(childNod)&& node.getNamespaceURI().equals(ns.getURI()))
            		al.add(node);
            }
        }
		return al;
	}
	public static List getChildrenPrefix(Element documentElement,String prefix) {
		// TODO Auto-generated method stub
		NodeList nodeList = documentElement.getChildNodes();
		ArrayList al =new ArrayList();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	if(node.getPrefix() !=null && node.getPrefix().equals(prefix))
            		al.add(node);
            }
        }
		return al;
	}
	public static String getAttribute(Element e, String attr) {
		// TODO Auto-generated method stub
		if(e==null) return null;
		return e.getAttribute(attr);
	}

	public static void addAttribute(Element element, String atrName,
			Namespace nSEB, String val) {
		// TODO Auto-generated method stub
		
		element.setAttributeNS(nSEB.getURI(),atrName, val);
		Attr attr = element.getAttributeNodeNS (nSEB.getURI(), atrName) ;
		attr.setPrefix (nSEB.getPrefix()) ;
	}
	
	public static void setAttribute(Element ele,String atrName,String val)
	{
		if (ele==null||atrName==null||val==null) return;
		
		Attr attr =ele.getAttributeNode(atrName);
		if (attr==null){
			ele.setAttribute(atrName, val);
		}
		else attr.setValue(val);
	}

	public static void addElementText(Element element, String content) {
		// TODO Auto-generated method stub
		Document document = element.getOwnerDocument();
        
        if (content != null) {
            
            element.appendChild(document.createTextNode(content));
        }
	}
	public static void setText(Element element, String content) {
		// TODO Auto-generated method stub
		
        
        if (element !=null && content != null) {
        	Document document = element.getOwnerDocument();
            NodeList nl =element.getChildNodes();
            Node nod =null;
            for (int i=0;i<nl.getLength();i++){
            	nod =nl.item(i);
            	if (nod.getNodeType()== Node.TEXT_NODE){
            		element.removeChild(nod);
            	}
            }
            element.appendChild(document.createTextNode(content));
        }
	}
	public static Element copyElement(Element element) {
		// TODO Auto-generated method stub
		
		NodeList nodeList = element.getChildNodes();
		NamedNodeMap xatt =null ;
		Document dDoc =element.getOwnerDocument();
		Element dstNod =dDoc.createElementNS(element.getNamespaceURI(),element.getNodeName());
		copyAttributes(element,dstNod);
		
		if (nodeList != null) 
		{
			for (int i = 0; i < nodeList.getLength(); i++) 
			{
				if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) 
				{
					Node nod = nodeList.item(i) ;
					Element tmpNod = dDoc.createElementNS(nod.getNamespaceURI(), nod.getNodeName()) ;
					copyAttributes((Element)nod,tmpNod);
					copyElement( nod,tmpNod ) ;
					dstNod.appendChild( tmpNod );
				}
				else if (nodeList.item(i).getNodeType() == Node.TEXT_NODE ) 
				{
					String nval = nodeList.item(i).getNodeValue();
					Text tmpText = dDoc.createTextNode(nval);
					dstNod.appendChild(tmpText);
				}
			}
		}
		return dstNod;
	}
	public static void copyElement(Node element,Node dstNod ) {
		// TODO Auto-generated method stub
		
		NodeList nodeList = element.getChildNodes();
		NamedNodeMap xatt =null ;
		Document dDoc =dstNod.getOwnerDocument();
		
		if (nodeList != null) 
		{
			for (int i = 0; i < nodeList.getLength(); i++) 
			{
				if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) 
				{
					Node nod = nodeList.item(i) ;
					Element tmpNod = dDoc.createElementNS(nod.getNamespaceURI(), nod.getNodeName()) ;
					
					copyAttributes((Element)nod,tmpNod);
					copyElement( nod, tmpNod ) ;
					dstNod.appendChild( tmpNod );
				}
				else if (nodeList.item(i).getNodeType() == Node.TEXT_NODE ) 
				{
					String nval = nodeList.item(i).getNodeValue();
					Text tmpText = dDoc.createTextNode(nval);
					dstNod.appendChild(tmpText);
				}
			}
		}
		
	}

	public static void saveToFile(Node doc ,String fpath) throws Exception
	{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fpath)));
        
        FileOutputStream fo=null;
		try{
			fo =new FileOutputStream(fpath);
			transformer.transform(new DOMSource(doc), new StreamResult(fo));
		}catch(Exception ex){
			throw ex;
		}finally{
			try{
				fo.close();
			}catch(Exception ee){}
		}
       
	}
	public static void saveToFile(Node doc ,String fpath,Map outputProperties) throws Exception
	{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		if(outputProperties !=null){
			Iterator ite =outputProperties.keySet().iterator();
			String okey =null;
			while(ite.hasNext()){
				okey =(String)ite.next();
				transformer.setOutputProperty(okey,(String)outputProperties.get(okey));
			}
		}
		FileOutputStream fo=null;
		try{
			fo =new FileOutputStream(fpath);
			transformer.transform(new DOMSource(doc), new StreamResult(fo));
		}catch(Exception ex){
			throw ex;
		}finally{
			try{
				fo.close();
			}catch(Exception ee){}
		}
       
	}
	public static void writeTo(Node doc ,OutputStream os) throws Exception
	{
		try{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(os));
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
       
	}

	public static void removeChildren(Element e) {
		// TODO Auto-generated method stub
		List alList =DOMUtil.getChildren(e);
		for(int i=0;i<alList.size();i++){
			Element child =(Element)alList.get(i);
			e.removeChild(child);
		}
	}
	public static void removeChild(Element e,String childName) {
		// TODO Auto-generated method stub
		List alList =DOMUtil.getChildren(e);
		for(int i=0;i<alList.size();i++){
			Element child =(Element)alList.get(i);
			if (childName.equals(child.getTagName())){
				e.removeChild(child);
				break;
			}
		}
	}

	public static String getAttributeByPath(Element rootEle, String path,
			String attrName) {
		// TODO Auto-generated method stub
		Element ele =getElementByPath(rootEle, path);
		if (ele !=null) return ele.getAttribute(attrName);
		return null;
	}
}
