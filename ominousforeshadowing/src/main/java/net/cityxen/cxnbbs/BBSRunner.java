package net.cityxen.cxnbbs;

import eu.sblendorio.bbs.core.BBServer;

public class BBSRunner implements Runnable {

	public BBSRunner() {
	}

	@Override
	public void run() {
		while (true) {
			try {
				BBServer.main(new String[] { "-b", "CityXenBBS", "-p", "6510" });
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}