package Models;

import java.util.Random;

public class BaseMap {

    private int myMap[][];
    private int fleetSize;

    public BaseMap(int fleetSize){

        this.fleetSize = fleetSize;
        myMap = new int[10][10];

        for (int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                myMap[i][j] = 0;
                myMap[i][j] = 0;
            }
        }

        genereateFleetPosition();
    }

    public void genereateFleetPosition(){

        Random rnd = new Random();
        int row;
        int column;
        for(int i = 0; i < this.fleetSize; i++){
            do{
                row = rnd.nextInt(10);
                column = rnd.nextInt(10);
            }while(myMap[row][column] != 0);
            myMap[row][column] = 1;
        }
    }

    public int getFieldValue(int row, int column){
        return myMap[row][column];
    }

    public int getFleetSize() {
        return fleetSize;
    }

    public void setFieldValue(int row, int colmun, int value){
        myMap[row][colmun] = value; // kilott mezo
    }

    public void setFleetSize(int value){
        this.fleetSize = value;
    }
}
