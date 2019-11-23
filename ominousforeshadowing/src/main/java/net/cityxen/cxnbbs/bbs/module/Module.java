package net.cityxen.cxnbbs.bbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.sblendorio.bbs.core.Colors;
import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.dao.MenuRepository;
import net.cityxen.cxnbbs.domain.Menu;
import net.cityxen.cxnbbs.domain.User;
import net.cityxen.cxnbbs.util.SpringContext;


public abstract class Module {
	MenuRepository menuRepository = SpringContext.getBean(MenuRepository.class);
	private final static Logger LOGGER = LoggerFactory.getLogger(Module.class);
	PetsciiThread pst;
	
	private static final String LOGOUT = "logout";
	
	public Module(PetsciiThread pst) {
		this.pst = pst;
	}
	
	public void startModule (User user, String action, Boolean isShortMenu) throws Exception{
		displayMenu(action,user,isShortMenu);
		String nextAction = readInput(action, user);
		if(null!=nextAction) {
			//Todo nextaction
			System.out.print("Next Action: " + nextAction);
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
			startModule(user, LOGOUT, true);
		}else {
			Menu menu = menuRepository.mapAll().get(action);
			actionOut = menu.getActionByHotkey(input);
		}
		return actionOut;
	}


	private void displayMenu(String action, User user, Boolean isShortMenu) {
		
		Menu menu = menuRepository.mapAll().get(action);
		int menuspace = 3;
		int indent = 1;
		//TODO check against user security to render individual menu items
		//TODO theme engine
		
		if(isShortMenu) {
			pst.write(Keys.UPPERCASE, Keys.CASE_LOCK);
			pst.gotoXY(0,24);
			pst.write(Keys.REVOFF, Colors.WHITE);
			pst.print(menu.getDisplayName().toLowerCase()); 
			pst.write(Keys.REVOFF, Colors.GREEN);
			pst.print(":[");
			menu.getMenuItems().forEach((item) -> {
				pst.write(Keys.REVON, Colors.WHITE);
				pst.print(item.getHotkey().toLowerCase());
				pst.write(Keys.REVOFF, Colors.GREEN);
				pst.print(",");
			});
			pst.write(Keys.REVON, Colors.WHITE);
			pst.print("?");
			pst.write(Keys.REVOFF, Colors.GREEN);
			if(menu.getHasLogoff()) {
				pst.print(",");
				pst.write(Keys.REVON, Colors.WHITE);
				pst.print("x");
				pst.write(Keys.REVOFF, Colors.GREEN);
			}
			pst.print("]: ");
		}else {
			pst.cls();
			pst.write(Keys.UPPERCASE, Keys.CASE_LOCK);
			pst.write(Keys.REVOFF, Colors.WHITE);
			pst.print("\r" + " ".repeat(menuspace) +menu.getDisplayName().toLowerCase() +":"); 
			pst.print("\r" + " ".repeat(menuspace) +"-".repeat(menu.getDisplayName().length()+1));
			pst.write(Keys.REVOFF, Colors.GREEN);
			menu.getMenuItems().forEach((item) -> {
				pst.write(Keys.REVON, Colors.WHITE); 
		        pst.print("\r" + " ".repeat(indent)+item.getHotkey().toLowerCase());
				pst.print(" ".repeat(indent));
				pst.write(Keys.REVOFF, Colors.GREEN);	
		        pst.print(item.getDisplayName().toLowerCase());
		});
			if(menu.getHasLogoff()) {
				pst.write(Keys.REVON, Colors.WHITE); 
		        pst.print("\r   x");
				pst.print(" ".repeat(indent));
				pst.write(Keys.REVOFF, Colors.GREEN);
				pst.print(" ".repeat(indent));
				pst.print("logout");
			}
		}
	}
		
}
