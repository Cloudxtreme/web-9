package cn.netkiller.pojo;

public class Redis {
	public String host = "127.0.0.1";
	public int db = 0;
	public String keys = "*";
	public Redis() {
		// TODO Auto-generated constructor stub
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getDb() {
		return db;
	}
	public void setDb(int db) {
		this.db = db;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	@Override
	public String toString() {
		return "Redis [host=" + host + ", db=" + db + ", keys=" + keys + "]";
	}

}
