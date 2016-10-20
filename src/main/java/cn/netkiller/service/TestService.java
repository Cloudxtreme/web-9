package cn.netkiller.service;

import java.util.List;

import cn.netkiller.pojo.Protocol;


public interface TestService {

	public String getName();
	public String toString();
	public String helloUser(String user);
	public List<Protocol> getProtocol();
}
