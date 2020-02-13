package com.lucy.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeHelper {

	/**
	 * 排序节点
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 */
	public static List<TreeNode> getSortedNodes(List<TreeNode> datas, int defaultExpandLevel) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		List<TreeNode> nodes = setUpNodesRelationship(datas);
		List<TreeNode> rootNodes = getRootBeans(nodes);
		for (TreeNode node : rootNodes) {
			addNode(result, node, defaultExpandLevel, 1);
		}
		return result;
	}
	
	/**
	 * 过滤出可见的节点
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<TreeNode> filterVisibleNodes(List<TreeNode> nodes) {
		List<TreeNode> result = new ArrayList<TreeNode>();

		for (TreeNode node : nodes) {
			if (node.isRoot() || node.isParentExpand()) {
				result.add(node);
			}
		}
		return result;
	}
	/**
	 * 把一个节点的所有孩子节点都放入result
	 * 
	 * @param result
	 * @param node
	 * @param defaultExpandLevel
	 * @param i
	 */
	private static void addNode(List<TreeNode> result, TreeNode node, int defaultExpandLevel,
			int currentLevel) {
		result.add(node);
		if (defaultExpandLevel >= currentLevel) {
			node.setExpand(true);
		}
		if (node.isLeaf()) {
			return;
		}
		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
		}
	}



	/**
	 * 设置Node间的节点关系
	 * 
	 * @param nodes
	 * @return
	 */
	private static List<TreeNode> setUpNodesRelationship(List<TreeNode> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			TreeNode n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				TreeNode m = nodes.get(j);
				if (n.getId() == m.getPid()) {
					m.setParent(n);
					n.getChildren().add(m);
				} else if (n.getPid() == m.getId()) {
					n.setParent(m);
					m.getChildren().add(n);
				}
			}
		}
		return nodes;
	}

	/**
	 * 从所有节点中过滤出根节点
	 * 
	 * @param nodes
	 * @return
	 */
	private static List<TreeNode> getRootBeans(List<TreeNode> nodes) {
		List<TreeNode> root = new ArrayList<TreeNode>();
		for (TreeNode node : nodes) {
			if (node.isRoot()) {
				root.add(node);
			}
		}
		return root;
	}

}
