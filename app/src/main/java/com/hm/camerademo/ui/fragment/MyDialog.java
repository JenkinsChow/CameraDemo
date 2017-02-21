package com.hm.camerademo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hm.camerademo.R;

/**
 * Created by dumingwei on 2017/2/21.
 */
public class MyDialog extends DialogFragment {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private TextView textTitle;
    private TextView textContent;
    private CheckBox checkBoxAskAgain;
    private Button btnDeny;
    private Button btnAllow;

    private String title;
    private String content;

    private boolean notAskAgain = false;//不再询问

    private OnAllowClickListener onAllowClickListener;

    public MyDialog() {
    }


    public static MyDialog newInstance(String title, String content) {
        MyDialog dialog = new MyDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        dialog.setArguments(args);
        return dialog;
    }

    public void setOnAllowClickListener(OnAllowClickListener onAllowClickListener) {
        this.onAllowClickListener = onAllowClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            content = getArguments().getString(CONTENT);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container);
        textTitle = (TextView) view.findViewById(R.id.text_title);
        textContent = (TextView) view.findViewById(R.id.text_content);
        textTitle.setText(title);
        textContent.setText(content);
        checkBoxAskAgain = (CheckBox) view.findViewById(R.id.checkbox_ask_again);
        btnDeny = (Button) view.findViewById(R.id.btn_deny);
        btnAllow = (Button) view.findViewById(R.id.btn_allow);
        checkBoxAskAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notAskAgain = isChecked;
            }
        });
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SpUtil.getInstance().putNotAskAgain(notAskAgain);
                dismiss();
            }
        });
        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这时候应该提示用户去应用里面设置权限
                dismiss();
                if (onAllowClickListener != null) {
                    onAllowClickListener.onClick();
                }
            }
        });
        return view;
    }

    public interface OnAllowClickListener {
        void onClick();
    }
}
