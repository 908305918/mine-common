package com.lucy.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private int id;
	private int pid;
	private String name;
	private boolean isExpand;// 是否展开
	private int level;
	private TreeNode parent;
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode(int id, int pid, String name) {
		this.id = id;
		this.pid = pid;
		this.name = name;
	}

	/**
	 * 是否为根节点
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * 是否为叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}

	/**
	 * 判断当前父节点的收缩状态
	 * 
	 * @return
	 */
	public boolean isParentExpand() {
		if (parent == null)
			return false;
		return parent.isExpand();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
		this.isExpand = isExpand;
		if (!isExpand) {
			for (TreeNode item : children) {
				item.setExpand(false);
			}
		}
	}

	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
