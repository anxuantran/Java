import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;
//EACH DRAW CYCLE IS ONE MINUTE
//UPON SPEEDING UP, EACH CYCLE IS TWO MINUTES
public class Main extends PApplet{
	Random r = new Random();
	private boolean hasWon = false;
	private int ncol = 1468/2, nrow = 870/2;
	private int carW = 194/3, carH = 114/3, startX = 30, currentFrame, placing;
	PImage redCar, blueCar, purpleCar, greenCar, orangeCar;
	PImage redBoost, blueBoost, purpleBoost, greenBoost, orangeBoost;
	PImage screen1, screen2, finishScreen, crown;
	PImage fwd,bwd,reset,fwdClicked,bwdClicked;
	ArrayList<Car> cars = new ArrayList<Car>();
	ArrayList<Car> finished = new ArrayList<Car>();
	ArrayList<PImage> carImg = new ArrayList<PImage>();
	PImage[] buttons = new PImage[5];
	public Main() {
	}
	public void settings() {
		size(ncol,nrow+100);
	}
	public void setup() {
		frameRate(30);
		textSize(20);
		fill(255,140,0);
		carImg.add(redCar = loadImage("data/red.png"));
		carImg.add(blueCar = loadImage("data/blue.png"));
		carImg.add(greenCar = loadImage("data/green.png"));
		carImg.add(purpleCar = loadImage("data/purple.png"));
		carImg.add(orangeCar = loadImage("data/orange.png"));
		
		carImg.add(redBoost = loadImage("data/redBoost.png"));
		carImg.add(blueBoost = loadImage("data/blueBoost.png"));
		carImg.add(greenBoost = loadImage("data/greenBoost.png"));
		carImg.add(purpleBoost = loadImage("data/purpleBoost.png"));
		carImg.add(orangeBoost = loadImage("data/orangeBoost.png"));
		
		for(PImage img: carImg) {
			img.resize(carW, carH);
		}
		
		screen1 = loadImage("data/screen1.png");
		screen1.resize(ncol, nrow);
		screen2 = loadImage("data/screen2.png");
		screen2.resize(ncol,nrow);
		finishScreen = loadImage("data/finishScreen.png");
		finishScreen.resize(ncol, nrow);
		crown = loadImage("data/crown.png");
		crown.resize(50, 50);
		
		fwd = loadImage("data/fwd.png"); buttons[1] = fwd;	
		bwd = loadImage("data/bwd.png"); buttons[0] = bwd;
		reset = loadImage("data/reset.png"); buttons[2] = reset;
		fwdClicked = loadImage("data/fwdClicked.png"); buttons[3] = fwdClicked; //LAST TWO ALTERNATE
		bwdClicked = loadImage("data/bwdClicked.png"); buttons[4] = bwdClicked;
		
		for(PImage but: buttons) {
			but.resize(100,100);
		}
		for(int i = 0; i < 10; i++) {
			int randYear = r.nextInt(2);
			cars.add(new Car(randYear));
		}
	}
	
