package sj.library.picker.adapter;

/**
 * Created by sj on 2016/11/27.
 */

public class HourWheelAdapter extends NumberWheelAdapter {

    /** The default min value */
    private static final int DEFAULT_MIN_VALUE = 0;

    /** The default max value */
    public static final int DEFAULT_MAX_VALUE = 23;

    public HourWheelAdapter() {
        super();
    }

    public HourWheelAdapter(int minValue, int maxValue) {
        super(minValue, Math.min(maxValue, DEFAULT_MAX_VALUE));
    }

    @Override
    public String getShowContent(int index) {
        return dateList.get(index) + "点";
    }
}
