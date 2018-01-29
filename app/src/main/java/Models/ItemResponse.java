package Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gandhi on 18-Jan-17.
 */

public class ItemResponse
{
    @SerializedName("item_info")
    public ArrayList<Item> item_info;

    public ArrayList<Item> getItem_info() {
        return item_info;
    }

    public void setItem_info(ArrayList<Item> item_info) {
        this.item_info = item_info;
    }
}
