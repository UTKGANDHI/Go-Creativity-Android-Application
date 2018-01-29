package Models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Gandhi on 17-Jan-17.
 */

public class Util1
{
    public static final String HOST = "http://creativityportal.tallyvm.co.in/api/code/main/frmApi_creativity.php?api=";
    public static final String GET_ALLCATEGORY_EPIC = HOST + "getAllCategory_epic";// url for all items
    public static final String GET_VERSATILELOGO_API = HOST + "getItemVersatileLogo";
    public static final String CANCEL = HOST + "cancelOrder";
    public static final String GET_SUBLOGOLIST_API=    HOST + "getSubLogoList";
    public static final String GET_SUBVIDEOLIST_API=   HOST + "getSubVideoList";
    public static final String GET_EXPLAINERVIDEO_API= HOST + "getItemExplainerVideo";
    public static final String GET_RECOMMENDEDITEM_API=  HOST + "getRecommendedItem";
    public static final String GET_ITEMCART_API=  HOST + "getItemCart";
    public static final String GET_PAYITEM_API=  HOST + "ConfirmPayment";
    public static final String GET_DESCRIPTION = HOST + "getDescription";
    public static final String GET_ORDERS_API = HOST + "getOrders";
    public static final String REQUEST =     HOST + "requestChange";
    public static final String REQUEST_COMPLETE =           HOST + "requestChangeComplete";
    public static final String GET_MAP_API =           HOST + "untitled.html";
    public static final String GET_LOCATION_API =      HOST +"update_user_location.php?user_id=";
    public static final String PAYPAL_CLIENT_ID = "Abw9SXVAFn_055p51vGzSrvxQKa1nGvIdY18e7u5sQv5E3I0gok1n1d52N9pF5R4UJIo--iVHSejToCh";


    public static boolean haveNetworkConnection(Context mContext) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public static void showAlertDialog(Context context, String title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
