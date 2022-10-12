package kr.main.heydr.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import kr.main.heydr.R;
import kr.main.heydr.controller.vo.ReviewVO;
import kr.main.heydr.view.search.hospital.frag.HosFragment1;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<ReviewVO> items = new ArrayList<ReviewVO>();
    LayoutInflater mLayoutInflater;

    TextView review_sub, review_title, review_id, review_num;
    private Context context;


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

    public void addItem(String title, int number, String content, String id) {
        ReviewVO item = new ReviewVO();
        item.setTitle(title);
        item.setId(id);
        item.setCont(content);
        item.setRnum(number);
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
            convertView = mLayoutInflater.inflate(R.layout.list_item_review, null);
        }

        review_num = (TextView)convertView.findViewById(R.id.review_num);
        review_id = (TextView)convertView.findViewById(R.id.review_id);
        review_title = (TextView)convertView.findViewById(R.id.review_title);
        review_sub= (TextView)convertView.findViewById(R.id.review_cont);

        review_id.setText(items.get(position).getId());
        review_num.setText(items.get(position).getRnum()+" 번");
        review_title.setText(items.get(position).getTitle());
        review_sub.setText(items.get(position).getCont());
        return convertView;
    }
}
