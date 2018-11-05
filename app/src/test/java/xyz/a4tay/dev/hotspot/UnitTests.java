package xyz.a4tay.dev.hotspot;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UnitTests {
    @InjectMocks
    @Spy
    private DotProtocols dotProtocols = new DotProtocols();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void testDotProtocolGetDots() {

        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        MockWebServer server = new MockWebServer();

        doNothing().when(dotProtocols).enableStrictMode();

        JSONObject body = new JSONObject();
        /*
         *   "colorCode": 5,
         *   "date_time": "2018-07-03 18:56:28.035623",
         *   "hash": "this is a test",
         *   "lat": 29.9544481,
         *   "lat_lng": "30.0_-90.1",
         *   "lng": -90.068798,
         *   "locationID": 987654321
         */

        try {
            body.put("colorCode", 5);
            body.put("date_time", "2018-07-03 18:56:28.035623");
            body.put("hash", "this is a test");
            body.put("lat", 123.11);
            body.put("lat_lng", "123.1_321.3");
            body.put("lng", 321.33);
            body.put("locationID", 987654321);

            server.enqueue(new MockResponse().setBody(body.toString()));
        }catch (JSONException e) {
            System.out.println("Bad json: " + e.getMessage());
        }
        // Start the server.
        try {
            server.start();
            // Ask the server for its URL. You'll need this to make HTTP requests.
        }
        catch (IOException e) {
            System.out.println("Server Startup Error: " + e.getMessage());
        }
        HttpUrl baseUrl = server.url("/v1");
        String url = baseUrl.toString();
        JSONObject jsonResult = dotProtocols.getDots(url, 123.11, 321.33);
        assertEquals(body.toString(), jsonResult.toString());
        RecordedRequest request1 = null;
        try {
            request1 = server.takeRequest();
        } catch (InterruptedException e) {
            System.out.println("Request Error: " + e.getMessage());
        }
        assertEquals("/v1?lat=123.11&lng=321.33", request1.getPath());
        try {
            // Shut down the server. Instances cannot be reused.
            server.shutdown();
        } catch (IOException e) {
            System.out.println("Server Shutdown Error: " + e.getMessage());
        }
    }

    @Test
    public void testEmojiHandler() {
        EmojiHandler fakeEmoji = new EmojiHandler();
        assertEquals("Tests validity of emoji handler", "{",
                fakeEmoji.getEmojiByUnicode(123));
    }
}