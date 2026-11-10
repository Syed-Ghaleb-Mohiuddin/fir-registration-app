# FIR Registration App

A console-based Java application for recording the fields of a police first information report (FIR). It collects the form in the terminal, creates a printable PDF, and can send the result by email and an SMS confirmation when delivery credentials are configured. The project took first position in its class.

```text
FIR registration
District: Example District
Police station: Central Station
FIR number: DEMO-001
...
PDF created: output\fir-registration-form.pdf
Email skipped in demo mode.
SMS skipped in demo mode.
```

## What it does

- Collects 15 report fields in the console, including the station, complainant and offence details.
- Creates an A4 PDF with the submitted fields and a signature line.
- Sends the PDF as an email attachment when mail settings and a recipient are provided.
- Sends an SMS confirmation through Fast2SMS when its delivery setting and a mobile number are provided.
- Includes a demo mode that generates a PDF from clearly fictional data without contacting external services.

## Stack

- Java 8
- Maven
- Apache PDFBox
- JavaMail
- Fast2SMS HTTPS API

## Run it locally

You need JDK 8 or newer and Maven.

```bash
mvn compile exec:java
```

To generate a PDF with fictional data and skip email and SMS:

```bash
mvn compile exec:java -Dexec.args="--demo"
```

Email and SMS are optional. Copy the placeholder names from `.env.example` into your shell environment before running the normal flow. For example, in PowerShell:

```powershell
$env:FIR_MAIL_USER = "YOUR_MAIL_ADDRESS"
$env:FIR_MAIL_CREDENTIAL = "YOUR_MAIL_APP_CREDENTIAL"
$env:FIR_SMS_CREDENTIAL = "YOUR_SMS_SERVICE_CREDENTIAL"
mvn compile exec:java
```

Generated reports are written to `output/` and are ignored by Git.

## Credits

Built by Ghaleb Mohiuddin Syed. Apache PDFBox and JavaMail remain under their own licences.

## Licence

The source code is available under the MIT Licence. See `LICENSE`.
