package com.itheima.googleplay.fragment;


import java.util.List;

import com.itheima.googleplay.adapter.ListBaseAdapter;
import com.itheima.googleplay.domain.AppInfo;
import com.itheima.googleplay.protocol.GameProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.BaseListView;
import com.itheima.googleplay.view.LoadingPage.LoadResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFragment extends BaseFragment {

	private List<AppInfo> datas;
	/**
	 * 加载成功的界面
	 */
	@Override
	public View createSuccessView() {
		BaseListView  listView=new BaseListView(UiUtils.getContext());
		listView.setAdapter(new ListBaseAdapter(datas,listView){

			@Override
			protected List<AppInfo> onload() {
				GameProtocol protocol=new GameProtocol();
				List<AppInfo> load = protocol.load(datas.size());  //  
				datas.addAll(load);
				return load;
			}
			
		});
		return listView;
	}
	/**
	 * 请求服务器加载数据
	 */
	@Override
	protected LoadResult load() {
		GameProtocol protocol=new GameProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}
}
