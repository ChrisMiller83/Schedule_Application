package model;

/**
 * MessageLambdaInterface -- provides an interface that displays a message when displayMessage is called in a lambda expression
 */
public interface MessageLambdaInterface {
    /**
     * displayMessage -- is a void abstract message called in a lambda expression
     * @param s -- string parameter passed into the lambda expression
     */
    void displayMessage(String s);
}
