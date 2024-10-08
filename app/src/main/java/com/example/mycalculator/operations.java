package com.example.mycalculator;

public class operations {

    public Object add(float a, float b)
    {
        return (Object) (a + b);
    }

    public Object multiply(float a, float b)
    {
        return (Object) (a * b);
    }

    public Object div(float a, float b)
    {
        return (Object) (a / b);
    }

    public Object minus(float a, float b)
    {
        return (Object) (a - b);
    }

    public Object percent(float a, float b)
    {
        return (Object) ((a / b) * 100);
    }
}
