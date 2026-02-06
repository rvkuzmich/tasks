package ru.kuzmich;

import java.util.Stack;

public class CustomStringBuilder {

    private StringBuilder stringBuilder;
    private final Stack<StringBuilderMemento> history;

    public CustomStringBuilder() {
        this.stringBuilder = new StringBuilder();
        this.history = new Stack<>();
    }

    public CustomStringBuilder(String str) {
        this.stringBuilder = new StringBuilder(str);
        this.history = new Stack<>();
    }

    private StringBuilderMemento createMemento() {
        return new StringBuilderMemento(stringBuilder.toString());
    }

    private void restoreMemento(StringBuilderMemento memento) {
        stringBuilder = new StringBuilder(memento.getState());
    }

    private void saveState() {
        history.push(createMemento());
    }

    public CustomStringBuilder append(String str) {
        saveState();
        stringBuilder.append(str);
        return this;
    }

    public CustomStringBuilder append(char c) {
        saveState();
        stringBuilder.append(c);
        return this;
    }

    public CustomStringBuilder append(int num) {
        saveState();
        stringBuilder.append(num);
        return this;
    }

    public CustomStringBuilder insert(int index, String str) {
        saveState();
        stringBuilder.insert(index, str);
        return this;
    }

    public CustomStringBuilder delete(int start, int end) {
        saveState();
        stringBuilder.delete(start, end);
        return this;
    }

    public CustomStringBuilder replace(int start, int end, String str) {
        saveState();
        stringBuilder.replace(start, end, str);
        return this;
    }

    public CustomStringBuilder reverse() {
        saveState();
        stringBuilder.reverse();
        return this;
    }

    public boolean undo() {
        if (!history.isEmpty()) {
            restoreMemento(history.pop());
            return true;
        }
        return false;
    }

    public String toString() {
        return stringBuilder.toString();
    }

    public int length() {
        return stringBuilder.length();
    }

    public CustomStringBuilder clear() {
        saveState();
        stringBuilder.setLength(0);
        return this;
    }
}
