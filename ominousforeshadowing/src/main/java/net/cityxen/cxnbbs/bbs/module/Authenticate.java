package net.cityxen.cxnbbs.bbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.sblendorio.bbs.core.Colors;
import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.bbs.CityXenBBS;
import net.cityxen.cxnbbs.dao.UserRepository;
import net.cityxen.cxnbbs.domain.User;
import net.cityxen.cxnbbs.util.RenderPETMate;
import net.cityxen.cxnbbs.util.SpringContext;

public class Authenticate extends Module {

	UserRepository userRepository = SpringContext.getBean(UserRepository.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(Authenticate.class);
	private static final int LOGINATTEMPTS = 3; 
	PetsciiThread pst;
	RenderPETMate render;
	
	public Authenticate(PetsciiThread pst) {
		super(pst);
		this.pst=pst;
	}
	
	public void startModule(User user, String action) throws Exception {
		this.render = new RenderPETMate(pst);
		render.drawFromFile("ominousforeshadowing/resources/art/cityxen-logo.json");
		pst.write(Colors.GREEN);
		pst.print("\rwelcome to ominous foreshadowing v"+CityXenBBS.getVersion()+"..\r");
		Thread.sleep(3000);		
		pst.print("initializing...\r");
		Thread.sleep(2000);
		pst.print("\rinit filesystem...\r");
		Thread.sleep(500);
		pst.print("2tb filesystem: online\r");
		Thread.sleep(500);
		pst.print("\rfinished initializing...\r");
		Thread.sleep(1500);

		//////////////////////////////////////////////////////////////////////////////
		// LOGIN
		if (doLogin(LOGINATTEMPTS) == null) {
			pst.write(Keys.CLR);
			pst.print("too many invalid login attempts...");
			// add invalid login artwork screen to show here
			render.drawFromFile("ominousforeshadowing/resources/art/goodbye.json", 5, 2);
			pst.print("\r");
			return;
		}else {
			//TODO call main processor;
			LOGGER.debug("Calling main processor");
		}
		
	}
	private User doLogin(int NumTries) throws Exception {
		// LOGIN Screen
		for (int i = 0; i < NumTries; i++) {
			pst.write(Keys.CLR);
			render.drawFromFile("ominousforeshadowing/resources/art/ominousforeshadowing3.json");
			pst.gotoXY(22, 14);
			pst.write(Colors.RED);
			final String username = pst.readLine(16);
			if (username.equalsIgnoreCase("new")) {
				//TODO Call registartuion
				return null;
			}
			pst.gotoXY(22, 15);
			final String password = pst.readPassword();
			LOGGER.debug("Attempted LOGIN as : USER: " + username + " PASS: " + password);
			User user = userRepository.findByUsername(username);
			if(user!=null) {
				LOGGER.debug("User:"+user.toString());
				return user; 
			}
		}
		return null;
	};

	
	
}
