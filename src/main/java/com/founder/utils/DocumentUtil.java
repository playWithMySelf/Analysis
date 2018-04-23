package com.founder.utils;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * xml处理
 * @author Administrator
 *
 */
public class DocumentUtil {

	List<Element> relist = null;
	public Document doc = null;
	public DocumentUtil(Document doc){
		this.doc = doc;
	}

	//获取单节点
	public Element selectSingleNode(String nodestr){
		//空
		if(doc==null || nodestr==null || "".equals(nodestr)){
			return null;
		}

		//doc取法
		if(doc.selectSingleNode("//"+nodestr)!=null){
			return (Element)doc.selectSingleNode("//"+nodestr);
		}

		//递归获取
		Element root = doc.getRootElement();
		if(root.getName().equals(nodestr)){
			return root;
		}else{
			return selectSingleNode_dg(root,nodestr);
		}
	}
	private Element selectSingleNode_dg(Element root,String nodestr){
		List<Element> elelist = root.elements();
		if(elelist!=null && elelist.size()>0){
			for(Element ele : elelist){
				if(ele.getName().equals(nodestr)){
					return ele;
				}else{
					Element eleobj = selectSingleNode_dg(ele,nodestr);
					if(eleobj!=null){
						return eleobj;
					}
				}
			}
		}
		return null;
	}

	//获取多节点
	public List<Element> selectNodes(String nodestr){
		//空
		if(doc==null || nodestr==null || "".equals(nodestr)){
			return null;
		}

		//doc取法
		List list = doc.selectNodes("//"+nodestr);
		if(list!=null && list.size()>0){
			return (List<Element>)list;
		}

		//递归获取
		relist = new ArrayList<Element>();
		Element root = doc.getRootElement();
		if(root.getName().equals(nodestr)){
			relist.add(root);
		}
		selectNodes_dg(root,nodestr);
		return relist;
	}
	private void selectNodes_dg(Element root,String nodestr){
		List<Element> elelist = root.elements();
		if(elelist!=null && elelist.size()>0){
			for(Element ele : elelist){
				//本节点是否满足要求
				if(ele.getName().equals(nodestr)){
					relist.add(ele);
				}

				//下级节点
				selectNodes_dg(ele,nodestr);
			}
		}
	}

}
