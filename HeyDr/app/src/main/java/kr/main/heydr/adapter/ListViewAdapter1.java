package kr.main.heydr.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import kr.main.heydr.R;
import kr.main.heydr.controller.vo.ReserveVO;
import kr.main.heydr.controller.vo.ReviewVO;
import kr.main.heydr.utils.Calculate_Date;

public class ListViewAdapter1 extends BaseAdapter {
    ArrayList<ReserveVO> items = new ArrayList<ReserveVO>();
    LayoutInflater mLayoutInflater;
    int aa = 0;
    TextView res_num, res_rdate, res_time;
    private Context context;
    ConstraintLayout constraintLayout;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private Inflater inflater;

    public void addItem(int res_num, String res_rdate, String res_time) {
        ReserveVO item = new ReserveVO();
        item.setReservNum(res_num);
        item.setRdate(res_rdate);
        item.setRtime(res_time);
        items.add(item);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if(mLayoutInflater == null){
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.list_item_res, null);
        }
        Date mDate;
        constraintLayout = convertView.findViewById(R.id.gaga);
        Calculate_Date calculate_date = new Calculate_Date();
        String dataa = calculate_date.WhatTimeIsItAll();
        String nowday = calculate_date.WhatTimeIsItDate();
        try {
            aa = calculate_date.calDateBetweenAandB(nowday, items.get(position).getRdate());
            Log.e("tata", aa+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        res_num = (TextView)convertView.findViewById(R.id.res_num);
        res_rdate = (TextView)convertView.findViewById(R.id.res_rdate);
        res_time = (TextView)convertView.findViewById(R.id.res_time);

        if( aa == 1 ){

        }else {

                res_num.setText(items.get(position).getReservNum() + "");
                res_rdate.setText(items.get(position).getRdate() + "");
                res_time.setText(items.get(position).getRtime() + "");


            }

        return convertView;
    }
}
