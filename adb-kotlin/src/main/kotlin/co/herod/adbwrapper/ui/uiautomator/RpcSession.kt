@file:JvmName("RpcSession")

package co.herod.adbwrapper.ui.uiautomator

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.rx.rx_responseString
import io.reactivex.Single

class RpcSession @JvmOverloads constructor(
        private val port: Int = 9008,
        private val hostName: String = "localhost"
) {
    private val rpcUrl: String by lazy {
        "http://$hostName:$port/jsonrpc/0"
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Companion.dumpWindowHierarchy(RpcSession(9008))
        }

        private fun makeRpcRequestBody(method: String) =
                "{ \"jsonrpc\": \"2.0\", \"id\": \"json\", \"method\": \"$method\" }"

        fun ping(rpcSession: RpcSession): Single<String> = Fuel.post(rpcSession.rpcUrl)
                .body(makeRpcRequestBody("ping"))
                .rx_responseString()
                .map { it.second.get() }

        fun dumpWindowHierarchy(rpcSession: RpcSession): Single<String> = Fuel.post(rpcSession.rpcUrl)
                .body("{ " +
                        "\"jsonrpc\": \"2.0\", " +
                        "\"id\": \"json\", " +
                        "\"method\": \"dumpWindowHierarchy\", " +
                        "\"params\": [ \"true\" ]" +
                        " }")
                .rx_responseString()
                .map {
                    it.second.get()
                            .substringBeforeLast('>')
                            .substringAfter('<')
                            .let { "<$it>" }
                            .replace("\\r", "\r")
                            .replace("\\n", "\n")
                            .replace("\\\"", "\"")
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