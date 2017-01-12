package cn.netkiller.pojo;

import java.util.List;

public class Deploy {

	private String group;
	private String project;
	private String branch;
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
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
		return "Deploy [group=" + group + ", project=" + project + ", branch=" + branch + ", arguments=" + arguments + "]";
	}
}
