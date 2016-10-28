package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import com.micetweaks.resources.Assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * author Łukasz 's4bba7' Gąsiorowski
 */
class HotPlug {
	/**
	 * Detects plugged in devices and adds or removes them from Assets.DEVICES_LIST.
	 *
	 * @param removeFlag if device is plugged off it additionally remove that device from list.
	 */
	public void detectUsbDevices(boolean removeFlag) {
		String buff;
		Process p = xinputExecute();
		HashSet<String> devNameBuff = new HashSet<>();

		if (p != null) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

				while ((buff = in.readLine()) != null) {
					String[] dev = buff.split("\t");

					if (dev[2].contains("slave  pointer")) {
						String devName = dev[0].substring(6).trim();

						if (!devName.equalsIgnoreCase("Virtual core XTEST pointer")) {
							int id = Integer.parseInt(dev[1].substring(3));
							devNameBuff.add(devName);

							// If device exist in hashmap just add another devID into it.
							if (Assets.DEVICES_LIST.containsKey(devName)) {
								DeviceProps devID = Assets.DEVICES_LIST.get(devName);
								devID.addId(id);
							} else {
								DeviceProps devID = new DeviceProps(id);
								Assets.DEVICES_LIST.put(devName, devID);
							}
						}
						// When there's no more pointers just break.
					} else if (dev[2].contains("keyboard")) break;
				}

				// Check if any device is plugged off. If so remove it from list.
				if (removeFlag) {
					Iterator<Map.Entry<String, DeviceProps>> i = Assets.DEVICES_LIST.entrySet().iterator();
					for (; i.hasNext(); ) {
						Map.Entry<String, DeviceProps> e = i.next();
						if (!devNameBuff.contains(e.getKey())) i.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private Process xinputExecute() {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("xinput");
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return p;
	}

}
