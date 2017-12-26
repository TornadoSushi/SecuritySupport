/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.infinity.jerry.securitysupport.R;

import java.lang.reflect.Field;
/**
 * Created by root on 5/2/17.
 */

public class DoubleDatePickerDialog2 extends AlertDialog implements Dialog.OnClickListener, DatePicker.OnDateChangedListener {


    private static final String START_YEAR = "start_year";
    private static final String END_YEAR = "end_year";
    private static final String START_MONTH = "start_month";
    private static final String END_MONTH = "end_month";
    private static final String START_DAY = "start_day";
    private static final String END_DAY = "end_day";

    private final DatePicker mDatePicker_start;
    private final DatePicker mDatePicker_end;
    private final OnDateSetListener mCallBack;

    public interface OnDateSetListener {
        /**
         * 58          * @param view
         * 59          *            The view associated with this listener.
         * 60          * @param year
         * 61          *            The year that was set.
         * 62          * @param monthOfYear
         * 63          *            The month that was set (0-11) for compatibility with
         * 64          *            {@link java.util.Calendar}.
         * 65          * @param dayOfMonth
         * 66          *            The day of the month that was set.
         * 67
         */

        void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,
                       DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth);


    }

    /**
     * 73      * @param context
     * 74      *            The context the dialog is to run in.
     * 75      * @param callBack
     * 76      *            How the parent is notified that the date is set.
     * 77      * @param year
     * 78      *            The initial year of the dialog.
     * 79      * @param monthOfYear
     * 80      *            The initial month of the dialog.
     * 81      * @param dayOfMonth
     * 82      *            The initial day of the dialog.
     * 83
     */


    public DoubleDatePickerDialog2(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth);

    }


    public DoubleDatePickerDialog2(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                                   int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth, true);

    }

    /**
     * 94      * @param context
     * 95      *            The context the dialog is to run in.
     * 96      * @param theme
     * 97      *            the theme to apply to this dialog
     * 98      * @param callBack
     * 99      *            How the parent is notified that the date is set.
     * 100      * @param year
     * 101      *            The initial year of the dialog.
     * 102      * @param monthOfYear
     * 103      *            The initial month of the dialog.
     * 104      * @param dayOfMonth
     * 105      *            The initial day of the dialog.
     * 106
     */

    public DoubleDatePickerDialog2(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                                   int dayOfMonth, boolean isDayVisible) {
        super(context, theme);

        mCallBack = callBack;

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        // setButton(BUTTON_POSITIVE,
        // themeContext.getText(android.R.string.date_time_done), this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker_dialog2, null);
        setView(view);
        mDatePicker_start = (DatePicker) view.findViewById(R.id.datePickerStart);
        mDatePicker_end = (DatePicker) view.findViewById(R.id.datePickerEnd);
        mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
        mDatePicker_end.init(year, monthOfYear, dayOfMonth, this);
        // updateTitle(year, monthOfYear, dayOfMonth);

        // 如果要隐藏当前日期，则使用下面方法。
        if (!isDayVisible) {
            hidDay(mDatePicker_start);
            hidDay(mDatePicker_end);

        }

    }

    /**
     * 137      * 隐藏DatePicker中的日期显示
     * 138      *
     * 139      * @param mDatePicker
     * 140
     */
    private void hidDay(DatePicker mDatePicker) {
        Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
        for (Field datePickerField : datePickerfFields) {
            if ("mDaySpinner".equals(datePickerField.getName())) {
                datePickerField.setAccessible(true);
                Object dayPicker = new Object();
                try {
                    dayPicker = datePickerField.get(mDatePicker);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();

                }
                // datePicker.getCalendarView().setVisibility(View.GONE);
                ((View) dayPicker).setVisibility(View.GONE);

            }

        }

    }


    public void onClick(DialogInterface dialog, int which) {
        // Log.d(this.getClass().getSimpleName(), String.format("which:%d",
        // which));
        // 如果是“取 消”按钮，则返回，如果是“确 定”按钮，则往下执行
        if (which == BUTTON_POSITIVE)
            tryNotifyDateSet();

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.datePickerStart)
            mDatePicker_start.init(year, month, day, this);
        if (view.getId() == R.id.datePickerEnd)
            mDatePicker_end.init(year, month, day, this);
        // updateTitle(year, month, day);

    }

    /**
     * 178      * 获得开始日期的DatePicker
     * 179      *
     * 180      * @return The calendar view.
     * 181
     */


    public DatePicker getDatePickerStart() {
        return mDatePicker_start;

    }

    /**
     * 187      * 获得结束日期的DatePicker
     * 188      *
     * 189      * @return The calendar view.
     * 190
     */


    public DatePicker getDatePickerEnd() {
        return mDatePicker_end;

    }

    public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);

    }

    public void updateEndDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_end.updateDate(year, monthOfYear, dayOfMonth);

    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePicker_start.clearFocus();
            mDatePicker_end.clearFocus();

            mCallBack.onDateSet(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(),
                    mDatePicker_start.getDayOfMonth(), mDatePicker_end, mDatePicker_end.getYear(),
                    mDatePicker_end.getMonth(), mDatePicker_end.getDayOfMonth());

        }

    }

    @Override
    protected void onStop() {
        // tryNotifyDateSet();
        super.onStop();

    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_YEAR, mDatePicker_start.getYear());
        state.putInt(START_MONTH, mDatePicker_start.getMonth());
        state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
        state.putInt(END_YEAR, mDatePicker_end.getYear());
        state.putInt(END_MONTH, mDatePicker_end.getMonth());
        state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
        return state;

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_YEAR);
        int start_month = savedInstanceState.getInt(START_MONTH);
        int start_day = savedInstanceState.getInt(START_DAY);
        mDatePicker_start.init(start_year, start_month, start_day, this);

        int end_year = savedInstanceState.getInt(END_YEAR);
        int end_month = savedInstanceState.getInt(END_MONTH);
        int end_day = savedInstanceState.getInt(END_DAY);
        mDatePicker_end.init(end_year, end_month, end_day, this);


    }
}
