package com.FirstLaunchPad.Email;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public final class MainClass {
    private MainClass() {
    }

    public static void main(String[] args) throws Exception {
        boolean demo = args.length > 0 && "--demo".equals(args[0]);
        Scanner scanner = new Scanner(System.in);
        FirReport report = demo ? ConsoleForm.demo() : ConsoleForm.collect(scanner);

        Path outputDirectory = Paths.get("output");
        Files.createDirectories(outputDirectory);
        Path pdf = outputDirectory.resolve("fir-registration-form.pdf");
        GeneratePDF.write(report, pdf);
        System.out.println("PDF created: " + pdf.toAbsolutePath());

        if (demo) {
            System.out.println("Email skipped in demo mode.");
            System.out.println("SMS skipped in demo mode.");
            return;
        }

        System.out.print("Recipient email (leave blank to skip): ");
        String recipient = scanner.nextLine().trim();
        if (!recipient.isEmpty()) {
            try {
                Email.send(report, recipient, pdf);
                System.out.println("Email sent.");
            } catch (Exception exception) {
                System.out.println("Email not sent: " + exception.getMessage());
            }
        }

        System.out.print("Recipient mobile number (leave blank to skip): ");
        String number = scanner.nextLine().trim();
        if (!number.isEmpty()) {
            try {
                SMS.send(report, number);
                System.out.println("SMS sent.");
            } catch (Exception exception) {
                System.out.println("SMS not sent: " + exception.getMessage());
            }
        }
    }
}
