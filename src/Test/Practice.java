package Test;

public class Practice {

	private String color;
	 public Practice() {
	 this("white");
	}
	 public Practice(String color) {
		 color = color;
	}
	public static void main(String[] args) {
	 Practice e = new Practice();
	 System.out.println("Color:" + e.color);
	 } 
}

