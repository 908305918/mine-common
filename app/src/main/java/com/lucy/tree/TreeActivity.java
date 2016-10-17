package com.lucy.tree;

import java.util.ArrayList;
import java.util.List;

import com.lucy.common.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TreeActivity extends Activity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_tree);
		listView = (ListView) super.findViewById(R.id.list_tree);
		TreeAdapter adapter = new TreeAdapter(listView, this, initTreeNodes());
		listView.setAdapter(adapter);
	}

	private List<TreeNode> initTreeNodes() {
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode(1, 0, "目录1");
		list.add(node);
		node = new TreeNode(2, 1, "子目录1-1");
		list.add(node);
		node = new TreeNode(3, 1, "子目录1-2");
		list.add(node);
		node = new TreeNode(4, 0, "目录2");
		list.add(node);
		node = new TreeNode(5, 4, "子目录2-1");
		list.add(node);
		node = new TreeNode(6, 4, "子目录2-2");
		list.add(node);
		return list;
	}

}
