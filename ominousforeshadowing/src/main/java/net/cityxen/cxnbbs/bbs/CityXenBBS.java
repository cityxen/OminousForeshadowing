package net.cityxen.cxnbbs.bbs;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
// import java.util.LinkedHashMap; 
// import java.util.Map; 
// import java.util.Iterator;

import eu.sblendorio.bbs.core.Colors;
import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.dao.SecurityRepository;
import net.cityxen.cxnbbs.domain.Security;
import net.cityxen.cxnbbs.util.SpringContext;

public class CityXenBBS extends PetsciiThread {

	SecurityRepository securityRepository = SpringContext.getBean(SecurityRepository.class);
	
	public CityXenBBS() {}

	    @Override
	    public void doLoop() throws Exception {
	    	write(Keys.CLR);
	    	print("Hello CXN World\r");
			write(Colors.RED);
			print("Ominous Foreshadowing\r");
			write(Colors.GREEN);
			print("Secuirty Groups\r");
			List<Security> groups = securityRepository.findAll();
			for (Security security : groups) {
				print(security.getName()+"\r");
			}

			ReadPETmateJSON("resources/art/ominousforeshadowing1.json");

		}

		private void logo() throws Exception {
			write(Keys.CLR, Keys.LOWERCASE, Keys.CASE_LOCK);
			write(LOGO);
			
			write(Colors.GREY3); gotoXY(0,5);
		}
		private final static byte[] LOGO = {
			32, 32, 28, -95, 32, 32, -41, 69, 76, 67, 79, 77, 69, 32, 84, 79,
			32, -95, 32, 32, 32, 18, -95, -110, 13, 32, 18, -95, -95, -65, -110, 32,
			-69, -94, 32, -84, 32, -84, 32, -84, -69, 32, -84, -66, 32, -84, -69, -95,
			32, 32, 32, (byte) Colors.CYAN, -56, -49, -51, -59, -96, -49, -58, -96, -44, -56, -59, 13,
			32, 18, 28, -68, -65, -110, -69, 32, 18, -84, -110, 32, 32, -95, -95, 18,
			-65, -110, -84, -66, 18, -95, -110, 32, 18, -65, -110, 32, -84, -66, 18, -95,
			-110, 32, (byte) Colors.CYAN, -61, -49, -51, -51, -49, -60, -49, -46, -59, 32, 54, 52, 32,
			-57, -63, -51, -59, -45, 13, 18, 28, -95, -110, 32, 32, -95, 18, -95, -110,
			32, 32, 18, -95, -110, 32, -65, -66, -68, -94, -66, 32, -65, -66, -68, -94,
			-66, 18, -68, -110, 13
		};


		private void ReadPETmateJSON(String File) throws Exception {
			/*
			 petmate json format:
			 {
				framebufs: [{
					screencodes: [1,2,3,etc...]
					colors: [1,2,3,4,etc]
				}
				]
			}
			*/
			Object jpars = new JSONParser().parse(new FileReader(File));
			JSONObject json = (JSONObject) jpars;
			JSONArray framebufs = (JSONArray) json.get("framebufs");
			System.out.println(((JSONObject) framebufs.get(0)).get("screencodes"));
			System.out.println(((JSONObject) framebufs.get(0)).get("colors"));
			//System.out.println(screencodes);
			//  print(jscn.toString());
			// "screencodes"
			// String screencodes = json.getJSONObject("framebufs").getString("screencodes");
			//for (int i = 0; i < jarr.size(); i++) {
    			// JSONObject code = jarr.getJSONObject(i); 
				// println(id + ", " + species + ", " + name);
			//}
			// String screencodes = arr.getJSONObject(0).ge
			// write(jarr.toString());
		};
}

