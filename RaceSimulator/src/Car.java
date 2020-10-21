import java.util.Random;

import processing.core.PApplet;
public class Car{
	private String make, color;
	private int x = 30, y, year, speed, placing, minsPerCycle =2;
	private double distanceTravelled;
	Random r = new Random();
	
	public Car(int r) {
		color = randColor();
		make = randMake();
		year = randYear(r);
	}
	public void speedCycleRate() {
		minsPerCycle = 4;
	}
	public void slowCycleRate() {
		minsPerCycle = 1;
	}
	public void resetCycleRate() {
		minsPerCycle = 2;
	}
	public void setY(int yin) {
		y = yin;
	}
	public void setX(int xin) {
		x = xin;
	}
	public String randColor() {
		String[] colors = new String[] {"Red", "Blue","Orange","Purple","Green"};
		return colors[r.nextInt(colors.length)];
	}
	
	public int randYear(int i) { //50% must be 2010 or later
		if(i == 0) {
		return r.nextInt((2020-2010)+1)+2010; }
		return r.nextInt((2009-1999)+1)+1999;}
	
	public String randMake() {
		String[] makes = new  String[] {"Toyota","Ford","Audi","BMW","Acura"};
		return makes[r.nextInt(makes.length)];
	}
	public String getMake() {
		return make;
	}
	public int getYear() {
		return year;
	}
	public String getColor() {
		return color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int sp) {
		speed = sp;
	}
	public void resetDistance() {
		distanceTravelled = 0;
	}
	public double getDistance() {
		return distanceTravelled;
	}
	public void addDistance(double d) {
		distanceTravelled += d;
	}
	public int getPlacing() {
		return placing;
	}
	
	//gameplay methods
	public void accelerate() {
		if(year < 2010)
			speed += 15;
		else
			speed += 30;
	}
	public void brake() {
		if(year < 2010 && (speed - 5) >= 0)
			speed -= 5;
		else if (year >= 2010 && (speed - 10) >=0)
			speed -= 10;
	}
	
	public void stop() {
		speed = 0;
	}
	
	public void randMethod() {
		
		switch(r.nextInt(3)) {
		case 0: accelerate(); break;
		case 1: brake(); break;
		case 2: stop(); break;
		}
	}
	public int getX() {
		return x;
	}
	public void update() { //minsPerCycle by default = 2
		x = x + speed/(120/minsPerCycle); //mph/120 set as default (when sped up misPerCycle decreases)
	}	
}
