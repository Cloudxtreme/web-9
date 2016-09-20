package cn.netkiller.pojo;

import java.util.List;

public class Deploy {

	private String group;
	private String envionment;
	private String project;
	private List<String> arguments;
	public Deploy() {
		// TODO Auto-generated constructor stub
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getEnvionment() {
		return envionment;
	}
	public void setEnvionment(String envionment) {
		this.envionment = envionment;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public List<String> getArguments() {
		return arguments;
	}
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}
	@Override
	public String toString() {
		return "Deploy [group=" + group + ", envionment=" + envionment + ", project=" + project + ", arguments=" + arguments + "]";
	}
	
}
