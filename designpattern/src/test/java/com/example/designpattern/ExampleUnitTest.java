package com.example.designpattern;

import com.example.designpattern.factory.OperationTest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() {
        OperationTest operationTest=new OperationTest();
        operationTest.setNumA(10);
        operationTest.setNumB(15);
        int result = operationTest.getResult("+");
        System.out.println("result="+result);
    }


}