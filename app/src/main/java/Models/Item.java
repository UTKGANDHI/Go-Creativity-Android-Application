package Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gandhi on 18-Jan-17.
 */

public class Item
{
    @SerializedName("prod_id")
    public String prod_id;

    @SerializedName("prod_name")
    public String prod_name;

    public String getId() {
        return prod_id;
    }

    public void setId(String prod_id) {
        this.prod_id = prod_id;
    }

    public String prod_name() {
        return prod_name;
    }

    public void setItem_name(String prod_name) {
        this.prod_name = prod_name;
    }
}
