package com.FirstLaunchPad.Email;

final class EmailTemplate {
    private EmailTemplate() {
    }

    static String render(FirReport report) {
        return "<html><body style=\"font-family:Arial,sans-serif\">"
            + "<h1>FIR registration confirmation</h1>"
            + "<p>The report for " + escape(report.complainantName()) + " has been registered.</p>"
            + "<p>Reference: <strong>" + escape(report.firNumber()) + "</strong></p>"
            + "<p>The printable report is attached.</p>"
            + "</body></html>";
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
}
