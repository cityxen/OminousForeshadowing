package net.cityxen.cxnbbs.bbs;

import java.util.List;

import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.bbs.module.Authenticate;
import net.cityxen.cxnbbs.bbs.module.CoreBBS;
import net.cityxen.cxnbbs.dao.SecurityRepository;
import net.cityxen.cxnbbs.domain.Security;
import net.cityxen.cxnbbs.domain.User;
import net.cityxen.cxnbbs.util.SpringContext;

public class CityXenBBS extends PetsciiThread {

	private static final String VERSION ="1.0";
	SecurityRepository securityRepository = SpringContext.getBean(SecurityRepository.class);

	public CityXenBBS() {}

	@Override
	public void doLoop() throws Exception {

		//////////////////////////////////////////////////////////////////////////////
		// WELCOME SCREEN
		write(Keys.CLR);
	
		User user = new User();
		Authenticate auth = new Authenticate(this);
		auth.startModule(user, "auth");
		
		CoreBBS module = new CoreBBS(this);
		module.startModule(user, "main", true);
		
		
		
		
		
		
		
		
		
		
		
		
	
		final List<Security> groups = securityRepository.findAll();
		for (final Security security : groups) {
			print(security.getName() + "\r");
		}


		//////////////////////////////////////////////////////////////////////////////
		// INTRO SEQUENCE (ie; NEWS, MESSAGES, STARTUP SCRIPTS, ETC)

		// NOTHING HERE YET

		//////////////////////////////////////////////////////////////////////////////
		// MAIN LOOP

		Boolean LoggedIn = true;
		while (LoggedIn) {

			// LOGOUT
			LoggedIn = false;
		}

		//////////////////////////////////////////////////////////////////////////////
		// LOGOUT SEQUENCE

		//PETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing1.json");
		sleep(5000);
		//PETmateJSON("ominousforeshadowing/resources/art/goodbye.json", 5, 19);
		sleep(5000);

	}

	public static String getVersion() {
		return VERSION;
	}


}

