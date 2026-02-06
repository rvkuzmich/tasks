package ru.kuzmich;

public class StringBuilderMemento {

    private final String state;

    public StringBuilderMemento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
