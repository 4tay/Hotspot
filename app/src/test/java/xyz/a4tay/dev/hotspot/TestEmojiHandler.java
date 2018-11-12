package xyz.a4tay.dev.hotspot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEmojiHandler {
    @Test
    public void testEmojiHandler() {
        EmojiHandler fakeEmoji = new EmojiHandler();
        assertEquals("Tests validity of emoji handler", "{",
                fakeEmoji.getEmojiByUnicode(123));
    }
}
