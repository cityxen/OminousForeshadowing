package net.cityxen.cxnbbs.bbs;

import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.bbs.module.Authenticate;
import net.cityxen.cxnbbs.dao.SecurityRepository;
import net.cityxen.cxnbbs.domain.User;
import net.cityxen.cxnbbs.util.SpringContext;

public class CityXenBBS extends PetsciiThread {

	private static final String VERSION = "1.0";
	SecurityRepository securityRepository = SpringContext.getBean(SecurityRepository.class);

	public CityXenBBS() {
	}

	@Override
	public void doLoop() throws Exception {
		write(Keys.CLR);
		User user = new User();
		Authenticate auth = new Authenticate(this);
		auth.startModule(user, "auth");
	}

	public static String getVersion() {
		return VERSION;
	}

}
