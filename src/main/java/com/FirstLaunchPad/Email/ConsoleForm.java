package com.FirstLaunchPad.Email;

import java.util.Scanner;

final class ConsoleForm {
    private ConsoleForm() {
    }

    static FirReport collect(Scanner scanner) {
        String[] values = new String[FirReport.LABELS.length];
        System.out.println("FIR registration");
        for (int i = 0; i < FirReport.LABELS.length; i++) {
            System.out.print(FirReport.LABELS[i] + ": ");
            values[i] = scanner.nextLine().trim();
        }
        return new FirReport(values);
    }

    static FirReport demo() {
        return new FirReport(new String[] {
            "Example District",
            "Central Station",
            "2021",
            "DEMO-001",
            "2021-10-15",
            "Written",
            "Alex Example",
            "Sam Example",
            "2000-01-01",
            "SAMPLE000",
            "Designer",
            "100 Example Road",
            "2021-10-14",
            "Example Market",
            "2 km"
        });
    }
}
