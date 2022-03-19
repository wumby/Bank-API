package com.JackZiegler.Stack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StackTest {

    private Stack<Integer> stack;


    @BeforeEach
    void setup(){
        stack = new Stack<>();
    }


    @Test
    void popToTopOfStack(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        assertEquals(5,stack.size());

    }


    @Test
    void popFromTopOfStack(){

    }

    @AfterEach
    void cleanup(){
        stack = null;
    }

}