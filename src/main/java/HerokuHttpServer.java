import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HerokuHttpServer {
    private final int PORT;
    
    public HerokuHttpServer(int port) {
        super();
        this.PORT = port;
    }
    
    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }
}

class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "ACTIVE";
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
