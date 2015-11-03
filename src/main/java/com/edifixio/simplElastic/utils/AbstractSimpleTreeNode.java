package com.edifixio.simplElastic.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AbstractSimpleTreeNode<ElementType> implements TreeNode<ElementType>{
	protected String name;
	protected ElementType element;
	protected Map<String,TreeNode<ElementType>> childs;

	public AbstractSimpleTreeNode(String name,ElementType element) {
		super();
		this.name = name;
		this.element=element;
		this.childs=new HashMap<String, TreeNode<ElementType>>();
	}
	
	public AbstractSimpleTreeNode(String name,ElementType element,Collection<TreeNode<ElementType>> childs) {
		super();
		this.name = name;
		this.element=element;
		this.childs=new HashMap<String, TreeNode<ElementType>>();
		Iterator<TreeNode<ElementType>> childsIter=childs.iterator();
		TreeNode<ElementType> entry;
		while(childsIter.hasNext()){
			entry=childsIter.next();
			this.childs.put(entry.getName(), entry);
		}
	}

	public ElementType getElement() {
		return element;
	}

	public String getName() {
		return name;
	}

	public Collection<TreeNode<ElementType>> getChildsNode() {
		return childs.values();
	}

	public TreeNode<ElementType> getChild(String nodeName) {
		return childs.get(nodeName);
	}

	public void setName(String name) {
		this.name=name;
		
	}

	public void getElement(ElementType element) {
		this.element=element;
		
	}



	public void addChild(TreeNode<ElementType> node) {
		childs.put(node.getName(), node);
		
	}

	public void removeElement(String name) {
		this.element=null;
		
	}

	public void removeChildsNode(String name) {
		this.childs=null;
		
	}

	@Override
	public String toString() {
		return "AbstractSimpleTreeNode [name=" + name + ", element=" + element + ", childs=" + childs + "]";
	}
	
	

}
