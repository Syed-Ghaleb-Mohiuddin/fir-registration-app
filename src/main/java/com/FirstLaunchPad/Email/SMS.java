package com.FirstLaunchPad.Email;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

final class SMS {
    private SMS() {
    }

    static void send(FirReport report, String number) throws IOException {
        String credential = required("FIR_SMS_CREDENTIAL");
        String sender = valueOrDefault("FIR_SMS_SENDER", "FSTSMS");
        String body = report.complainantName() + " registered FIR " + report.firNumber()
            + ". Check your email for the printable report.";

        String requestUrl = "https://www.fast2sms.com/dev/bulk?authorization="
            + encode(credential)
            + "&sender_id=" + encode(sender)
            + "&message=" + encode(body)
            + "&language=english&route=p&numbers=" + encode(number);

        HttpsURLConnection connection = (HttpsURLConnection) new URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("cache-control", "no-cache");

        int status = connection.getResponseCode();
        connection.disconnect();
        if (status < 200 || status >= 300) {
            throw new IOException("SMS service returned HTTP " + status + ".");
        }
    }

    private static String encode(String value) throws IOException {
        return URLEncoder.encode(value, "UTF-8");
    }

    private static String required(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(name + " is not configured.");
        }
        return value;
    }

    private static String valueOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
