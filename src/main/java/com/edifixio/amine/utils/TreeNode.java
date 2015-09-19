package com.edifixio.amine.utils;

import java.util.Collection;

public interface TreeNode<ElementType>{
	
	public ElementType getElement();
	public String getName();
	public Collection<TreeNode<ElementType>> getChildsNode();
	public TreeNode<ElementType> getChild(String nodeName);
	
	public void setName(String name);
	public void getElement(ElementType element);
	public void addChild(TreeNode<ElementType> node);
	
	public void removeElement(String name);
	public void removeChildsNode(String name);
	

}
