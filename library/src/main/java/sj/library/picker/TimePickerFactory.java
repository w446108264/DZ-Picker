package sj.library.picker;

import android.content.Context;

/**
 * Created by sj on 2016/11/27.
 */

public class TimePickerFactory {

    public enum Type {
        SINGLEDAY, DHM
    }

    public static TimePicker create(Context context, Type type) {
        switch (type) {
            case SINGLEDAY:
                return new SingleDayTimePicker(context);
            case DHM:
                return new DHMTimePicker(context);
            default:
                break;
        }
        return null;
    }
}
