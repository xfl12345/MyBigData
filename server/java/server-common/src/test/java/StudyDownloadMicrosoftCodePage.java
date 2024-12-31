import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public class StudyDownloadMicrosoftCodePage {
    public static void main(String[] args) throws IOException {
        LinkedHashMap<String, String> codePageMap = new LinkedHashMap<>();

        URL url = new URL("https://learn.microsoft.com/en-us/windows/win32/intl/code-page-identifiers");

        Document document = Jsoup.parse(url, 10000);
        System.out.println(document.toString());

        Elements elements = document.getElementsByTag("thead");
        for (Element element : elements) {
            Element headTr = element.child(0);
            Elements ths = headTr.getElementsByTag("th");
            if (ths.size() == 3 &&
                "Identifier".equals(ths.get(0).text()) &&
                ".NET Name".equals(ths.get(1).text()) &&
                "Additional information".equals(ths.get(2).text())
            ) {
                Element tableElement = element.parent();
                if (tableElement != null) {
                    Elements tableBodyElements = tableElement.getElementsByTag("tbody");
                    if (tableBodyElements.size() == 1) {
                        Element tableBody = tableBodyElements.get(0);

                        int no = 1;
                        for (Element bodyTr : tableBody.getElementsByTag("tr")) {
                            try {
                                Elements bodyTd = bodyTr.getElementsByTag("td");
                                String idText = bodyTd.get(0).text();
                                String charsetName = bodyTd.get(1).text();
                                if (!"".equals(charsetName)) {
                                    codePageMap.put(idText, charsetName);
                                }
                            } catch (Exception e) {
                                System.err.println("Reading No." + no + " failed. Error=" + e.getMessage());
                            }
                        }
                    }
                }
            }
        }


        System.out.print("\n".repeat(5));
        // String mapInJsonText = JSONObject.toJSONString(codePageMap, JSONWriter.Feature.PrettyFormat);
        // System.out.println(mapInJsonText);
        // JsonWriter jsonWriter = new JsonWriter(new PrintWriter(System.out));
        // jsonWriter.setIndent();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String mapInJsonText = gson.toJson(codePageMap);
        System.out.println(mapInJsonText);


        System.out.print("\n".repeat(5));
        // int indent = 2;
        // String indentText = " ".repeat(indent);
        // String kvTemplate = "%s%s: \"%s\"";


        File localMapFile = new File(System.getProperty("user.dir"), "src/main/resources/" + "cc/xfl12345/mybigdata/server/common/json/microsoft_windows_code_page.json");
        if (localMapFile.exists() && localMapFile.canWrite()) {
            System.out.println("Updating file=" + localMapFile.getPath());
            FileOutputStream fileOutputStream = new FileOutputStream(localMapFile);
            fileOutputStream.write(mapInJsonText.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            System.out.println("Done!");
        } else {
            System.err.println("Read-Only file=" + localMapFile.getPath());
        }
    }
}
