package net.cityxen.cxnbbs.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {

	@Id
	private String id;
	private String username;
	private String title;
	private int securityLevel;
	private String email;
	private String password;

}
