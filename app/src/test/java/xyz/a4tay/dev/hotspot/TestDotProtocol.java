package xyz.a4tay.dev.hotspot;

import okhttp3.HttpUrl;
import okhttp3.Response;
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
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

public class TestDotProtocol {

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
    public void testDotProtocolPostDot() {

        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        MockWebServer server = new MockWebServer();

        JSONObject expectedBody = new JSONObject();
        try {
            expectedBody.put("colorCode", 5);
            expectedBody.put("hash", "this is a test");
            expectedBody.put("lat", 123.11);
            expectedBody.put("lng", 321.33);
            expectedBody.put("locationID", 987654321);

            server.enqueue(new MockResponse().setBody(expectedBody.toString()));
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
        Response response = dotProtocols.putDot(url, 123.11, 321.33, 5, "987654321");
        RecordedRequest request = null;
        try {
            request = server.takeRequest();
            assertEquals("PUT /v1 HTTP/1.1", request.getRequestLine());
            assertEquals("application/json; charset=utf-8", request.getHeader("Content-Type"));
            JSONObject requestBody = new JSONObject(request.getBody().readUtf8());
            JSONObject requestBodyParsed = requestBody.optJSONObject("body");

            Iterator<String> keys = expectedBody.keys();

            //Here I test each value in the expected request is in the actual request.
            while (keys.hasNext()) {
                String key = keys.next();
                assertEquals(expectedBody.optString(key), requestBodyParsed.optString(key));
            }

        } catch (InterruptedException e) {
            System.out.println("Request Error: " + e.getMessage());
        }
        catch (JSONException e) {
            System.out.println("Json Error: " + e.getMessage());
        }
        try {
            // Shut down the server. Instances cannot be reused.
            server.shutdown();
        } catch (IOException e) {
            System.out.println("Server Shutdown Error: " + e.getMessage());
        }
    }

    @Test
    public void testDotProtocolPostDotWithHash() {

        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        MockWebServer server = new MockWebServer();

        JSONObject expectedBody = new JSONObject();

        try {
            expectedBody.put("colorCode", 5);
            expectedBody.put("hash", "TestHash");
            expectedBody.put("lat", 123.11);
            expectedBody.put("lng", 321.33);
            expectedBody.put("locationID", 987654321);

            server.enqueue(new MockResponse().setBody(expectedBody.toString()));
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
        Response response = dotProtocols.putDot(url, 123.11, 321.33, 5, "987654321", "TestHash");
        RecordedRequest request = null;
        try {
            request = server.takeRequest();
            assertEquals("PUT /v1 HTTP/1.1", request.getRequestLine());
            assertEquals("application/json; charset=utf-8", request.getHeader("Content-Type"));
            JSONObject requestBody = new JSONObject(request.getBody().readUtf8());
            JSONObject requestBodyParsed = requestBody.optJSONObject("body");

            Iterator<String> keys = expectedBody.keys();

            //Here I test each value in the expected request is in the actual request.
            while (keys.hasNext()) {
                String key = keys.next();
                assertEquals(expectedBody.optString(key), requestBodyParsed.optString(key));
            }

        } catch (InterruptedException e) {
            System.out.println("Request Error: " + e.getMessage());
        }
        catch (JSONException e) {
            System.out.println("Json Error: " + e.getMessage());
        }
        try {
            // Shut down the server. Instances cannot be reused.
            server.shutdown();
        } catch (IOException e) {
            System.out.println("Server Shutdown Error: " + e.getMessage());
        }
    }
}
