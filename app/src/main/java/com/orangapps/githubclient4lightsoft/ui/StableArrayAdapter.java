package com.orangapps.githubclient4lightsoft.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangapps.githubclient4lightsoft.R;
import com.orangapps.githubclient4lightsoft.data.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rurik on 28.09.14.
 */

public class StableArrayAdapter extends ArrayAdapter<User> {

    private List<User> users = new ArrayList<User>();

    private Context context;

    public StableArrayAdapter(Context aContext, List<User> userList) {
        super(aContext, R.layout.list_node, userList);
        context = aContext;
        users = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_node, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.login_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.avatar_icon);
        textView.setText(users.get(position).getLogin());
        return rowView;
    }

}
