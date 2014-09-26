package com.test.vue.vuetest.presenters;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.vue.vuetest.R;

/**
 * Created by advisors on 20/9/14.
 */
public class SettingsFragment extends Fragment {
    private Typeface typeface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, null);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Regular.ttf");
        TextView notificationId = (TextView) view.findViewById(R.id.notification_id);
        TextView locationId = (TextView) view.findViewById(R.id.location_id);
        TextView tracking_textId = (TextView) view.findViewById(R.id.tracking_text_id);
        TextView aboutId = (TextView) view.findViewById(R.id.about_id);
        TextView versionId = (TextView) view.findViewById(R.id.version_id);
        TextView privacyId = (TextView) view.findViewById(R.id.privacy_id);
        TextView rightsTextId = (TextView) view.findViewById(R.id.rights_text_id);
        rightsTextId.setTypeface(typeface);
        privacyId.setTypeface(typeface);
        versionId.setTypeface(typeface);
        aboutId.setTypeface(typeface);
        tracking_textId.setTypeface(typeface);
        locationId.setTypeface(typeface);
        notificationId.setTypeface(typeface);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
