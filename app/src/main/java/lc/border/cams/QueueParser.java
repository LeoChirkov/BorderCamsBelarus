package lc.border.cams;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QueueParser {

    private static String URL_BY = "http://gpk.gov.by/maps/ochered.php";
    private static String URL_LT = "http://www.pkpd.lt/lt/border";
    private static ArrayList<String[]> borders;

    private String[] getQueuesBY(String filePath) {
        Document doc;
        Elements links;
        ArrayList<String> queue = new ArrayList<String>();

        String[] kppArray = { "Котловка: ", "Каменный Лог: ", "Бенякони: ", "Привалка: " };
        for (String kpp : kppArray) {
            Log.i("КПП", kpp);
            try {
                File input = new File(filePath);
                doc = Jsoup.parse(input, "UTF-8");
                links = doc.getElementsByAttributeValue("title", kpp);

                queue.add(links.get(0).text());
                queue.add(links.get(1).text());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] queueBY = queue.toArray(new String[0]);
        Log.i("QUEUE BY SIZE", String.valueOf(queueBY.length));
        return queueBY;
    }

    private String[] getQueuesLT(String filePath) {
        Document doc = null;
        try {
            File input = new File(filePath);
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> kpp = new ArrayList<String>();
        if(doc.hasText()) {
            Element table = doc.select("table").get(0); // select the first table.
            Elements rows = table.select("tr");

            // we need only rows for BY which start from 10th
            for (int i = 10; i < rows.size(); i++) {

                Element row = rows.get(i);
                Elements cols = row.select("td");

                for (Element col : cols) {
                    if (col.text().equals("Lengvieji")) {
                        kpp.add(col.nextElementSibling().text()); // add value of the next col after "Lengvieji"
                    } else if (col.text().equals("Krovininiai")) {
                        kpp.add(col.nextElementSibling().text());
                    }
                }
            }
            String[] queueLT = kpp.toArray(new String[0]);
            return queueLT;
        } else {

            return null;
        }
    }

    public ArrayList<String[]> createArrayList(String filePath) {
        String[] lavoriskes = {"LT Lavoriškės", "http://www.pkpd.lt/project/modules/border/assets/image.php?video=lavoriskes",
                "0", "0", "time"};
        String[] kotlovka = {"BY Котловка", "http://cam.gpk.gov.by/kotlovka-gtk.jpg",
                "0", "0", "time"};

        String[] medininkai = {"LT Medininkai", "http://www.pkpd.lt/project/modules/border/assets/image.php?video=medininkai",
                "0", "0", "time"};
        String[] kamLog = {"BY Каменный Лог", "http://cam.gpk.gov.by/kamlog1.jpg",
                "0", "0", "time"};

        String[] salcininkai = {"LT Šalčininkai", "http://www.pkpd.lt/project/modules/border/assets/image.php?video=salcininkai",
                "0", "0", "time"};
        String[] beniakoni = {"BY Бенякони", "http://cam.gpk.gov.by/beniakoni1.jpg",
                "0", "0", "time"};

        String[] raigardas = {"LT Raigardas", "http://www.pkpd.lt/project/modules/border/assets/image.php?video=raigardas",
                "0", "0", "time"};
        String[] privalka = {"BY Привалка", "http://cam.gpk.gov.by/privalka-gtk.jpg",
                "0", "0", "time"};

        String [] queuesLT = getQueuesLT(filePath+"/pkpd.lt");
        String [] queuesBY = getQueuesBY(filePath+"/gpk.by");

        if (queuesLT != null) {
            lavoriskes[2] = queuesLT[0];
            lavoriskes[3] = queuesLT[1];
            medininkai[2] = queuesLT[2];
            medininkai[3] = queuesLT[3];
            salcininkai[2] = queuesLT[4];
            salcininkai[3] = queuesLT[5];
            raigardas[2] = queuesLT[6];
            raigardas[3] = queuesLT[7];
        }

        if(queuesBY.length == 8) {
            kotlovka[2] = queuesBY[0];
            kotlovka[3] = queuesBY[1];
            kamLog[2] = queuesBY[2];
            kamLog[3] = queuesBY[3];
            beniakoni[2] = queuesBY[4];
            beniakoni[3] = queuesBY[5];
            privalka[2] = queuesBY[6];
            privalka[3] = queuesBY[7];
        }

        borders = new ArrayList<String[]>();
        borders.add(lavoriskes);
        borders.add(kotlovka);
        borders.add(medininkai);
        borders.add(kamLog);
        borders.add(salcininkai);
        borders.add(beniakoni);
        borders.add(raigardas);
        borders.add(privalka);

        return borders;
    }
}
