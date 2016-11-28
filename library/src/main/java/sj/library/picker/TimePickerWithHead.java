package sj.library.picker;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * 带头部的时间选择滚轮
 * Created by sj on 2016/11/27.
 */

public abstract class TimePickerWithHead extends TimePicker implements View.OnClickListener{

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private LayoutInflater layoutInflater;
    private ViewGroup parent;

    private View btnSubmit, btnCancel;
    private TextView tvTitle;

    public TimePickerWithHead(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.ic_picker_container, contentContainer);

        parent = (ViewGroup) findViewById(R.id.ly_picker_parent);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        if(btnSubmit != null) {
            btnSubmit.setTag(TAG_SUBMIT);
            btnSubmit.setOnClickListener(this);
        }
        if(btnCancel != null) {
            btnCancel.setTag(TAG_CANCEL);
            btnCancel.setOnClickListener(this);
        }
    }

    @Override
    public TimePicker setTimeBuilder(TimeBuilder builder) {
        return null;
    }

    protected void setPickerContainerView(@LayoutRes int layoutResID) {
        if(parent != null) {
            parent.addView(layoutInflater.inflate(layoutResID, null));
        }
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if(onTimeSelectListener != null) {
                onTimeSelectListener.onTimeSelect(getSelectDate());
            }
            return;
        }
    }

    public TimePickerWithHead setTitle(String title){
        if(tvTitle != null) {
            tvTitle.setText(title);
        }
        return this;
    }

    @Override
    public TimePicker setOnTimeSelectListener(OnTimeSelectListener listener) {
        onTimeSelectListener = listener;
        return this;
    }
}
