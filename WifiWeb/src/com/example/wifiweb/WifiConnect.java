package com.example.wifiweb;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiConnect {

	WifiManager wifiManager;

	// ���弸�ּ��ܷ�ʽ��һ����WEP��һ����WPA������û����������
	public enum WifiCipherType {
		WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
	}

	// ���캯��
	public WifiConnect(Context context) {
		this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}

	// �ṩһ���ⲿ�ӿڣ�����Ҫ���ӵ�������
	public boolean Connect(String SSID, String Password, WifiCipherType Type) {
		if (!this.OpenWifi()) {
			return false;
		}
		
		// ����wifi������Ҫһ��ʱ��(�����ֻ��ϲ���һ����Ҫ1-3������)������Ҫ�ȵ�wifi
		// ״̬���WIFI_STATE_ENABLED��ʱ�����ִ����������
		while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				// Ϊ�˱������һֱwhileѭ��������˯��100�����ڼ�⡭��
				Thread.currentThread();
				Thread.sleep(300);
			} catch (InterruptedException ie) {
			}
		}
		
		boolean bRet = false;
				
		WifiConfiguration tempConfig = this.IsExsits(SSID);
		
		Log.d("yanjun","tempConfig="+tempConfig);
		
		if(tempConfig != null){
			bRet = wifiManager.enableNetwork(tempConfig.networkId, true);
		}

		Log.d("yanjun","temp enable ="+bRet);
			
		if(bRet){
			return bRet;
		}else{
			if(tempConfig != null){
				wifiManager.removeNetwork(tempConfig.networkId);
			}
			
			WifiConfiguration wifiConfig = this.CreateWifiInfo(SSID, Password, Type);

			if (wifiConfig == null) {
				return false;
			}
			
			int netID = wifiManager.addNetwork(wifiConfig);
			
			Log.d("yanjun","new netID="+netID);
			
			bRet = wifiManager.enableNetwork(netID, true);
		}
		return bRet;
	}
	
	public static boolean isWifiNetworkConnected(Context context) { 
		
		ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		
		if (connectMgr == null) { 
			return false; 
		} else { 
			NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
			if(wifiNetInfo.isConnected()){
				return true;
			}
		}
		
		return false; 
	} 

	
	public boolean isConnectedToSsid(String ssid){
		if(!wifiManager.isWifiEnabled()){
			return false;
		}
		
		String curSSid = wifiManager.getConnectionInfo().getSSID();

		Log.d("yanjun", "curSSid="+curSSid + " ss="+ssid);
		
		if(curSSid != null && curSSid.contains(ssid)){
			return true;
		}else{
			return false;
		}
	}

	// ��wifi����
	public boolean OpenWifi() {
		boolean bRet = true;
		if (!wifiManager.isWifiEnabled()) {
			bRet = wifiManager.setWifiEnabled(true);
		}
		return bRet;
	}
	
	// �ر�wifi
	public boolean CloseWifi() {
		boolean bRet = true;
		if (wifiManager.isWifiEnabled()) {
			bRet = wifiManager.setWifiEnabled(false);
		}
		return bRet;
	}
	

	// �鿴��ǰ�Ƿ�Ҳ���ù��������
	private  WifiConfiguration IsExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = wifiManager
				.getConfiguredNetworks();
		
		if(existingConfigs == null){
			return null;
		}
		
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}

	private WifiConfiguration CreateWifiInfo(String SSID, String Password,
			WifiCipherType Type) {
		
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		
		if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiCipherType.WIFICIPHER_WEP) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiCipherType.WIFICIPHER_WPA) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			
			config.status = WifiConfiguration.Status.ENABLED;
		} else {
			return null;
		}
		return config;
	}

}
