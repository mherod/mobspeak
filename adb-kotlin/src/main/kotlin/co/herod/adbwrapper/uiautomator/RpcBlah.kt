@file:JvmName("RpcBlah")

package co.herod.adbwrapper.uiautomator

import com.github.kittinunf.fuel.Fuel

fun main(args: Array<String>) {

    Fuel.post("http://localhost:9008/jsonrpc/0")
            .body("{\n" +
                    "\"jsonrpc\": \"2.0\",\n" +
                    "\"id\": \"json\",\n" +
                    "\"method\": \"dumpWindowHierarchy\",\n" +
                    "\"params\": [ \"true\" ]\n" +
                    "}")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)
            }
}

/*
curl -X "POST" "http://localhost:9008/jsonrpc/0" \
                 -H "Content-Type: application/json; charset=utf-8" \
                 -d \
'{
"jsonrpc": "2.0",
"id": "json",
"method": "dumpWindowHierarchy",
"params": [ "true" ]
}'
 */

/*
public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();

String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(JSON, json);
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  Response response = client.newCall(request).execute();
  return response.body().string();
}
 */