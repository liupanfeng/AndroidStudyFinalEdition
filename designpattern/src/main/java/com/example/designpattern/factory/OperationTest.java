package com.example.designpattern.factory;

public class OperationTest {
    private  int numA=0;
    private  int numB=0;
    public  int getResult(String operation){
        switch (operation){
            case "+":
                return numA+numB;
            case "-":
                return numA-numB;
            case "*":
                return numA*numB;
            case "/":
                return numA/numB;
            default:
                return 0;
        }
    }


    public  void setNumA(int numA) {
        this.numA = numA;
    }

    public  void setNumB(int numB) {
        this.numB = numB;
    }
}
