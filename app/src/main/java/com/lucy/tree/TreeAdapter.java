package com.lucy.tree;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lucy.common.R;
import com.lucy.common.adapter.CommonAdapter;
import com.lucy.common.adapter.ViewHolder;

public class TreeAdapter extends CommonAdapter<TreeNode> {
	protected ListView mTreeListView;
	protected List<TreeNode> mAllNodes;
	protected List<TreeNode> mVisibleNodes;

	public TreeAdapter(ListView listView, Context context, List<TreeNode> dataList) {
		super(context, dataList, R.layout.item_tree);
		mTreeListView = listView;
		mAllNodes = TreeHelper.getSortedNodes(dataList, 1);
		mDataList = TreeHelper.filterVisibleNodes(mAllNodes);
		mTreeListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				expandOrCollapse(position);
			}
		});
	}

	@Override
	public void convert(ViewHolder viewHolder, TreeNode item) {
		viewHolder.setText(R.id.name, item.getName());
		View convertView = viewHolder.getConvertView();
		convertView.setPadding(30*item.getLevel(), 0, 0, 0);
	}

	/**
	 * 点击收缩或者展开
	 * 
	 * @param position
	 */
	private void expandOrCollapse(int position) {
		TreeNode n = mDataList.get(position);
		if (n != null) {
			if (n.isLeaf())
				return;
			n.setExpand(!n.isExpand());
			mDataList = TreeHelper.filterVisibleNodes(mAllNodes);
			notifyDataSetChanged();
		}
	}

}
