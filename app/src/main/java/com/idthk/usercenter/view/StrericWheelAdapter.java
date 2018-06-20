package com.idthk.usercenter.view;


import java.util.ArrayList;
import java.util.List;

public class StrericWheelAdapter implements WheelAdapter {

    /**
     * The default min value
     */
    private List<WheelModel> mData = new ArrayList<>();

    /**
     * @param mData
     */
    public StrericWheelAdapter(List<WheelModel> mData) {
        this.mData = mData;
    }


    public List<WheelModel> getData() {
        return mData;
    }


    public void setData(List<WheelModel> mData) {
        this.mData = mData;
    }


    public String getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            return mData.get(index).getName();
        }
        return null;
    }

    public int getItemsCount() {
        return mData.size();
    }

    public int getMaximumLength() {
        int maxLen = 5;
        return maxLen;
    }
}
