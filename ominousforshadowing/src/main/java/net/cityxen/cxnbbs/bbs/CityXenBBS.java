package net.cityxen.cxnbbs.bbs;

import eu.sblendorio.bbs.core.PetsciiThread;

public class CityXenBBS extends PetsciiThread {

	    public CityXenBBS() {}

	    @Override
	    public void doLoop() throws Exception {
	    	  print("Hello CXN World");
	    }

}
