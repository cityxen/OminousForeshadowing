package net.cityxen.cxnbbs.bbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.sblendorio.bbs.core.Colors;
import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.dao.MenuRepository;
import net.cityxen.cxnbbs.domain.Menu;
import net.cityxen.cxnbbs.domain.MenuItem;
import net.cityxen.cxnbbs.domain.User;
import net.cityxen.cxnbbs.util.RenderPETMate;
import net.cityxen.cxnbbs.util.SpringContext;


public abstract class Module {
	MenuRepository menuRepository = SpringContext.getBean(MenuRepository.class);
	private final static Logger LOGGER = LoggerFactory.getLogger(Module.class);
	PetsciiThread pst;
	RenderPETMate render;
	
	private static final String LOGOUT = "logout";
	
	public Module(PetsciiThread pst) {
		this.pst = pst;
	}
	
	public void startModule (User user, String action, Boolean isShortMenu) throws Exception{
		displayMenu(action,user,isShortMenu);
		String nextAction = readInput(action, user);
		if(null!=nextAction) {
			//Todo nextaction
			LOGGER.info("Next Action: " + nextAction);
			ActionProcessor ap = new ActionProcessor(pst);
			ap.startModule(user, nextAction, isShortMenu);
		}else {
			//Todo display error for invalid input
		}
 	}

	
	private String readInput( String action, User user) throws Exception {
		String input = pst.readLine(1);
		String actionOut = null;
		if(input.equalsIgnoreCase("?")) {
			startModule(user, action, false);
		}else if(input.equalsIgnoreCase("x")) {
			actionOut = LOGOUT;
		}else {
			
			//TODO refactor to check security against this
			Menu menu = menuRepository.mapAll().get(action);
			actionOut = menu.getActionByHotkey(input);
		}
		return actionOut;
	}


	private void displayMenu(String action, User user, Boolean isShortMenu) {

		Menu menu = menuRepository.mapAll().get(action);

		// TODO theme engine
		renderMenuTop(isShortMenu, menu.getDisplayName());
		if (isShortMenu) {
			pst.print(":[");
		}
		int count = 0;
		int target =menu.getMenuItems().size(); 

		for (MenuItem item : menu.getMenuItems()) {
			count+=1;
			if (user.getSecurityLevel() >= item.getSecurityLevel()) {
				//TODO fix delimiter when not rendering becuase of security level
				renderItem(isShortMenu, item.getHotkey(), item.getDisplayName(), count<target);
			}
		}
		
		if (isShortMenu) {
			pst.print(",");
			renderItem(isShortMenu, "?", "help", false);
		}
		if (menu.getHasLogoff()) {
			pst.print(",");
			renderItem(isShortMenu, "x", "logoff", false);
		}
		if (isShortMenu) {
			pst.print("]:");
		}else {
			pst.print("\r choice: ");
		}

	}
	
	private void renderItem(Boolean isShortMenu, String hotkey, String displayName, Boolean hasNext) {
		int indent = 1;
		if(isShortMenu) {
			pst.write(Keys.REVON, Colors.WHITE);
			pst.print(hotkey.toLowerCase());
			pst.write(Keys.REVOFF, Colors.GREEN);
			if(hasNext) {
				pst.print(",");	
			}
		}else {
			pst.write(Keys.REVON, Colors.WHITE); 
	        pst.print("\r"+" ".repeat(indent) +hotkey.toLowerCase() +" ".repeat(indent));
			pst.write(Keys.REVOFF, Colors.GREEN);
			pst.print(displayName.toLowerCase());
		}
	}
	private void renderMenuTop(Boolean isShortMenu, String displayName) {
		int menuspace = 3;
		if(isShortMenu) {
			pst.write(Keys.UPPERCASE, Keys.CASE_LOCK);
			pst.gotoXY(0,24);
			pst.write(Keys.REVOFF, Colors.WHITE);
			pst.print(displayName.toLowerCase()); 
			pst.write(Keys.REVOFF, Colors.GREEN);
		}else {
			pst.cls();
			pst.write(Keys.UPPERCASE, Keys.CASE_LOCK);
			pst.write(Keys.REVOFF, Colors.WHITE);
			pst.print("\r" + " ".repeat(menuspace) +displayName.toLowerCase() +":"); 
			pst.print("\r" + " ".repeat(menuspace) +"-".repeat(displayName.length()+1));
			pst.write(Keys.REVOFF, Colors.GREEN);
		}
		
	}
		
}
