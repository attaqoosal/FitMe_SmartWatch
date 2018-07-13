package test.com.fitMe.data.model;

public class DataVO implements Comparable<DataVO>{
	
	public String gender;
	public int avg_walk;
	public String gen;
	public int popular;
	public String type;
	
	public int getPopular() {
		return popular;
	}
	public void setPopular(int popular) {
		this.popular = popular;
	}
	
	public int getAvg_walk() {
		return avg_walk;
	}
	public void setAvg_walk(int avg_walk) {
		this.avg_walk = avg_walk;
	}
	public String getGen() {
		return gen;
	}
	public void setGen(String gen) {
		this.gen = gen;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int compareTo(DataVO arg0) {
		if(arg0.gender != null) {
			// TODO Auto-generated method stub
			if(this.gender.compareTo(arg0.gender) > 0) {
				return 1;
			}
			else if(this.gender.compareTo(arg0.gender) < 0) {
				return -1;
			}else {
				return this.gen.compareTo(arg0.gen);
			}
		}else {
			return this.gen.compareTo(arg0.gen);
		}
	}
	

}
