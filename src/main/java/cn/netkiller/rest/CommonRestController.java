package cn.netkiller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommonRestController {

	public String workspace = "/www/workspace";
	
	@RequestMapping("ping")
	@ResponseStatus(HttpStatus.OK)
	public String welcome() {
		return "PONG";
	}

	@RequestMapping("auth")
	@ResponseStatus(HttpStatus.OK)
	public String auth(@AuthenticationPrincipal final UserDetails user) {

		return String.format("%s: %s %s", user.getUsername(), user.getPassword(), user.getAuthorities());
	}
}
