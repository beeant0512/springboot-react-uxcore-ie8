package com.xstudio.example.websocket;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/25
 */
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
