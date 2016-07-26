package com.hdj.downapp_market.activity;

import java.util.ArrayList;
import java.util.List;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.R.id;
import com.hdj.downapp_market.R.layout;
import com.mz.bean.AppInfo;
import com.umeng.analytics.c;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	List<AppInfo>  list=null;
	Context context;
	LayoutInflater inflater;
	public MyAdapter(Context context,List<AppInfo>  list) {
		this.context=context;
		this.list=list;
		inflater=LayoutInflater.from(context);
	}
	public void setList(List<AppInfo>  list){
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AppInfo app=list.get(position);
		ViewHolder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item, null);
			holder=new ViewHolder();
			holder.rb_app=(RadioButton)convertView.findViewById(R.id.rb_app_name);
			holder.tv_pn=(TextView)convertView.findViewById(R.id.tv_pn);
			convertView.setTag(holder);
		}
		else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.rb_app.setText(app.getAppName());
//		holder.rb_app.setChecked(app.getStatus()==0?false:true);
		holder.tv_pn.setText(app.getPackageName());
		if(selection==position){
			holder.rb_app.setChecked(true);
		}else {
			holder.rb_app.setChecked(false);
		}
		return convertView;
	}
	int selection;
	public void setSelection(int position){
		selection=position;
	}
	class ViewHolder{
		RadioButton rb_app;
		TextView tv_pn;
	}

}
