package ru.kuzmich;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomStringBuilderTest {

    private CustomStringBuilder sb;

    @BeforeEach
    void setUp() {
        sb = new CustomStringBuilder();
    }

    @Test
    @DisplayName("Тест конструктора по умолчанию")
    void testDefaultConstructor() {
        assertEquals("", sb.toString());
        assertEquals(0, sb.length());
    }

    @Test
    @DisplayName("Тест конструктора со строкой")
    void testConstructorWithString() {
        CustomStringBuilder sbWithStr = new CustomStringBuilder("Hello");
        assertEquals("Hello", sbWithStr.toString());
        assertEquals(5, sbWithStr.length());
    }

    @Test
    @DisplayName("Тест метода append(String)")
    void testAppendString() {
        sb.append("Hello");
        assertEquals("Hello", sb.toString());

        sb.append(" World");
        assertEquals("Hello World", sb.toString());
    }

    @Test
    @DisplayName("Тест метода append(char)")
    void testAppendChar() {
        sb.append('H');
        sb.append('e');
        sb.append('l');
        sb.append('l');
        sb.append('o');

        assertEquals("Hello", sb.toString());
    }

    @Test
    @DisplayName("Тест метода append(int)")
    void testAppendInt() {
        sb.append("Number: ");
        sb.append(42);

        assertEquals("Number: 42", sb.toString());
    }

    @Test
    @DisplayName("Тест метода insert")
    void testInsert() {
        sb.append("Hello World");
        sb.insert(5, ",");

        assertEquals("Hello, World", sb.toString());
    }

    @Test
    @DisplayName("Тест метода delete")
    void testDelete() {
        sb.append("Hello World");
        sb.delete(5, 11);

        assertEquals("Hello", sb.toString());
    }

    @Test
    @DisplayName("Тест метода replace")
    void testReplace() {
        sb.append("Hello World");
        sb.replace(6, 11, "Java");

        assertEquals("Hello Java", sb.toString());
    }

    @Test
    @DisplayName("Тест метода reverse")
    void testReverse() {
        sb.append("Hello");
        sb.reverse();

        assertEquals("olleH", sb.toString());
    }

    @Test
    @DisplayName("Тест метода clear")
    void testClear() {
        sb.append("Hello World");
        sb.clear();

        assertEquals("", sb.toString());
        assertEquals(0, sb.length());
    }

    @Test
    @DisplayName("Тест отмены одной операции")
    void testUndoSingleOperation() {
        sb.append("Hello");
        assertTrue(sb.undo());
        assertEquals("", sb.toString());
    }

    @Test
    @DisplayName("Тест отмены нескольких операций")
    void testUndoMultipleOperations() {
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        sb.append("!");

        assertEquals("Hello World!", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello World", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello ", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello", sb.toString());

        assertTrue(sb.undo());
        assertEquals("", sb.toString());
    }

    @Test
    @DisplayName("Тест отмены с вставкой и удалением")
    void testUndoWithInsertAndDelete() {
        sb.append("Hello World");
        sb.insert(5, ",");
        sb.delete(7, 12);
        sb.replace(0, 5, "Hi");

        assertEquals("Hi, ", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello, ", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello, World", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello World", sb.toString());
    }

    @Test
    @DisplayName("Тест отмены при пустой истории")
    void testUndoWithEmptyHistory() {
        assertFalse(sb.undo());

        sb.append("Hello");
        sb.undo();
        assertFalse(sb.undo());
    }

    @Test
    @DisplayName("Тест цепочки методов")
    void testMethodChaining() {
        sb.append("Hello").append(" ").append("World").append("!");

        assertEquals("Hello World!", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello World", sb.toString());
    }

    @Test
    @DisplayName("Тест отмены после clear")
    void testUndoAfterClear() {
        sb.append("Hello World");
        sb.clear();

        assertEquals("", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello World", sb.toString());
    }

    @Test
    @DisplayName("Тест отмены операции reverse")
    void testUndoReverse() {
        sb.append("Hello");
        String beforeReverse = sb.toString();
        sb.reverse();

        assertNotEquals(beforeReverse, sb.toString());

        assertTrue(sb.undo());
        assertEquals(beforeReverse, sb.toString());
    }

    @Test
    @DisplayName("Тест многократных отмен и новых операций")
    void testUndoAndNewOperations() {
        sb.append("Hello");
        sb.append(" World");

        assertTrue(sb.undo());
        assertEquals("Hello", sb.toString());

        sb.append(" Java");
        assertEquals("Hello Java", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello", sb.toString());

        assertTrue(sb.undo());
        assertEquals("", sb.toString());
    }

    @Test
    @DisplayName("Тест граничных случаев insert")
    void testInsertBoundaryCases() {
        sb.append("Hello");

        sb.insert(0, "Start: ");
        assertEquals("Start: Hello", sb.toString());

        sb.insert(sb.length(), " End");
        assertEquals("Start: Hello End", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Start: Hello", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello", sb.toString());
    }

    @Test
    @DisplayName("Тест граничных случаев delete")
    void testDeleteBoundaryCases() {
        sb.append("Hello World");

        sb.delete(0, 6);
        assertEquals("World", sb.toString());

        sb.delete(0, sb.length());
        assertEquals("", sb.toString());

        assertTrue(sb.undo());
        assertEquals("World", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Hello World", sb.toString());
    }

    @Test
    @DisplayName("Тест с пустой строкой и null")
    void testEmptyAndNullOperations() {
        sb.append("");
        assertEquals("", sb.toString());

        sb.append(null);
        assertEquals("null", sb.toString());

        assertTrue(sb.undo());
        assertEquals("", sb.toString());
    }

    @Test
    @DisplayName("Тест длинной последовательности операций")
    void testLongSequence() {
        StringBuilder expected = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append("Step" + i + " ");
            expected.append("Step" + i + " ");
            assertEquals(expected.toString(), sb.toString());
        }

        for (int i = 0; i < 10; i++) {
            assertTrue(sb.undo());
            expected.delete(expected.length() - 6, expected.length());
            assertEquals(expected.toString(), sb.toString());
        }

        assertFalse(sb.undo());
    }

    @Test
    @DisplayName("Тест комбинированных операций разных типов")
    void testCombinedOperations() {
        sb.append("Start");
        sb.insert(0, "Before ");
        sb.append(" End");
        sb.replace(7, 12, "Middle");
        sb.delete(14, 18);
        sb.reverse();

        assertTrue(sb.undo());
        assertEquals("Before Middle ", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Before Middle End", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Before Start End", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Before Start", sb.toString());

        assertTrue(sb.undo());
        assertEquals("Start", sb.toString());

        assertTrue(sb.undo());
        assertEquals("", sb.toString());
    }
}
