package at.technikum.mse.st.poc;

import at.technikum.mse.st.Label;

public class TestClass {
    @Label("TestStringLabel")
    private String testString;
    private char testChar;
    private short testShort;
    private int testInt;
    private long testLong;
    private float testFloat;
    private double testDouble;
    private boolean testBoolean;

    public TestClass(String testString, char testChar, short testShort, int testInt, long testLong, float testFloat, double testDouble, boolean testBoolean) {
        this.testString = testString;
        this.testChar = testChar;
        this.testShort = testShort;
        this.testInt = testInt;
        this.testLong = testLong;
        this.testFloat = testFloat;
        this.testDouble = testDouble;
        this.testBoolean = testBoolean;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public char getTestChar() {
        return testChar;
    }

    public void setTestChar(char testChar) {
        this.testChar = testChar;
    }

    public short getTestShort() {
        return testShort;
    }

    public void setTestShort(short testShort) {
        this.testShort = testShort;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public long getTestLong() {
        return testLong;
    }

    public void setTestLong(long testLong) {
        this.testLong = testLong;
    }

    public float getTestFloat() {
        return testFloat;
    }

    public void setTestFloat(float testFloat) {
        this.testFloat = testFloat;
    }

    public double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    public boolean isTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }
}