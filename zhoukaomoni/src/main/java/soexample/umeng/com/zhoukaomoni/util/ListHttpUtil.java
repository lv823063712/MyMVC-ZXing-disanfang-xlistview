package soexample.umeng.com.zhoukaomoni.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListHttpUtil {

    public static String getStr(String mUrl) {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();
            String jsons = getStream(stream);
            return jsons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer sb = new StringBuffer();
        String con;
        while ((con = reader.readLine()) != null) {
            sb.append(con);
        }
        reader.close();
        return sb.toString();
    }
}
