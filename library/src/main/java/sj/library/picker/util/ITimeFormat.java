package sj.library.picker.util;

import java.text.SimpleDateFormat;

/**
 * Created by sj on 2016/11/28.
 */

public class ITimeFormat {

    ThreadLocal<SimpleDateFormat> dateFormaterRoomLastMsgInToday = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };

    ThreadLocal<SimpleDateFormat> dateFormaterRoomLastMsgInNoYeardate = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };

    ThreadLocal<SimpleDateFormat> dateFormaterRoomLastMsgInAnydate = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yy-MM-dd");
        }
    };

    ThreadLocal<SimpleDateFormat> dateFormaterWhole = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        }
    };

    ThreadLocal<SimpleDateFormat> dateFormaterMonth = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd HH:mm");
        }
    };
}
