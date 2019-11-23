package net.cityxen.cxnbbs.bbs;

import eu.sblendorio.bbs.core.Colors;
import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;
import net.cityxen.cxnbbs.dao.SecurityRepository;
import net.cityxen.cxnbbs.domain.Security;
import net.cityxen.cxnbbs.util.SpringContext;
import java.util.List;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class CityXenBBS extends PetsciiThread {

	String version="1.0";

	SecurityRepository securityRepository = SpringContext.getBean(SecurityRepository.class);

	public CityXenBBS() {}

	@Override
	public void doLoop() throws Exception {

		//////////////////////////////////////////////////////////////////////////////
		// WELCOME SCREEN

		write(Keys.CLR);
		PETmateJSON("ominousforeshadowing/resources/art/cityxen-logo.json");
		write(Colors.GREEN);
		print("\rwelcome to ominous foreshadowing v"+version+"...\r");
		sleep(3000);		
		print("initializing...\r");
		sleep(1500);
		print("\rsecurity groups:\r");
		sleep(500);
		final List<Security> groups = securityRepository.findAll();
		for (final Security security : groups) {
			print(security.getName() + "\r");
		}
		sleep(2000);
		print("\rinit filesystem...\r");
		sleep(500);
		print("2tb filesystem: online\r");
		sleep(500);
		print("\rfinished initializing...\r");
		sleep(1500);

		//////////////////////////////////////////////////////////////////////////////
		// LOGIN

		if (DoLogin(3) == false) {
			write(Keys.CLR);
			print("too many invalid login attempts...");
			// add invalid login artwork screen to show here
			PETmateJSON("ominousforeshadowing/resources/art/goodbye.json", 5, 2);
			print("\r");
			return;
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

		PETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing1.json");
		sleep(5000);
		PETmateJSON("ominousforeshadowing/resources/art/goodbye.json", 5, 19);
		sleep(5000);

	}

	private Boolean DoLogin(final Integer NumTries) throws Exception {
		// LOGIN Screen
		for (int i = 0; i < NumTries; i++) {
			write(Keys.CLR);
			PETmateJSON("ominousforeshadowing/resources/art/ominousforeshadowing3.json");
			gotoXY(22, 14);
			write(Colors.RED);
			final String username = readLine(16);
			gotoXY(22, 15);
			final String password = readPassword();
			System.out.println("LOGIN: USER: " + username + " PASS: " + password);
			if (username.equals("new")) {
				return true;
			}
			// do further security tests here
		}
		return false;
	};

	private void PETmateJSON(final String File) throws Exception { PETmateJSON(File, 0, 0); };
	private void PETmateJSON(final String File, final Integer XLoc, final Integer YLoc) throws Exception {

		gotoXY(XLoc, YLoc);

		final HashMap<Integer, Integer> codehash = new HashMap<Integer, Integer>();

		for (int i = 0; i < 32; i++)    { codehash.put(i, i + 64); }
		for (int i = 64; i < 96; i++)   { codehash.put(i, i + 128); }
		for (int i = 96; i < 128; i++)  { codehash.put(i, i + 64); }
		for (int i = 128; i < 160; i++) { codehash.put(i, i - 64); }
		for (int i = 160; i < 192; i++) { codehash.put(i, i - 128); }
		for (int i = 224; i < 256; i++) { codehash.put(i, i - 64); }

		final HashMap<Integer, Integer> colorhash = new HashMap<Integer, Integer>();

		colorhash.put(0, Colors.BLACK);
		colorhash.put(1, Colors.WHITE);
		colorhash.put(2, Colors.RED);
		colorhash.put(3, Colors.CYAN);
		colorhash.put(4, Colors.PURPLE);
		colorhash.put(5, Colors.GREEN);
		colorhash.put(6, Colors.BLUE);
		colorhash.put(7, Colors.YELLOW);
		colorhash.put(8, Colors.ORANGE);
		colorhash.put(9, Colors.BROWN);
		colorhash.put(10, Colors.LIGHT_RED);
		colorhash.put(11, Colors.GREY1);
		colorhash.put(12, Colors.GREY2);
		colorhash.put(13, Colors.LIGHT_GREEN);
		colorhash.put(14, Colors.LIGHT_BLUE);
		colorhash.put(15, Colors.GREY3);

		final Object jpars = new JSONParser().parse(new FileReader(File));
		final JSONObject json = (JSONObject) jpars;
		final JSONArray framebufs = (JSONArray) json.get("framebufs");
		final JSONArray screencodes = (JSONArray) ((JSONObject) framebufs.get(0)).get("screencodes");
		final JSONArray colors = (JSONArray) ((JSONObject) framebufs.get(0)).get("colors");
		final String charset = (String) ((JSONObject) framebufs.get(0)).get("charset");
		final Long width = (Long) ((JSONObject) framebufs.get(0)).get("width");
		final Long height = (Long) ((JSONObject) framebufs.get(0)).get("height");

		if (charset.equals((String) "upper")) {
			write(Keys.UPPERCASE, Keys.CASE_LOCK);
		} else {
			write(Keys.LOWERCASE, Keys.CASE_LOCK);
		}

		Integer outcolor;
		Integer lastcolor = null;
		Boolean reverseStatus = false;
		Integer outcode;
		Integer compcode;
		int linecounter = 0;
		int linecounter2 = 0;
		int heightcounter = 0;
		Integer DrawSize = screencodes.size();

		for (int i = 0; i < DrawSize; i++) {
			if(width<40) {
				if(linecounter == width) {
					write(Keys.DOWN);
					for(int zz=0;zz<width;zz++) {
						write(Keys.LEFT);
					}
					linecounter=0;
				}
			}
			outcolor = colorhash.get((int) (long) colors.get(i));

			if(lastcolor!=outcolor) {
				write(outcolor);
				lastcolor=outcolor;
			}

			outcode  = (int)(long)screencodes.get(i);

			if(outcode>127) {
				if(reverseStatus == false) {
					write(Keys.REVON);
					reverseStatus = true;
				}
			}
			else {
				if(reverseStatus == true) {
					write(Keys.REVOFF);
					reverseStatus = false;
				}
			}

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

