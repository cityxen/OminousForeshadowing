package net.cityxen.cxnbbs.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItem {

	private String displayName;
	private String hotkey;
	private String action;
	private int securityLevel;

}
