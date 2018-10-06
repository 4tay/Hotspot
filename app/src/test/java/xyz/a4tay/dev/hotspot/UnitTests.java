package xyz.a4tay.dev.hotspot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UnitTests {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


//    @Mock
//    Request mockRequest = mock(Request.class);
//    @Mock
//    Response mockResponse = mock(Response.class);
//    @Mock
//    OkHttpClient mockClient = mock(OkHttpClient.class);
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//
//
//    @Test
//    public void testDotProtocolGetDots() {
//        DotProtocols dotProtocols = new DotProtocols();
//        when(mockRequest.newBuilder().url("https://eg75gef3gi.execute-api.us-east-1.amazonaws.com/alpha?lat=" + 123.11 + "&lng=" + 321.33)
//                .get()
//                .addHeader("Content-Type", "application/json")
//                .build()).thenReturn(mockRequest);
//        try {
//            when(mockClient.newCall(mockRequest).execute()).thenReturn(mockResponse);
//        }
//        catch (IOException e) {
//            Log.e(this.getClass().getCanonicalName(), "error: " + e.toString());
//        }
//        JSONObject jsonResult = dotProtocols.getDots(123.11, 321.33);
//        System.out.println("Result: " + jsonResult.toString());
//        assertEquals("testDotProtocolGetDots", "", "");
//    }
    @Test
    public void testEmojiHandler() {
        EmojiHandler fakeEmoji = new EmojiHandler();
        assertEquals("Tests validity of emoji handler", "{",
                fakeEmoji.getEmojiByUnicode(123));
    }
}