package com.orangapps.githubclient4lightsoft.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangapps.githubclient4lightsoft.R;
import com.orangapps.githubclient4lightsoft.data.User;
import com.orangapps.githubclient4lightsoft.githubApi.AsyncImageRequest;

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
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.avatar_icon);

        final User user = users.get(position);

        textView.setText(user.getLogin());

        String avatar_url = user.getAvatar_url();
        if (user.getImg() != null) {
            imageView.setImageBitmap(user.getImg());
        } else {
            try {
                new AsyncImageRequest() {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        user.setImg(bitmap);
                        imageView.setImageBitmap(bitmap);
                    }
                }.execute(avatar_url).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return rowView;
    }


}
