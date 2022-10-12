package kr.main.heydr.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kr.main.heydr.R;


public class NumberAdapter extends BaseAdapter {
   private Context context;
   private LayoutInflater inflater;
   private String[] arrNumberWord;
   private int[] arrNumberImage;

    public NumberAdapter(Context context, String[] arrNumberWord, int[] arrNumberImage) {
        this.context = context;
        this.arrNumberWord = arrNumberWord;
        this.arrNumberImage = arrNumberImage;
    }

    @Override
    public int getCount() {
        return arrNumberWord.length;
    }

    @Override
    public Object getItem(int position) {
        return arrNumberWord[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = inflater.inflate(R.layout.number_layout, null);
        }
        ImageView numberImage = view.findViewById(R.id.numberImage);
        TextView numberText =  view.findViewById(R.id.numberText);
        numberText.setTextColor(Color.parseColor("#000000") );
        numberImage.setImageResource(arrNumberImage[position]);
        numberText.setText(arrNumberWord[position]);

        return view;
    }
}
