package net.cityxen.cxnbbs.util;

import java.io.FileReader;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import eu.sblendorio.bbs.core.Colors;
import eu.sblendorio.bbs.core.Keys;
import eu.sblendorio.bbs.core.PetsciiThread;

public class RenderPETMate {

	PetsciiThread pst;
	
	public RenderPETMate(PetsciiThread pst) {
		this.pst = pst;
	}
	
	public void drawFromFile(final String File) throws Exception {
		drawFromFile(File, 0, 0);
	};

	public void drawFromFile(final String File, final Integer XLoc, final Integer YLoc) throws Exception {

		pst.gotoXY(XLoc, YLoc);

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
		// System.out.println("\nFile: "+File+" (CHARSET: ["+charset+"] WIDTH:
		// ["+width+"] HEIGHT: ["+height+"])");
		if (charset.equals((String) "upper")) {
			pst.write(Keys.UPPERCASE, Keys.CASE_LOCK);
		} else {
			pst.write(Keys.LOWERCASE, Keys.CASE_LOCK);
		}
		Integer outcolor;
		Integer lastcolor = null;
        Boolean reverseStatus = false;
		Integer outcode;
		Integer compcode;
		int linecounter = 0;
		int linecounter2 = 0;
		int heightcounter = 0;
		int DrawSize = screencodes.size();
		for (int i = 0; i < DrawSize; i++) {
			if(width<40) {
				if(linecounter==width) {
					pst.write(Keys.DOWN);
					for(int zz=0;zz<width;zz++) {
						pst.write(Keys.LEFT);
					}
					linecounter=0;
				}
			}
			outcolor = colorhash.get((int) (long) colors.get(i));
			if(lastcolor!=outcolor) {
				pst.write(outcolor);
				lastcolor=outcolor;
			}
			outcode  = (int)(long)screencodes.get(i);

            if(outcode>127) {
                if(reverseStatus == false) {
                	pst.write(Keys.REVON);
                    reverseStatus = true;
                }
            }
            else {
                if(reverseStatus == true) {
                	pst.write(Keys.REVOFF);
                    reverseStatus = false;
                }
            }
			compcode = codehash.get(outcode);
			if(compcode!=null) {
				outcode=compcode;
			}
			pst.write(outcode);
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
