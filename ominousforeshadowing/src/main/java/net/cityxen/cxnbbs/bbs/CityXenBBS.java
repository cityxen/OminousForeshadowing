package net.cityxen.cxnbbs.bbs;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

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

			ReadPETmateJSON("ominousforeshadowing/resources/art/cityxen-logo-1.json");
			sleep(5000);
			ReadPETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing3.json");
			sleep(5000);
			ReadPETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing2.json");
			sleep(5000);			
			ReadPETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing1.json");
			sleep(5000);
			



		}

		private void logo() throws Exception {
			write(Keys.CLR, Keys.UPPERCASE, Keys.CASE_LOCK);
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

			// This was very difficult to figure out
			// Note: when doing art work, must take into account that the bottom right character needs to be
			// black so that all characters will fit onto the screen
			// It does not draw the last character otherwise it scrolls off the top portion

			HashMap<Integer,Integer> codehash =new HashMap<Integer,Integer>();

			for(int i = 0; i < 32; i++)    { codehash.put(i,i+64);  }
			for(int i = 64; i < 96; i++)   { codehash.put(i,i+128); }
			for(int i = 96; i < 128; i++)  { codehash.put(i,i+64);  }			
			for(int i = 128; i < 160; i++) { codehash.put(i,i-64);  }
			for(int i = 160; i < 192; i++) { codehash.put(i,i-128); }
			for(int i = 224; i < 256; i++) { codehash.put(i,i-64);  }

			HashMap<Integer,Integer> colorhash=new HashMap<Integer,Integer>();

			colorhash.put(0,Colors.BLACK);
			colorhash.put(1,Colors.WHITE);
			colorhash.put(2,Colors.RED);
			colorhash.put(3,Colors.CYAN);
			colorhash.put(4,Colors.PURPLE);
			colorhash.put(5,Colors.GREEN);
			colorhash.put(6,Colors.BLUE);
			colorhash.put(7,Colors.YELLOW);
			colorhash.put(8,Colors.ORANGE);
			colorhash.put(9,Colors.BROWN);			
			colorhash.put(10,Colors.LIGHT_RED);
			colorhash.put(11,Colors.GREY1);
			colorhash.put(12,Colors.GREY2);
			colorhash.put(13,Colors.LIGHT_GREEN);
			colorhash.put(14,Colors.LIGHT_BLUE);
			colorhash.put(15,Colors.GREY3);

			Object jpars = new JSONParser().parse(new FileReader(File));
			JSONObject json = (JSONObject) jpars;
			JSONArray framebufs = (JSONArray) json.get("framebufs");
			JSONArray screencodes = (JSONArray) ((JSONObject) framebufs.get(0)).get("screencodes");
			JSONArray colors = (JSONArray) ((JSONObject) framebufs.get(0)).get("colors");

			write(Keys.CLR, Keys.UPPERCASE, Keys.CASE_LOCK);

			Integer outcolor;
			Integer outcode;
			Integer compcode;

			for (int i = 0; i < screencodes.size()-1 ; i++) {
				outcolor = colorhash.get((int) (long) colors.get(i));
				write(outcolor);
				outcode  = (int)(long)screencodes.get(i);
				if(outcode>127) write(Keys.REVON);
				else write(Keys.REVOFF);
				compcode = codehash.get(outcode);
				if(compcode!=null) {
					outcode=compcode;
				}
				write(outcode);
			}
			write(Keys.HOME);
		};

}

