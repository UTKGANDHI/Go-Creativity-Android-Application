package com.example.ashutosh.datasource;

import java.util.Date;

/**
 * Created by Admin on 12/20/2016.
 */

public class DrawerChildModel {
    private Date mDate;
    private  String mstr;
    private boolean mSolved;
    public DrawerChildModel()
    {}

    public DrawerChildModel(Date date, boolean solved) {
        mDate = date;
        mSolved = solved;
    }

    public String getMstr() {
        return mstr;
    }

    public void setMstr(String mstr) {
        this.mstr = mstr;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    /**

     * Getter and setter methods
     */
}
