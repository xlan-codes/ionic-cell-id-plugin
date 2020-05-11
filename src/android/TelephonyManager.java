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
import android.content.Context;

/**
 * This class echoes a string called from JavaScript.
 */
public class TelephonyManager extends CordovaPlugin {

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

    @SuppressLint("MissingPermission")
    @SuppressWarnings("NewApi")
    private String getCellId(JSONArray args, CallbackContext callbackContext) {


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        List<CellInfo> cellList = telephonyManager.getAllCellInfo();

        if (cellList != null) {
            for (final CellInfo cell : cellList) {

                final CellLocationClient.CellTower cellTower = new CellLocationClient.CellTower();

                if (cell instanceof CellInfoGsm) {
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cell;
                    cellTower.setRadioType("gsm");
                    cellTower.setMobileCountryCode(cellInfoGsm.getCellIdentity().getMcc());
                    cellTower.setMobileNetworkCode(cellInfoGsm.getCellIdentity().getMnc());
                    cellTower.setLocationAreaCode(cellInfoGsm.getCellIdentity().getLac());
                    cellTower.setCellId(cellInfoGsm.getCellIdentity().getCid());
                } else if (cell instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cell;
                    cellTower.setRadioType("lte");
                    cellTower.setMobileCountryCode(cellInfoLte.getCellIdentity().getMcc());
                    cellTower.setMobileNetworkCode(cellInfoLte.getCellIdentity().getMnc());
                    cellTower.setLocationAreaCode(cellInfoLte.getCellIdentity().getTac());
                    cellTower.setCellId(cellInfoLte.getCellIdentity().getCi());
                } else if (cell instanceof CellInfoWcdma) {
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cell;
                    cellTower.setRadioType("wcdma");
                    cellTower.setMobileCountryCode(cellInfoWcdma.getCellIdentity().getMcc());
                    cellTower.setMobileNetworkCode(cellInfoWcdma.getCellIdentity().getMnc());
                    cellTower.setLocationAreaCode(cellInfoWcdma.getCellIdentity().getLac());
                    cellTower.setCellId(cellInfoWcdma.getCellIdentity().getCid());
                }

                if (cellTower.getCellId() != 0 && cellTower.getCellId() != Integer.MAX_VALUE && !cells.containsKey(cellTower.getCellId())) {
                    callbackContext.success(""+cellTower.getCellId());
                    return cellTower.getCellId();
                    // cellLocationClient.getCellLocation(cellTower, new CellLocationClient.CellLocationCallback() {
                    //     @Override
                    //     public void onSuccess(final double lat, final double lon) {
                    //         runOnUiThread(new Runnable() {
                    //             @Override
                    //             public void run() {
                    //                 System.out.println()
                    //                 // addCellTower(
                    //                 //         cellTower.getMobileCountryCode(), cellTower.getMobileNetworkCode(),
                    //                 //         cellTower.getLocationAreaCode(), cellTower.getCellId(), lat, lon);
                    //             }
                    //         });
                    //     }

                    //     @Override
                    //     public void onFailure() {
                    //     }
                    // });
                }

            }
        }

        // final TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
        //     final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
        //     if (location != null) {
        //         msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
        //     }
        // }
    }
}
