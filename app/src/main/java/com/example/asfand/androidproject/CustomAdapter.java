package com.example.asfand.androidproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Asfand on 14-Nov-17.
 */

public class CustomAdapter extends BaseAdapter {
    private ArrayList<ListShow> row;
    private LayoutInflater thisinflater;

    public CustomAdapter(Context context, ArrayList<ListShow> aRow) {

        this.row = aRow;
        thisinflater = ( LayoutInflater.from(context) );

    }

    @Override
    public int getCount() {
        return row.size();
    }

    @Override
    public Object getItem(int position) {
        return  row.get( position );

    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = thisinflater.inflate(R.layout.listcontents, parent, false);
        }

            TextView catT = (TextView) convertView.findViewById(R.id.catergory);
            TextView descT = (TextView) convertView.findViewById(R.id.des);
            TextView nameT =(TextView) convertView.findViewById(R.id.name);
            TextView dateT =(TextView) convertView.findViewById(R.id.date);

//            ImageView conditionImage = (ImageView) convertView.findViewById(R.id.conditiondfayimage);

            ListShow currentRow = (ListShow) getItem(position);

            catT.setText( currentRow.getCategory() );
            descT.setText( currentRow.getDescription() );
            nameT.setText(currentRow.getName());
            dateT.setText(currentRow.getDate() );

        return convertView;
    }

}