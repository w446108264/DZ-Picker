package sj.library.picker.adapter;

/**
 *
 * 进制单位为10分钟的 分钟滚轮
 *
 * Created by sj on 2016/11/27.
 */

public class MinuteWheelAdapter extends NumberWheelAdapter {

    /**
     * minute growth rate
     * 分钟进制单位 为 10
     */
    private static final int MINUTE_RATE = 10;

    public MinuteWheelAdapter() {
        super();
    }

    public MinuteWheelAdapter(int minValue, int maxValue) {
        super(minValue, maxValue);
    }

    @Override
    public String getShowContent(int index) {
        return (dateList.get(index) * MINUTE_RATE) + "分";
    }

    /**
     * 进制单位为10分钟的 得到的时间 * MINUTE_RATE
     * @param index
     * @return
     */
    public int getItemRealShowTime(int index) {
        return getItemReal(index) * MINUTE_RATE;
    }
}
