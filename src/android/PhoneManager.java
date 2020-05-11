package cordova.plugin.telephonymanager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.telephony.CellLocation;
import android.content.Context;
import java.util.*;

/**
 * This class echoes a string called from JavaScript.
 */
public class PhoneManager extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCellId")) {
            this.getCellId(args, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    // @SuppressLint("MissingPermission")
    // @SuppressWarnings("NewApi")
    private String getCellId(JSONArray args, CallbackContext callbackContext) {


        TelephonyManager telephonyManager = (TelephonyManager) this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        List<CellInfo> cellList = telephonyManager.getAllCellInfo();

        if (cellList != null) {
            for (final CellInfo cell : cellList) {

                int cellId = 0;

                if (cell instanceof CellInfoGsm) {
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cell;
                    cellId = cellInfoGsm.getCellIdentity().getCid();
                } else if (cell instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cell;
                    cellId = cellInfoLte.getCellIdentity().getCi();
                } else if (cell instanceof CellInfoWcdma) {
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cell;
                    cellId = cellInfoWcdma.getCellIdentity().getCid();
                }

                callbackContext.success(""+cellId);
                return cellId+"";

            }
        }
        return "0";

        // final TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
        //     final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
        //     if (location != null) {
        //         msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
        //     }
        // }
    }
}