	public void draw() {
		if(frameCount % 3 == 0 && !hasWon) {
			image(screen2,0,0);
		}
		else if (!hasWon){
			image(screen1,0,0);
		}
		else {
			image(finishScreen,0,0);
		}
		
		
		//code to press buttons (AND HOLD)
		if(mouseX > 2*(ncol/7) && mouseX < 2*(ncol/7)+100 && mouseY > nrow && mouseY < nrow+100 && mousePressed) {
			//if hovering over button 1 (fwd) and mouse is pressed
			text("Speed X 0.5",30,30);
			image(buttons[4],2*(ncol/7),nrow);
			for(Car c : cars) {
				c.slowCycleRate();
			}
			
		}
		else if(mouseX > 2*(ncol/7)+(100) && mouseX < 2*(ncol/7)+200 && mouseY > nrow && mouseY < nrow+100 && mousePressed) {
			//if hovering over button 2 (bwd) and mouse is pressed
			text("Speed X 2",30,30);
			image(buttons[3],2*(ncol/7)+(100*1),nrow);
			for(Car c: cars) {
				c.speedCycleRate();
			}
		}
		else if(mouseX > 2*(ncol/7)+(200) && mouseX < 2*(ncol/7)+300 && mouseY > nrow && mouseY < nrow+100 && mousePressed) {
			//if hovering over button 3 (reset) and mouse pressed
			//reset all screen and car positionns
			//reset arrays
			hasWon = false;
			for(int i = 0; i < finished.size();i++) { //loop runs for as many cars has won
				cars.add(finished.get(i)); //place all the finished cars back into original arrlist
			} 
			finished.clear(); //empty array of finished cars
			
			for(Car c: cars) {
				c.setX(startX); //30 is the starting point
			}
		}
		else { //if not buttons pressed: display normal buttons
			for(Car c: cars) {
				c.resetCycleRate();
			}
			for(int i = 0; i < 3; i++) {
				image(buttons[i],2*(ncol/7)+(100*i),nrow); //to center the 3 buttons
			}
		}
		
		if(mousePressed) {
			for(Car c : cars) {
				if(mouseX > c.getX() && mouseX < c.getX()+carW && mouseY > carH*(cars.indexOf(c)+1)&& mouseY < carH*(cars.indexOf(c)+2)) {
					//if mouse is clicked over a certain car
					c.setSpeed(50);
					switch(c.getColor()) {
					case "Red" : image(redBoost,c.getX(), carH*(cars.indexOf(c)+1)); break;
					case "Blue" : image(blueBoost, c.getX(), carH*(cars.indexOf(c)+1)); break;
					case "Purple" : image(purpleBoost, c.getX(), carH*(cars.indexOf(c)+1)); break;
					case "Orange" : image(orangeBoost, c.getX(), carH*(cars.indexOf(c)+1)); break;
					case "Green" : image(greenBoost, c.getX(), carH*(cars.indexOf(c)+1)); break;
					}
				}
			}
		}
		
		for(int i = 0; i < cars.size(); i++) { //display cars in game
			if(cars.get(i).getX() >= 130) { //130 because each pixel is 1 mile &^ starting x = 30
				placing++;
				finished.add(cars.get(i));
				cars.remove(cars.get(i));
				hasWon = true;
				break;
			}
			switch(cars.get(i).getColor()) {
			case "Red" : image(redCar,cars.get(i).getX(), carH*(i+1)); break;
			case "Blue" : image(blueCar, cars.get(i).getX(), carH*(i+1)); break;
			case "Purple" : image(purpleCar, cars.get(i).getX(), carH*(i+1)); break;
			case "Orange" : image(orangeCar, cars.get(i).getX(), carH*(i+1)); break;
			case "Green" : image(greenCar, cars.get(i).getX(), carH*(i+1)); break;
			}
			cars.get(i).randMethod();
			cars.get(i).update();
		}
		
		for(int i = 0; i < finished.size(); i++) { //display finished cars
			String info = finished.get(i).getMake() +" " +finished.get(i).getYear();
			switch(finished.get(i).getColor()) {
			case "Red" : image(redCar,366, carH*(i+1)); break;
			case "Blue" : image(blueCar, 366, carH*(i+1)); break;
			case "Purple" : image(purpleCar, 366, carH*(i+1)); break;
			case "Orange" : image(orangeCar, 366, carH*(i+1)); break;
			case "Green" : image(greenCar, 366, carH*(i+1)); break;
			}
			if(i == 0) {
				image(crown,366+carW,carH*(i+1)-30);
				text("Winner: " + "("+info+")",366+carW,carH*(i+1)+25);
			}
			else {
			text("Placing # : " + (i+1) +" " + "(" + info +")", 366+carW, carH*(i+1)+25);}
		}
	}
	
}
