package dkeep.logic;

import java.util.Random;

public class DrunkGuard extends Guard {

	private char direction;

	public DrunkGuard(int i, int j) {
		super(i,j);
		this.direction = 'f';
	}

	@Override
	public void move(GameMap board) {

		char caracter = 'G';
		int num;
		Random oj = new Random();

		if(direction == 'f')
			num = oj.nextInt(2);
		else if(direction == 'b')
			num = oj.nextInt(2)+1;
		else 
			num = oj.nextInt(3);


		int index = indexGuard;
		switch(num) {
		// advance
		case 0:
			if(index < getPathSize()-1)
				index++;
			else
				index = 0;
			direction = 'f';
			break;
			// go back
		case 2:
			if(index > 0 && index < getPathSize()-1)
				index--;
			else if(index == 0)
				index = getPathSize()-1;
			else
				index = 0;
			direction = 'b';
			break;
			// sleeps	
		case 1:
			caracter = 'g';
			direction = 'g';
			break;
		}

		this.indexGuard = index;
		this.character = caracter;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);
	}
}
