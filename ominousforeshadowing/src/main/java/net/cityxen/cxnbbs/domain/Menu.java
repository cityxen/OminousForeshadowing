package net.cityxen.cxnbbs.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "menu")
public class Menu {
	
	@Id
	private String id;
	private String displayName;
	private String action;
	private Boolean hasLogoff;
	private Boolean hasReturnToMain;
	private List<MenuItem> menuItems;

	public String getActionByHotkey(String key) {
		String result = null;
		Map<String, String> menuItemsMap = this.menuItems.stream().collect(
	                Collectors.toMap(MenuItem::getHotkey, MenuItem::getAction));
		result = menuItemsMap.get(key);
		return result; 
	}
}
