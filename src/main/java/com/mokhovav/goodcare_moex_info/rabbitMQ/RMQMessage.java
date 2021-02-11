package com.mokhovav.goodcare_moex_info.rabbitMQ;

public class RMQMessage {
    private RMQMessageType type;
    private String[] args;

    public RMQMessage() {
    }

    public RMQMessage(RMQMessageType type, String[] args) {
        this.type = type;
        this.args = args;
    }

    public RMQMessageType getType() {
        return type;
    }

    public void setType(RMQMessageType type) {
        this.type = type;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
