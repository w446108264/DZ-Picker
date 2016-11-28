package sj.library.picker;

import android.content.Context;

import com.bigkoo.pickerview.view.BasePickerView;

/**
 * Created by sj on 2016/11/27.
 */

public abstract class TimePicker extends BasePickerView {

    protected TimeBuilder timeBuilder;

    protected OnTimeSelectListener onTimeSelectListener;

    public TimePicker(Context context) {
        super(context);
    }

    /**
     * 设置时间相关的Builder
     * 支持设置指定区间 {@link TimeBuilder}
     * @param builder
     * @return
     */
    public abstract TimePicker setTimeBuilder(TimeBuilder builder);

    /**
     * 设置标题
     * @param title
     * @return
     */
    public abstract TimePicker setTitle(String title);

    /**
     * 获取当前选择的日期
     * @return Date
     */
    public abstract Object getSelectDate() ;

    /**
     * 时间选择后回调事件
     * @param listener
     * @return
     */
    public abstract TimePicker setOnTimeSelectListener(final OnTimeSelectListener listener);

    /**
     * 时间选择后回调监听器
     */
    public interface OnTimeSelectListener {

        /**
         * 返回当前滚轮中选择的时间
         * @param date
         */
        void onTimeSelect(Object date);
    }
}
