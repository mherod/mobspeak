@file:JvmName("RpcBlah")

package co.herod.adbwrapper.uiautomator

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.rx.rx_string
import io.reactivex.Single

class RpcBlah {
    companion object {
        private const val RPC_URL = "http://localhost:9008/jsonrpc/0"

        @JvmStatic
        fun main(args: Array<String>) {
            tryRpcUi()
        }

        fun tryRpcUi(): Single<String> {
            return Fuel.post(RPC_URL)
                    .body("{ " +
                            "\"jsonrpc\": \"2.0\", " +
                            "\"id\": \"json\", " +
                            "\"method\": \"dumpWindowHierarchy\", " +
                            "\"params\": [ \"true\" ]" +
                            " }")
                    .rx_string()
                    .map {
                        it.get()
                                .substringBeforeLast('>')
                                .substringAfter('<')
                                .let { "<$it>" }
                                .replace("\\r", "\r")
                                .replace("\\n", "\n")
                                .replace("\\\"", "\"")
                    }
                    .toObservable()
                    .firstOrError()
        }
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