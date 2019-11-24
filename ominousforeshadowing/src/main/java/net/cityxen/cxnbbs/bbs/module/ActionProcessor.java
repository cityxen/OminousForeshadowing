package net.cityxen.cxnbbs.bbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.domain.User;
import net.cityxen.cxnbbs.util.RenderPETMate;

public class ActionProcessor extends Module{
	private static final Logger LOGGER = LoggerFactory.getLogger(Authenticate.class);
	
	public ActionProcessor(PetsciiThread pst) {
		super(pst);
		this.pst=pst;
		this.render = new RenderPETMate(pst);
	}

	@Override
	public void startModule (User user, String action, Boolean isShortMenu) throws Exception{
		pst.cls();
		switch (action) {
		case "main":
			CoreBBS module = new CoreBBS(pst);
			module.startModule(user, "main", true);
			break;
		case "logout":
	        //TODO move this to a logout processor
			render.drawFromFile("ominousforeshadowing/resources/art/ominousforeshadowing1.json");
	        Thread.sleep(5000);
	        render.drawFromFile("ominousforeshadowing/resources/art/goodbye.json", 5, 19);
	        Thread.sleep(5000);
			LOGGER.info("Logging Out");
			break;
		case "admin":
			LOGGER.info("Admin module");
			break;
		default:
			break;
		}
		
 	}
	

}
