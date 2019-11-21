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
			write(Colors.RED);
			print("Welcome to Ominous Foreshadowing...\r");
			sleep(3000);

			write(Colors.GREEN);
			print("Security Groups\r");
			List<Security> groups = securityRepository.findAll();
			for (Security security : groups) {
				print(security.getName()+"\r");
			}
			sleep(2000);

			ReadPETmateJSON("ominousforeshadowing/resources/art/cityxen-logo.json");
			sleep(5000);

			ReadPETmateJSON("ominousforeshadowing/resources/art/cityxen-logo-1.json");
			sleep(5000);

			ReadPETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing3.json");
			sleep(5000);

			ReadPETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing2.json");
			sleep(5000);

			ReadPETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing1.json");
			sleep(5000);

			write(Keys.HOME);
			ReadPETmateJSON("ominousforeshadowing/resources/art/10x10-test.json");
			sleep(5000);

		}

		private void ReadPETmateJSON(String File) throws Exception {

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
			String charset = (String) ((JSONObject)framebufs.get(0)).get("charset");
			Long width  = (Long) ((JSONObject)framebufs.get(0)).get("width");
			Long height = (Long) ((JSONObject)framebufs.get(0)).get("height");

			// System.out.println("\nFile: "+File+" (CHARSET: ["+charset+"] WIDTH: ["+width+"] HEIGHT: ["+height+"])");

			if(charset.equals((String)"upper")) {
				// System.out.println(" \n WRITING UPPERCASE \n");
				write(Keys.UPPERCASE,Keys.CASE_LOCK);
			}
			else {
				// System.out.println(" \n WRITING LOWERCASE \n");
				write(Keys.LOWERCASE,Keys.CASE_LOCK);
			}

			Integer outcolor;
			Integer lastcolor=null;
			Integer outcode;
			Integer compcode;
			int linecounter=0;
			int linecounter2=0;
			int heightcounter=0;
			int DrawSize=screencodes.size();
			// if(width==40) { DrawSize--; }

			for (int i = 0; i < DrawSize; i++) {
				if(width<40) {
					if(linecounter==width) {
						write(Keys.RETURN);
						linecounter=0;
					}
				}
				outcolor = colorhash.get((int) (long) colors.get(i));
				if(lastcolor!=outcolor) {
					write(outcolor);
					lastcolor=outcolor;
				}
				outcode  = (int)(long)screencodes.get(i);
				if(outcode>127) write(Keys.REVON);
				else write(Keys.REVOFF);
				compcode = codehash.get(outcode);
				if(compcode!=null) {
					outcode=compcode;
				}
				write(outcode);

				linecounter++;
				linecounter2++;
				if(linecounter2 == width) {
					linecounter2=0;
					heightcounter++;
					if(heightcounter>height) {
						break;
					}
				}
			}
		};

}

