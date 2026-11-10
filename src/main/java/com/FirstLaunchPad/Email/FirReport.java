package com.FirstLaunchPad.Email;

final class FirReport {
    static final String[] LABELS = {
        "District",
        "Police station",
        "Year",
        "FIR number",
        "Registration date",
        "Information type (written/oral)",
        "Complainant name",
        "Father's name",
        "Birth date",
        "Passport number",
        "Occupation",
        "Address",
        "Date of offence",
        "Address of offence",
        "Distance from police station"
    };

    private final String[] values;

    FirReport(String[] values) {
        if (values.length != LABELS.length) {
            throw new IllegalArgumentException("Expected " + LABELS.length + " report fields.");
        }
        this.values = values.clone();
    }

    String valueAt(int index) {
        return values[index];
    }

    String firNumber() {
        return values[3];
    }

    String complainantName() {
        return values[6];
    }
}
