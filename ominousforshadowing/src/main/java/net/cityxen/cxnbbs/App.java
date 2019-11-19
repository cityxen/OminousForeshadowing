package net.cityxen.cxnbbs;

import eu.sblendorio.bbs.core.BBServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	new Thread(() -> {
			try {
				BBServer.main(new String[] { "-b", "CityXenBBS" , "-p", "6510"});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
    }
    
}
